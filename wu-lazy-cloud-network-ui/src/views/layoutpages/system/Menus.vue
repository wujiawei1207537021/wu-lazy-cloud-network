<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="名称" prop="name">
                <el-input
                    clearable
                    v-model="name"
                    placeholder="名称"
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
                rowKey: 'code',
                defaultExpandAll: true,
            }"
            :pagination="{
                hideOnSinglePage: true,
                onSizeChange: (val) =>
                    handleSizeChange(val, params, getDataList),
                onCurrentChange: (val) =>
                    handleCurrentChange(val, params, getDataList),
                currentPage: page,
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
                <el-button
                    v-permission="['restore']"
                    size="small"
                    type="primary"
                    @click="handleMenuRestore(buttons.restore.name)"
                >
                    {{ buttons.restore.name }}
                </el-button>
            </template>
            <el-table-column prop="name" label="名称"></el-table-column>
            <el-table-column prop="icon" label="图标">
                <template v-slot="{ row }">
                    <template v-if="row.type !== 2">
                        <el-icon
                            style="margin-right: 6px; vertical-align: middle"
                        >
                            <component :is="row.icon" />
                        </el-icon>
                        <span style="vertical-align: middle">
                            {{ row.icon }}
                        </span>
                    </template>
                    <span v-else>/</span>
                </template>
            </el-table-column>
            <el-table-column prop="type" label="类型">
                <template v-slot="{ row }">
                    <el-tag
                        :type="
                            row.type === 0
                                ? ''
                                : row.type === 1
                                  ? 'success'
                                  : 'warning'
                        "
                    >
                        {{
                            row.type == 0
                                ? "目录"
                                : row.type == 1
                                  ? "菜单"
                                  : "按钮"
                        }}
                    </el-tag>
                </template>
            </el-table-column>
            <el-table-column prop="sort" label="排序">
                <template v-slot="{ row }">
                    <span v-if="row.type !== 2" style="font-weight: bold">
                        {{ row.sort }}
                    </span>
                    <span v-else>/</span>
                </template>
            </el-table-column>
            <el-table-column prop="iframe" label="Iframe">
                <template v-slot="{ row }">
                    <el-tag
                        v-if="row.type == 1"
                        :type="row.iframe == 0 ? 'danger' : ''"
                        effect="dark"
                    >
                        {{ row.iframe == 0 ? "否" : "是" }}
                    </el-tag>
                    <span v-else>/</span>
                </template>
            </el-table-column>
            <el-table-column prop="url" label="URL/标识" show-overflow-tooltip>
                <template v-slot="{ row }">
                    <template v-if="row.type === 1">
                        <el-link
                            v-if="isURL(row.url)"
                            type="primary"
                            :href="row.url"
                            target="_blank"
                        >
                            {{ row.url }}
                        </el-link>
                        <span v-else>{{ row.url }}</span>
                    </template>
                    <span v-else-if="row.type === 2">{{ row.menu }}</span>
                    <span v-else>/</span>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="240">
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
                        v-permission="['addChild']"
                        v-if="row.type === 0"
                        @click.prevent="handleEdit(buttons.addChild.name, row)"
                        type="warning"
                        size="small"
                    >
                        {{ buttons.addChild.name }}
                    </el-button>
                    <el-button
                        v-permission="['addBtn']"
                        v-if="row.type === 1 && row.iframe === 0"
                        @click.prevent="handleEdit(buttons.addBtn.name, row)"
                        type="success"
                        size="small"
                    >
                        {{ buttons.addBtn.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <menu-edit
            v-if="showDialog"
            :menuList="tableData"
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
        description: "菜单查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            restore: { name: "恢复出厂设置" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            addChild: { name: "添加子级" },
            addBtn: { name: "添加按钮" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Menu",
        name: "菜单管理",
        parentMenu: menu,
    }),
};
</script>

<script setup>
import { isURL } from "@/utils/validate";
import MenuEdit from "./components/MenuEdit";
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
const dialogTitle = ref("");
const showDialog = ref(false);
const rowData = ref(null);
const tableData = ref([]);
const params = reactive({
    name: "",
    size: 10,
    page: 1,
    total: 0,
});
const { name, size, page, total } = toRefs(params);

/**
 * 处理菜单恢复出厂设置操作
 * */
