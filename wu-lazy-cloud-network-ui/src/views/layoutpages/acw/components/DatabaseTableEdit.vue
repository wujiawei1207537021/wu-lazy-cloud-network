<template>
    <el-descriptions title="数据库表信息" :column="4" border>
        <el-descriptions-item
            label="数据库实例"
            label-align="right"
            align="center"
            property="instanceId"
        >
            <el-select
                v-model="instanceId"
                @change="changedatabaseInstance"
                placeholder="数据库实例"
                filterable
            >
                <el-option
                    v-for="item in serverList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                /> </el-select
        ></el-descriptions-item>
        <el-descriptions-item
            label="数据库名"
            label-align="right"
            align="center"
            label-class-name="my-label"
            class-name="my-content"
            width="150px"
        >
            <el-select
                clearable
                v-model="schemaName"
                placeholder="数据库名"
                @change="getDataList"
                filterable
            >
                <el-option
                    v-for="item in schemaNameList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
            </el-select>
        </el-descriptions-item>

        <el-descriptions-item label="表名称" label-align="right" align="center">
            <el-input
                v-model="tableName"
                placeholder="tableName"
                clearable
            ></el-input>
        </el-descriptions-item>
        <el-descriptions-item label="表引擎" label-align="right" align="center">
            <el-select v-model="engine" placeholder="表引擎" filterable>
                <el-option label="ARCHIVE" value="ARCHIVE" />
                <el-option label="BLACKHOLE" value="BLACKHOLE" />
                <el-option label="CSV" value="CSV" />
                <el-option label="InnoDB" value="InnoDB" />
                <el-option label="MEMORY" value="MEMORY" />
                <el-option label="MRG_MYISAM" value="MRG_MYISAM" />
                <el-option label="MyISAM" value="MyISAM" />
                <el-option
                    label="PERFORMANCE_SCHEMA"
                    value="PERFORMANCE_SCHEMA"
                />
            </el-select>
        </el-descriptions-item>
        <el-descriptions-item label="表描述" label-align="right" align="center">
            <el-input
                v-model="tableComment"
                placeholder="tableComment"
                clearable
            ></el-input
        ></el-descriptions-item>
    </el-descriptions>
    <el-tabs
        v-model="activeName"
        type="card"
        class="demo-tabs"
        @tab-click="handleClick"
    >
        <el-tab-pane label="字段" name="first">
            <el-form
                :model="form"
                ref="formRef"
                :rules="rules"
                label-width="80px"
                :inline="false"
            >
                <ve-table
                    :table="{
                        data: form.tableColumnList,
                    }"
                    :rules="rules"
                    style="width: 100%"
                    :row-class-name="tableRowClassName"
                >
                    <el-table-column
                        prop="columnName"
                        label="字段名称"
                        sortable
                    >
                        <template v-slot:default="{ row }">
                            <el-input
                                v-model="row.columnName"
                                placeholder="columnName"
                                clearable
                            ></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column prop="dataType" label="类型">
                        <template v-slot:default="{ row }">
                            <el-select
                                v-model="row.dataType"
                                placeholder="字段类型"
                                filterable
                            >
                                <el-option label="bigint" value="bigint" />
                                <el-option label="binary" value="binary" />
                                <el-option label="bit" value="bit" />
                                <el-option label="blob" value="blob" />
                                <el-option label="char" value="char" />
                                <el-option label="date" value="date" />
                                <el-option label="datetime" value="datetime" />
                                <el-option label="decimal" value="decimal" />
                                <el-option label="double" value="double" />
                                <el-option label="enum" value="enum" />
                                <el-option label="float" value="float" />
                                <el-option label="geometry" value="geometry" />
                                <el-option
                                    label="geometrycollection"
                                    value="geometrycollection"
                                />
                                <el-option label="int" value="int" />
                                <el-option label="integer" value="integer" />
                                <el-option label="json" value="json" />
                                <el-option
                                    label="linestring"
                                    value="linestring"
                                />
                                <el-option label="longblob" value="longblob" />
                                <el-option label="longtext" value="longtext" />
                                <el-option
                                    label="mediumblob"
                                    value="mediumblob"
                                />
                                <el-option
                                    label="mediumint"
                                    value="mediumint"
                                />
                                <el-option
                                    label="mediumtext"
                                    value="mediumtext"
                                />
                                <el-option
                                    label="multilinestring"
                                    value="multilinestring"
                                />
                                <el-option
                                    label="multipoint"
                                    value="multipoint"
                                />
                                <el-option
                                    label="multipolygon"
                                    value="multipolygon"
                                />
                                <el-option label="numeric" value="numeric" />
                                <el-option label="point" value="point" />
                                <el-option label="polygon" value="polygon" />
                                <el-option label="real" value="real" />
                                <el-option label="set" value="set" />
                                <el-option label="smallint" value="smallint" />
                                <el-option label="text" value="text" />
                                <el-option label="time" value="time" />
                                <el-option
                                    label="timestamp"
                                    value="timestamp"
                                />
                                <el-option label="tinyblob" value="tinyblob" />
                                <el-option label="tinyint" value="tinyint" />
                                <el-option label="tinytext" value="tinytext" />
                                <el-option
                                    label="varbinary"
                                    value="varbinary"
                                />
                                <el-option label="varchar" value="varchar" />
                                <el-option label="year" value="year" />
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="characterMaximumLength"
                        label="字段长度"
                    >
                        <template v-slot:default="{ row }">
                            <el-input
                                v-model="row.characterMaximumLength"
                                placeholder="characterMaximumLength"
                                clearable
                            ></el-input
                        ></template>
                    </el-table-column>
                    <el-table-column
                        prop="columnType"
                        label="字段类型"
                        sortable
                    >
                        <template v-slot:default="{ row }">
                            <el-input
                                v-model="row.columnType"
                                placeholder="columnType"
                                clearable
                            ></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column prop="generationExpression" label="小数点">
                        <template v-slot:default="{ row }">
                            <el-input
                                v-model="row.generationExpression"
                                placeholder="generationExpression"
                                clearable
                            ></el-input
                        ></template>
                    </el-table-column>
                    <el-table-column prop="isNullable" label="不是null">
                        <template v-slot:default="{ row }">
                            <el-select
                                v-model="row.isNullable"
                                placeholder="不是null"
                            >
                                <el-option label="YES" value="YES" />
                                <el-option label="NO" value="NO" />
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column prop="numericPrecision" label="虚拟">
                        <template v-slot:default="{ row }">
                            <el-input
                                v-model="row.numericPrecision"
                                placeholder="numericPrecision"
                                clearable
                            ></el-input
                        ></template>
                    </el-table-column>
                    <el-table-column prop="numericScale" label="键">
                        <template v-slot:default="{ row }">
                            <el-input
                                v-model="row.numericScale"
                                placeholder="numericScale"
                                clearable
                            ></el-input
                        ></template>
                    </el-table-column>
                    <el-table-column prop="columnComment" label="描述">
                        <template v-slot:default="{ row }">
                            <el-input
                                v-model="row.columnComment"
                                placeholder="columnComment"
                                clearable
                            ></el-input
                        ></template>
                    </el-table-column>
                    <template #tool_bar>
                        <el-button
                            @click="addColumnItem()"
                            style="float: right"
                            type="primary"
                            >增加</el-button
                        >
                        <el-button @click="closeDialog()">取消</el-button>
                        <el-button type="primary" @click="onSubmit()"
                            >确定</el-button
                        >
                    </template>
                    <el-table-column fixed="right" label="操作">
                        <template v-slot="{ $index }">
                            <el-button
                                @click="removeColumnItem($index)"
                                style="float: right"
                                type="danger"
                                size="small"
                            >
                                {{ buttons.del.name }}
                            </el-button></template
                        >
                    </el-table-column>
                </ve-table>
            </el-form>
        </el-tab-pane>
        <el-tab-pane label="索引" name="second">
            <ve-table
                :table="{
                    data: form.tableColumnIndexList,
                }"
                :rules="rules"
                style="width: 100%"
                :row-class-name="tableRowClassName"
            >
                <el-table-column prop="indexName" label="索引名称" sortable>
                    <template v-slot:default="{ row }">
                        <el-input
                            v-model="row.indexName"
                            placeholder="indexName"
                            clearable
                        ></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="columnNameList" label="索引字段">
                    <template v-slot:default="{ row }">
                        <el-select
                            v-model="row.columnNameList"
                            placeholder="索引字段"
                            filterable
                            multiple
                        >
                            <el-option
                                v-for="item in form.tableColumnList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column prop="indexType" label="索引类型">
                    <template v-slot:default="{ row }">
                        <el-select
                            v-model="row.indexType"
                            placeholder="索引类型"
                            filterable
                        >
                            <el-option label="FULLTEXT" value="FULLTEXT" />
                            <el-option label="NORMAL" value="NORMAL" />
                            <el-option label="SPATIAL" value="SPATIAL" />
                            <el-option
                                label="UNIQUE"
                                value="UNIQUE"
                            /> </el-select
                    ></template>
                </el-table-column>
                <el-table-column prop="indexType" label="索引方法">
                    <template v-slot:default="{ row }">
                        <el-select
                            v-model="row.indexType"
                            placeholder="索引方法"
                            filterable
                        >
                            <el-option label="BTREE" value="BTREE " />
                            <el-option label="HASH" value="HASH" /> </el-select
                    ></template>
                </el-table-column>
                <el-table-column prop="indexComment" label="注释">
                    <template v-slot:default="{ row }">
                        <el-input
                            v-model="row.indexComment"
                            placeholder="索引注释"
                        />
                    </template>
                </el-table-column>
                <template #tool_bar>
                    <el-button
                        @click="addColumnIndexItem()"
                        style="float: right"
                        type="primary"
                        >增加</el-button
                    >
                    <el-button @click="closeDialog()">取消</el-button>
                    <el-button type="primary" @click="onSubmit()"
                        >确定</el-button
                    >
                </template>
                <el-table-column fixed="right" label="操作">
                    <template v-slot="{ $index }">
                        <el-button
                            @click="removeColumnIndexItem($index)"
                            style="float: right"
                            type="danger"
                            size="small"
                        >
                            {{ buttons.del.name }}
                        </el-button></template
                    >
                </el-table-column>
            </ve-table>
        </el-tab-pane>
        <!--        <el-tab-pane label="外键" name="third">外键</el-tab-pane>-->
        <!--        <el-tab-pane label="触发器" name="fourth">触发器</el-tab-pane>-->
        <!--        <el-tab-pane label="选项" name="five">选项</el-tab-pane>-->
        <!--        <el-tab-pane label="注释" name="six">注释</el-tab-pane>-->
        <!--        <el-tab-pane label="SQL预览" name="seven">SQL预览</el-tab-pane>-->
    </el-tabs>
