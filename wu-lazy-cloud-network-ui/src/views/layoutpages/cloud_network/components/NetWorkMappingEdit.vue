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
                <el-select v-model="clientId" placeholder="客户端ID" clearable>
                    <el-option
                        v-for="item in clientList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        :serverId="item"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="访问端口" prop="visitorPort">
                <el-select
                    v-model="visitorPort"
                    placeholder="访问端口"
                    clearable
                >
                    <el-option
                        v-for="item in visitorList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="客户端目标地址" prop="clientTargetIp">
                <el-input
                    v-model="clientTargetIp"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="客户端目标端口" prop="clientTargetPort">
                <el-input
                    v-model="clientTargetPort"
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
const clientList = ref(null);
const visitorList = ref(null);

const form = reactive({
    clientId: "",
    clientTargetIp: "0.0.0.0",
    clientTargetPort: "",
    visitorPort: "",
    describe: "",
});
const { clientId, clientTargetIp, clientTargetPort, visitorPort, describe } =
    toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((clientId.value = rowData.value.clientId),
    (clientTargetIp.value = rowData.value.clientTargetIp),
    (clientTargetPort.value = rowData.value.clientTargetPort),
    (visitorPort.value = rowData.value.visitorPort),
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
                res = await VE_API.cloudNetwork.networkMappingSave(form);
            } else {
                res = await VE_API.cloudNetwork.networkMappingUpdate({
                    clientTargetPort: rowData.value.clientTargetPort,
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
onMounted(async () => {
    VE_API.cloudNetwork.cloudClientFindList().then((res) => {
        res.data.map((item) => {
            item.label = item.clientId;
            item.value = item.clientId;
        });
        clientList.value = res.data ? res.data : [];
    });
    findVisitorPort();
});
/**
 * 获取未使用的访客端口
 * @param visitorPort
 * @returns {Promise<void>}
 */
const findVisitorPort = async () => {
    // 查询未使用的访客端口
    let res = await VE_API.cloudNetwork.visitorList();
    const { code } = res;
    if (code === 0) {
        res.data.map((item) => {
            item.label = item.describe;
            item.value = item.visitorPort;
        });
        visitorList.value = res.data ? res.data : [];
        // databaseSchemaId.value = serverSchemaList.value[0].id;
    }
};
</script>

<style lang="scss" scoped></style>
