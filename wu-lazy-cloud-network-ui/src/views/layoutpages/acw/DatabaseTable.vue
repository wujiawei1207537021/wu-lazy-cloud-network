<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="数据库实例" property="instanceId">
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
            </el-form-item>
            <el-form-item label="数据库名" prop="schemaName">
                <el-select
                    clearable
                    v-model="params.schemaName"
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
            </el-form-item>
            <el-form-item label="表名" prop="tableName">
                <el-input
                    clearable
                    v-model="tableName"
                    placeholder="表名"
                ></el-input>
            </el-form-item>
            <el-form-item
                label="是否初始化数据库到本地"
                prop="initializeToLocal"
            >
                <el-select
                    v-model="initializeToLocal"
                    placeholder="是否初始化数据库到本地"
                    clearable
                    @change="getDataList"
                >
                    <el-option label="初始化数据库到本地" value="true" />
                    <el-option label="不初始化数据库到本地" value="false" />
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button
                    type="primary"
                    @click="onSubmit(params, getDataList)"
                >
                    {{ buttons.search.name }}
                </el-button>
                <el-button @click="resetForm(queryForm, params, getDataList)">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <!-- 列表 -->
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
        >
            <template #tool_bar>
                <el-button
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEditRoute(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
                <el-button
                    v-permission="['batchDel']"
                    size="small"
                    type="danger"
                    @click="batchDelTable"
                >
                    {{ buttons.batchDel.name }}
                </el-button>
            </template>

            <el-table-column type="selection" width="55" />
            <el-table-column
                prop="schemaName"
                label="数据库库名"
            ></el-table-column>
            <el-table-column prop="tableName" label="表名称"> </el-table-column>
            <el-table-column
                prop="tableComment"
                label="表描述"
                width="120"
            ></el-table-column>
            <el-table-column prop="tableType" label="表类型"></el-table-column>
            <el-table-column
                prop="tableRows"
                sortable
                label="表行数"
            ></el-table-column>
            <!--            <el-table-column-->
            <!--                prop="avgRowLength"-->
            <!--                label="avgRowLength"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="autoIncrement"-->
            <!--                label="autoIncrement"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="checkTime"-->
            <!--                label="checkTime"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column prop="checksum" label="checksum"></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="createOptions"-->
            <!--                label="createOptions"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column prop="dataFree" label="dataFree"></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="dataLength"-->
            <!--                label="dataLength"-->
            <!--            ></el-table-column>-->
            <el-table-column prop="engine" label="表引擎"></el-table-column>
            <el-table-column
                prop="dataLength"
                label="数据大小"
            ></el-table-column>
            <el-table-column
                prop="indexLength"
                label="索引大小"
            ></el-table-column>
            <!--            <el-table-column-->
            <!--                prop="maxDataLength"-->
            <!--                label="maxDataLength"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="rowFormat"-->
            <!--                label="rowFormat"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="tableCatalog"-->
            <!--                label="tableCatalog"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="tableCollation"-->
            <!--                label="tableCollation"-->
            <!--            ></el-table-column>-->

            <el-table-column
                prop="createTime"
                label="创建时间"
            ></el-table-column>
            <el-table-column prop="version" label="版本"></el-table-column>
            <el-table-column
                prop="updateTime"
                label="更新时间"
            ></el-table-column>
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button
                        v-permission="['edit']"
                        @click.prevent="handleEditRoute(buttons.edit.name, row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.edit.name }}
                    </el-button>
                    <el-button
                        v-permission="['del']"
                        @click.prevent="handleDel(row)"
                        type="danger"
                        size="small"
                    >
                        {{ buttons.del.name }}
                    </el-button>
                    <el-button
                        v-permission="['more']"
                        @click.prevent="handleShowMore(buttons.more.name, row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.more.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <database-table-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handleDialog($event)"
        />
        <!--   更多     -->
        <database-table-more
            v-if="showMore"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showMore"
            @closeDialog="handleShowMoreDialog($event)"
        />
    </div>
</template>
<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    data: () => ({
        description: "数据库表查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加表" },
            edit: { name: "编辑" },
            // crud: { name: "表crud操作" },
            del: { name: "删除" },
            batchDel: { name: "批量删除" },
            more: { name: "更多" },

            java_code: { name: "Java代码本地生成" },
            export_insert_sql: { name: "导出insert-sql" },
            export_upsert_sql: { name: "导出upsert-sql" },
            export_table_excel: { name: "导出表结构.excel" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "CopyDocument",
        name: "数据库表管理",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import DatabaseTableEdit from "./components/DatabaseTableEdit";
import DatabaseTableMore from "./components/DatabaseTableMore";
import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";
//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
    getAsyncRouteName,
} from "@/views/layoutpages/common";
import { useRoute, useRouter } from "vue-router";
const { proxy } = getCurrentInstance();
const route = useRoute();
const router = useRouter();
const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);
const showMore = ref(false);
const showJavaCodeDialog = ref(false);
const showDocumentDialog = ref(false);
const showTableColumnDialog = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const schemaNameList = ref(null);
const serverList = ref(null);

const batchSelectDatabaseSchemaTableList = ref([]);
const params = reactive({
    tableName: "",
    schemaName: "",
    instanceId: "",
    initializeToLocal: false,
    size: 10,
    current: 1,
    total: 0,
});

const { tableName, initializeToLocal, size, current, total } = toRefs(params);
/**
 * @description: 添加页面路由式
 * @param {*}
 * @return {*}
 */
const handleEditRoute = async (title, row = null) => {
    let path = "acw/components/DatabaseTableEdit";
    const toName = await getAsyncRouteName(title, path, "add", {
        router,
        route,
    });
    router.push({
        name: toName,
        query: row,
    });
};
/**
 * 展示更多
 *
 * */
const handleShowMore = (title, row = null) => {
    console.log(showMore.value);
    showMore.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};
const handleShowMoreDialog = (e) => {
    showMore.value = e;
    getDataList();
};
/**
 * @description: dialog事件
 * @param {*}
 * @return {*}
 */
const handleDialog = (e) => {
    showDialog.value = e;
    showJavaCodeDialog.value = e;
    showDocumentDialog.value = e;
    showTableColumnDialog.value = e;
    getDataList();
};

/**
 * 全选事件
 *
 */
const handleSelectionChange = (val) => {
    batchSelectDatabaseSchemaTableList.value = val;
    console.log(batchSelectDatabaseSchemaTableList.value);
};
/**
 *
 * 批量删除
 */
const batchDelTable = () => {
    if (batchSelectDatabaseSchemaTableList.value.length == 0) {
        proxy.$message({
            type: "info",
            message: "请选择一个表",
        });
        return;
    }
    proxy
        .$confirm("此操作将批量永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            let databaseSchemaDeleteCommandList =
                batchSelectDatabaseSchemaTableList.value.map((item) => {
                    return {
                        instanceId: item.instanceId,
                        schemaName: item.schemaName,
                        tableName: item.tableName,
                    };
                });
            const { code } = await VE_API.system.batchDeleteTableDelete(
                databaseSchemaDeleteCommandList,
            );
            if (code === 0) {
                getDataList();
            }
        })
        .catch(() => {
            proxy.$message({
                type: "info",
                message: "已取消删除",
            });
        });
};
/**删除行数据
 * @description:
 * @param {*}
 * @return {*}
 */
