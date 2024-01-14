<template>
    <div class="ve_container">
        <!-- 表单 -->
        <el-form
            :model="form"
            ref="formRef"
            :rules="rules"
            label-width="80px"
            :inline="false"
        >
            <el-form-item label="数据库实例" prop="instanceId">
                <el-select
                    v-model="instanceId"
                    placeholder="数据库实例"
                    filterable
                    @change="(val) => getSchemaList(val)"
                >
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="数据库库名" prop="schemaName">
                <el-select
                    v-model="schema"
                    filterable
                    placeholder="数据库实例"
                    @change="findInstanceSchemaColumnList"
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>

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
        </el-form>

        <span>
            <el-button type="primary" @click="onSubmit()">确定</el-button>
        </span>
    </div>
</template>
<script>
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    data: () => ({
        description: "数据库信息导入",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            analysisSchema: { name: "解析schema" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "MagicStick",
        name: "数据库信息导入",
        parentMenu: acwMenu,
    }),
};
</script>
<script setup>
import { reactive, toRefs, onMounted, ref } from "vue";

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

const formRef = ref(null);
const form = reactive({
    schema: "",
    instanceId: "",
    data: [],
});
const { schema, instanceId, data } = toRefs(form);
const serverList = ref(null);
const schemaList = ref(null);

/**
 * 获取数据库实例
 */
const getInstanceList = () => {
    // 查询数据库实例
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
    });
};
const getSchemaList = (databaseInstance = null) => {
    console.log(databaseInstance);
    // 查询数据schema信息
    VE_API.system
        .schemaList({
            instanceId: instanceId.value,
        })
        .then((res) => {
            res.data.map((item) => {
                item.label = item.schemaName;
                item.value = item.schemaName;
            });
            schemaList.value = res.data ? res.data : [];
        });
};

onMounted(async () => {
    getInstanceList();
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
            fd.append("multipartFileList", form.data); //传文件
            fd.append("instanceId", form.instanceId);
            fd.append("schema", form.schema);

            let res = await VE_API.system.aceSchemaImportData(fd);
            const { code } = res;
            if (code === 0) {
                console.log(code);
            }
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};
</script>

<style lang="scss" scoped></style>
