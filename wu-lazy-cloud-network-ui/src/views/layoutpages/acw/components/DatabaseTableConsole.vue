<template>
    <el-descriptions title="数据库表信息" :column="4" border>
        <el-descriptions-item
            label="数据库实例"
            label-align="right"
            align="center"
            property="instanceId"
        >
            <el-select
                v-model="params.instanceId"
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
        </el-descriptions-item>
        <el-descriptions-item
            label="数据库名"
            label-align="right"
            align="center"
            width="150px"
            property="schemaName"
        >
            <el-select
                v-model="params.schemaName"
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
        </el-descriptions-item>

        <el-descriptions-item
            label="数据库表名"
            label-align="right"
            align="center"
        >
            <el-select
                v-model="params.tableName"
                placeholder="数据库表名"
                @change="getDatabaseTablesColumnList"
                filterable
            >
                <el-option
                    v-for="item in tableList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    ><el-row>
                        <el-col :span="12">
                            <span style="float: left">{{ item.label }}</span>
                        </el-col>
                        <el-col :span="12">
                            <span
                                style="
                                    float: right;
                                    color: #8492a6;
                                    font-size: 13px;
                                "
                                >{{ item.tableComment }}
                            </span>
                        </el-col>
                    </el-row></el-option
                >
            </el-select>
        </el-descriptions-item>
        <el-descriptions-item label="表引擎" label-align="right" align="center">
            <el-select
                v-model="params.engine"
                placeholder="表引擎"
                filterable
                :disabled="true"
            >
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
                :disabled="true"
            ></el-input>
        </el-descriptions-item>
        <el-descriptions-item
            label="查询字段"
            label-align="right"
            align="center"
        >
            <el-select
                v-model="selectColumnList"
                value-key="columnName"
                placeholder="查询字段"
                filterable
                multiple
                collapse-tags
                style="width: 240px"
                @change="selectColumnOnChange"
            >
                <el-checkbox
                    v-model="params.checked"
                    :indeterminate="
                        selectColumnList.length !== tableColumnList.length
                    "
                    @change="selectAllFields()"
                    style="margin-left: 20px"
                >
                    全选
                </el-checkbox>
                <el-option
                    v-for="item in tableColumnList"
                    :key="item.value"
                    :label="item.label"
                    :value="item"
                >
                    <el-row>
                        <el-col :span="12">
                            <span style="float: left">{{ item.label }}</span>
                        </el-col>
                        <el-col :span="12">
                            <span
                                style="
                                    float: right;
                                    color: #8492a6;
                                    font-size: 13px;
                                "
                                >{{ item.columnComment }}
                            </span>
                        </el-col>
                    </el-row>
                </el-option>
            </el-select>
        </el-descriptions-item>
        <el-descriptions-item label="表头展示模式" prop="status">
            <el-radio-group v-model="params.showTitleMode">
                <el-radio-button label="columnName">字段名称</el-radio-button>
                <el-radio-button label="columnComment"
                    >字段描述</el-radio-button
                >
            </el-radio-group>
        </el-descriptions-item>
    </el-descriptions>

    <el-row>
        <el-button @click="addColumnItem()" style="float: left" type="primary"
            >添加搜索参数
        </el-button>
        <el-button @click="getDataList" style="float: left" type="primary"
            >执行
        </el-button>
        <!--        <el-button-->
        <!--            v-permission="['batchDel']"-->
        <!--            type="danger"-->
        <!--            @click="batchDelRow"-->
        <!--            style="float: left"-->
        <!--        >-->
        <!--            {{ buttons.batchDel.name }}-->
        <!--        </el-button>-->
        <el-button type="danger" @click="batchDelRow" style="float: left">
            {{ buttons.batchDel.name }}
        </el-button>
        <el-button
            @click="addRowData"
            style="float: left"
            type="primary"
            :hidden="!params.checked"
            >添加数据
        </el-button>
        <el-button
            @click="handleExportUpsertSql"
            style="float: left"
            type="primary"
            >导出查询的upsert语句
        </el-button>
        <el-button
            @click="handleExportResult2Md"
            style="float: left"
            type="primary"
            >导出查询结果为.MD
        </el-button>
    </el-row>
    <div style="padding: 10px">
        <el-row
            v-for="(it, index) in params.queryCriteriaColumnList"
            :key="index"
        >
            <el-col span="3">
                <el-checkbox v-model="it.checked" label=" " size="large" />
            </el-col>
            <el-col span="6">
                <el-select
                    v-model="it.columnName"
                    placeholder="字段名称"
                    filterable
                    @change="getOnlyColumnValue(it)"
                >
                    <el-option
                        v-for="item in tableColumnList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                        <el-row>
                            <el-col :span="12">
                                <span style="float: left">{{
                                    item.label
                                }}</span>
                            </el-col>
                            <el-col :span="12">
                                <span
                                    style="
                                        float: right;
                                        color: #8492a6;
                                        font-size: 13px;
                                    "
                                    >{{ item.columnComment }}
                                </span>
                            </el-col>
                        </el-row>
                    </el-option>
                </el-select>
            </el-col>
            <el-col span="6">
                <el-select
                    v-model="it.condition"
                    placeholder="条件(大于、等于、模糊)"
                    filterable
                >
                    <el-option label="小等于" value="<" />
                    <el-option label="等于" value="=" />
                    <el-option label="大于等于" value=">=" />
                    <el-option label="小于等于" value="<=" />
                    <el-option label="不等于" value="!=" />
                    <el-option label="模糊查询" value="like" />
                    <el-option label="in" value="in" />
                </el-select>
            </el-col>
            <el-col span="6">
                <el-select
                    v-model="it.data"
                    style="width: 230px"
                    placeholder="数据"
                    filterable
                    clearable
                    allow-create
                >
                    <el-option
                        v-for="columnUnitValue in it.columnUnitValueList"
                        :key="columnUnitValue"
                        :label="columnUnitValue"
                        :value="columnUnitValue"
                    />
                </el-select>
            </el-col>
            <el-col span="6">
                <el-button
                    @click="removeColumnItem(index)"
                    style="float: right"
                    type="danger"
                    size="small"
                >
                    {{ buttons.del.name }}
                </el-button>
            </el-col>
        </el-row>
    </div>

    <!--        列表  -->
    <div style="height: 120%">
        <ve-table
            :table="{
                data: tableData,
            }"
            :pagination="{
                onSizeChange: (val) =>
                    handleSizeChange(val, params, getDataList),
                onCurrentChange: (val) =>
                    handleCurrentChange(val, params, getDataList),
                currentPage: current,
                pageSize: size,
                total: total,
            }"
            @selectionChange="handleSelectionChange"
            :key="tableKey"
        >
            <!--    复选框      -->
            <el-table-column
                v-if="tableData.length !== 0"
                type="selection"
                width="55"
            />
            <!--    所有的数据列表    -->
            <!--            <el-table-column-->
            <!--                v-for="(item, index) in tableColumnList"-->
            <!--                :prop="item.columnName"-->
            <!--                :label="item.columnName"-->
            <!--                :key="index"-->
            <!--                sortable-->
            <!--            >-->
            <!--                <template v-slot="scope">-->
            <!--                    <el-input-->
            <!--                        v-model="scope.row[item.columnName]"-->
            <!--                        placeholder="数据"-->
            <!--                        @change="changeRow(scope.row)"-->
            <!--                    ></el-input>-->
            <!--                </template>-->
            <!--            </el-table-column>-->

            <!--    选取的返回结果      -->

            <!--    展示字段描述     -->
            <el-table-column
                v-for="(item, index) in selectColumnList"
                :prop="dynamicColumnList[index].columnName"
                :label="
                    params.showTitleMode === 'columnName'
                        ? item.columnName
                        : item.columnComment === ''
                          ? item.columnName
                          : item.columnComment
                "
                :key="`col_${index}`"
                sortable
            >
                <template v-slot="scope">
                    <el-input
                        v-model="scope.row[item.columnName]"
                        placeholder="数据"
                        @change="changeRow(scope.row)"
                    ></el-input>
                </template>
            </el-table-column>
        </ve-table>

        <div style="position: relative">
            <el-input
                type="textarea"
                v-model="tableData.sql"
                readonly
            ></el-input>
            <el-icon
                @click="copyAnswer(tableData.sql)"
                style="float: right; position: absolute; right: 10px; top: 10px"
            >
                <DocumentCopy
            /></el-icon>
        </div>
    </div>

    <!--        <el-button @click="closeDialog()">取消</el-button>-->
    <!--        <el-button type="primary" @click="onSubmit()">确定</el-button>-->
