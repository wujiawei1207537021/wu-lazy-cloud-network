<template>
    <common>
        <div class="ve_container">
            <el-card :body-style="{ background: 'rgba(0,0,0,0.15)' }">
                <router-link style="float: right" :to="{ path: 'register' }"
                    >注册</router-link
                >
                <h1>wu-lazy-cloud-network-ui</h1>
                <transition name="el-fade-in-linear">
                    <el-form
                        :model="form"
                        :rules="rules"
                        v-show="!success"
                        class="ve_form"
                        ref="ref_form"
                        :inline="false"
                        @keyup.enter="onSubmit"
                    >
                        <el-form-item prop="username">
                            <el-input
                                v-model.trim="username"
                                placeholder="用户名"
                            >
                                <template #prepend>
                                    <el-icon :size="20"><Avatar /></el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="password">
                            <el-input
                                v-model.trim="password"
                                show-password
                                placeholder="密码"
                            >
                                <template #prepend>
                                    <el-icon :size="20"><Key /></el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button
                                class="ve_submit"
                                type="primary"
                                @click="onSubmit"
                            >
                                登录
                            </el-button>
                        </el-form-item>
                    </el-form>
                </transition>
            </el-card>
        </div>
    </common>
</template>

<script setup>
import {
    SET_ACCESS_TOKEN,
    SET_REFRESH_TOKEN,
    SET_TOKEN,
    SET_UNAME,
    SET_ZONE_ID,
} from "@/store/modules/app/type";
import Common from "@/components/Common";
import { ref, reactive, toRefs } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const rules = {
    username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
};
const store = useStore();
const router = useRouter();
const form = reactive({
    username: "",
    password: "",
});
const { username, password } = toRefs(form);
const ref_form = ref(null);
const success = ref(false);
sessionStorage.clear();
store.dispatch(`app/${SET_TOKEN}`, "");
router.options.isAddDynamicMenuRoutes = false;

/**
 * 登录
 */
const onSubmit = () => {
    ref_form.value.validate(async (valid) => {
        if (valid) {
            const data = await VE_API.system.login(form);
            if (data.code === 0) {
                await store.dispatch(`app/${SET_TOKEN}`, data.data);
                await store.dispatch(
                    `app/${SET_ACCESS_TOKEN}`,
                    data.data.accessToken,
                );
                await store.dispatch(
                    `app/${SET_REFRESH_TOKEN}`,
                    data.data.refreshToken,
                );
                await store.dispatch(`app/${SET_UNAME}`, form.username);
                // 获取用户信息
                const userResult = await VE_API.system.resolvingUser({
                    accessToken: data.data.accessToken,
                });

                console.log(userResult);
                if (userResult && userResult.id) {
                    await store.dispatch(`app/${SET_ZONE_ID}`, userResult.id);
                }
                success.value = true;
                router.push({ name: "AppMain" });
            }
        } else {
            return;
        }
    });
};
</script>

<style lang="scss" scoped>
.ve_container {
    position: absolute;
    z-index: 1;
    width: 400px;
    top: 50%;
    left: 100px;
    transform: translateY(-50%);
    transition: all 1s;
    min-height: 273px;
    text-align: center;
    h1 {
        font-size: 24px;
        transition: all 1s;
        font-weight: bold;
        margin-bottom: 36px;
    }
    .ve_form {
        .ve_submit {
            width: 100%;
        }
        :deep(.el-input-group__prepend) {
            padding: 0 10px;
        }
    }
}
</style>
