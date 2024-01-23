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
            <el-form-item label="客户端ID" prop="clientId">
                <el-input v-model="clientId" placeholder="" disabled></el-input>
            </el-form-item>
            <el-form-item label="发送的消息" prop="describe">
                <el-input v-model="message" placeholder="" clearable></el-input>
            </el-form-item>
        </el-form>

        <template v-slot:footer>
            <span>
                <el-button @click="closeDialog()">取消</el-button>
                <el-button type="primary" @click="onSubmit()">发送</el-button>
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
    clientId: [
        {
            required: true,
            message: "请输入选择客户端",
            trigger: "blur",
        },
    ],
    clientTargetIp: [
        {
            required: true,
            message: "请输入客户端目标IP",
            trigger: "blur",
        },
    ],
    clientTargetPort: [
        {
            required: true,
            message: "请输入客户端目标端口",
            trigger: "blur",
        },
    ],
    visitorPort: [
        {
            required: true,
            message: "请选择访客端口",
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
    message: "",
    clientId: "",
});
const { clientId, message } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value && (clientId.value = rowData.value.clientId);
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res = (res =
                await VE_API.cloudNetwork.sendMessage2HeartbeatClient(form));
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
