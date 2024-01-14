<template>
    <div class="ve_container">
        <el-form ref="queryForm" :inline="true" :model="params">
            <!-- 搜索 -->
            <el-form-item>
                <el-button
                    type="primary"
                    @click="onSubmit(params, getTranslateDataList)"
                >
                    {{ buttons.search.name }}
                </el-button>
            </el-form-item>

            <el-form-item label="名称" prop="word">
                <el-input
                    clearable
                    v-model="params.word"
                    placeholder="请输入"
                ></el-input>
            </el-form-item>
        </el-form>
        <div>{{ translate }}</div>

        <div>
            <json-viewer :value="translateReactive" copyable boxed sort />
        </div>
        <template>
            <json-viewer :value="translateReactive" copyable boxed sort />
        </template>
    </div>
</template>
<script>
import menu from "@/views/layoutpages/system/components/menu";

export default {
    data: () => ({
        description: "翻译",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            dictionaryData: { name: "查看翻译详细数据", toPath: true }, //topath:true 需要设置跳转路径
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Microphone",
        name: "文本翻译管理",
        parentMenu: menu,
    }),
};
</script>

<script setup>
const params = reactive({
    word: "输入的翻译字段",
});
import { onMounted, reactive, ref } from "vue";
import { onSubmit } from "@/views/layoutpages/common";
import JsonViewer from "vue3-json-viewer";
//添加样式
import "vue3-json-viewer/dist/index.css";

const translate = ref(null);
const translateReactive = reactive(translate);

const getTranslateDataList = async () => {
    const { code, data } = await VE_API.system.translate(params);
    if (code === 0) {
        console.log(data);
        translate.value = data;
    }
};
onMounted(async () => {});
</script>

<style lang="scss" scoped></style>
