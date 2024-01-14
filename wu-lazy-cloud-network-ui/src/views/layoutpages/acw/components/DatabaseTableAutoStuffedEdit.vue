<template>
    <el-dialog
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        @close="closeDialog()"
    >
        <!-- <span>{{ rowData }}</span> -->
        <!-- 表单 -->
        <el-form
            :model="form"
            ref="formRef"
            :rules="rules"
            label-width="80px"
            :inline="false"
        >
            <el-form-item label="数据库实例" prop="instanceId">
                <el-select
                    v-model="instanceId"
                    placeholder="数据库实例"
                    filterable
                    @change="(val) => getSchemaList(val)"
                >
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="数据库库名" prop="schemaName">
                <el-select
                    v-model="schemaName"
                    filterable
                    placeholder="数据库实例"
                    @change="(val) => getTableList(val)"
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="表名称" prop="tableNameList">
                <el-select
                    v-model="tableNameList"
                    placeholder="表名称"
                    @change="chooseTable"
                    filterable
                    multiple
                >
                    <el-checkbox
                        v-model="checked"
                        :indeterminate="
                            tableNameList.length !== tableList.length
                        "
                        @change="selectAll()"
                        style="margin-left: 20px"
                    >
                        全选
                    </el-checkbox>
                    <el-option
                        v-for="item in tableList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="自动填充数量" prop="autoStuffedNum">
                <el-input
                    v-model="autoStuffedNum"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
        </el-form>

        <template v-slot:footer>
            <span>
                <el-button @click="closeDialog()">取消</el-button>
                <el-button type="primary" @click="onSubmit()">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import {
    reactive,
    toRefs,
    onMounted,
    ref,
    defineProps,
    defineEmits,
} from "vue";
const rules = {
    databaseSchemaId: [
        {
            required: true,
            message: "请输入数据库库名",
            trigger: "blur",
        },
    ],
    instanceId: [
        {
            required: true,
            message: "请输入数据库服务器",
            trigger: "blur",
        },
    ],
};
const props = defineProps({
    showDialog: {
        type: Boolean,
        default: true,
    },
    title: {
        type: String,
        default: "添加",
    },
    rowData: {
        type: Object,
        default: null,
    },
});
const emit = defineEmits(["closeDialog"]);
const { title, rowData } = toRefs(props);
const closeDialog = () => {
    emit("closeDialog", false);
};
const formRef = ref(null);
const form = reactive({
    schemaName: "",
    databaseSchemaId: "",
    instanceId: "",
    instanceName: "",
    autoStuffedNum: "",
    status: false,
    tableNameList: [],
    id: "",
});
const {
    id,
    schemaName,
    databaseSchemaId,
    instanceId,
    instanceName,
    autoStuffedNum,
    status,
    tableNameList,
} = toRefs(form);
const serverList = ref(null);
const schemaList = ref(null);
const tableList = ref([]);
const checked = ref(false);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((id.value = rowData.value.id),
    (schemaName.value = rowData.value.schemaName),
    (databaseSchemaId.value = rowData.value.databaseSchemaId),
    (instanceId.value = rowData.value.instanceId),
    (instanceName.value = rowData.value.instanceName),
    (autoStuffedNum.value = rowData.value.autoStuffedNum),
    (status.value = rowData.value.status),
    (tableNameList.value = [rowData.value.tableName]));

onMounted(async () => {
    getInstanceList();
    // getSchemaList();
    // getTableList();
});
const getInstanceList = () => {
    // 查询数据库实例
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
    });
};
const getSchemaList = (databaseInstance = null) => {
    console.log(databaseInstance);
    // 查询数据schema信息
    VE_API.system
        .schemaList({
            instanceId: instanceId.value,
        })
        .then((res) => {
            res.data.map((item) => {
                item.label = item.schemaName;
                item.value = item.schemaName;
            });
            schemaList.value = res.data ? res.data : [];
        });
};

const getTableList = () => {
    // 查询数据表信息
    VE_API.system
        .tableList({
            instanceId: instanceId.value,
            schemaName: schemaName.value,
        })
        .then((res) => {
            if (res.data) {
                res.data.map((item) => {
                    item.label = item.tableName;
                    item.value = item.tableName;
                });
                tableList.value = res.data ? res.data : [];
            }
        });
};
/**
 * 选择表事件
 */
const chooseTable = () => {
    let table = tableList.value.find(
        (item) => item.schemaNameId === databaseSchemaId.value,
    );
    console.log(table);
    if (table) {
        instanceName.value = table.instanceName;
        schemaName.value = table.schemaName;
    }
};
const selectAll = () => {
    console.log(checked.value);
    if (checked.value) {
        checked.value = false;
        tableNameList.value = [];
    } else {
        checked.value = true;
        tableNameList.value = tableList.value.map((d) => d.tableName);
    }
};
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value === "添加自动填充记录") {
                console.log(form);
                res = await VE_API.system.tableAutoStuffedRecordBatchAdd(form);
            } else {
                res = await VE_API.system.tableAutoStuffedRecordEdit({
                    id: rowData.value.id,
                    ...form,
                });
            }
            const { code } = res;
            if (code === 0) {
                closeDialog();
            }
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};
</script>

<style lang="scss" scoped></style>
