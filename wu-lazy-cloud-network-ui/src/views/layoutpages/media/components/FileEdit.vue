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
            <el-form-item label="文件数据" prop="data">
                <el-upload
                    name="data"
                    :multiple="false"
                    :show-file-list="true"
                    :on-change="onChange"
                    limit="1"
                    v-model="data"
                    placeholder=""
                    clearable
                    :auto-upload="false"
                >
                    <!--                    <el-button size="medium" type="primary">上传文件</el-button>-->
                    <div style="padding: 10px 0">
                        <Icon
                            type="ios-cloud-upload"
                            size="52"
                            style="#3399ff"
                        ></Icon>
                        <p style="margin-top: 10px; font-size: 14px">
                            点击或拖拽文件至此即可上传文件
                        </p>
                        <p
                            style="
                                margin-top: 20px;
                                font-size: 14px;
                                color: red;
                            "
                        >
                            请上传10GB以内的待测对象
                        </p>
                    </div>
                </el-upload>
            </el-form-item>
            <el-form-item label="文件名称" prop="name">
                <el-input
                    v-model="name"
                    placeholder="文件名称"
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
            <el-form-item label="文件类型" prop="type">
                <el-input v-model="type" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="大小" prop="length">
                <el-input v-model="length" placeholder="" clearable></el-input>
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
            message: "请输入文件名称",
            trigger: "blur",
        },
    ],

    data: [
        {
            required: true,
            message: "请上传文件",
            trigger: "blur",
        },
    ],
    type: [
        {
            required: true,
            message: "请选择文件类型",
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
    uid: "",
    data: [],
    describe: "",
    type: "",
    length: 0,
});
const { name, uid, data, describe, type, length } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((name.value = rowData.value.name),
    (uid.value = rowData.value.uid),
    (data.value = rowData.value.data),
    (describe.value = rowData.value.describe),
    (length.value = rowData.value.length),
    (type.value = rowData.value.type));

onMounted(async () => {
    console.log("123");
});

/**
 * 计算文件大小
 * @param fileSize
 * @returns {string}
 */
const formatFileSize = (fileSize) => {
    if (fileSize < 1024) {
        return fileSize + "B";
    } else if (fileSize < 1024 * 1024) {
        let temp = fileSize / 1024;
        temp = temp.toFixed(2);
        return temp + "KB";
    } else if (fileSize < 1024 * 1024 * 1024) {
        let temp = fileSize / (1024 * 1024);
        temp = temp.toFixed(2);
        return temp + "MB";
    } else {
        let temp = fileSize / (1024 * 1024 * 1024);
        temp = temp.toFixed(2);
        return temp + "GB";
    }
};
/**
 * 文件更改
 * */
const onChange = (file) => {
    var name = file.name;
    var size = file.size;
    form.name = name;
    form.uid = file.uid;
    if (size != null) {
        form.length = formatFileSize(size);
    }

    form.data = file.raw;
    if (name !== null) {
        var split = name.split(".");
        form.type = split[split.length - 1];
    }
};
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let fd = new FormData();
            fd.append("data", form.data); //传文件
            fd.append("name", form.name);
            fd.append("describe", form.describe);
            fd.append("type", form.type);
            fd.append("length", form.length);
            fd.append("uid", form.uid);

            let res;
            if (title.value === "添加") {
                res = await VE_API.system.playFileAdd(fd);
            } else {
                res = await VE_API.system.playFileEdit({
                    id: rowData.value.id,
                    ...fd,
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
