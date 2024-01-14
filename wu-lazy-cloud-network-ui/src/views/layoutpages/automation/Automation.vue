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
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
            </template>
            <el-table-column prop="id" label="自动化ID"></el-table-column>
            <el-table-column prop="name" label="自动化名称"></el-table-column>
            <el-table-column
                prop="timeInterval"
                label="处理时间间隔(空不定时处理)"
            ></el-table-column>

            <el-table-column prop="status" label="状态"></el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间"
            ></el-table-column>
            <el-table-column
                prop="updateTime"
                label="更新时间"
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
    </div>
</template>

<script>
import automationMenu from "@/views/layoutpages/automation/AutomationMenu";

export default {
    data: () => ({
        description: "自动化配置",
        buttons: {
            search: { name: "查询" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            add: { name: "新增自动化配置" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "PictureRounded",
        name: "自动化配置",
        parentMenu: automationMenu,
    }),
};
</script>
<script setup>
import { reactive, toRefs, ref, onMounted } from "vue";

import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
    getAsyncRouteName,
} from "@/views/layoutpages/common";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();

const queryForm = ref(null);
const tableData = ref([]);

const params = reactive({
    instanceId: "",
    // status: 1,
    schemaName: "",
    size: 10,
    current: 1,
    total: 0,
});
const { size, current, total } = toRefs(params);

/**
 * @description:添加or编辑事件
 * @param {*}
 * @return {*}
 */
const handleEdit = async (title, row = null) => {
    let path = "automation/components/AutomationEdit";
    const toName = await getAsyncRouteName(title, path, "add", {
        router,
        route,
    });
    if (row) {
        console.log(row);
        await router.push({ name: toName, query: { id: row.id } });
    } else {
        await router.push({ name: toName, query: {} });
    }
};
/**
 * 删除
 * @param id
 */
const handleDel = async (id) => {
    const { code } = await VE_API.system.automationRemove({ id });
    if (code === 0) {
        getDataList();
    }
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.automationFindPage(params);
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
