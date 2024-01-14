<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="汉字" prop="word">
                <el-input
                    clearable
                    v-model="word"
                    placeholder="汉字"
                ></el-input>
            </el-form-item>
            <el-form-item label="音色" property="timbreCode">
                <el-select
                    v-model="timbreCode"
                    @change="getDataList"
                    placeholder="音色"
                    filterable
                    clearable
                >
                    <el-option
                        v-for="item in timbreList"
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
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
            </template>
            <el-table-column prop="word" label="汉字"></el-table-column>
            <el-table-column
                prop="timbreCode"
                label="音色编码"
            ></el-table-column>

            <el-table-column
                prop="createTime"
                label="创建时间"
            ></el-table-column>
            <el-table-column
                prop="updateTime"
                label="修改时间"
            ></el-table-column>
            <el-table-column prop="voiceUrl" label="voiceUrl"></el-table-column>
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
                    <div>
                        <audio
                            ref="audio"
                            :src="row.voiceUrl"
                            autoplay
                            loop
                            controls="controls"
                        ></audio>
                    </div>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <tts-chinese-characters-timbre-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>
</template>
<script>
import ttsMenu from "@/views/layoutpages/tts/TtsMenu";

export default {
    data: () => ({
        description: "TTS汉字音色管理",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "BrushFilled",
        name: "TTS汉字音色管理",
        parentMenu: ttsMenu,
    }),
};
</script>

<script setup>
import TtsChineseCharactersTimbreEdit from "./components/TtsChineseCharactersTimbreEdit.vue";
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
    word: "",
    timbreCode: "",
    size: 10,
    current: 1,
    total: 0,
});
const timbreList = ref([]);
const { word, timbreCode, size, current, total } = toRefs(params);

/**
 * 获取音色数据
 */
const getTimbreList = async () => {
    let res = await VE_API.system.ttsTimbreFindList();
    res.data.map((item) => {
        item.label = item.name;
        item.value = item.code;
    });
    timbreList.value = res.data ? res.data : [];
};
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
            const { code } =
                await VE_API.system.ttsChineseCharactersTimbreRemove({
                    id: id,
                });
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
        await VE_API.system.ttsChineseCharactersTimbreFindPage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;
        record.map((item) => {
            const blob = new Blob([item.voice], { type: "audio/mp3" }); // 构造一个blob对象来处理数据，并设置文件类型
            item.voiceUrl = window.URL.createObjectURL(blob);
        });
        console.log(record);
        tableData.value = record;
    }
};
onMounted(async () => {
    await getDataList();
    await getTimbreList();
});
</script>

<style lang="scss" scoped></style>