</template>
<script>
export default {
    data: () => ({
        description: "数据库表快捷查询",
        buttons: {
            del: { name: "删除字段" },
            batchDel: { name: "批量删除数据" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Finished",
        name: "数据库表快捷查询",
    }),
};
</script>
<script setup>
//导入公共查询方法
import {
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";
import { reactive, toRefs, ref, defineProps, onMounted } from "vue";
import { DocumentCopy } from "@element-plus/icons-vue";
import useClipboard from "vue-clipboard3";
const { toClipboard } = useClipboard();
import Sortable from "sortablejs";
import { useRoute } from "vue-router";
import { ElMessage } from "element-plus";

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
const { rowData } = toRefs(props);
// 表字段
const tableColumnList = ref([]);
// 返回结果
const selectColumnList = ref([]);
const tableKey = ref(1);

const params = reactive({
    schemaName: "",
    instanceId: "",
    tableComment: "",
    tableName: "",
    engine: "",
    tableCatalog: "",
    checked: true,
    showTitleMode: "columnName",
    // 查询条件字段
    queryCriteriaColumnList: [],
    //返回结果
    selectColumnList: [],
    current: 1,
    size: 10,
    total: 0,
});
const {
    schemaName,
    instanceId,
    tableComment,
    tableName,
    engine,
    size,
    current,
    total,
} = toRefs(params);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((schemaName.value = rowData.value.schemaName),
    (instanceId.value = rowData.value.instanceId),
    (tableName.value = rowData.value.tableName));

const schemaList = ref(null);
const tableList = ref(null);
const tableData = ref([]);

const serverList = ref(null);

const batchSelectRowList = ref(null);

// const oldList = ref(JSON.parse(JSON.stringify(selectColumnList.value)));
// 动态列
const dynamicColumnList = ref([]);
//
// /**
//  * 表头预览
//  * @param h
//  * @param column
//  * @returns {*}
//  */
// const renderColumnHeader = (h) => {
//     // // h即为cerateElement的简写，具体可看vue官方文档
//     // return h("div", [
//     //     h("span", "column.label"),
//     //     h("i", {
//     //         class: "el-icon-question",
//     //     }),
//     // ]);
// };
/**
 * 选择行改变事件
 */
const selectColumnOnChange = () => {
    console.log("selectColumnList: " + selectColumnList.value);
    console.log("dynamicColumnList: " + dynamicColumnList.value);
    dynamicColumnList.value = selectColumnList.value;
};
/**
 * 全选字段
 *
 */
const selectAllFields = () => {
    console.log(params.checked);
    if (params.checked) {
        params.checked = true;
        // selectColumnList.value = tableColumnList.value;
        initColumnList(tableColumnList.value);
    } else {
        params.checked = false;
        // selectColumnList.value = [];
        initColumnList([]);
    }
};
/**
 * 获取惟一数据
 * @returns {Promise<void>}
 */
const getOnlyColumnValue = async (rowColumn) => {
    const columnName = rowColumn.columnName;
    console.log(rowColumn);
    let res = await VE_API.system.tableColumnSqlConsole({
        instanceId: params.instanceId,
        schemaName: params.schemaName,
        tableName: params.tableName,
        column: columnName,
        current: 1,
        size: 100,
    });
    const { code, data } = res;
    if (code === 0) {
        rowColumn.columnUnitValueList = data.record ? data.record : [];
        // console.log(res);
    }
};
/**
 * 批量删除数据
 */
const batchDelRow = async () => {
    console.log(batchSelectRowList.value);
    let deleteBatchRow = {
        schemaName: params.schemaName,
        instanceId: params.instanceId,
        tableName: params.tableName,
        tableRowList: [],
    };
    for (let row of batchSelectRowList.value) {
        deleteBatchRow.tableRowList.push(row);
    }
    console.log(deleteBatchRow);

    let res = await VE_API.system.tableRowBatchDelete(deleteBatchRow);
    const { code, data } = res;
    if (code === 0) {
        console.log(data); //
    }
    await getDataList();
};
/**
 * 全选事件
 *
 */
const handleSelectionChange = (val) => {
    batchSelectRowList.value = val;
    console.log(batchSelectRowList.value);
};
/**
 * 行数据变更
 * @param row 行数据
 * @returns {Promise<void>}
 */
const changeRow = async (row) => {
    let storyRow = {
        schemaName: params.schemaName,
        instanceId: params.instanceId,
        tableName: params.tableName,
        tableRow: row,
    };
    console.log(storyRow);
    let res = await VE_API.system.tableRowStory(storyRow);
    const { code, data } = res;
    if (code === 0) {
        console.log(data); //
        await getDataList();
    }
};
/**
 * 新增一行数据
 */
const addRowData = () => {
    tableData.value.push({});
};
/**
 * 获取当前表数据
 */
const getDataList = async () => {
    console.log(selectColumnList.value);
    console.log(selectColumnList.value.length);
    params.selectColumnList = selectColumnList.value.map((d) => d.columnName);
    let res = await VE_API.system.tableSqlConsole(params);
    const { code, data, ext } = res;
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;
        tableData.value = record;
        tableData.value.tableHeader = ext.tableHeader;
        tableData.value.sql = ext.sql;
        if (record.length === 0) {
            return;
        }
        console.log(ext);
        // tableData.value.tableHeader = Object.keys(tableData.value[0]);

        // console.log(tableData.value);
    }
};
/**
 * 导出查询的 upsert 数据
 */
const handleExportUpsertSql = async () => {
    params.selectColumnList = selectColumnList.value.map((d) => d.columnName);
    let res = await VE_API.system.tableSqlConsoleUpsertExport(params, {
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
 * 导出结果为md
 * @returns {Promise<void>}
 */
const handleExportResult2Md = async () => {
    params.selectColumnList = selectColumnList.value.map((d) => d.columnName);
    let res = await VE_API.system.tableSqlConsoleMdExport(params, {
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
// 添加表字段
const addColumnItem = () => {
    params.queryCriteriaColumnList.push({
        condition: "=",
        checked: true,
    });
};
// 删除字段
const removeColumnItem = (index) => {
    // 删除时,我们带两个参数,这个 it 可用可不用,因为我当时只是想看到删除的这个对象的信息,故而带上了; index 是 list 中该对象对应的下标,也是 el-form-item 的项数
    // 根据这个 index 下标删除 list 中 的该对象
    params.queryCriteriaColumnList.splice(index, 1);
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
const getSchemaList = async () => {
    // 查询数据库
    let res = await VE_API.system.schemaList({
        instanceId: params.instanceId,
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
    tableName.value = null;
    tableComment.value = null;
    let res;
    if (instanceId.value == null) {
        return;
    } else {
        res = await VE_API.system.tableList({
            instanceId: instanceId.value,
            schemaName: schemaName.value,
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
const getDatabaseTablesColumnList = async () => {
    // 设置表备注
    let table = tableList.value.find(
        (item) => item.tableName === tableName.value,
    );

    tableColumnList.value = [];
    params.queryCriteriaColumnList = [];
    if (table != null) {
        tableComment.value = table.tableComment;
        engine.value = table.engine;
    } else {
        return;
    }
    let res = await VE_API.system.findDatabaseTableColumnList({
        instanceId: instanceId.value,
        schemaName: schemaName.value,
        tableName: tableName.value,
    });
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.columnName;
            item.value = item.columnName;
        });
        tableColumnList.value = data ? data : [];
        // 默认全选数据
        initColumnList(tableColumnList.value);
    }
    // 获取数据
    await getDataList();
};
/**
 * 初始化列、动态列
 */
const initColumnList = (tableColumnList) => {
    selectColumnList.value = tableColumnList;
    dynamicColumnList.value = JSON.parse(JSON.stringify(tableColumnList));
    //
    rowDrop();
    colDrop();
};

// 列表 **行拖拽
const rowDrop = async () => {
    // 此时找到的元素是要拖拽元素的父容器
    const tbody = document.querySelector(".el-table__body-wrapper tbody");

    Sortable.create(tbody, {
        //  指定父元素下可被拖拽的子元素
        draggable: ".el-table__row",
        onEnd({ newIndex, oldIndex }) {
            const currRow = tableData.value.splice(oldIndex, 1)[0];
            tableData.value.splice(newIndex, 0, currRow);
        },
    });
};
// 列表 **列拖拽
const colDrop = async () => {
    const wrapperTr = document.querySelector(".el-table__header-wrapper tr");
    Sortable.create(wrapperTr, {
        animation: 180,
        delay: 0,
        onEnd: (evt) => {
            const empty = 1;
            // 跳过显示的列数量，如开头我们用了一个多选框，h和序号
            const oldItem = dynamicColumnList.value[evt.oldIndex - empty];
            dynamicColumnList.value.splice(evt.oldIndex - empty, 1);
            dynamicColumnList.value.splice(evt.newIndex - empty, 0, oldItem);
            // 每一次拖拽后都要重绘一次
            reDrawTable();
        },
    });
};

const copyAnswer = async (copyData) => {
    console.log("copyData", copyData);
    if (copyData === "") {
        ElMessage.warning("请输入文本再复制");
        return;
    }
    try {
        await toClipboard(copyData);
        ElMessage.success(`复制: ${copyData} 成功！`);
    } catch (error) {
        ElMessage.warning(`复制失败: ${error} ！`);
        console.error(error);
    }
};
/**
 * 触发表格重绘
 */
const reDrawTable = () => {
    tableKey.value = Math.random();
};
/**
 * 页面初始化方法
 */
onMounted(async () => {
    let route = useRoute();
    let row = route.query;
    // await initTableData(row);
    await getServerInstanceList();
});
</script>

<style lang="scss" scoped></style>
