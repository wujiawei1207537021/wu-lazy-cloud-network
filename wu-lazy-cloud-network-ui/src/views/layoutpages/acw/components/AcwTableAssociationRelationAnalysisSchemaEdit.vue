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
                    v-model="schema"
                    filterable
                    placeholder="数据库实例"
                    @change="findInstanceSchemaColumnList"
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="忽略解析字段" prop="ignoreFieldList">
                <el-select
                    v-model="ignoreFieldList"
                    filterable
                    multiple
                    placeholder="忽略解析字段"
                >
                    <el-option
                        v-for="item in tableColumnList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                        <span style="float: left"
                            >{{ item.label }}【{{ item.columnComment }}】</span
                        >
                        <span
                            style="
                                float: right;
                                color: var(--el-text-color-secondary);
                                font-size: 13px;
                            "
                            >{{ item.tableName }}</span
                        ></el-option
                    >
                </el-select>
            </el-form-item>
            <el-form-item label="解析阀值" prop="relationThreshold">
                <el-input
                    v-model="relationThreshold"
                    placeholder="500"
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
    schema: "",
    instanceId: "",
    relationThreshold: 99,
    ignoreFieldList: [],
});
const { schema, instanceId, relationThreshold, ignoreFieldList } = toRefs(form);
const serverList = ref(null);
const schemaList = ref(null);

const tableColumnList = ref([]);
/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((schema.value = rowData.value.schema),
    (instanceId.value = rowData.value.instanceId),
    (relationThreshold.value = rowData.value.relationThreshold));

onMounted(async () => {
    getInstanceList();
});

/**
 * 获取当前表对应的字段
 * @returns {Promise<void>}
 */
const findInstanceSchemaColumnList = async () => {
    // 设置表备注
    tableColumnList.value = [];
    let res = await VE_API.system.findInstanceSchemaColumnList({
        instanceId: instanceId.value,
        schemaName: schema.value,
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
 * 获取数据库实例
 */
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

/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            console.log(form);
            res =
                await VE_API.system.acwTableAssociationRelationAnalysisSchema(
                    form,
                );
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
