<template>
    <div class="ve_personal">
        <el-button-group>
            <el-button
                title="刷新"
                style="border: none; font-size: 20px"
                circle
                plain
                @click="reload()"
            >
                <el-icon :size="20" style="vertical-align: middle">
                    <Refresh />
                </el-icon>
            </el-button>
            <el-button
                title="全屏"
                style="border: none; font-size: 20px"
                circle
                plain
                @click="toggle()"
            >
                <el-icon :size="14" style="vertical-align: middle">
                    <full-screen />
                </el-icon>
            </el-button>
            <el-button
                :title="dark ? '夜间模式' : '明亮模式'"
                style="border: none; font-size: 20px"
                circle
                plain
                @click="toggleTheme()"
            >
                <el-icon :size="14" style="vertical-align: middle">
                    <component :is="dark ? 'moon' : 'sunny'" />
                </el-icon>
            </el-button>
        </el-button-group>
        <el-divider direction="vertical"></el-divider>
        <el-dropdown @command="handleCommand">
            <span class="ve_nav_dropdown">
                你好!{{ uname }}
                <el-icon>
                    <arrow-down-bold />
                </el-icon>
            </span>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item :command="{ name: 'Login' }">
                        退出登录
                    </el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
        <!-- <router-link :to="{ name: 'Login' }">
            <el-link :underline="false">退出</el-link>
        </router-link> -->
    </div>
</template>

<script setup>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { inject, computed, ref } from "vue";
import { useFullscreen } from "@vueuse/core";
const { toggle } = useFullscreen();
const store = useStore();
const router = useRouter();
const uname = computed(() => store.getters.uname);
const reload = inject("reload");
const handleCommand = (command) => {
    router.push(command);
};
const dark = ref(false);
const toggleTheme = () => {
    dark.value = !dark.value;
    document.documentElement.classList.toggle("dark");
};
</script>

<style lang="scss" scoped>
.ve_personal {
    flex: 1;
    text-align: right;
    .ve_nav_dropdown {
        font-weight: bold;
    }
}
</style>
