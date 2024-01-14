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
            <el-form-item label="项目名" prop="projectId">
                <el-select
                    v-model="projectId"
                    placeholder="项目名"
                    @change="changeProject"
                    clearable
                >
                    <el-option
                        v-for="item in projectList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                        :serverId="item"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="数据库" prop="schemaName">
                <el-select v-model="schemaName" placeholder="数据库" clearable>
                    <el-option
                        v-for="item in serverSchemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="应用名" prop="applicationName">
                <el-input
                    v-model="applicationName"
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
const projectList = ref(null);
const serverSchemaList = ref(null);

const form = reactive({
    applicationName: "",
    projectId: "",
    applicationId: "",
    instanceId: "",
    schemaName: "",
});
const { projectId, applicationName, applicationId, instanceId, schemaName } =
    toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((projectId.value = rowData.value.projectId),
    (applicationId.value = rowData.value.applicationId),
    (instanceId.value = rowData.value.instanceId),
    (schemaName.value = rowData.value.schemaName),
    (applicationName.value = rowData.value.applicationName));
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
                res = await VE_API.system.applicationAdd(form);
            } else {
                res = await VE_API.system.applicationEdit({
                    applicationId: rowData.value.applicationId,
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
    VE_API.system.projectList().then((res) => {
        res.data.map((item) => {
            item.label = item.projectName;
            item.value = item.id;
        });
        projectList.value = res.data ? res.data : [];
    });
});
const findSchema = async (instanceId = null) => {
    if (instanceId == null) {
        return;
    }
    // 查询数据库
    let res = await VE_API.system.schemaList({
        instanceId,
    });
    const { code } = res;
    if (code === 0) {
        res.data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        serverSchemaList.value = res.data ? res.data : [];
        // databaseSchemaId.value = serverSchemaList.value[0].id;
    }
};
const changeProject = (projectId) => {
    console.log(projectId);
    if (projectId) {
        let project = projectList.value.find((item) => item.id === projectId);
        findSchema(project.instanceId);
    } else {
        serverSchemaList.value = [];
    }
};
</script>

<style lang="scss" scoped></style>
