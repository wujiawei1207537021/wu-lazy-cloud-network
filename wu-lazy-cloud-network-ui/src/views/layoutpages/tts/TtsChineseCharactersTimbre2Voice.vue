<template>
    <div class="ve_home">
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="汉字" prop="text">
                <el-input
                    clearable
                    v-model="text"
                    placeholder="汉字"
                ></el-input>
            </el-form-item>
            <el-form-item label="音色" property="timbreCode">
                <el-select
                    v-model="timbreCode"
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
                <el-button type="primary" @click="onSubmit()">
                    {{ buttons.search.name }}
                </el-button>
            </el-form-item>
        </el-form>

        <div v-if="textVoiceUrl">
            <audio ref="audio" :src="textVoiceUrl" controls="controls"></audio>
        </div>
    </div>
</template>
<script>
import ttsMenu from "@/views/layoutpages/tts/TtsMenu";

export default {
    data: () => ({
        description: "TTS汉字转换语音",
        buttons: {
            search: { name: "查询" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "Pointer",
        name: "TTS汉字转换语音",
        parentMenu: ttsMenu,
    }),
};
</script>

<script setup>
import { reactive, toRefs, ref, onMounted } from "vue";

const queryForm = ref(null);

const textVoiceUrl = ref(null);

const params = reactive({
    text: "欢迎来到TTS测试",
    timbreCode: "",
    size: 10,
    current: 1,
    total: 0,
});
const timbreList = ref([]);
const { text, timbreCode } = toRefs(params);

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
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const onSubmit = async () => {
    if (textVoiceUrl.value) {
        window.URL.revokeObjectURL(textVoiceUrl.value); // 释放内存
    }
    let res = await VE_API.system.ttsChineseCharactersTimbreTextToBytes(
        params,
        { responseType: "blob" },
    );
    textVoiceUrl.value = window.URL.createObjectURL(new Blob([res.data]));
};
onMounted(async () => {
    await getTimbreList();
});
</script>

<style lang="scss" scoped></style>
