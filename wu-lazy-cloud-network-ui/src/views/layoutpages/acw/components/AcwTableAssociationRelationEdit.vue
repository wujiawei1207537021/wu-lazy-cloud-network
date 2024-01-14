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
            label-width="120px"
            :inline="false"
        >
            <el-form-item label="数据库实例ID" prop="instanceId">
                <el-select
                    v-model="instanceId"
                    @change="getSchemaList"
                    placeholder="数据库实例"
                    filterable
                >
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="数据库实例schema" prop="schema">
                <el-select
                    v-model="schema"
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
            <el-form-item label="原始表" prop="sourceTable">
                <el-select
                    v-model="sourceTable"
                    placeholder="数据库表名"
                    @change="getDatabaseTablesColumnList(sourceTable)"
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
            <el-form-item label="原始表字段" prop="sourceColumn">
                <el-select
                    v-model="sourceColumn"
                    placeholder="原始表字段"
                    filterable
                >
                    <el-option
                        v-for="item in tableColumnList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="关系表" prop="relationTable">
                <el-select
                    v-model="relationTable"
                    placeholder="关系表"
                    @change="getDatabaseTablesColumnList(relationTable)"
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
            <el-form-item label="关系表字段" prop="relationTableColumn">
                <el-select
                    v-model="relationTableColumn"
                    placeholder="关系表字段"
                    filterable
                >
                    <el-option
                        v-for="item in tableColumnList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="类型(1:自动产生的、2:手动添加的)" prop="type">
                <el-select
                    v-model="type"
                    placeholder="类型(1:自动产生的、2:手动添加的)"
                    clearable
                >
                    <el-option label="自动产生的" :value="1" />
                    <el-option label="手动添加的" :value="2" />
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
    ref,
    defineProps,
    defineEmits,
    onMounted,
} from "vue";

const rules = {
    instanceId: [
        {
            required: true,
            message: "请输入服务器名称",
            trigger: "blur",
        },
    ],
    schema: [
        {
            required: true,
            message: "请输入账户",
            trigger: "blur",
        },
    ],
    sourceTable: [
        {
            required: true,
            message: "请输入密码",
            trigger: "blur",
        },
    ],
    sourceColumn: [
        {
            required: true,
            message: "host不能为空",
            trigger: "change",
        },
    ],
    relationTable: [
        {
            required: true,
            message: "端口不能为空",
            trigger: "change",
        },
    ],
    relationTableColumn: [
        {
            required: true,
            message: "driverClassName",
            trigger: "change",
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
    instanceId: "",
    schema: "",
    sourceTable: "",
    sourceColumn: "",
    relationTable: "",
    relationTableColumn: "",
    type: 2,
});
const {
    instanceId,
    schema,
    sourceTable,
    sourceColumn,
    relationTable,
    relationTableColumn,
    type,
} = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((instanceId.value = rowData.value.instanceId),
    (schema.value = rowData.value.schema),
    (sourceTable.value = rowData.value.sourceTable),
    (sourceColumn.value = rowData.value.sourceColumn),
    (relationTable.value = rowData.value.relationTable),
    (relationTableColumn.value = rowData.value.relationTableColumn),
    (type.value = rowData.value.type));

const schemaList = ref(null);
const tableList = ref(null);
const tableColumnList = ref([]);

const serverList = ref(null);

// 查询数据库实例
const getServerInstanceList = async () => {
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
    });
};
/**
 * 选择数据库实例
 * @param serverInstanceId
 * @returns {Promise<void>}
 */
const getSchemaList = async () => {
    // 查询数据库
    let res = await VE_API.system.schemaList({
        instanceId: instanceId.value,
    });
    const { code } = res;
    if (code === 0) {
        if (res.data) {
            res.data.map((item) => {
                item.label = item.schemaName;
                item.value = item.schemaName;
            });
            schemaList.value = res.data ? res.data : [];
        }
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
            instanceId: instanceId.value,
            schemaName: schema.value,
        });
    }
    const { code, data } = res;
    if (code === 0) {
        if (data) {
            data.map((item) => {
                item.label = item.tableName;
                item.value = item.tableName;
            });
            tableList.value = data;
        }
    }
};
/**
 * 获取当前表对应的字段
 * @param tableIds
 * @returns {Promise<void>}
 */
const getDatabaseTablesColumnList = async (table) => {
    // 设置表备注
    tableColumnList.value = [];
    let res = await VE_API.system.findDatabaseTableColumnList({
        instanceId: instanceId.value,
        schemaName: schema.value,
        tableName: table,
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
 * 页面初始化方法
 */
onMounted(async () => {
    await getServerInstanceList();
});
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value === "添加") {
                res =
                    await VE_API.system.acwTableAssociationRelationStory(form);
            } else {
                res = await VE_API.system.acwTableAssociationRelationStory({
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
