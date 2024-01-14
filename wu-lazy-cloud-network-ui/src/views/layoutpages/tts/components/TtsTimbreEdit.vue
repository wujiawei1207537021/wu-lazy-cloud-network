<template>
    <el-dialog
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        @close="closeDialog()"
    >
        <!-- 表单 -->
        <el-form
            :model="form"
            ref="formRef"
            :rules="rules"
            label-width="80px"
            :inline="false"
        >
            <el-form-item label="音色名称" prop="name">
                <el-input
                    v-model="name"
                    placeholder="音色名称"
                    clearable
                ></el-input>
            </el-form-item>

            <el-form-item label="音色编码" prop="code">
                <el-input v-model="code" placeholder="" clearable></el-input>
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
    onMounted,
    ref,
    defineProps,
    defineEmits,
} from "vue";

const rules = {
    name: [
        {
            required: true,
            message: "请输入音色名称",
            trigger: "blur",
        },
    ],

    code: [
        {
            required: true,
            message: "请输入音色编码",
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
    name: "",
    code: "",
});
const { name, code } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((name.value = rowData.value.name), (code.value = rowData.value.code));

onMounted(async () => {
    // console.log("123");
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
                res = await VE_API.system.ttsTimbreStory(form);
            } else {
                res = await VE_API.system.ttsTimbreUpdateOne({
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
