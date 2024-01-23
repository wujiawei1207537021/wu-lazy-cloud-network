<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="客户端ID" prop="clientId">
                <el-input
                    clearable
                    v-model="clientId"
                    placeholder="客户端ID"
                ></el-input>
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
        >
            <template #tool_bar>
                <el-button
                    title="弹窗式"
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
            </template>
            <el-table-column prop="clientId" label="客户端ID"></el-table-column>
            <el-table-column prop="onLineState" label="客户端在线状态">
            </el-table-column>
            <el-table-column prop="stagingState" label="暂存状态">
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button
                        v-permission="['del']"
                        @click.prevent="handleDel(row.clientId)"
                        type="danger"
                        size="small"
                    >
                        {{ buttons.del.name }}
                    </el-button>
                    <el-button
                        v-permission="['sendMessage']"
                        @click.prevent="handleArouse2SendMessage(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.sendMessage.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>
        <!--发送消息到客户端-->
        <cloud-server-send-message2-clinet
            v-if="showDialog"
            :rowData="rowData"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>
<script>
import cloudNetworkMenu from "@/views/layoutpages/cloud_network/components/CloudNetworkMenu";

export default {
    data: () => ({
        description: "客户端管理",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            sendMessage: { name: "发送消息" },
            export: { name: "导出用户" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Avatar",
        name: "客户端管理",
        parentMenu: cloudNetworkMenu,
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
import CloudServerSendMessage2Clinet from "@/views/layoutpages/cloud_network/components/CloudServerSendMessage2Clinet.vue";

const { proxy } = getCurrentInstance();
const queryForm = ref(null);
const tableData = ref([]);

const rowData = ref(null);
const showDialog = ref(false);

const params = reactive({
    clientId: "",
    size: 10,
    current: 1,
    total: 0,
});
const { clientId, size, current, total } = toRefs(params);

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
 * @description:添加or编辑事件
 * @param {*}
 * @return {*}
 */
const handleArouse2SendMessage = (row = null) => {
    showDialog.value = true;
    rowData.value = row;
};

/**删除行数据
 * @description:
 * @param {*}
 * @return {*}
 */
const handleDel = (clientId) => {
    proxy
        .$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            const { code } = await VE_API.cloudNetwork.cloudClientDelete({
                clientId,
            });
            if (code == "00") {
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
        await VE_API.cloudNetwork.cloudClientFindPage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;
        tableData.value = record;
    }
};
onMounted(async () => {
    await getDataList();
    // maxHeight(pagination, queryForm, toolBar, ve_max_height);
});
</script>

<style lang="scss" scoped></style>
