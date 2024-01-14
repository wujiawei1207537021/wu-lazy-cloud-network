<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="文件名" prop="name">
                <el-input
                    clearable
                    v-model="name"
                    placeholder="文件名"
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
            </template>
            <el-table-column prop="name" label="文件名称"></el-table-column>

            <el-table-column prop="uid" label="文件uid"></el-table-column>
            <el-table-column prop="length" label="文件大小"></el-table-column>
            <el-table-column prop="describe" label="描述"></el-table-column>
            <el-table-column prop="type" label="文件类型"></el-table-column>
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
                        v-permission="['preview']"
                        @click.prevent="handleDel(row.id)"
                        type="danger"
                        size="small"
                    >
                        {{ buttons.preview.name }}
                    </el-button>
                    <el-button
                        v-permission="['down']"
                        @click.prevent="openUrl(row.id)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.down.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <file-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>
<script>
import mediaMenu from "@/views/layoutpages/media/MediaMenu";

export default {
    data: () => ({
        description: "文件信息查询与设置",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            preview: { name: "预览", toPath: true }, //topath:true 需要设置跳转路径
            down: { name: "下载" }, //topath:true 需要设置跳转路径
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "UploadFilled",
        name: "文件管理",
        parentMenu: mediaMenu,
    }),
};
</script>

<script setup>
import FileEdit from "./components/FileEdit";
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
const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    name: "",
    size: 10,
    current: 1,
    total: 0,
});
const { name, size, current, total } = toRefs(params);
/**
 * @description:添加or编辑事件
 * @return {*}
 * @param title
 * @param row
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
            const { code } = await VE_API.system.playFileDelete({ id });
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
    const { code, data } = await VE_API.system.playFilePage(params);
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
const openUrl = async (id) => {
    console.log(id);
    // window.open("/play/file/retrieve/data/" + id, "_blank");
    let res = await VE_API.system.playFileData(
        { id: id },
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
</script>

<style lang="scss" scoped></style>
