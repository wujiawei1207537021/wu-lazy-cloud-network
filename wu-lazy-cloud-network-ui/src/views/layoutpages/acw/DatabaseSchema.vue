<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="数据库实例" property="instanceId">
                <el-select
                    v-model="instanceId"
                    @change="getDataList"
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
            <el-form-item label="数据库库名" prop="schemaName">
                <el-input
                    clearable
                    v-model="schemaName"
                    placeholder="数据库库名"
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
            border
            style="width: 80%"
            @selectionChange="handleSelectionChange"
            ref="multipleTableRef"
        >
            >
            <template #tool_bar>
                <el-button
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
                <el-button
                    v-permission="['batchDel']"
                    size="small"
                    type="danger"
                    @click="batchDel"
                >
                    {{ buttons.batchDel.name }}
                </el-button>
                <el-button
                    v-permission="['batchExportData']"
                    size="small"
                    type="primary"
                    @click="batchExportData"
                >
                    {{ buttons.batchExportData.name }}
                </el-button>
                <el-button
                    v-permission="['schemaDeriveView']"
                    @click="
                        handleSchemaDeriveView(
                            buttons.schemaDeriveView.name,
                            row,
                        )
                    "
                    type="primary"
                    size="small"
                >
                    {{ buttons.schemaDeriveView.name }}
                </el-button>
            </template>
            <el-table-column type="selection" width="55" />
            <!--            <el-table-column prop="id" label="ID"></el-table-column>-->
            <el-table-column
                prop="instanceName"
                label="数据库实例"
            ></el-table-column>

            <el-table-column
                prop="schemaName"
                label="数据库库名"
            ></el-table-column>
            <el-table-column label="是否初始化数据库到本地">
                <template v-slot="{ row }">
                    <el-select
                        v-model="row.initializeToLocal"
                        placeholder="是否初始化数据库到本地"
                        disabled
                    >
                        <el-option label="初始化数据库到本地" :value="true" />
                        <el-option
                            label="不初始化数据库到本地"
                            :value="false"
                        />
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column
                prop="dataLength"
                label="数据大小"
            ></el-table-column>
            <el-table-column
                prop="indexLength"
                label="索引大小"
            ></el-table-column>
            <!--            <el-table-column-->
            <!--                prop="characterSet"-->
            <!--                label="字符集"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column-->
            <!--                prop="sortingRules"-->
            <!--                label="排序规则"-->
            <!--            ></el-table-column>-->
            <!--            <el-table-column prop="ext" label="ext"></el-table-column>-->
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button
                        v-permission="['edit']"
                        @click.prevent="handleEdit(buttons.edit.name, row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.edit.name }}
                    </el-button>
                    <el-button
                        v-permission="['exportData']"
                        @click.prevent="handleExportData(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.exportData.name }}
                    </el-button>
                    <el-button
                        v-permission="['exportStructure']"
                        @click.prevent="handleExportStructure(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.exportStructure.name }}
                    </el-button>
                    <el-button
                        v-permission="['exportStructureExcel']"
                        @click.prevent="handleExportStructureExcel(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.exportStructureExcel.name }}
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
                        v-permission="['reload']"
                        @click="handleDatabaseSchemaReload(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.reload.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <database-schema-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
        <!--   数据库衍生视图   -->
        <database-schema-derive-view
            v-if="showSchemaDeriveViewDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showSchemaDeriveViewDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>