const handleDel = (row) => {
    proxy
        .$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            const { code } = await VE_API.system.tableDelete({
                instanceId: row.instanceId,
                schemaName: row.schemaName,
                tableName: row.tableName,
            });
            if (code === 0) {
                getDataList();
            }
        })
        .catch(() => {
            proxy.$message({
                type: "info",
                message: "已取消删除",
            });
        });
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    // console.log(params);
    const { code, data } = await VE_API.system.tablePage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;
        tableData.value = record;
    }
};
/**
 * 获取 数据库
 * @param instanceId 数据库实例ID
 * @returns {Promise<void>}
 */
const getSchemaList = async (instanceId) => {
    let res;
    if (instanceId == null) {
        return;
    } else {
        res = await VE_API.system.schemaList({
            instanceId,
        });
    }
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaNameList.value = data;
        if (schemaNameList.value.list !== 0) {
            params.schemaName = schemaNameList.value[0].schemaName;
        }
    }
    await getDataList();
};
// 查询数据库实例
const getServerInstanceList = async () => {
    VE_API.system.databaseInstanceList().then(async (res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
        if (serverList.value.length !== 0) {
            // 设置默认instance
            params.instanceId = serverList.value[0].id;
            getSchemaList(params.instanceId);
        }
    });
};

onMounted(async () => {
    await getServerInstanceList();
});
</script>

<style lang="scss" scoped></style>
