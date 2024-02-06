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
            <el-form-item label="访客端口" prop="visitorPort">
                <el-input
                    v-model="visitorPort"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="描述" prop="describe">
                <el-input
                    v-model="describe"
                    placeholder=""
                    clearable
                ></el-input>
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
    projectId: [
        {
            required: true,
            message: "请输入选择项目",
            trigger: "blur",
        },
    ],
    schemaName: [
        {
            required: true,
            message: "请输入选择数据库",
            trigger: "blur",
        },
    ],
    applicationName: [
        {
            required: true,
            message: "请输入应用名",
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
    visitorPort: "",
    describe: "",
});
const { visitorPort, describe } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((visitorPort.value = rowData.value.visitorPort),
    (describe.value = rowData.value.describe));
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
                res = await VE_API.cloudNetwork.visitorSave(form);
            } else {
                res = await VE_API.cloudNetwork.visitorUpdate({
                    visitorPort: rowData.value.visitorPort,
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
onMounted(async () => {});
</script>

<style lang="scss" scoped></style>
