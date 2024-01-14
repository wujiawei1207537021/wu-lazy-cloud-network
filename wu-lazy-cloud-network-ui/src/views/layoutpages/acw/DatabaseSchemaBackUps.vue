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
                    @change="getSchemaList"
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
            <el-form-item label="数据库名" prop="schemaName">
                <el-select
                    clearable
                    filterable
                    @change="getDataList"
                    v-model="params.schemaName"
                    placeholder="数据库名"
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
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
                prop="schemaName"
                label="数据库名称"
            ></el-table-column>
            <el-table-column
                prop="tableNum"
                label="数据库表数量"
                :sortable="true"
            ></el-table-column>
            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column prop="path" label="备份数据库地址"
                ><template #default="scope">
                    <a
                        style="color: #7099f9"
                        @click="downloadBackUps(scope.row)"
                        >{{ scope.row.path }}
                    </a>
                </template></el-table-column
            >

            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button
                        v-permission="['down']"
                        @click.prevent="downloadBackUps(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.down.name }}
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
        <database-schema-back-ups-add
            v-if="showsBackUpsDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showsBackUpsDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>

<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";

export default {
    data: () => ({
        description: "数据库备份",
        buttons: {
            search: { name: "查询" },
            down: { name: "下载" },
            del: { name: "删除" },
            backups: { name: "数据库备份" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Ship",
        name: "数据库备份",
        parentMenu: acwMenu,
    }),
};
</script>
<script setup>
import { reactive, toRefs, ref, defineProps, onMounted } from "vue";

import databaseSchemaBackUpsAdd from "./components/DatabaseSchemaBackUpsAdd";

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

const schemaList = ref(null);

const params = reactive({
    instanceId: "",
    // status: 1,
    schemaName: "",
    size: 10,
    current: 1,
    total: 0,
});
const { size, current, total } = toRefs(params);

const form = reactive({
    instanceId: "",
    status: 1,
});
const { instanceId, status } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((instanceId.value = rowData.value.instanceId),
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
 * 删除备份记录
 * @param id
 */
const handleDel = async (id) => {
    const { code } = await VE_API.system.databaseSchemaBackUpRemove({ id: id });
    if (code === 0) {
        getDataList();
    }
};
/**
 *@param row 行数据
 * 下载备份数据
 *
 *  */
const downloadBackUps = async (row) => {
    // console.log(row);
    let res = await VE_API.system.downLocalFile(
        { path: row.path },
        {
            responseType: "blob",
        },
    );
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
    const { code, data } = await VE_API.system.databaseSchemaBackUpPage(params);
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

/**
 * 获取 数据库
 * @param instanceId 数据库实例ID
 * @returns {Promise<void>}
 */
const getSchemaList = async (instanceId) => {
    let res;
    if (instanceId == null) {
        return;
    } else {
        res = await VE_API.system.schemaList({
            instanceId,
        });
    }
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaList.value = data;
        if (schemaList.value) {
            // 默认第一个实例的第一个数据库
            params.schemaName = schemaList.value[0].schemaName;
        }
        return schemaList.value;
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
        // 默认第一个实例
        if (serverList.value && serverList.value.length > 0) {
            params.instanceId = serverList.value[0].id;
            getSchemaList(params.instanceId);
        }
        return serverList.value;
    });
};

onMounted(async () => {
    await getServerInstanceList();
    await getDataList();
});
</script>

<style lang="scss" scoped></style>
