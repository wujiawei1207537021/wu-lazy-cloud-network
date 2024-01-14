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
            <el-form-item label="sql类型" prop="sqlType">
                <el-select
                    v-model="sqlType"
                    placeholder="sql类型"
                    @change="getDataList"
                >
                    <el-option label="查询" value="SELECT" />
                    <el-option label="更新" value="UPDATE" />
                    <el-option label="插入" value="INSERT" />
                    <el-option label="删除" value="DELETE" />
                    <el-option label="创建" value="CREATE" />
                    <el-option label="DDL" value="DDL" />
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
                    v-permission="['batchDel']"
                    size="small"
                    type="danger"
                    @click="batchDelSqlAudit"
                >
                    {{ buttons.batchDel.name }}
                </el-button>
                <el-button
                    v-permission="['batchExport']"
                    size="small"
                    type="primary"
                    @click="batchExportSqlAudit"
                >
                    {{ buttons.batchExport.name }}
                </el-button>
            </template>
            <el-table-column type="selection" width="55" />
            <el-table-column prop="id" label="sql执行ID" show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                prop="applicationName"
                label="应用名称"
            ></el-table-column>
            <el-table-column prop="instanceId" label="实例ID">
                <template v-slot="scope">
                    <el-select
                        v-model="scope.row.instanceId"
                        placeholder="实例ID"
                        filterable
                        :disabled="true"
                    >
                        <el-option
                            v-for="item in serverList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column prop="sqlType" label="执行类型"></el-table-column>
            <el-table-column prop="schema" label="数据库"> </el-table-column>
            <el-table-column prop="ip" label="ip"></el-table-column>
            <el-table-column
                prop="tableList"
                label="操作的表"
            ></el-table-column>
            <el-table-column
                prop="executeSql"
                label="执行sql"
                show-overflow-tooltip
            >
            </el-table-column>
            <el-table-column
                prop="requestId"
                label="请求ID"
                show-overflow-tooltip
            ></el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间"
            ></el-table-column>
            <el-table-column
                prop="updateTime"
                label="更新时间"
            ></el-table-column>
        </ve-table>
    </div>
</template>
<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    data: () => ({
        description: "SQL审计",
        buttons: {
            search: { name: "查询" },
            batchDel: { name: "批量删除SQL审计" },
            batchExport: { name: "批量导出" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "DataLine",
        name: "SQL审计",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";

//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";

const { proxy } = getCurrentInstance();
const queryForm = ref(null);
const tableData = ref([]);
const schemaNameList = ref(null);
const serverList = ref(null);
const batchSelectSQLAuditList = ref(null);
const params = reactive({
    tableName: "",
    schemaNameId: "",
    instanceId: "",
    status: true,
    sqlType: "",
    size: 10,
    current: 1,
    total: 0,
});
const { tableName, size, sqlType, current, total } = toRefs(params);

/**
 * 全选事件
 *
 */
const handleSelectionChange = (val) => {
    batchSelectSQLAuditList.value = val;
    console.log(batchSelectSQLAuditList.value);
};

/**
 * 批量导出
 */
const batchExportSqlAudit = async () => {
    let res = await VE_API.system.slqAuditExport(params, {
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
 *
 * 批量删除
 */
const batchDelSqlAudit = () => {
    if (batchSelectSQLAuditList.value.length == 0) {
        proxy.$message({
            type: "info",
            message: "请选择一个SQL记录",
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
            let ids = batchSelectSQLAuditList.value.map((item) => {
                return item.id;
            });
            const { code } = await VE_API.system.batchDeleteSlqAudit(ids);
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
    const { code, data } = await VE_API.system.slqAuditPage(params);
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
    schemaNameList.value = [];
    let res;
    if (instanceId == null || instanceId === "") {
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
