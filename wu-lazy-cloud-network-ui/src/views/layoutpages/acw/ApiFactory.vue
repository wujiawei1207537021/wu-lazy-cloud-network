<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="接口分组" prop="interfaceGroup">
                <el-input
                    clearable
                    v-model="interfaceGroup"
                    placeholder="接口分组"
                ></el-input>
            </el-form-item>
            <el-form-item label="接口描述" prop="interfaceDescribe">
                <el-input
                    clearable
                    v-model="interfaceDescribe"
                    placeholder="接口描述"
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
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
            </template>

            <el-table-column prop="tag" label="接口分组"></el-table-column>
            <el-table-column
                prop="description"
                label="接口描述"
            ></el-table-column>
            <el-table-column prop="method" label="接口类型"></el-table-column>
            <el-table-column prop="path" label="接口地址"></el-table-column>
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

        <!-- 编辑组件 -->
        <api-factory-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
        <!-- 编辑组件 -->
        <api-factory-edit
            v-if="showInfo"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showInfo"
            @closeDialog="handelInfo($event)"
        />
    </div>
</template>
<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";

export default {
    data: () => ({
        description: "接口工厂查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            info: { name: "详情" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Histogram",
        name: "接口工厂管理",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import ApiFactoryEdit from "@/views/layoutpages/acw/components/ApiFactoryEdit";
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
const showInfo = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    interfaceGroup: "",
    interfaceDescribe: "",
    size: 10,
    current: 1,
    total: 0,
});
const { interfaceGroup, interfaceDescribe, size, current, total } =
    toRefs(params);
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
            const { code } = await VE_API.system.apiDelete({ id });
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
 * 行信息
 */
const handleInfo = (title, row = null) => {
    showInfo.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};
const handelInfo = (e) => {
    showInfo.value = e;
    getDataList();
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.apiPage(params);
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
