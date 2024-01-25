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
            <el-table-column prop="clientId" label="客户端ID"></el-table-column>
            <el-table-column prop="outFlow" label="客户端出口流量">
            </el-table-column>
            <el-table-column prop="inFlow" label="客户端进口流量">
            </el-table-column>
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button
                        v-permission="['offLine']"
                        @click.prevent="handleOffLine(row.clientId)"
                        type="danger"
                        size="small"
                    >
                        {{ buttons.offLine.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>
    </div>
</template>
<script>
import cloudNetworkMenu from "@/views/layoutpages/cloud_network/components/CloudNetworkMenu";

export default {
    data: () => ({
        description: "客户端流量管理",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            offLine: { name: "下线" },
            sendMessage: { name: "发送消息" },
            export: { name: "导出用户" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Avatar",
        name: "客户端流量管理",
        parentMenu: cloudNetworkMenu,
    }),
};
</script>

<script setup>
import { reactive, toRefs, ref, onMounted } from "vue";
//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";

const queryForm = ref(null);
const tableData = ref([]);

// eslint-disable-next-line no-unused-vars
const rowData = ref(null);
// eslint-disable-next-line no-unused-vars
const showDialog = ref(false);

const params = reactive({
    clientId: "",
    size: 10,
    current: 1,
    total: 0,
});
const { clientId, size, current, total } = toRefs(params);

/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } =
        await VE_API.cloudNetwork.visitorClientFlowPage(params);
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
