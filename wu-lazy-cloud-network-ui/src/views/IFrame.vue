<template>
    <div
        v-loading="load"
        element-loading-text="拼命加载中"
        element-loading-spinner="el-icon-loading"
    >
        <iframe ref="iframe" :src="props.url" frameborder="0"></iframe>
    </div>
</template>

<script setup>
import { defineProps, onMounted, ref } from "vue";
import { nav_height } from "@/styles/variables.scss.js";

const props = defineProps({
    url: { type: String },
});
const load = ref(true);
const iframe = ref(null);

const setLoad = () => {
    const $onLoad = () => {
        load.value = false;
    };
    if (iframe.value.attachEvent) {
        iframe.value.attachEvent("onload", $onLoad);
    } else {
        iframe.value.onload = $onLoad;
    }
};

onMounted(() => {
    setLoad();
});
</script>

<style lang="scss" scoped>
iframe {
    width: 100%;
    height: calc(100vh - v-bind(nav_height) - 83px);
}
</style>
