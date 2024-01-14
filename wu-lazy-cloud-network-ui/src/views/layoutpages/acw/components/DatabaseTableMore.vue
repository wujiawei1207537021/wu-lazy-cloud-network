<template>
    <el-drawer
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        @close="closeDialog()"
    >
        <el-form ref="form">
            <el-descriptions
                class="margin-top"
                :column="3"
                border
                title="表详情"
            >
                <!--            <el-descriptions-item-->
                <!--                label="id"-->
                <!--                property="id"-->
                <!--                class="contentClassName"-->
                <!--            >-->
                <!--                {{ this.rowData.id }}-->
                <!--            </el-descriptions-item>-->
                <el-descriptions-item label="数据库服务器ID">
                    <el-tag size="small"> {{ this.rowData.instanceId }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="数据库服务器名称">
                    <el-tag size="small">
                        {{ this.rowData.instanceName }}</el-tag
                    >
                </el-descriptions-item>

                <el-descriptions-item label="数据库名称">
                    {{ this.rowData.schemaName }}</el-descriptions-item
                >
                <!--            <el-descriptions-item label="数据库ID">-->
                <!--                {{ this.rowData.schemaNameId }}</el-descriptions-item-->
                <!--            >-->

                <el-descriptions-item label="表引擎">
                    {{ this.rowData.engine }}</el-descriptions-item
                >
                <el-descriptions-item label="表名称">
                    {{ this.rowData.tableName }}</el-descriptions-item
                >
                <el-descriptions-item label="表名描述">
                    {{ this.rowData.tableComment }}</el-descriptions-item
                >
            </el-descriptions>

            <el-descriptions border>
                <el-descriptions-item label="表字段">
                    <span v-for="(item, index) in tableColumnList" :key="index"
                        >{{ item.columnName }} <el-divider direction="vertical"
                    /></span>
                </el-descriptions-item>
                <el-descriptions-item label="生成本地Java代码地址">
                    <el-tree-select
                        v-model="javaLocalPath"
                        :data="resourceData"
                        lazy
                        :load="loadNode"
                        :props="resourceProps"
                        filterable
                        check-strictly
                        :render-after-expand="false"
                        show-checkbox
                        check-on-click-node
                    />
                </el-descriptions-item>
            </el-descriptions>

            <el-form-item>
                <el-button
                    v-permission="['java_code']"
                    @click.prevent="handleJavaCode(buttons.java_code.name, row)"
                    type="primary"
                    size="small"
                >
                    {{ buttons.java_code.name }}
                </el-button>
                <el-button
                    v-permission="['export_insert_sql']"
                    @click.prevent="
                        exportInsertSql(buttons.export_insert_sql.name, row)
                    "
                    type="primary"
                    size="small"
                >
                    {{ buttons.export_insert_sql.name }}
                </el-button>
                <el-button
                    v-permission="['export_upsert_sql']"
                    @click.prevent="
                        exportUpsertSql(buttons.export_upsert_sql.name, row)
                    "
                    type="primary"
                    size="small"
                >
                    {{ buttons.export_upsert_sql.name }}
                </el-button>
                <el-button
                    v-permission="['export_table_excel']"
                    @click.prevent="
                        exporTableExcel(buttons.export_table_excel.name, row)
                    "
                    type="primary"
                    size="small"
                >
                    {{ buttons.export_table_excel.name }}
                </el-button>
            </el-form-item>

            <el-timeline>
                <el-timeline-item timestamp="实体" placement="top">
                    <el-card>
                        <h4>表对应的实体</h4>
                        <p style="white-space: pre-wrap">
                            {{ javaModel.javaEntity }}
                        </p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item timestamp="实体对应xml" placement="top">
                    <el-card>
                        <h4>实体对应xml</h4>
                        <p style="white-space: pre-wrap">
                            {{ javaModel.javaMapperXml }}
                        </p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item timestamp="控制层" placement="top">
                    <el-card>
                        <h4>表对应的控制层</h4>
                        <p style="white-space: pre-wrap">
                            {{ javaModel.javaController }}
                        </p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item timestamp="业务接口" placement="top">
                    <el-card>
                        <h4>表对应的业务接口</h4>
                        <p style="white-space: pre-wrap">
                            {{ javaModel.javaService }}
                        </p>
                    </el-card>
                </el-timeline-item>
                <el-timeline-item timestamp="业务接口实现" placement="top">
                    <el-card>
                        <h4>表对应的业务接口实现</h4>
                        <p style="white-space: pre-wrap">
                            {{ javaModel.javaServiceImpl }}
                        </p>
                    </el-card>
                </el-timeline-item>
            </el-timeline>

            <template v-slot:footer>
                <span>
                    <el-button @click="closeDialog()">取消</el-button>
                    <el-button type="primary" @click="onSubmit()"
                        >确定</el-button
                    >
                </span>
            </template>
        </el-form>
    </el-drawer>
</template>
<script>
export default {
    data: () => ({
        description: "数据库表查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加表" },
            java_code: { name: "Java代码生成" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            more: { name: "更多" },
            export_insert_sql: { name: "导出insert-sql" },
            export_upsert_sql: { name: "导出upsert-sql" },
            export_table_excel: { name: "导出表结构.excel" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "CopyDocument",
        name: "数据库表查询与设置更多",
    }),
};
</script>
<script setup>
import {
    reactive,
    toRefs,
    ref,
    defineProps,
    defineEmits,
    onMounted,
} from "vue";

const javaLocalPath = ref();
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
/**
 *
 private StringBuilder javaController;
 private StringBuilder javaEntity;
 private StringBuilder javaService;
 private StringBuilder javaServiceImpl;
 private StringBuilder javaMapper;
 private StringBuilder javaMapperXml;
 */
const javaModel = ref({});
const closeDialog = () => {
    emit("closeDialog", false);
};
const formRef = ref(null);
const form = reactive({
    id: "",
    schemaName: "",
    schemaNameId: "",
    instanceName: "",
    instanceId: "",
    engine: "",
    autoIncrement: "",
    tableComment: "",
    tableName: "",
    tableCatalog: "",
    tableRows: "",
    tableCollation: "",
    maxDataLength: "",
    indexLength: "",
    dataLength: "",
    avgRowLength: "",
    createOptions: "",
    checkTime: "",
    checksum: "",
    dataFree: "",
    rowFormat: "",
    tableType: "",
    createTime: "",
    updateTime: "",
    version: "",
});
const {
    id,
    schemaName,
    schemaNameId,
    instanceName,
    instanceId,
    engine,
    autoIncrement,
    tableComment,
    tableName,
    tableCatalog,
    tableRows,
    tableCollation,
    maxDataLength,
    indexLength,
    dataLength,
    avgRowLength,
    createOptions,
    checkTime,
    checksum,
    dataFree,
    rowFormat,
    tableType,
    createTime,
    updateTime,
    version,
} = toRefs(form);
const tableColumnList = ref(null);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((schemaName.value = rowData.value.schemaName),
    (id.value = rowData.value.id),
    (schemaNameId.value = rowData.value.schemaNameId),
    (instanceName.value = rowData.value.instanceName),
    (instanceId.value = rowData.value.instanceId),
    (engine.value = rowData.value.engine),
    (autoIncrement.value = rowData.value.autoIncrement),
    (tableComment.value = rowData.value.tableComment),
    (tableName.value = rowData.value.tableName),
    (tableCatalog.value = rowData.value.tableCatalog),
    (tableRows.value = rowData.value.tableRows),
    (tableCollation.value = rowData.value.tableCollation),
    (maxDataLength.value = rowData.value.maxDataLength),
    (indexLength.value = rowData.value.indexLength),
    (dataLength.value = rowData.value.dataLength),
    (avgRowLength.value = rowData.value.avgRowLength),
    (createOptions.value = rowData.value.createOptions),
    (checkTime.value = rowData.value.checkTime),
    (checksum.value = rowData.value.checksum),
    (dataFree.value = rowData.value.dataFree),
    (rowFormat.value = rowData.value.rowFormat),
    (tableType.value = rowData.value.tableType),
    (createTime.value = rowData.value.createTime),
    (updateTime.value = rowData.value.updateTime),
    (version.value = rowData.value.version));

/**
 * handleJavaCode 事件
 * @param title
 * @param row
 */
const handleJavaCode = (title, row = rowData.value) => {
    // showJavaCodeDialog.value = true;
    // dialogTitle.value = title;
    rowData.value = row;
    VE_API.system
        .generateLocalJava({
            instanceId: rowData.value.instanceId,
            schemaName: rowData.value.schemaName,
            tableList: [rowData.value.tableName],
            absolutePath: javaLocalPath.value,
        })
        .then((res) => {
            const { code } = res;
            if (code === 0) {
                console.log(code);
            }
        });
};
/***
 *  导出insert sql
 * */
const exportInsertSql = async (title, row = rowData.value) => {
    let res = await VE_API.system.tableExportInsertSql(row, {
        responseType: "blob",
    });
    let fileName = res.headers["file-name"];
    // 获取文件名
    fileName = decodeURIComponent(fileName);
    let url = window.URL.createObjectURL(new Blob([res.data]));
    let link = document.createElement("a");
    link.style.display = "none";
    link.href = url;
    // eslint-disable-next-line no-undef
    link.setAttribute("download", fileName); //指定下载后的文件名，防跳转
    document.body.appendChild(link);
    link.click();
    // 释放内存
    window.URL.revokeObjectURL(link.href);
};
/***
 *  导出 upsert sql
 * */
const exportUpsertSql = async (title, row = rowData.value) => {
    let res = await VE_API.system.tableExportUpsertSql(row, {
        responseType: "blob",
    });
    let fileName = res.headers["file-name"];
    // 获取文件名
    fileName = decodeURIComponent(fileName);
    let url = window.URL.createObjectURL(new Blob([res.data]));
    let link = document.createElement("a");
    link.style.display = "none";
    link.href = url;
    // eslint-disable-next-line no-undef
    link.setAttribute("download", fileName); //指定下载后的文件名，防跳转
    document.body.appendChild(link);
    link.click();
    // 释放内存
    window.URL.revokeObjectURL(link.href);
};
/**
 *  导出表结构
 * */
const exporTableExcel = async (title, row = rowData.value) => {
    let res = await VE_API.system.exportTableStructureExcel(row, {
        responseType: "blob",
    });
    let fileName = res.headers["file-name"];
    // 获取文件名
    fileName = decodeURIComponent(fileName);
    let url = window.URL.createObjectURL(new Blob([res.data]));
    let link = document.createElement("a");
    link.style.display = "none";
    link.href = url;
    // eslint-disable-next-line no-undef
    link.setAttribute("download", fileName); //指定下载后的文件名，防跳转
    document.body.appendChild(link);
    link.click();
    // 释放内存
    window.URL.revokeObjectURL(link.href);
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
            if (title.value === "添加") {
                res = await VE_API.system.schemaAdd(form);
            } else {
                res = await VE_API.system.schemaEdit({
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
/**
 * 获取表字段
 * @returns {Promise<void>}
 */
const getDatabaseTablesColumnList = async () => {
    let res = await VE_API.system.databaseTablesColumnList({
        instanceId: rowData.value.instanceId,
        schemaName: rowData.value.schemaName,
        tableName: rowData.value.tableName,
    });
    const { code } = res;
    if (code === 0) {
        res.data.map((item) => {
            item.label = item.columnName;
            item.value = item.columnName;
        });
        tableColumnList.value = res.data ? res.data : [];
    }
};

const resourceData = ref([]);
const params = reactive({
    path: "/",
});

const resourceProps = {
    label: "name",
    children: "children",
    isLeaf: "leaf",
};

const loadNode = async (resourceNode, resolve) => {
    if (resourceNode.level === 0) {
        return resolve([]);
    }

    resourceNode.loaded = true;
    var resourceNodeData = resourceNode.data;
    if (resourceNodeData.isFile) {
        return resolve([]);
    }
    console.log(resourceNodeData);
    const { code, data } = await VE_API.system.playFileResourceList({
        path:
            "/" === resourceNodeData.rootPath
                ? "/" + resourceNodeData.name
                : resourceNodeData.rootPath + "/" + resourceNodeData.name,
    });
    if (code === 0) {
        if (data == null) {
            return resolve([]);
        }
        data.map((item) => {
            item.value = item.absolutePath;
        });
        // resourceNode.childNodes = data;
        console.log(resourceNode);
        return resolve(data);
    }
};

/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getFileList = async () => {
    const { code, data } = await VE_API.system.playFileResourceList(params);
    if (code === 0) {
        data.map((item) => {
            item.value = item.absolutePath;
        });
        resourceData.value = data;
    }
};

onMounted(async () => {
    await getFileList();
    VE_API.system
        .generateJavaModel({
            instanceId: rowData.value.instanceId,
            schemaName: rowData.value.schemaName,
            tableName: rowData.value.tableName,
        })
        .then((res) => {
            const { code } = res;
            console.log(code);
            if (code === 0) {
                javaModel.value = res.data;
            }
        });
    await getDatabaseTablesColumnList();
});
</script>

<style lang="scss" scoped></style>
