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
            <el-form-item label="服务器名称" prop="instanceName">
                <el-input
                    v-model="instanceName"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="用户名" prop="username">
                <el-input
                    v-model="username"
                    placeholder="root"
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-input
                    v-model="password"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="host" prop="host">
                <el-input
                    v-model="host"
                    placeholder="127.0.0.1"
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="端口" prop="port">
                <el-input
                    v-model="port"
                    placeholder="3306"
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="驱动程序类名" prop="driverClassName">
                <el-input
                    v-model="driverClassName"
                    placeholder="com.mysql.cj.jdbc.Driver"
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-input v-model="status" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="数据源类型" prop="lazyDataSourceType">
                <el-select
                    v-model="lazyDataSourceType"
                    placeholder="数据源类型"
                    clearable
                >
                    <el-option label="MySQL" value="MySQL" />
                    <el-option label="H2" value="H2" />
                    <el-option label="CLICK_HOUSE" value="CLICK_HOUSE" />
                    <el-option label="POSTGRESQL" value="POSTGRESQL" />
                </el-select>
            </el-form-item>
            <el-form-item label="排序" prop="sort">
                <el-input v-model="sort" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item
                label="是否初始化数据库到本地"
                prop="initializeToLocal"
            >
                <el-select
                    v-model="initializeToLocal"
                    placeholder="是否初始化数据库到本地"
                    clearable
                >
                    <el-option label="初始化数据库到本地" :value="true" />
                    <el-option label="不初始化数据库到本地" :value="false" />
                </el-select>
            </el-form-item>
        </el-form>

        <template v-slot:footer>
            <span>
                <el-button style="float: left" @click="testConnection()"
                    >测试连接</el-button
                >
                <el-button @click="closeDialog()">取消</el-button>
                <el-button type="primary" @click="onSubmit()">确定</el-button>
            </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { reactive, toRefs, ref, defineProps, defineEmits } from "vue";

const rules = {
    instanceName: [
        {
            required: true,
            message: "请输入服务器名称",
            trigger: "blur",
        },
    ],
    username: [
        {
            required: true,
            message: "请输入账户",
            trigger: "blur",
        },
    ],
    password: [
        {
            required: true,
            message: "请输入密码",
            trigger: "blur",
        },
    ],
    host: [
        {
            required: true,
            message: "host不能为空",
            trigger: "change",
        },
    ],
    port: [
        {
            required: true,
            message: "端口不能为空",
            trigger: "change",
        },
    ],
    driverClassName: [
        {
            required: true,
            message: "driverClassName",
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
    instanceName: "",
    driverClassName: "com.mysql.cj.jdbc.Driver",
    username: "root",
    password: "",
    host: "127.0.0.1",
    port: 3306,
    status: "",
    lazyDataSourceType: "MySQL",
    sort: 1,
    initializeToLocal: false,
});
const {
    instanceName,
    driverClassName,
    username,
    password,
    host,
    port,
    status,
    lazyDataSourceType,
    sort,
    initializeToLocal,
} = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((instanceName.value = rowData.value.instanceName),
    (username.value = rowData.value.username),
    (password.value = rowData.value.password),
    (driverClassName.value = rowData.value.driverClassName),
    (host.value = rowData.value.host),
    (port.value = rowData.value.port),
    (status.value = rowData.value.status),
    (lazyDataSourceType.value = rowData.value.lazyDataSourceType),
    (sort.value = rowData.value.sort),
    (initializeToLocal.value = rowData.value.initializeToLocal));

/**
 * 测试连接
 */
const testConnection = async () => {
    let res = await VE_API.system.databaseInstanceTest(form);
    const { code } = res;
    if (code === 0) {
        console.log("连接成功");
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
            let res;
            if (title.value === "添加") {
                res = await VE_API.system.databaseInstanceAdd(form);
            } else {
                res = await VE_API.system.databaseInstanceEdit({
                    id: rowData.value.id,
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
</script>

<style lang="scss" scoped></style>
