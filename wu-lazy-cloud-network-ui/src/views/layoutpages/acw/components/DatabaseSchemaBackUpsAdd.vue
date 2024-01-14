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
            label-width="120px"
            :inline="false"
        >
            <el-form-item label="数据库实例" prop="instanceId">
                <el-select
                    v-model="instanceId"
                    placeholder="数据库实例"
                    @change="getSchemaList"
                    filterable
                >
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="数据库名" prop="schemaName">
                <el-select
                    clearable
                    filterable
                    v-model="schemaName"
                    placeholder="数据库名"
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
        </el-form>

        <el-progress
            :text-inside="true"
            :stroke-width="24"
            :percentage="backUpsSchemaProgress.percentage"
            :hidden="backUpsSchemaProgress.hidden"
            :status="backUpsSchemaProgress.status"
        >
            <span>备份数据库</span>
        </el-progress>
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
    instanceId: [
        {
            required: true,
            message: "请选择数据库实例",
            trigger: "blur",
        },
    ],
    schemaName: [
        {
            required: true,
            message: "请选择数据库",
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
        default: "数据库备份",
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

const serverList = ref(null);
const schemaList = ref(null);
const instanceId = ref(null);
const schemaName = ref(null);
const backUpsSchemaProgress = reactive({
    hidden: true,
    percentage: 0,
    status: "success",
});

const form = reactive({
    instanceId: "",
    schemaName: "",
});

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value && (instanceId.value = rowData.value.instanceId);
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    form.schemaName = schemaName.value;
    form.instanceId = instanceId.value;
    formRef.value.validate(async (valid) => {
        if (valid) {
            backUpsSchemaProgress.hidden = false;
            // 修改进度条
            setInterval(() => {
                backUpsSchemaProgress.percentage =
                    (backUpsSchemaProgress.percentage % 100) + 10;
            }, 500);
            let res = await VE_API.system.databaseSchemaBackUp({
                instanceId: instanceId.value,
                schemaName: schemaName.value,
            });
            const { code } = res;
            if (code === 0) {
                backUpsSchemaProgress.status = "success";
                closeDialog();
            }
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};

/**
 * 获取 数据库
 * @param instanceId 数据库实例ID
 * @returns {Promise<void>}
 */
const getSchemaList = async (instanceId) => {
    let res;
    if (instanceId == null) {
        return;
    } else {
        res = await VE_API.system.schemaList({
            instanceId,
        });
    }
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaList.value = data;
        if (schemaList.value) {
            // 默认第一个实例的第一个数据库
            schemaName.value = schemaList.value[0].schemaName;
        }
        return schemaList.value;
    }
};
// 查询数据库实例
const getServerInstanceList = async () => {
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
        // 默认第一个实例
        if (serverList.value && serverList.value.length > 0) {
            instanceId.value = serverList.value[0].id;
            getSchemaList(instanceId.value);
        }
        return serverList.value;
    });
};

onMounted(async () => {
    await getServerInstanceList();
});
</script>

<style lang="scss" scoped></style>
