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
            <el-form-item label="项目名称" prop="projectName">
                <el-input
                    v-model="projectName"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="数据库实例" prop="instanceId">
                <el-select
                    v-model="instanceId"
                    placeholder="数据库实例"
                    clearable
                >
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="项目拥有者" prop="owner">
                <el-input v-model="owner" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="项目版本" prop="version">
                <el-input v-model="version" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="ORM框架" prop="ormFrameEnums">
                <el-select
                    v-model="ormFrameEnums"
                    placeholder="ORM框架"
                    clearable
                >
                    <el-option label="Lazy" value="UPSERT" />
                    <el-option label="mybatis" value="MYBATIS" />
                    <el-option label="JPA" value="JPA" />
                </el-select>
            </el-form-item>
            <el-form-item label="UI框架" prop="uiFrameEnums">
                <el-select
                    v-model="uiFrameEnums"
                    placeholder="UI框架"
                    clearable
                >
                    <el-option label="VUE" value="VUE" />
                </el-select>
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
    projectName: [
        {
            required: true,
            message: "请输入服务器名称",
            trigger: "blur",
        },
    ],
    instanceId: [
        {
            required: true,
            message: "请选择数据库服务器",
            trigger: "blur",
        },
    ],
    version: [
        {
            required: true,
            message: "请输入项目版本",
            trigger: "blur",
        },
    ],
    ormFrameEnums: [
        {
            required: true,
            message: "请选择ORM框架",
            trigger: "change",
        },
    ],
    owner: [
        {
            required: true,
            message: "请输入项目拥有者",
            trigger: "change",
        },
    ],
    uiFrameEnums: [
        {
            required: true,
            message: "请输入项目UI框架",
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
    projectName: "",
    instanceId: "",
    version: "",
    ormFrameEnums: "",
    uiFrameEnums: "",
    owner: "",
});
const { projectName, instanceId, version, ormFrameEnums, uiFrameEnums, owner } =
    toRefs(form);
const serverList = ref(null);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((projectName.value = rowData.value.projectName),
    (instanceId.value = rowData.value.instanceId),
    (version.value = rowData.value.version),
    (ormFrameEnums.value = rowData.value.ormFrameEnums),
    (owner.value = rowData.value.owner),
    (uiFrameEnums.value = rowData.value.uiFrameEnums));

onMounted(async () => {
    // 查询数据库实例
    let res = await VE_API.system.databaseInstanceList();
    res.data.map((item) => {
        item.label = item.instanceName;
        item.value = item.id;
        serverList.value = res.data ? res.data : [];
    });
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
            if (title.value == "添加") {
                res = await VE_API.system.projectAdd(form);
            } else {
                res = await VE_API.system.projectEdit({
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
</script>

<style lang="scss" scoped></style>
