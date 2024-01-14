<template>
    <el-drawer
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        @close="closeDialog()"
    >
        <el-descriptions class="margin-top" :column="3" border title="表详情">
            <el-descriptions-item
                label="id"
                property="id"
                class="contentClassName"
            >
                {{ this.rowData.id }}
            </el-descriptions-item>
            <el-descriptions-item label="数据库服务器ID">
                <el-tag size="small"> {{ this.rowData.instanceId }}</el-tag>
            </el-descriptions-item>
            <el-descriptions-item label="数据库服务器名称">
                <el-tag size="small"> {{ this.rowData.instanceName }}</el-tag>
            </el-descriptions-item>

            <el-descriptions-item label="数据库名称">
                {{ this.rowData.schemaName }}</el-descriptions-item
            >
            <el-descriptions-item label="数据库ID">
                {{ this.rowData.schemaNameId }}</el-descriptions-item
            >

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
        </el-descriptions>
        <el-checkbox-group v-model="checkedCities" :min="1" :max="2">
            <el-checkbox v-for="city in cities" :key="city" :label="city">{{
                city
            }}</el-checkbox>
        </el-checkbox-group>

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
                <el-button type="primary" @click="onSubmit()">确定</el-button>
            </span>
        </template>
    </el-drawer>
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
const checkedCities = ref(["swagger"]);
const cities = ["swagger", "mybatis", "lazy", "Shenzhen"];
/**
 *     private StringBuilder javaController;
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
            if (code === "00") {
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
 * @param tableIds
 * @returns {Promise<void>}
 */
const getDatabaseTablesColumnList = async (tableIds) => {
    let res = await VE_API.system.databaseTablesColumnList({
        tableIds,
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

onMounted(async () => {
    VE_API.system.generateJavaModel({ id: rowData.value.id }).then((res) => {
        const { code } = res;
        console.log(code);
        if (code === 0) {
            javaModel.value = res.data;
        }
    });
    await getDatabaseTablesColumnList(rowData.value.id);
});
</script>

<style lang="scss" scoped></style>
