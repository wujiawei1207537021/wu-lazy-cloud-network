<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="服务器名称" prop="instanceName">
                <el-input
                    clearable
                    v-model="instanceName"
                    placeholder="服务器名称"
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
            <el-table-column
                prop="instanceName"
                label="服务器名称"
            ></el-table-column>
            <el-table-column
                prop="driverClassName"
                label="驱动程序类名"
            ></el-table-column>
            <el-table-column prop="username" label="登录名"></el-table-column>
            <el-table-column prop="password" label="登陆密码">
                <template v-slot="{ row }">
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
                </template>
            </el-table-column>
            <el-table-column prop="host" label="host"></el-table-column>
            <el-table-column prop="port" label="端口"></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column prop="sort" label="排序"></el-table-column>
            <el-table-column label="数据源类型">
                <template v-slot="{ row }">
                    <el-select
                        v-model="row.lazyDataSourceType"
                        placeholder="数据源类型"
                        disabled
                    >
                        <el-option label="MySQL" value="MySQL" />
                        <el-option label="H2" value="H2" />
                        <el-option label="CLICK_HOUSE" value="CLICK_HOUSE" />
                        <el-option label="POSTGRESQL" value="POSTGRESQL" />
                    </el-select>
                </template>
            </el-table-column>
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
                        v-permission="['reload']"
                        @click="handleDatabaseInstanceReload(row.id)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.reload.name }}
                    </el-button>
                    <el-button
                        v-permission="['backups']"
                        @click="handleBackUpsRoute(row.id)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.backups.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <database-instance-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
        <!-- schema组件 -->
        <database-schema-edit
            v-if="showschemaDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showschemaDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>

<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";

export default {
    data: () => ({
        description: "数据库服务器查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            reload: { name: "初始化服务器" },
            backups: { name: "实例备份" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Coin",
        name: "数据库实例管理",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import databaseInstanceEdit from "./components/DatabaseInstanceEdit";
import DatabaseSchemaEdit from "./components/DatabaseSchemaEdit";
import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";
//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
    getAsyncRouteName,
} from "@/views/layoutpages/common";
import { useRoute, useRouter } from "vue-router";

const { proxy } = getCurrentInstance();

const route = useRoute();
const router = useRouter();

const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);
const showschemaDialog = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    instanceName: "",
    initializeToLocal: "",
    size: 10,
    current: 1,
    total: 0,
});
const { instanceName, initializeToLocal, size, current, total } =
    toRefs(params);

/**
 * 左侧服务器树
 * @type {Ref<UnwrapRef<*[]>>}
 */
const leftServerTree = ref([]);
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
 * @description: 数据库实例备份信息
 * @param {*}
 * @return {*}
 */
const handleBackUpsRoute = async (title) => {
    let path = "acw/DatabaseInstanceBackUps";
    const toName = await getAsyncRouteName(title, path, "add", {
        router,
        route,
    });
    router.push({ name: toName });
};
/**
 * 重新加载实例
 */
const handleDatabaseInstanceReload = async (id) => {
    // 重新加载服务器
    console.log("重新加载服务器:" + id);
    const { code } = await VE_API.system.databaseInstanceReload({ id });
    if (code === 0) {
        getDataList();
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
            const { code } = await VE_API.system.databaseInstanceDelete({ id });
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
    const { code, data } = await VE_API.system.databaseInstancePage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;
        tableData.value = record;
        record.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        leftServerTree.value = record;
    }
};
onMounted(async () => {
    await getDataList();
    // maxHeight(pagination, queryForm, toolBar, ve_max_height);
});
</script>

<style lang="scss" scoped>
.prefix {
    color: var(--el-color-primary);
    margin-right: 10px;
}
.prefix.is-leaf {
    color: var(--el-color-success);
}
</style>
