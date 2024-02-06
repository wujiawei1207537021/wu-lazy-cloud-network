<template>
    <el-dialog
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        @close="closeDialog()"
    >
        <!-- <span>{{ rowData }}</span> -->
        <!-- 表单 -->
        <el-form
            :model="form"
            ref="formRef"
            :rules="rules"
            label-width="80px"
            :inline="false"
        >
            <el-form-item label="用户名" prop="username">
                <el-input
                    v-model="username"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input
                    v-model="password"
                    show-password
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="角色" prop="roleIds">
                <el-select
                    style="width: 100%"
                    v-model="roleIds"
                    multiple
                    placeholder=""
                    clearable
                >
                    <el-option
                        v-for="item in roleList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="状态">
                <el-radio-group v-model="status">
                    <el-radio-button :label="true">启用</el-radio-button>
                    <el-radio-button :label="false">停用</el-radio-button>
                </el-radio-group>
            </el-form-item>
        </el-form>

        <template v-slot:footer>
            <span>
                <el-button @click="closeDialog()">取消</el-button>
                <el-button type="primary" @click="onSubmit()">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import {
    reactive,
    toRefs,
    ref,
    defineProps,
    defineEmits,
    onMounted,
} from "vue";
const rules = {
    username: [
        {
            required: true,
            message: "请输入用户名",
            trigger: "blur",
        },
    ],
    password: [
        {
            required: true,
            message: "请输入密码",
            trigger: "blur",
        },
    ],
    roleIds: [
        {
            required: true,
            message: "请选择角色",
            trigger: "change",
        },
    ],
};
const props = defineProps({
    showDialog: {
        type: Boolean,
        default: true,
    },
    title: {
        type: String,
        default: "添加",
    },
    rowData: {
        type: Object,
        default: null,
    },
});
const emit = defineEmits(["closeDialog"]);
const { title, rowData } = toRefs(props);
const closeDialog = () => {
    emit("closeDialog", false);
};
const formRef = ref(null);
const form = reactive({
    username: "",
    password: "",
    roleIds: [],
    status: true,
});
const { username, password, roleIds, status } = toRefs(form);
const roleList = ref([]);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((username.value = rowData.value.username),
    (password.value = rowData.value.password),
    (roleIds.value = rowData.value.roleIds),
    (status.value = rowData.value.status));
/**
 * @description: 获取角色列表
 * @param {*}
 * @return {*}
 */
const getRoleList = async () => {
    const { code, data } = await VE_API.system.roleList();
    if (code === 0) {
        roleList.value = data;
    }
};

onMounted(async () => {
    await getRoleList();
});

/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value === "添加") {
                res = await VE_API.system.userAdd(form);
            } else {
                res = await VE_API.system.userEdit({
                    id: rowData.value.id,
                    ...form,
                });
            }
            const { code } = res;
            if (code === 0) {
                closeDialog();
            }
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};
</script>

<style lang="scss" scoped></style>
