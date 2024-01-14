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
            <el-form-item label="任务名称" prop="taskName">
                <el-input
                    v-model="taskName"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="类型" prop="type">
                <el-input v-model="type" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="配置" prop="config">
                <el-input v-model="config" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="状态">
                <el-radio-group v-model="status">
                    <el-radio-button :label="1">启用</el-radio-button>
                    <el-radio-button :label="0">停用</el-radio-button>
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
import { reactive, toRefs, ref, defineProps, defineEmits } from "vue";
const rules = {
    taskName: [
        {
            required: true,
            message: "请输入任务名",
            trigger: "blur",
        },
    ],
    type: [
        {
            required: true,
            message: "请输入任务类型",
            trigger: "blur",
        },
    ],
    config: [
        {
            required: true,
            message: "请添加配置",
            trigger: "blur",
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
    taskName: "",
    type: "",
    config: "",
    status: 1,
});
const { taskName, config, type, status } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((taskName.value = rowData.value.taskName),
    (config.value = rowData.value.config),
    (type.value = rowData.value.type),
    (status.value = rowData.value.status));

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
                res = await VE_API.system.upsertAdd(form);
            } else {
                res = await VE_API.system.upsertAdd({
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
