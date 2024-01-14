<template>
    <div class="ve_container">
        <!--        <el-descriptions border>-->
        <!--            <el-descriptions-item label="实例名称"-->
        <!--                >实例名称-->
        <!--            </el-descriptions-item>-->
        <!--            <el-descriptions-item label="实例数据库数量"-->
        <!--                >实例数据库数量-->
        <!--            </el-descriptions-item>-->
        <!--            <el-descriptions-item label="实例表数量"-->
        <!--                >实例表数量-->
        <!--            </el-descriptions-item>-->
        <!--        </el-descriptions>-->

        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="数据库实例" prop="instanceId">
                <el-select
                    v-model="params.instanceId"
                    placeholder="数据库实例"
                    filterable
                >
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
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
                    v-permission="['backups']"
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.backups.name)"
                >
                    {{ buttons.backups.name }}
                </el-button>
            </template>
            <el-table-column prop="id" label="记录ID"></el-table-column>
            <el-table-column
                prop="instanceName"
                label="实例名称"
            ></el-table-column>
            <el-table-column
                prop="schemaNum"
                label="数据库数量"
            ></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>

            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
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
        <database-instance-back-ups-add
            v-if="showsBackUpsDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showsBackUpsDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>

<script>
import DatabaseInstanceBackUpsAdd from "@/views/layoutpages/acw/components/DatabaseInstanceBackUpsAdd";
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    components: { DatabaseInstanceBackUpsAdd },
    data: () => ({
        description: "数据库实例备份",
        buttons: {
            search: { name: "查询" },
            del: { name: "删除数据库实例备份" },
            backups: { name: "实例备份" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Guide",
        name: "数据库实例备份",
        parentMenu: acwMenu,
    }),
};
</script>
<script setup>
import { reactive, toRefs, ref, defineProps, onMounted } from "vue";
import databaseInstanceBackUpsAdd from "./components/DatabaseInstanceBackUpsAdd";

import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";

const props = defineProps({
    showDialog: {
        type: Boolean,
        default: true,
    },
    title: {
        type: String,
        default: "添加",
    },
    rowData: {
        type: Object,
        default: null,
    },
});

const { rowData } = toRefs(props);

const queryForm = ref(null);
const tableData = ref([]);
const showsBackUpsDialog = ref(false);
const serverList = ref(null);

const params = reactive({
    instanceName: "",
    initializeToLocal: "",
    size: 10,
    current: 1,
    total: 0,
});

const form = reactive({
    name: "",
    userName: "",
    password: "",
    role: "",
    status: 1,
});
const { size, current, total } = toRefs(params);
const { userName, name, password, role, status } = toRefs(form);
const roleList = ref([]);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((name.value = rowData.value.name),
    (userName.value = rowData.value.userName),
    (password.value = rowData.value.password),
    (role.value = rowData.value.role),
    (status.value = rowData.value.status));

/**
 * @description:添加or编辑事件
 * @param {*}
 * @return {*}
 */
const handleEdit = (title, row = null) => {
    showsBackUpsDialog.value = true;
    rowData.value = row;
};
/**
 * 删除数据库事例备份
 */
const handleDel = async (id) => {
    const { code } = await VE_API.system.databaseInstanceBackUpDelete({
        id: id,
    });
    if (code === 0) {
        getDataList();
    }
};
/**
 * @description: 获取角色列表
 * @param {*}
 * @return {*}
 */
const getRoleList = async () => {
    const { code, data } = await VE_API.system.roleList(
        {
            page: 1,
            size: 10,
        },
        { Global: false },
    );
    if (code === "0") {
        const { list } = data;
        roleList.value = list;
    }
};
getRoleList();

/**
 * 隐藏弹框
 * */
const handelDialog = (e) => {
    showsBackUpsDialog.value = e;
    getDataList();
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } =
        await VE_API.system.databaseInstanceBackUpPage(params);
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
    }
};

// 查询数据库实例
const getServerInstanceList = async () => {
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
        return serverList.value;
    });
};

onMounted(async () => {
    await getServerInstanceList();
    await getDataList();
});
</script>

<style lang="scss" scoped></style>
