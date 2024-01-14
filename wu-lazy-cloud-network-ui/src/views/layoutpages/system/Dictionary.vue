<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="字典名称" prop="name">
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
        <div style="flex-wrap: wrap">
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
                <el-table-column prop="name" label="字典名称" width="120">
                    <template v-slot="scope">
                        <!-- 注意：这个地方要传参数进去才能进行操作  函数名称(scope.row) -->
                        <el-button
                            type="primary"
                            @click="getDictionaryDataList(scope.row.code)"
                        >
                            {{ scope.row.name }}
                        </el-button>
                    </template>
                </el-table-column>
                <el-table-column prop="code" label="字典编码">
                </el-table-column>

                <el-table-column prop="status" label="字典类型">
                    <template v-slot="{ row }">
                        <el-tag :type="row.type === 0 ? 'danger' : ''">
                            {{ row.type == 0 ? "列表" : "树状" }}
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
                            v-permission="['addDictionaryData']"
                            size="small"
                            type="primary"
                            @click="
                                handleDictionaryDataSave(
                                    buttons.addDictionaryData.name,
                                    row,
                                )
                            "
                        >
                            {{ buttons.addDictionaryData.name }}
                        </el-button>
                    </template>
                </el-table-column>
            </ve-table>

            <!--          字典项列表     -->
            <div style="width: 50%">
                <ve-table
                    :table="{
                        data: dictionaryDataList,
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
                    <el-table-column
                        prop="name"
                        label="字典名称"
                        width="120"
                    ></el-table-column>
                    <el-table-column
                        prop="code"
                        label="字典编码"
                    ></el-table-column>
                    <el-table-column
                        prop="description"
                        label="字典项描述"
                        width="120"
                    ></el-table-column>
                    <el-table-column prop="status" label="字典类型">
                        <template v-slot="{ row }">
                            <el-tag :type="row.type === 0 ? 'danger' : ''">
                                {{ row.type == 0 ? "列表" : "树状" }}
                            </el-tag>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作">
                        <template v-slot:default="{ row }">
                            <el-button
                                v-permission="['editDictionaryData']"
                                @click.prevent="
                                    handleDictionaryDataEdit(
                                        buttons.editDictionaryData.name,
                                        row,
                                    )
                                "
                                type="primary"
                                size="small"
                            >
                                {{ buttons.editDictionaryData.name }}
                            </el-button>
                            <el-button
                                v-permission="['delDictionaryData']"
                                @click.prevent="handleDictionaryDataDel(row)"
                                type="danger"
                                size="small"
                            >
                                {{ buttons.delDictionaryData.name }}
                            </el-button>
                        </template>
                    </el-table-column>
                </ve-table>
                <!-- 编辑字典项组件 -->
                <dictionary-data-edit
                    v-if="dictionaryDataShowDialog"
                    :rowData="dictionaryDataRowData"
                    :title="dictionaryDataDialogTitle"
                    :showDialog="dictionaryDataShowDialog"
                    @closeDialog="handelDictionaryDataDialog($event)"
                />
            </div>
        </div>
        <!-- 编辑字典组件 -->
        <dictionary-edit
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
        description: "字典信息查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            addDictionaryData: { name: "添加字典项" },
            editDictionaryData: { name: "编辑字典项" },
            delDictionaryData: { name: "删除字典项" },
            dictionaryData: { name: "查看字典详细数据", toPath: true }, //topath:true 需要设置跳转路径
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Reading",
        name: "字典管理",
        parentMenu: menu,
    }),
};
</script>

<script setup>
import DictionaryEdit from "./components/DictionaryEdit";
import DictionaryDataEdit from "./components/DictionaryDataEdit";
import { reactive, toRefs, ref, onMounted, getCurrentInstance } from "vue";

//*导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";

const { proxy } = getCurrentInstance();
const rowData = ref(null);
const dictionaryDataRowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);

const dictionaryDataDialogTitle = ref("");
const dictionaryDataShowDialog = ref(false);

const queryForm = ref(null);
const tableData = ref([]);
// 字典数据
const dictionaryDataList = ref([]);

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
const handleDictionaryDataSave = (title, row = null) => {
    dictionaryDataShowDialog.value = true;
    dictionaryDataDialogTitle.value = title;
    dictionaryDataRowData.value = { dictionaryCode: row.code };
    console.log(dictionaryDataRowData.value);
};

/**
 * @description:添加or编辑事件
 * @param {*}
 * @return {*}
 */
const handleDictionaryDataEdit = (title, row = null) => {
    dictionaryDataShowDialog.value = true;
    dictionaryDataDialogTitle.value = title;
    dictionaryDataRowData.value = row;
};
/**
 * @description: dialog事件
 * @param {*}
 * @return {*}
 */
const handelDictionaryDataDialog = (e) => {
    dictionaryDataShowDialog.value = e;
    getDataList();
    console.log(rowData.value);
    getDictionaryDataList(dictionaryDataRowData.value.dictionaryCode);
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
            const { code } = await VE_API.system.dictionaryDelete({ id });
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

/**删除行数据
 * @description:
 * @param {*}
 * @return {*}
 */
const handleDictionaryDataDel = (dictionaryData) => {
    let id = dictionaryData.id;
    proxy
        .$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            const { code } = await VE_API.system.dictionaryDataDelete({ id });
            if (code === 0) {
                getDictionaryDataList(dictionaryData.dictionaryCode);
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
 * @return {*}
 * @param dictionaryCode
 */
const getDictionaryDataList = async (dictionaryCode) => {
    const { code, data } = await VE_API.system.dictionaryDataList({
        dictionaryCode: dictionaryCode,
    });
    if (code === 0) {
        dictionaryDataList.value = data;
    }
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.dictionaryPage(params);
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
