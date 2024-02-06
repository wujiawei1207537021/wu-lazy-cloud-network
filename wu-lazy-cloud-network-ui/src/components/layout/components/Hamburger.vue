<template>
    <div class="ve_slider_menu" @click="fn_slider_menu">
        <el-icon>
            <component :is="opened ? 'expand' : 'fold'" />
        </el-icon>
    </div>
</template>

<script setup>
import { TOGGLE_SLIDER } from "@/store/modules/app/type";
import { computed } from "vue";
import { useStore } from "vuex";
const store = useStore();
const opened = computed(() => store.getters.opened);
const fn_slider_menu = () => {
    store.dispatch(`app/${TOGGLE_SLIDER}`);
};
</script>

<style lang="scss" scoped>
.ve_slider_menu {
    cursor: pointer;
    margin-right: 10px;
    i {
        font-size: 40px;
    }
    &:hover i {
        color: $base-color;
    }
}
</style>
