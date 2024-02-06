<template>
    <div>
        <el-page-header
            @back="$router.back()"
            :content="title"
        ></el-page-header>
        <el-alert
            style="margin-top: 20px"
            type="info"
            description="当前为动态权限页面!离开后将无法访问!刷新页面请使用本系统自带刷新按钮!!!"
            show-icon
            close-text="知道了"
        ></el-alert>
        <el-divider></el-divider>
        <slot />
    </div>
</template>

<script setup>
import { toRefs, defineProps } from "vue";
const props = defineProps({
    title: {
        type: String,
        require: true,
    },
});
const { title } = toRefs(props);
</script>

<style lang="scss" scoped></style>
