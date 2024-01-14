<template>
    <div class="vu_content">
        <view
            v-for="item_iframe in iframeList"
            :key="item_iframe.id"
            class="content-item"
        >
            <span>窗口{{ item_iframe.id }}</span>
            <el-input
                v-model="item_iframe.tilt"
                placeholder="窗口描述"
                clearable
            ></el-input>
            <el-input
                v-model="item_iframe.src"
                placeholder="窗口地址"
                clearable
            ></el-input>
            <iframe
                :src="item_iframe.src"
                style="height: 100%; width: 100%; margin: 0; border: 0"
            >
            </iframe>
        </view>
        <view class="content-item">
            <el-button type="primary" @click="addIframe()"
                >添加iframe</el-button
            >
        </view>
    </div>
</template>
<script>
import menu from "@/views/layoutpages/system/components/menu";

export default {
    data: () => ({
        description: "动态iframe",
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
        name: "动态iframe",
        parentMenu: menu,
    }),
};
</script>

<script setup>
import { onMounted, ref } from "vue";

const iframeList = ref([]);
//添加样式
import "vue3-json-viewer/dist/index.css";

onMounted(async () => {});
const addIframe = () => {
    console.log("111");
    iframeList.value.push({
        id: "默认ID",
        src: "www.baidu.com",
        tilt: "默认表头",
    });
};
</script>

<style lang="scss" scoped>
.content {
    display: flex;
    flex-wrap: wrap;
    padding: calc((100vw - 40vw - 40vw) / 6);
}
.content-item {
    width: 40vw;
    height: 140px;
    background: #ffffff;
    margin: calc((100vw - 40vw - 40vw) / 6);
}
</style>
