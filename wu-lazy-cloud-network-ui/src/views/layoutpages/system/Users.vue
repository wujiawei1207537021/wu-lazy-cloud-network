<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="角色" prop="role">
                <el-select clearable v-model="role" placeholder="请选择">
                    <el-option
                        v-for="item in roleList"
                        :key="item.code"
                        :label="item.name"
                        :value="item.code"
                    ></el-option>
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
                <el-button
                    title="路由式"
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEditRoute(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
                <el-button
                    title="路由式"
                    v-permission="['export']"
                    size="small"
                    type="primary"
                    @click="handleExportUserList(buttons.export.name)"
                >
                    {{ buttons.export.name }}
                </el-button>
            </template>
            <el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="password" label="密码">
                <template v-slot="{ row }">
                    <el-tooltip
                        class="item"
                        effect="dark"
                        :content="row.password"
                        placement="top"
                    >
                        <span>
                            {{
                                row.password &&
                                row.password
                                    .split("")
                                    .fill("*", 1, -1)
                                    .join()
                                    .replace(/\,/g, "")
                            }}
                        </span>
                    </el-tooltip>
                </template>
            </el-table-column>
            <el-table-column prop="roleList" label="角色">
                <template v-slot="{ row }">
                    <el-tag>
                        {{
                            row.roleList
                                .filter((item) => {
                                    if (row.roleList != null) {
                                        return item.name;
                                    }
                                })
                                .map((role) => {
                                    return role.name;
                                })
                        }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="状态">
                <template v-slot="{ row }">
                    <el-switch
                        :loading="row.load"
                        v-model="row.status"
                        :active-value="1"
                        :inactive-value="0"
                        active-color="#13ce66"
                        inactive-color="#ff4949"
                        @change="(val) => handelSwitch(val, row)"
                    >
                        >
                    </el-switch>
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
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <users-edit
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
        description: "用户信息查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            export: { name: "导出用户" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Avatar",
        name: "用户管理",
        parentMenu: menu,
    }),
};
</script>

<script setup>
import UsersEdit from "./components/UsersEdit";
import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";
import { useRoute, useRouter } from "vue-router";
//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
    getAsyncRouteName,
} from "@/views/layoutpages/common";

const { proxy } = getCurrentInstance();
const route = useRoute();
const router = useRouter();
const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    role: isNaN(route.query.id * 1) ? "" : route.query.id * 1,
    size: 10,
    current: 1,
    total: 0,
});
const { role, size, current, total } = toRefs(params);

const roleList = ref([]);

/***
 *  导出insert sql
 * */
const handleExportUserList = async (title, row = rowData.value) => {
    let res = await VE_API.system.userExportList(params, {
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
 * @description: 添加页面路由式
 * @param {*}
 * @return {*}
 */
const handleEditRoute = async (title) => {
    let path = "system/components/UsersEditRoute";
    const toName = await getAsyncRouteName(title, path, "add", {
        router,
        route,
    });
    router.push({ name: toName });
};
/**
 * @description: 获取角色列表
 * @param {*}
 * @return {*}
 */
const getRoleList = async () => {
    const { code, data } = await VE_API.system.roleList();
    if (code === 0) {
        roleList.value = data;
    }
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
 * @description:用户状态切换
 * @param {*}
 * @return {*}
 */
const handelSwitch = async (val, row) => {
    if (row.id == undefined) return;
    row.load = 1;
    const { code } = await VE_API.system.userEdit(row);
    setTimeout(() => {
        row.load = 0;
        if (code !== 0) {
            row.status = val === 1 ? 0 : 1;
        }
    }, 500);
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
            const { code } = await VE_API.system.userDel({ id });
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
    const { code, data } = await VE_API.system.userPage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        record.map((item) => {
            item.roleIds = item.roleList.map((role) => {
                return role.id;
            });
        });
        params.size = size;
        params.current = current;
        params.total = total;
        tableData.value = record;
    }
};
onMounted(async () => {
    await getRoleList();
    await getDataList();
    // maxHeight(pagination, queryForm, toolBar, ve_max_height);
});
</script>

<style lang="scss" scoped></style>
