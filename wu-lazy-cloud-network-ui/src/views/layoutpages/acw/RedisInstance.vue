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

        <el-button
            v-permission="['addInstance']"
            size="small"
            type="primary"
            @click="handleEdit(buttons.addInstance.name)"
        >
            {{ buttons.addInstance.name }}
        </el-button>

        <el-row v-if="tableData && tableData.length > 0">
            <el-col span="8" style="margin-right: 10%">
                <el-tree
                    :props="resourceProps"
                    :load="loadNode"
                    :data="tableData"
                    @node-click="checkNode"
                    lazy
                    show-checkbox
                    :filter-node-method="filterNode"
                >
                    <template #default="{ data }">
                        <span class="custom-tree-node">
                            <img
                                v-if="data.type === 'instance'"
                                src="../../../../src/static/redis.png"
                                style="width: 16px; height: 16px"
                            />
                            <img
                                v-if="data.type === 'database'"
                                src="../../../../src/static/database.png"
                                style="width: 16px; height: 16px"
                            />
                            <img
                                v-if="data.type === 'key'"
                                src="../../../../src/static/key.png"
                                style="width: 16px; height: 16px"
                            />
                            <!--                        实例展示  -->
                            <span v-if="data.type === 'instance'">
                                {{ data.name }}
                            </span>
                            <el-button
                                v-if="data.type === 'instance'"
                                v-permission="['editInstance']"
                                size="small"
                                type="primary"
                                @click="
                                    handleEdit(buttons.editInstance.name, data)
                                "
                            >
                                {{ buttons.editInstance.name }}
                            </el-button>
                            <el-button
                                v-if="data.type === 'instance'"
                                v-permission="['delInstance']"
                                size="small"
                                type="primary"
                                @click="handleDel(data.id)"
                            >
                                {{ buttons.delInstance.name }}
                            </el-button>
                            <!--                          数据库展示 -->
                            <span v-if="data.type === 'database'">
                                {{ data.name }} ({{ data.keysNum }})
                            </span>
                            <!--                         key展示 -->
                            <span v-if="data.type === 'key'">
                                {{ data.name }}
                            </span>

                            <el-button
                                v-if="data.type === 'key'"
                                v-permission="['delKey']"
                                @click.prevent="handleDelKey(data)"
                                type="danger"
                                size="small"
                            >
                                {{ buttons.delKey.name }}
                            </el-button>
                        </span>
                    </template>
                </el-tree>
            </el-col>
            <el-col
                span="16"
                v-if="
                    currentClickRowData &&
                    (currentClickRowData.type === 'database' ||
                        currentClickRowData.type === 'key')
                "
                style="position: absolute; right: 20px"
            >
                <!-- 搜索key -->
                <!--                <el-form ref="queryForm" :inline="true" :model="params">-->
                <!--                    <el-form-item label="key" prop="key">-->
                <!--                        <el-input-->
                <!--                            clearable-->
                <!--                            v-model="instanceName"-->
                <!--                            placeholder="key"-->
                <!--                        ></el-input>-->
                <!--                    </el-form-item>-->

                <!--                    <el-form-item>-->
                <!--                        <el-button-->
                <!--                            type="primary"-->
                <!--                            @click="onSubmit(params, getDataList)"-->
                <!--                        >-->
                <!--                            {{ buttons.search.name }}-->
                <!--                        </el-button>-->
                <!--                        <el-button-->
                <!--                            @click="resetForm(queryForm, params, getDataList)"-->
                <!--                        >-->
                <!--                            重置-->
                <!--                        </el-button>-->
                <!--                    </el-form-item>-->
                <!--                </el-form>-->
                <!--    展示database  下多有的keys        -->
                <ve-table
                    :table="{
                        data: currentShowData.redisInstanceConsoleFindKeysValues,
                    }"
                    :pagination="{
                        onSizeChange: (val) =>
                            handleSizeChange(
                                val,
                                params,
                                findKeysValues(currentClickRowData.value),
                            ),
                        onCurrentChange: (val) =>
                            handleCurrentChange(
                                val,
                                params,
                                findKeysValues(currentClickRowData.value),
                            ),
                        currentPage: current,
                        pageSize: size,
                        total: total,
                    }"
                >
                    <template #tool_bar>
                        <el-button
                            v-permission="['addKey']"
                            size="small"
                            type="primary"
                            @click="handleKeyEdit({})"
                        >
                            {{ buttons.addKey.name }}
                        </el-button>
                    </template>
                    <el-table-column prop="key" label="key"></el-table-column>
                    <el-table-column
                        prop="value"
                        label="value"
                    ></el-table-column>
                    <el-table-column fixed="right" label="操作">
                        <template v-slot:default="{ row }">
                            <el-button
                                v-permission="['editKey']"
                                @click.prevent="handleKeyEdit(row)"
                                type="primary"
                                size="small"
                            >
                                {{ buttons.editKey.name }}
                            </el-button>
                            <el-button
                                v-permission="['delKey']"
                                @click.prevent="handleDelKey(row)"
                                type="danger"
                                size="small"
                            >
                                {{ buttons.delKey.name }}
                            </el-button>
                        </template>
                    </el-table-column>
                </ve-table>
                <!--    展示database  的keys结束        -->
            </el-col>
        </el-row>
        <el-empty v-show="tableData.length === 0" />
        <!-- 列表 -->
        <!--        <ve-table-->
        <!--            :table="{-->
        <!--                data: tableData,-->
        <!--            }"-->
        <!--            :pagination="{-->
        <!--                onSizeChange: (val) =>-->
        <!--                    handleSizeChange(val, params, getDataList),-->
        <!--                onCurrentChange: (val) =>-->
        <!--                    handleCurrentChange(val, params, getDataList),-->
        <!--                currentPage: current,-->
        <!--                pageSize: size,-->
        <!--                total: total,-->
        <!--            }"-->
        <!--        >-->
        <!--            <template #tool_bar>-->
        <!--                <el-button-->
        <!--                    v-permission="['add']"-->
        <!--                    size="small"-->
        <!--                    type="primary"-->
        <!--                    @click="handleEdit(buttons.add.name)"-->
        <!--                >-->
        <!--                    {{ buttons.add.name }}-->
        <!--                </el-button>-->
        <!--            </template>-->
        <!--            <el-table-column-->
        <!--                prop="instanceName"-->
        <!--                label="服务器名称"-->
        <!--            ></el-table-column>-->
        <!--            <el-table-column prop="username" label="登录名"></el-table-column>-->
        <!--            <el-table-column prop="password" label="登陆密码">-->
        <!--                <template v-slot="{ row }">-->
        <!--                    <span>-->
        <!--                        {{-->
        <!--                            row.password &&-->
        <!--                            row.password-->
        <!--                                .split("")-->
        <!--                                .fill("*", 1, -1)-->
        <!--                                .join()-->
        <!--                                .replace(/\,/g, "")-->
        <!--                        }}-->
        <!--                    </span>-->
        <!--                </template>-->
        <!--            </el-table-column>-->
        <!--            <el-table-column prop="host" label="host"></el-table-column>-->
        <!--            <el-table-column prop="port" label="端口"></el-table-column>-->
        <!--            <el-table-column prop="status" label="状态"></el-table-column>-->
        <!--            <el-table-column prop="sort" label="排序"></el-table-column>-->

        <!--            <el-table-column fixed="right" label="操作">-->
        <!--                <template v-slot:default="{ row }">-->
        <!--                    <el-button-->
        <!--                        v-permission="['edit']"-->
        <!--                        @click.prevent="handleEdit(buttons.edit.name, row)"-->
        <!--                        type="primary"-->
        <!--                        size="small"-->
        <!--                    >-->
        <!--                        {{ buttons.edit.name }}-->
        <!--                    </el-button>-->
        <!--                    <el-button-->
        <!--                        v-permission="['del']"-->
        <!--                        @click.prevent="handleDel(row.id)"-->
        <!--                        type="danger"-->
        <!--                        size="small"-->
        <!--                    >-->
        <!--                        {{ buttons.del.name }}-->
        <!--                    </el-button>-->
        <!--                </template>-->
        <!--            </el-table-column>-->
        <!--        </ve-table>-->

        <!-- 编辑实例组件 -->
        <redis-instance-edit
            v-if="showDialog.instanceEdit"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog.instanceEdit"
            @closeDialog="handelDialog($event)"
        />

        <!--  编辑key -->
        <el-dialog
            :title="新增或者修改key"
            append-to-body
            destroy-on-close
            :model-value="showDialog.keyEdit"
            @close="handelKeyDialog($event)"
        >
            <!-- 表单 -->
            <el-form
                :model="redisKeyData"
                ref="formRef"
                label-width="120px"
                :inline="false"
            >
                <el-form-item label="数据库的key" prop="redisKeyData.key">
                    <el-input
                        v-model="redisKeyData.key"
                        placeholder=""
                        clearable
                    ></el-input>
                </el-form-item>
                <el-form-item label="value" prop="redisKeyData.value">
                    <el-input
                        v-model="redisKeyData.value"
                        placeholder=""
                        clearable
                    ></el-input>
                </el-form-item>
            </el-form>

            <template v-slot:footer>
                <span>
                    <el-button @click="handelKeyDialog(false)">取消</el-button>
                    <el-button type="primary" @click="handelAddKey()"
                        >确定</el-button
                    >
                </span>
            </template>
        </el-dialog>
    </div>
