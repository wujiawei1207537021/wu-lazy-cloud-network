<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="类型(1:自动产生的、2:手动添加的)" prop="type">
                <el-select
                    v-model="type"
                    placeholder="类型(1:自动产生的、2:手动添加的)"
                    clearable
                    @change="getDataList"
                >
                    <el-option label="自动产生的" value="1" />
                    <el-option label="手动添加的" value="2" />
                </el-select>
            </el-form-item>
            <el-form-item label="原始表" prop="sourceTable">
                <el-input
                    clearable
                    v-model="sourceTable"
                    placeholder="原始表"
                ></el-input>
            </el-form-item>
            <el-form-item label="原始表字段" prop="sourceTableColumn">
                <el-input
                    clearable
                    v-model="sourceTableColumn"
                    placeholder="原始表字段"
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
                <el-button
                    v-permission="['analysisSchema']"
                    size="small"
                    type="primary"
                    @click="handleAnalysisSchemaEdit()"
                >
                    {{ buttons.analysisSchema.name }}
                </el-button>
            </template>
            <el-table-column
                prop="instanceId"
                label="数据库实例ID"
            ></el-table-column>
            <el-table-column
                prop="schema"
                label="数据库实例schema"
            ></el-table-column>
            <el-table-column
                prop="sourceTable"
                label="原始表"
            ></el-table-column>
            <el-table-column prop="sourceTableColumn" label="原始表字段">
            </el-table-column>
            <el-table-column
                prop="relationTable"
                label="关系表"
            ></el-table-column>
            <el-table-column
                prop="relationTableColumn"
                label="关系表字段"
            ></el-table-column>
            <el-table-column prop="relation" label="关系"></el-table-column>
            <el-table-column
                prop="type"
                label="类型(1:自动产生的、2:手动添加的)"
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

        <!-- 编辑组件 -->
        <acw-table-association-relation-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
        <acw-table-association-relation-analysis-schema-edit
            v-if="showAnalysisSchemaDialog"
            :showDialog="showAnalysisSchemaDialog"
            @closeDialog="handleAnalysisSchemaDialog($event)"
        />
    </div>
</template>

<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    data: () => ({
        description: "表关联关系",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            analysisSchema: { name: "解析schema" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Flag",
        name: "表关联关系管理",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";
//?导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";
import AcwTableAssociationRelationEdit from "@/views/layoutpages/acw/components/AcwTableAssociationRelationEdit.vue";
import AcwTableAssociationRelationAnalysisSchemaEdit from "@/views/layoutpages/acw/components/AcwTableAssociationRelationAnalysisSchemaEdit.vue";

const { proxy } = getCurrentInstance();

const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);
const showAnalysisSchemaDialog = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    type: "2",
    sourceTable: "",
    sourceTableColumn: "",
    size: 10,
    current: 1,
    total: 0,
});
const { type, sourceTable, sourceTableColumn, size, current, total } =
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
 * @description: dialog事件
 * @param {*}
 * @return {*}
 */
const handelDialog = (e) => {
    showDialog.value = e;
    getDataList();
};

const handleAnalysisSchemaEdit = () => {
    showAnalysisSchemaDialog.value = true;
};
const handleAnalysisSchemaDialog = (e) => {
    showAnalysisSchemaDialog.value = e;
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
            const { code } =
                await VE_API.system.acwTableAssociationRelationDelete({ id });
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
    const { code, data } =
        await VE_API.system.acwTableAssociationRelationPage(params);
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