</template>
<script>
export default {
    data: () => ({
        description: "数据库表字段设置",
        buttons: {
            del: { name: "删除字段" },
        },
    }),
};
</script>
<script setup>
import { reactive, toRefs, ref, defineProps, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
const rules = {
    instanceId: [
        {
            required: true,
            message: "请选择数据库实例",
            trigger: "blur",
        },
    ],
    characterSet: [
        {
            required: true,
            message: "请输入字符集",
            trigger: "blur",
        },
    ],
    sortingRules: [
        {
            required: true,
            message: "请输入排序规则",
            trigger: "blur",
        },
    ],
    ext: [
        {
            required: true,
            message: "ext",
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
const { title, rowData } = toRefs(props);
const closeDialog = () => {
    // router.go(-1);
    router.back();
};
const formRef = ref(null);
const form = reactive({
    id: "",
    schemaName: "",
    instanceName: "",
    instanceId: "",
    engine: "InnoDB",
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
    tableColumnList: ref([]),
    tableColumnIndexList: ref([]),
});
const {
    id,
    schemaName,
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

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((schemaName.value = rowData.value.schemaName),
    (id.value = rowData.value.id),
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

const schemaNameList = ref(null);
const serverList = ref(null);
const router = useRouter();

const activeName = ref("first");

/**
 * 点标签
 * @param tab 标签
 * @param event 事件
 * @returns {Promise<void>}
 */
const handleClick = async (tab, event) => {
    console.log(tab, event);
};
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    let databaseInstance = serverList.value.find(
        (item) => item.id === form.instanceId,
    );

    form.instanceName = databaseInstance.instanceName;
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value === "添加") {
                res = await VE_API.system.tableAdd(form);
            } else {
                res = await VE_API.system.tableAdd({
                    id: rowData.value.id,
                    ...form,
                });
            }
            const { code } = res;
            if (code === 0) {
                router.go(-1);
            }
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};

// 添加表字段
const addColumnItem = () => {
    form.tableColumnList.push({});
};
// 删除字段
const removeColumnItem = (index) => {
    // 删除时,我们带两个参数,这个 it 可用可不用,因为我当时只是想看到删除的这个对象的信息,故而带上了; index 是 list 中该对象对应的下标,也是 el-form-item 的项数
    // 根据这个 index 下标删除 list 中 的该对象
    form.tableColumnList.splice(index, 1);
};
// 添加表索引
const addColumnIndexItem = () => {
    form.tableColumnIndexList.push({});
};
// 删除索引
const removeColumnIndexItem = (index) => {
    // 删除时,我们带两个参数,这个 it 可用可不用,因为我当时只是想看到删除的这个对象的信息,故而带上了; index 是 list 中该对象对应的下标,也是 el-form-item 的项数
    // 根据这个 index 下标删除 list 中 的该对象
    form.tableColumnIndexList.splice(index, 1);
};
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
const changedatabaseInstance = async (instanceId) => {
    if (instanceId == null) {
        return;
    }
    // 查询数据库
    let res = await VE_API.system.schemaList({
        instanceId,
    });
    const { code } = res;
    if (code === 0) {
        res.data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaNameList.value = res.data ? res.data : [];
    }
};

const initTableData = async (row) => {
    let instanceId = row.instanceId;
    if (instanceId !== "" && instanceId !== undefined) {
        form.schemaName = row.schemaName;
        form.instanceId = row.instanceId;
        form.instanceName = row.instanceName;
        form.engine = row.engine;
        form.autoIncrement = row.autoIncrement;
        form.tableComment = row.tableComment;
        form.tableName = row.tableName;
        form.tableCatalog = row.tableCatalog;
        form.tableRows = row.tableRows;
        form.tableCollation = row.tableCollation;
        form.maxDataLength = row.maxDataLength;
        form.indexLength = row.indexLength;
        form.dataLength = row.dataLength;
        form.avgRowLength = row.avgRowLength;
        form.createOptions = row.createOptions;
        form.checkTime = row.checkTime;
        form.checksum = row.checksum;
        form.dataFree = row.dataFree;
        form.rowFormat = row.rowFormat;
        form.tableType = row.tableType;
        form.createTime = row.createTime;
        form.updateTime = row.updateTime;
        form.version = row.version;
        // 判断数据库实例

        form.instanceId = instanceId;
        // 获取当前表选择的数据库实例数据库列表
        if (
            form.instanceId !== null &&
            form.instanceId !== "" &&
            form.instanceId !== undefined &&
            !isNaN(form.instanceId)
        ) {
            await changedatabaseInstance(form.instanceId);
        }
        // 查询当前表字段
        if (
            form.tableName !== null &&
            form.tableName !== "" &&
            form.tableName !== undefined
        ) {
            await getDatabaseTablesColumnList(
                form.instanceId,
                form.schemaName,
                form.tableName,
            );
        }
    }
};
/**
 * 获取当前表对应的字段
 * @param tableIds
 * @returns {Promise<void>}
 */
const getDatabaseTablesColumnList = async (
    instanceId,
    schemaName,
    tableName,
) => {
    let res = await VE_API.system.databaseTableColumnList({
        instanceId: instanceId,
        schemaName: schemaName,
        tableName: tableName,
    });
    const { code, data } = res;
    if (code === 0) {
        const { acwTableColumnDTOList, acwTableColumnIndexDTOList } = data;
        if (acwTableColumnDTOList && acwTableColumnDTOList.length > 0) {
            acwTableColumnDTOList.map((item) => {
                item.label = item.columnName;
                item.value = item.columnName;
            });
            form.tableColumnList = acwTableColumnDTOList
                ? acwTableColumnDTOList
                : [];
            form.tableColumnIndexList = acwTableColumnIndexDTOList;
        }
    }
};
const tableRowClassName = async ({ row, rowIndex }) => {
    // 把每一行的索引放进row
    row.index = rowIndex;
};
onMounted(async () => {
    let route = useRoute();
    let row = route.query;
    await initTableData(row);
    await getServerInstanceList();
});
</script>

<style lang="scss" scoped></style>