<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    data: () => ({
        description: "数据库信息查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            exportData: { name: "导出数据" },
            exportStructure: { name: "导出结构为SQL" },
            exportStructureExcel: { name: "导出结构为Excel" },
            batchDel: { name: "批量删除" },
            batchExportData: { name: "批量导出数据" },
            reload: { name: "初始化schema" },
            schemaDeriveView: { name: "数据库衍生视图" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "IceDrink",
        name: "数据库信息管理",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import databaseSchemaEdit from "./components/DatabaseSchemaEdit";
import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";
//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";
import DatabaseSchemaDeriveView from "@/views/layoutpages/acw/components/DatabaseSchemaDeriveView.vue";

const { proxy } = getCurrentInstance();
const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);
const showSchemaDeriveViewDialog = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const serverList = ref(null);
// 批量删除选中的 schema
const batchSelectDatabaseSchemaList = ref([]);

const params = reactive({
    instanceId: "",
    schemaName: "",
    initializeToLocal: false,
    size: 10,
    current: 1,
    total: 0,
});
const { instanceId, schemaName, initializeToLocal, size, current, total } =
    toRefs(params);
const multipleTableRef = ref(null);
/**
 * @description:添加or编辑事件
 * @param {*}
 * @return {*}
 */
const handleEdit = (title, row = null) => {
    showDialog.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};

/**
 * @description: dialog事件
 * @param {*}
 * @return {*}
 */
const handelDialog = (e) => {
    showDialog.value = e;
    getDataList();
};
/**
 *
 * 批量导出数据
 */
const batchExportData = () => {
    if (batchSelectDatabaseSchemaList.value.length == 0) {
        proxy.$message({
            type: "info",
            message: "请选择一个schema",
        });
        return;
    }
    proxy
        .$confirm("此操作将批量导出数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            let databaseSchemaDeleteCommandList =
                batchSelectDatabaseSchemaList.value.map((item) => {
                    return {
                        instanceName: item.instanceName,
                        schema: item.schemaName,
                    };
                });
            let res = await VE_API.system.batchExportSchemaData(
                databaseSchemaDeleteCommandList,
                {
                    responseType: "blob",
                },
            );

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
        })
        .catch(() => {
            proxy.$message({
                type: "info",
                message: "已取消删除",
            });
        })
        .finally(() => {
            getDataList();
        });
};
/**
 *
 * 批量删除
 */
const batchDel = () => {
    if (batchSelectDatabaseSchemaList.value.length === 0) {
        proxy.$message({
            type: "info",
            message: "请选择一个schema",
        });
        return;
    }
    proxy
        .$confirm(
            `此操作将批量永久删除【${batchSelectDatabaseSchemaList.value.length}】个数据库, 是否继续?`,
            "提示",
            {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "error",
            },
        )
        .then(async () => {
            let databaseSchemaDeleteCommandList =
                batchSelectDatabaseSchemaList.value.map((item) => {
                    return {
                        instanceName: item.instanceName,
                        instanceId: item.instanceId,
                        schema: item.schemaName,
                    };
                });
            const { code } = await VE_API.system.batchDeleteInstanceSchema(
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
            if (row === undefined) {
                getDataList();
            } else {
                let databaseSchemaDeleteCommandList = [
                    {
                        instanceName: row.instanceName,
                        instanceId: row.instanceId,
                        schema: row.schemaName,
                    },
                ];
                const { code } = await VE_API.system.batchDeleteInstanceSchema(
                    databaseSchemaDeleteCommandList,
                );
                if (code === 0) {
                    getDataList();
                }
            }
        })
        .catch(() => {
            proxy.$message({
                type: "info",
                message: "已取消删除",
            });
        });
};
/**导出数据
 * @description:
 * @param {*}
 * @return {*}
 */
const handleExportData = (row) => {
    proxy
        .$confirm("此操作将导出数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            if (row == undefined) {
                getDataList();
            } else {
                let databaseSchemaDeleteCommandList = [
                    {
                        instanceName: row.instanceName,
                        schema: row.schemaName,
                    },
                ];
                let res = await VE_API.system.batchExportSchemaData(
                    databaseSchemaDeleteCommandList,
                    {
                        responseType: "blob",
                    },
                );

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
            }
        })
        .catch(() => {
            proxy.$message({
                type: "info",
                message: "已取消删除",
            });
        })
        .finally(() => {
            getDataList();
        });
};
/**
 * 导出结构
 */
const handleExportStructure = async (row) => {
    let res = await VE_API.system.exportTableStructureSql(row, {
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
 * 导出结构
 */
const handleExportStructureExcel = async (row) => {
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
 * 全选事件
 *
 */
const handleSelectionChange = (val) => {
    batchSelectDatabaseSchemaList.value = val;
    console.log(batchSelectDatabaseSchemaList.value);
};

/**
 * 数据库衍生视图
 * @param row
 */
const handleSchemaDeriveView = (title, row) => {
    console.log(row);
    showSchemaDeriveViewDialog.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};
/**
 * 初始化数据库schema
 * **/
const handleDatabaseSchemaReload = async (row) => {
    const { code, data } = await VE_API.system.schemaReload({
        instanceId: row.instanceId,
        schema: row.schemaName,
    });
    if (code === 0) {
        console.log("success");
    }
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.schemaPage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;
        tableData.value = record;
    }
};
// 查询数据库实例
const getServerInstanceList = async () => {
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
        if (serverList.value.length == 0) {
            return;
        }
        params.instanceId = serverList.value[0].id;

        getDataList();
    });
};
onMounted(async () => {
    await getServerInstanceList();
});
</script>

<style lang="scss" scoped>
.ve_container {
    ::v-deep
        .el-table__header
        .el-table-column--selection
        .cell
        .el-checkbox:after {
        color: #606266;
        content: "全选";
        font-size: 12px;
        margin-left: 2px;
        font-weight: bold;
    }
}
</style>
