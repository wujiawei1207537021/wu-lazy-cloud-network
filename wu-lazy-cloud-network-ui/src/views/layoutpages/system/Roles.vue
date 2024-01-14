<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="名称" prop="name">
                <el-input
                    clearable
                    v-model="name"
                    placeholder="请输入"
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
            <el-table-column prop="code" label="角色编码"></el-table-column>
            <el-table-column
                prop="name"
                label="角色名"
                width="120"
            ></el-table-column>
            <el-table-column prop="status" label="状态">
                <template v-slot="{ row }">
                    <el-tag :type="row.status ? '' : 'danger'">
                        {{ row.status ? "启用" : "停用" }}
                    </el-tag>
                </template>
            </el-table-column>
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
                        v-permission="['member']"
                        @click.prevent="allMember(row.id)"
                        type="info"
                        size="small"
                    >
                        {{ buttons.member.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <role-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>
<script>
import menu from "@/views/layoutpages/system/components/menu";

export default {
    data: () => ({
        description: "角色信息查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            member: { name: "查看成员", toPath: true }, //topath:true 需要设置跳转路径
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Stamp",
        name: "角色管理",
        parentMenu: menu,
    }),
};
</script>

<script setup>
import RoleEdit from "./components/RoleEdit";
import {
    reactive,
    toRefs,
    computed,
    ref,
    onMounted,
    getCurrentInstance,
} from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { findName } from "../common";

//*导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";

const { proxy } = getCurrentInstance();
const route = useRoute();
const router = useRouter();
const store = useStore();
const menuList = computed(() => store.getters.menuList).value;

const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);

const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    name: "",
    size: 10,
    current: 1,
    total: 0,
});
const { name, size, current, total } = toRefs(params);

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
            const { code } = await VE_API.system.roleDel({ id });
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
 * @description:查看成员
 * @param {*}
 * @return {*}
 */
const allMember = (id) => {
    // 获取当前path的id
    let pathId = route.name.slice(route.name.lastIndexOf("-") + 1);
    // 获取要跳转到的路由
    const toName = findName("member", "system/Users", pathId, menuList, proxy);
    router.push({ name: toName, query: { id } });
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.rolePage(params);
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
