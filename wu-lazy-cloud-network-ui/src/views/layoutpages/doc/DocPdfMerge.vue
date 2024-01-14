<template>
    <div>
        <!-- 表单 -->
        <el-form
            :model="form"
            ref="formRef"
            :rules="rules"
            label-width="80px"
            :inline="false"
        >
            <el-form-item label="文件数据" prop="pdfList">
                <el-upload
                    name="pdfList"
                    :multiple="true"
                    :show-file-list="true"
                    :on-change="onChange"
                    :on-remove="pdfFileRemove"
                    limit="1000"
                    v-model="pdfList"
                    placeholder=""
                    clearable
                    :auto-upload="false"
                    accept=".pdf"
                    class="upload-demo"
                    drag
                >
                    <!--                    <el-button size="medium" type="primary">上传文件</el-button>-->
                    <div style="padding: 10px 0">
                        <el-icon class="el-icon--upload"
                            ><upload-filled
                        /></el-icon>
                        <p
                            style="
                                margin-top: 10px;
                                font-size: 14px;
                                color: #409eff;
                            "
                        >
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
            <el-form-item label="合并后文件名称" prop="targetName">
                <el-input
                    v-model="targetName"
                    placeholder="合并后文件名称"
                    clearable
                ></el-input>
            </el-form-item>
        </el-form>

        <span>
            <el-button type="primary" @click="onSubmit()"
                >合并文件并下载</el-button
            >
        </span>
    </div>
</template>

<script>
import docMenu from "@/views/layoutpages/doc/DocMenu";

export default {
    data: () => ({
        description: "[DOC]PDF合并",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            preview: { name: "预览", toPath: true }, //topath:true 需要设置跳转路径
            down: { name: "下载" }, //topath:true 需要设置跳转路径
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "UploadFilled",
        name: "[DOC]PDF合并",
        parentMenu: docMenu,
    }),
};
</script>
<script setup>
import { reactive, toRefs, onMounted, ref } from "vue";
import { UploadFilled } from "@element-plus/icons-vue";

const rules = {
    targetName: [
        {
            required: true,
            message: "请输入文件名称",
            trigger: "blur",
        },
    ],

    pdfList: [
        {
            required: true,
            message: "请上传文件",
            trigger: "blur",
        },
    ],
};

const formRef = ref(null);
const form = reactive({
    targetName: "",
    uid: "",
    pdfList: [],
    type: "",
    length: 0,
});
const { targetName, pdfList } = toRefs(form);

onMounted(async () => {
    console.log("1234");
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
    var targetName = file.name;
    var size = file.size;
    form.targetName = targetName;
    if (size != null) {
        form.length = formatFileSize(size);
    }

    // form.pdfList = file.raw;
    form.pdfList.push(file.raw);
    if (targetName !== null) {
        var split = targetName.split(".");
        form.type = split[split.length - 1];
    }
};
const pdfFileRemove = (file, fileList) => {
    form.pdfList = fileList.map((item) => item.raw);
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
            form.pdfList.forEach((item) => {
                fd.append("multipartFileList", item); //传文件
            });
            fd.append("targetName", form.targetName);

            let res = await VE_API.system.docPdfMerge(fd, {
                responseType: "blob",
            });

            let fileName = res.headers["file-name"];
            // 获取文件名
            fileName = decodeURIComponent(fileName);
            let url = window.URL.createObjectURL(new Blob([res.data]));
            let link = document.createElement("a");
            link.style.display = "none";
            link.href = url;
            // eslint-disable-next-line no-undef
            link.setAttribute("download", fileName); //指定下载后的文件名，防跳转
            document.body.appendChild(link);
            link.click();
            // 释放内存
            window.URL.revokeObjectURL(link.href);
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};
</script>

<style lang="scss" scoped></style>
