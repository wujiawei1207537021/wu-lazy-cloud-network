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
                    clearable
                >
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="数据库名" prop="schemaNameId">
                <el-select
                    clearable
                    v-model="params.schemaNameId"
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
            <el-form-item label="填充状态" prop="status">
                <el-select
                    v-model="status"
                    placeholder="填充状态"
                    clearable
                    @change="getDataList"
                >
                    <el-option label="成功" :value="true" />
                    <el-option label="失败" :value="false" />
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
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
                <el-button
                    v-permission="['batchDel']"
                    size="small"
                    type="danger"
                    @click="batchDelDatabaseTableAutoStuffed"
                >
                    {{ buttons.batchDel.name }}
                </el-button>
            </template>

            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="填充记录ID"> </el-table-column>
            <el-table-column
                prop="instanceName"
                label="数据库实例"
            ></el-table-column>
            <el-table-column
                prop="schemaName"
                label="数据库库名"
            ></el-table-column>
            <el-table-column prop="tableName" label="表名称"> </el-table-column>

            <el-table-column
                prop="autoStuffedNum"
                label="自动填充数量"
            ></el-table-column>
            <el-table-column prop="status" label="状态">
                <template v-slot="{ row }">
                    <el-select
                        v-model="row.status"
                        placeholder="数据填充状态"
                        disabled
                    >
                        <el-option label="成功" :value="true" />
                        <el-option label="失败" :value="false" />
                    </el-select>
                </template>
            </el-table-column>

            <el-table-column
                prop="createTime"
                label="创建时间"
            ></el-table-column>
            <el-table-column
                prop="updateTime"
                label="更新时间"
            ></el-table-column>
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
                        v-permission="['del']"
                        @click.prevent="handleDel(row.id)"
                        type="danger"
                        size="small"
                    >
                        {{ buttons.del.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <database-table-auto-stuffed-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handleDialog($event)"
        />
    </div>
</template>
<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    data: () => ({
        description: "数据库表数据自动填充查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加自动填充记录" },
            edit: { name: "编辑自动填充记录" },
            del: { name: "删除自动填充记录" },
            batchDel: { name: "批量删除自动填充记录" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "PictureRounded",
        name: "数据库表数据自动填充",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import DatabaseTableAutoStuffedEdit from "./components/DatabaseTableAutoStuffedEdit";

import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";
//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";
const { proxy } = getCurrentInstance();
const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);

const showJavaCodeDialog = ref(false);
const showDocumentDialog = ref(false);
const showTableColumnDialog = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const schemaNameList = ref(null);
const serverList = ref(null);
const batchSelectDatabaseTableAutoStuffedList = ref(null);
const params = reactive({
    tableName: "",
    schemaNameId: "",
    instanceId: "",
    status: true,
    size: 10,
    current: 1,
    total: 0,
});
const { tableName, status, size, current, total } = toRefs(params);

/**
 * 全选事件
 *
 */
const handleSelectionChange = (val) => {
    batchSelectDatabaseTableAutoStuffedList.value = val;
    console.log(batchSelectDatabaseTableAutoStuffedList.value);
};
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
const handleDialog = (e) => {
    showDialog.value = e;
    showJavaCodeDialog.value = e;
    showDocumentDialog.value = e;
    showTableColumnDialog.value = e;
    getDataList();
};

/**
 *
 * 批量删除
 */
const batchDelDatabaseTableAutoStuffed = () => {
    if (batchSelectDatabaseTableAutoStuffedList.value.length == 0) {
        proxy.$message({
            type: "info",
            message: "请选择一个填充记录",
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
            let ids = batchSelectDatabaseTableAutoStuffedList.value.map(
                (item) => {
                    return item.id;
                },
            );
            console.log(ids);
            const { code } =
                await VE_API.system.tableAutoStuffedRecordBatchDelete(ids);
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
const handleDel = (id) => {
    proxy
        .$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            const { code } = await VE_API.system.tableAutoStuffedRecordDelete({
                id,
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
    const { code, data } =
        await VE_API.system.tableAutoStuffedRecordPage(params);
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
        res = await VE_API.system.schemaList();
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
    }
    await getDataList();
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

onMounted(async () => {
    await getServerInstanceList();
    await getDataList();
});
</script>

<style lang="scss" scoped></style>