</template>

<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";

export default {
    data: () => ({
        description: "Redis实例管理",
        buttons: {
            search: { name: "查询" },
            addInstance: { name: "添加Redis实例" },
            addKey: { name: "添加Redis key" },
            add: { name: "添加实例" },
            editInstance: { name: "编辑redis实例" },
            editKey: { name: "编辑Redis key" },
            delKey: { name: "删除Redis key" },
            delInstance: { name: "删除Redis实例" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Collection",
        name: "Redis实例管理",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import redisInstanceEdit from "./components/RedisInstanceEdit.vue";

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
const showDialog = ref({});
const queryForm = ref(null);
const tableData = ref([]);
const redisKeyData = ref({});

// 当前node节点对应的数据
const currentClickRowData = ref({});

const currentShowData = ref({}); // 当前展示的所有数据都在这里

const params = reactive({
    instanceName: "",
    size: 10,
    current: 1,
    total: 0,
});
const { instanceName, size, current, total } = toRefs(params);

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
    showDialog.value.instanceEdit = true;
    dialogTitle.value = title;
    rowData.value = row;
};
/**
 * 编辑key
 */
const handleKeyEdit = (row) => {
    showDialog.value.keyEdit = true;
    redisKeyData.value = row;
};
/**
 * 删除key
 */
const handleDelKey = (row) => {
    VE_API.system.redisInstanceConsoleRemoveKey(row);
};

/**
 * @description: dialog事件
 * @param {*}
 * @return {*}
 */
const handelDialog = (e) => {
    showDialog.value.instanceEdit = e;
    getDataList();
};
/**
 *
 * @param e
 */
const handelKeyDialog = (e) => {
    showDialog.value.keyEdit = e;
    getDataList();
};
/**
 * 添加key
 */
const handelAddKey = async () => {
    redisKeyData.value.instanceId = currentClickRowData.value.instanceId;
    redisKeyData.value.database = currentClickRowData.value.database;

    const { code } = await VE_API.system.redisInstanceConsoleSetKey(
        redisKeyData.value,
    );
    if (code === 0) {
        handelKeyDialog(false);
    }
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
            const { code } = await VE_API.system.redisInstanceDelete({ id });
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
    const { code, data } = await VE_API.system.redisInstancePage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;

        record.map((item) => {
            item.label = item.instanceName;
            item.name = item.instanceName;
            item.value = item.id;
            item.instanceId = item.id;
        });
        tableData.value = record;
        leftServerTree.value = record;
    }
};
onMounted(async () => {
    await getDataList();
    // maxHeight(pagination, queryForm, toolBar, ve_max_height);
});

const resourceProps = {
    label: "name",
    children: "children",
    isLeaf: "leaf",
};

const loadNode = async (resourceNode, resolve) => {
    console.log(resourceNode);
    if (resourceNode.level === 0) {
        return resolve([]);
    }

    resourceNode.loaded = true;
    const resourceNodeData = resourceNode.data;
    currentClickRowData.value = resourceNodeData.data;
    if (resourceNodeData.isFile) {
        return resolve([]);
    }
    console.log(resourceNodeData);

    let res;
    if (resourceNodeData) {
        if (resourceNodeData && resourceNodeData.type === "instance") {
            // 查询database
            res = await VE_API.system.redisInstanceConsoleFindDataBases({
                instanceId: resourceNodeData.id,
            });
            if (res.code === 0) {
                res.data.record.map((item) => {
                    item.name = item.database;
                });
            }
        } else if (resourceNodeData.type === "database") {
            // 查询key
            res = await VE_API.system.redisInstanceConsoleFindKeys({
                instanceId: resourceNodeData.instanceId,
                database: resourceNodeData.database,
            });
            if (res.code === 0) {
                res.data.record.map((item) => {
                    item.name = item.key;
                });
            }
        } else if (resourceNodeData.type === "key") {
            // 查询数据
        }
        if (res) {
            const { code, data } = res;
            if (code === 0) {
                if (data == null) {
                    return resolve([]);
                }
                // resourceNode.childNodes = data;
                console.log(resourceNode);
                return resolve(data.record);
            }
        } else {
            return resolve([]);
        }
    }
};
/**
 * 点击 node节点
 */
const checkNode = (node) => {
    currentClickRowData.value = node;
    if (currentClickRowData.value.type === "database") {
        findKeysValues(currentClickRowData.value);
    }
    findKeyValue(currentClickRowData.value);

    console.log(currentClickRowData.value);
};

/**
 * 过滤node
 * @param node
 */
const filterNode = (value, tree) => {
    if (!value) return true;
    return tree.label.includes(value);
};
/**
 * 获取所有的keys
 * @param currentClickRowData
 * @returns {Promise<void>}
 */
const findKeysValues = async (currentClickRowData) => {
    if (currentClickRowData.type === "database") {
        const res =
            await VE_API.system.redisInstanceConsoleFindKeysValues(
                currentClickRowData,
            );
        const { code, data } = res;
        if (code === 0) {
            currentShowData.value.redisInstanceConsoleFindKeysValues =
                data.record;
            console.log(currentShowData.value);
        } else {
            console.log(res);
        }
    }
};

/**
 * 获取key对应的value
 * @param currentClickRowData
 * @returns {Promise<void>}
 */
const findKeyValue = async (currentClickRowData) => {
    if (currentClickRowData.type === "key") {
        const res =
            await VE_API.system.redisInstanceConsoleFindKeyValue(
                currentClickRowData,
            );
        const { code, data } = res;
        if (code === 0) {
            currentShowData.value.redisInstanceConsoleFindKeysValues =
                data.record;
            console.log(currentShowData.value);
        } else {
            console.log(res);
        }
    }
};

/**
 * 初始化加载数据
 */
onMounted(async () => {
    await getDataList();
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
