<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="客户端名称" prop="clientId">
                <el-input
                    clearable
                    v-model="clientId"
                    placeholder="客户端名称"
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
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
            </template>

            <el-table-column prop="ip" label="地址"></el-table-column>
            <el-table-column
                prop="clientId"
                label="客户端名称"
            ></el-table-column>
            <el-table-column prop="port" label="端口"></el-table-column>
            <el-table-column prop="path" label="路径"></el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间"
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
                        v-permission="['info']"
                        @click.prevent="handleInfo('详情', row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.info.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>
    </div>
</template>
<script>
import acwClientRegisterMenu from "@/views/layoutpages/acw_server/AcwClientRegisterMenu";

export default {
    data: () => ({
        description: "注册中心",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            info: { name: "详情" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Opportunity",
        name: "注册中心",
        parentMenu: acwClientRegisterMenu,
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

const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);
const showInfo = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    clientId: "",
    size: 10,
    current: 1,
    total: 0,
});
const { clientId, size, current, total } = toRefs(params);
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
 * 行信息
 */
const handleInfo = (title, row = null) => {
    showInfo.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};

/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.clientInstancePage(params);
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
});
</script>

<style lang="scss" scoped></style>
