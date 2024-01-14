<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="接口分组" prop="interfaceGroup">
                <el-input
                    clearable
                    v-model="interfaceGroup"
                    placeholder="接口分组"
                ></el-input>
            </el-form-item>
            <el-form-item label="接口描述" prop="apiComment">
                <el-input
                    clearable
                    v-model="apiComment"
                    placeholder="接口描述"
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
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
            </template>

            <el-table-column prop="apiId" label="接口ID"></el-table-column>
            <el-table-column
                prop="apiComment"
                label="接口注释"
            ></el-table-column>
            <el-table-column prop="apiPath" label="拦截路径"></el-table-column>
            <el-table-column
                prop="apiMethod"
                label="HttpMethod"
            ></el-table-column>
            <el-table-column prop="apiResultType" label="api返回结果类型">
                <template v-slot="scope">
                    <el-select
                        v-model="scope.row.apiResultType"
                        placeholder="api返回结果类型"
                        filterable
                        :disabled="true"
                    >
                        <el-option label="单个对象" :value="0" />
                        <el-option label="集合对象" :value="1" />
                        <el-option label="分页对象" :value="2" />
                        <el-option label="空" :value="3" />
                    </el-select>
                </template>
            </el-table-column>
            <el-table-column
                prop="executeType"
                label="执行类型"
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
                        @click.prevent="handleDel(row.apiId)"
                        type="danger"
                        size="small"
                    >
                        {{ buttons.del.name }}
                    </el-button>
                    <el-button
                        v-permission="['info']"
                        @click.prevent="handleInfo('详情', row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.info.name }}
                    </el-button>
                    <el-button
                        v-permission="['deriveCode']"
                        @click.prevent="deriveCode(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.deriveCode.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>
        <!-- 编辑组件 -->
        <client-api-factory-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
        <!-- 编辑组件 -->
        <client-api-factory-edit
            v-if="showInfo"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showInfo"
            @closeDialog="handelInfo($event)"
        />
    </div>
</template>
<script>
import acwClientMenu from "@/views/layoutpages/acw_client/AcwClientMenu";

export default {
    data: () => ({
        description: "客户端API工厂",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            deriveCode: { name: "衍生代码" },
            info: { name: "详情" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Lollipop",
        name: "客户端API工厂",
        parentMenu: acwClientMenu,
    }),
};
</script>

<script setup>
import ClientApiFactoryEdit from "@/views/layoutpages/acw_client/components/ClientApiFactoryEdit";
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
const showDialog = ref(false);
const showInfo = ref(false);
const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    interfaceGroup: "",
    apiComment: "",
    size: 10,
    current: 1,
    total: 0,
});
const { interfaceGroup, apiComment, size, current, total } = toRefs(params);
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
            const { code } = await VE_API.system.clientApiDelete({ apiId: id });
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
 * 衍生代码
 * @param row 行信息
 */
const deriveCode = (row) => {
    const { code } = VE_API.system.clientApiDerivativeCode({
        apiId: row.apiId,
        webArchitecture: "DDD_ARCHITECTURE",
        ormArchitecture: "LAZY",
    });
    if (code === 0) {
        getDataList();
    }
};
/**
 * 行信息
 */
const handleInfo = (title, row = null) => {
    showInfo.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};
const handelInfo = (e) => {
    showInfo.value = e;
    getDataList();
};
/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.clientApiPage(params);
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