const handleMenuRestore = () => {
    // 查询当前项目所有菜单
    // 存储当前菜单（更新、或者插入）
    let menusFiles = getMenusFiles();
    let parentMenuList = [];
    // console.log(menusFiles);
    let menuList = menusFiles.map((menu) => {
        var parentCode = menu.url.substring(0, menu.url.lastIndexOf("/"));
        // 父菜单
        parentMenuList.push({
            code: parentCode,
            url: "",
            name: menu.parentMenu ? menu.parentMenu.name : parentCode,
            // type 0:目录 1：菜单 2：按钮
            type: "0",
            icon: menu.icon,
            sort: "1",
            iframe: "0",
            parentCode: "-1",
            desc: "",
            isDeleted: false,
        });
        // 菜单
        return {
            code: menu.url.replace("/", "_"),
            url: menu.url,
            name: menu.name,
            // type 0:目录 1：菜单 2：按钮
            type: "1",
            icon: menu.icon,
            sort: "1",
            iframe: "0",
            parentCode: parentCode,
            desc: menu.description,
            isDeleted: false,
        };
    });
    // 存储按钮 7

    let buttonList = [];
    menusFiles.forEach((menu) => {
        var parentCode = menu.url.substring(0, menu.url.lastIndexOf("/"));
        let buttons = menu.buttons;
        if (buttons !== null && buttons !== undefined) {
            Object.keys(buttons)
                .filter((key) => {
                    return buttons[key].name != null;
                })
                .forEach((key) => {
                    buttonList.push({
                        code: menu.url.replace("/", "_") + "_" + key,
                        url: "",
                        name: buttons[key].name,
                        // type 1：菜单 2：按钮
                        type: "2",
                        icon: "",
                        sort: "1",
                        iframe: "1",
                        parentCode: menu.url.replace("/", "_"),
                        desc: menu.description + "-" + buttons[key].name,
                        isDeleted: false,
                        menu: key,
                    });
                });
        }
    });
    let menus = menuList.concat(parentMenuList, buttonList);
    VE_API.system.menuUpsert(menus).then((res) => {
        if (res && res.code === 0) {
            getDataList();
        }
        console.log(res);
    });
};

/***
 * 获取当前所有菜单文件
 * **/
const getMenusFiles = () => {
    const menusFiles = require.context("@/views/layoutpages", true, /\.vue$/);
    return menusFiles
        .keys()
        .map((key) => {
            return {
                url: key.replace(/^(\.\/)|(\.vue)/g, ""),
                description:
                    menusFiles(key).default.data &&
                    (menusFiles(key).default.data().description || "无"),
                buttons:
                    menusFiles(key).default.data &&
                    (menusFiles(key).default.data().buttons || []),
                type:
                    menusFiles(key).default.data &&
                    (menusFiles(key).default.data().type || "无"),
                icon:
                    menusFiles(key).default.data &&
                    (menusFiles(key).default.data().icon ||
                        dynamicChooseIcons()),
                name:
                    menusFiles(key).default.data &&
                    (menusFiles(key).default.data().name || "无"),
                parentMenu:
                    menusFiles(key).default.data &&
                    menusFiles(key).default.data().parentMenu,
            };
        })
        .filter((key) => {
            return !key.url.includes("/components/");
        });
};
/**
 * 动态获取获取icon
 * @returns {*[]}
 */
const dynamicChooseIcons = () => {
    const components = require("@element-plus/icons-vue");
    console.log("🚀 ~ file: index.js ~ line 107 ~ icons ~ e", components);

    const names = [];
    for (const key in components) {
        names.push(components[key].name);
    }
    return names[0];
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
            const { code } = await VE_API.system.menuDel({ id });
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
    const { code, data } = await VE_API.system.menuList(params);
    if (code === 0) {
        const list = XE.mapTree(
            XE.toArrayTree(data, {
                sortKey: "sort",
                key: "code",
                parentKey: "parentCode",
            }),
            (item) => {
                if (item.children && item.children.length <= 0) {
                    delete item.children;
                }
                return item;
            },
        );
        tableData.value = list;
    }
};

onMounted(async () => {
    await getDataList();
    // maxHeight(pagination, queryForm, toolBar, ve_max_height);
});
</script>

<style lang="scss" scoped></style>
