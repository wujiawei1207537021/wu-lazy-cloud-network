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
            <el-form-item label="json字符串" prop="jsonSource">
                <el-input
                    style="height: 300px"
                    v-model="jsonSource"
                    placeholder="json字符串"
                    clearable
                ></el-input>
            </el-form-item>
        </el-form>

        <span>
            <el-button type="primary" @click="onSubmit()"
                >Json转换成Excel</el-button
            >
        </span>
    </div>
</template>

<script>
import docMenu from "@/views/layoutpages/doc/DocMenu";

export default {
    data: () => ({
        description: "Excel下载",
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
        name: "Excel下载",
        parentMenu: docMenu,
    }),
};
</script>
<script setup>
import { reactive, toRefs, onMounted, ref } from "vue";

const rules = {
    jsonSource: [
        {
            required: true,
            message: "请输入JSON字符串",
            trigger: "blur",
        },
    ],
};

const formRef = ref(null);
const form = reactive({
    jsonSource: "",
});
const { jsonSource } = toRefs(form);

onMounted(async () => {
    console.log("1234");
});

/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            // 将字符串转换为JavaScript对象
            const jsonData = JSON.parse(jsonSource.value);
            let res = await VE_API.system.docJson2Excel(jsonData);

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
