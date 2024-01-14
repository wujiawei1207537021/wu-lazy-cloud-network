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
                    @change="getSchemaList"
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
                    clearable
                    v-model="schemaName"
                    placeholder="数据库名"
                    @change="getTableList"
                    filterable
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="数据库表名" prop="sortingRules">
                <el-select
                    clearable
                    v-model="tableName"
                    placeholder="数据库表名"
                    @change="getDatabaseTablesColumnList"
                    filterable
                >
                    <el-option
                        v-for="item in tableList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="字段名称" prop="columnName">
                <el-select
                    v-model="columnName"
                    placeholder="字段名称"
                    filterable
                >
                    <el-option
                        v-for="item in tableColumnList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
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
    schemaName: [
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
    tableName: [
        {
            required: true,
            message: "请输入字符集",
            trigger: "blur",
        },
    ],
    columnName: [
        {
            required: false,
            message: "请输入排序规则",
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
const { title } = toRefs(props);
const closeDialog = () => {
    emit("closeDialog", false);
};
const formRef = ref(null);
const tableColumnList = ref(null);

const form = reactive({
    schemaName: "",
    instanceId: "",
    tableName: "",
    columnName: "",
});
const { schemaName, instanceId, tableName, columnName } = toRefs(form);
const serverList = ref(null);

const schemaList = ref(null);
const tableList = ref(null);

/**
 * 获取当前表对应的字段
 * @param tableIds
 * @returns {Promise<void>}
 */
const getDatabaseTablesColumnList = async () => {
    // 获取数据
    let res = await VE_API.system.findDatabaseTableColumnList({
        instanceId: form.instanceId,
        schemaName: form.schemaName,
        tableName: form.tableName,
    });
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.columnName;
            item.value = item.columnName;
        });
        tableColumnList.value = data ? data : [];
    }
};
/**
 * 获取 数据库表
 * @param instanceId 数据库实例ID
 * @param schemaName 数据库名称
 * @returns {Promise<void>}
 */
const getTableList = async () => {
    let res;
    if (instanceId.value == null) {
        return;
    } else {
        res = await VE_API.system.tableList({
            instanceId: form.instanceId,
            schemaName: form.schemaName,
        });
    }
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.tableName;
            item.value = item.tableName;
        });
        tableList.value = data;
    }
};
/**
 * 选择数据库实例
 * @param serverInstanceId
 * @returns {Promise<void>}
 */
const getSchemaList = async () => {
    // 查询数据库
    let res = await VE_API.system.schemaList({
        instanceId: form.instanceId,
    });
    const { code } = res;
    if (code === 0) {
        res.data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaList.value = res.data ? res.data : [];
    }
};

onMounted(async () => {
    // 查询数据库实例
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
    });
});
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res = await VE_API.system.schemaDeriveView(form);
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
