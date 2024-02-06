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
            <el-form-item label="字典名称" prop="name">
                <el-input v-model="name" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="字典编码" prop="code">
                <el-input v-model="code" placeholder="" clearable></el-input>
            </el-form-item>

            <el-form-item label="字典类型" prop="status">
                <el-radio-group v-model="type">
                    <el-radio-button :label="1">树状</el-radio-button>
                    <el-radio-button :label="0">列表</el-radio-button>
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
    onMounted,
    reactive,
    ref,
    toRefs,
    defineProps,
    defineEmits,
} from "vue";

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
const form = reactive({
    code: "",
    name: "",
    type: 1,
});
const { code, name, type } = toRefs(form);
const formRef = ref(null);
const rules = {
    name: [
        {
            required: true,
            message: "请输入字典名称",
            trigger: "blur",
        },
    ],
    code: [
        {
            required: true,
            message: "请输入字典编码",
            trigger: "blur",
        },
    ],
};
/**
 * @description: 数据初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((name.value = rowData.value.name),
    (code.value = rowData.value.code),
    (type.value = rowData.value.type));
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
                res = await VE_API.system.dictionaryAdd(form);
            } else {
                res = await VE_API.system.dictionaryEdit({
                    id: rowData.value.id,
                    ...form,
                });
            }
            const { code } = res;
            if (code == "00") {
                closeDialog();
            }
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};
onMounted(async () => {});
</script>

<style lang="scss">
.fl {
    float: left;
}
.p0 {
    padding: 0 !important;
}
</style>
