<template>
    <common>
        <div class="ve_container">
            <el-card :body-style="{ background: 'rgba(0,0,0,0.15)' }">
                <router-link style="float: right" :to="{ path: 'login' }"
                    >登录</router-link
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
                        <el-form-item prop="username" label="注册账号">
                            <el-input
                                v-model.trim="username"
                                placeholder="注册账号"
                            >
                                <template #prepend>
                                    <el-icon :size="20"><Avatar /></el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="password" label="账号密码">
                            <el-input
                                v-model.trim="password"
                                show-password
                                placeholder="账号密码"
                            >
                                <template #prepend>
                                    <el-icon :size="20"><Key /></el-icon>
                                </template>
                            </el-input>
                        </el-form-item>
                        <el-form-item prop="checkPassword" label="密码确认">
                            <el-input
                                v-model.trim="checkPassword"
                                show-password
                                placeholder="密码确认"
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
                                注册
                            </el-button>
                        </el-form-item>
                    </el-form>
                </transition>
            </el-card>
        </div>
    </common>
</template>

<script setup>
import { SET_TOKEN, SET_UNAME } from "@/store/modules/app/type";
import Common from "@/components/Common";
import { ref, reactive, toRefs, getCurrentInstance } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
const rules = {
    username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
    password: [{ required: true, message: "请输入密码", trigger: "blur" }],
    checkPassword: [{ required: true, message: "密码确认", trigger: "blur" }],
};
const { proxy } = getCurrentInstance();
const store = useStore();
const router = useRouter();
const form = reactive({
    username: "",
    password: "",
    checkPassword: "",
});
const { username, password, checkPassword } = toRefs(form);
const ref_form = ref(null);
const success = ref(false);
sessionStorage.clear();
store.dispatch(`app/${SET_TOKEN}`, "");
router.options.isAddDynamicMenuRoutes = false;

const onSubmit = () => {
    // 验证两个密码是否一样
    if (form.password === form.checkPassword) {
        ref_form.value.validate(async (valid) => {
            if (valid) {
                const data = await VE_API.system.register(form);
                if (data.code === 0) {
                    store.dispatch(`app/${SET_TOKEN}`, data.data);
                    store.dispatch(`app/${SET_UNAME}`, form.username);
                    success.value = true;
                    router.push({ name: "AppMain" });
                }
            } else {
                return;
            }
        });
    } else {
        proxy.$message({
            type: "error",
            message: "请确认，两次输入密码是否一致!",
        });
    }
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
