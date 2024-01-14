<template>
    <div>
        <!-- <span>{{ rowData }}</span> -->
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

            <el-form-item label="数据库库名" prop="schema">
                <el-select
                    v-model="schema"
                    filterable
                    placeholder="数据库实例"
                    @change="getTableList"
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="表名称" prop="tableList">
                <el-select
                    v-model="tableList"
                    placeholder="表名称"
                    filterable
                    multiple
                >
                    <el-option
                        v-for="item in schemaTableList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                        <span style="float: left">{{ item.label }}</span>
                        <span
                            style="
                                float: right;
                                color: var(--el-text-color-secondary);
                                font-size: 13px;
                            "
                            >【{{ item.tableComment }}】</span
                        >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="客户端地址" prop="clientId">
                <el-select
                    v-model="clientId"
                    placeholder="客户端地址"
                    filterable
                    @change="(val) => getClientInstanceAlwaysUserPathList(val)"
                >
                    <el-option
                        v-for="item in clientList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="文件地址" prop="absolutePath">
                <el-select
                    v-model="absolutePath"
                    placeholder="文件地址"
                    filterable
                    clearable
                    allow-create
                >
                    <el-option
                        v-for="item in clientJavaPathList"
                        :key="item.value"
                        :label="item.absolutePath"
                        :value="item.absolutePath"
                    >
                        <el-row>
                            <el-col :span="12">
                                <span style="float: left">{{
                                    item.absolutePath
                                }}</span>
                            </el-col>
                            <el-col :span="12">
                                <span
                                    style="
                                        float: right;
                                        color: #8492a6;
                                        font-size: 13px;
                                    "
                                >
                                    使用的数据库 {{ item.schemaName }}
                                </span>
                            </el-col>
                        </el-row>
                    </el-option>
                </el-select>

                <!--                <el-input-->
                <!--                    v-model="absolutePath"-->
                <!--                    placeholder="/Users/wujiawei/IdeaProjects/wu-framework-parent/wu-freamwork-test"-->
                <!--                    clearable-->
                <!--                ></el-input>-->
            </el-form-item>
            <el-form-item label="文件前缀" prop="prefix">
                <el-input v-model="prefix" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="架构" prop="webArchitecture">
                <el-select
                    v-model="webArchitecture"
                    placeholder="架构"
                    filterable
                >
                    <el-option label="DDD" value="DDD_ARCHITECTURE" />
                    <el-option label="WEB_MVC" value="WEB_MVC" />
                    <el-option label="FEIGN" value="FEIGN_API" />
                </el-select>
            </el-form-item>
            <el-form-item label="ORM" prop="ormArchitecture">
                <el-select
                    v-model="ormArchitecture"
                    placeholder="ORM"
                    filterable
                >
                    <el-option label="MYBATIS" value="MYBATIS" />
                    <el-option label="LAZY" value="LAZY" />
                </el-select>
            </el-form-item>
            <el-form-item label="包名称" prop="packageName">
                <el-select
                    v-model="packageName"
                    placeholder="com.alimama.demo"
                    filterable
                    clearable
                    allow-create
                >
                    <el-option
                        v-for="item in clientJavaPathList"
                        :key="item.value"
                        :label="item.packageName"
                        :value="item.packageName"
                    >
                        <el-row>
                            <el-col :span="12">
                                <span style="float: left">{{
                                    item.packageName
                                }}</span>
                            </el-col>
                            <el-col :span="12">
                                <span
                                    style="
                                        float: right;
                                        color: #8492a6;
                                        font-size: 13px;
                                    "
                                >
                                    使用的数据库 {{ item.schemaName }}
                                </span>
                            </el-col>
                        </el-row>
                    </el-option>
                </el-select>
            </el-form-item>
        </el-form>

        <span>
            <el-button type="primary" @click="onSubmit()">确定</el-button>
        </span>
    </div>
</template>
<script>
import acwClientMenu from "@/views/layoutpages/acw_client/AcwClientMenu";

export default {
    data: () => ({
        description: "客户端生成code工厂",
        buttons: {
            generate: { name: "生成代码" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "HotWater",
        name: "客户端生成code工厂",
        parentMenu: acwClientMenu,
    }),
};
</script>

<script setup>
//?导入公共查询方法

import { onMounted, reactive, ref, toRefs } from "vue";

const rules = {
    instanceId: [
        {
            required: true,
            message: "请输入数据库服务器",
            trigger: "blur",
        },
    ],
    schema: [
        {
            required: true,
            message: "请选择数据库库名",
            trigger: "blur",
        },
    ],
    tableList: [
        {
            required: true,
            message: "请选择数据库表",
            trigger: "blur",
        },
    ],
    clientId: [
        {
            required: true,
            message: "请选择客户端",
            trigger: "blur",
        },
    ],
    absolutePath: [
        {
            required: true,
            message: "请输入文件输出地址",
            trigger: "blur",
        },
    ],
    packageName: [
        {
            required: true,
            message: "请输入项目包名",
            trigger: "blur",
        },
    ],
};

const formRef = ref(null);
const form = reactive({
    schema: "",
    instanceId: "",
    tableList: [],
    clientId: "",
    absolutePath: "",
    prefix: "",
    webArchitecture: "DDD_ARCHITECTURE",
    ormArchitecture: "LAZY",
    packageName: "",
});
const {
    schema,
    instanceId,
    tableList,
    clientId,
    absolutePath,
    prefix,
    webArchitecture,
    ormArchitecture,
    packageName,
} = toRefs(form);
const serverList = ref([]);
const schemaList = ref([]);
const schemaTableList = ref([]);
const clientList = ref([]);
const clientJavaPathList = ref([]);

onMounted(async () => {
    getInstanceList();
    getClientInstanceList();
});

/**
 * 获取实例上的表
 */
const getTableList = () => {
    // 查询数据表信息
    VE_API.system
        .tableList({
            instanceId: instanceId.value,
            schemaName: schema.value,
        })
        .then((res) => {
            if (res.data) {
                res.data.map((item) => {
                    item.label = item.tableName;
                    item.value = item.tableName;
                });
                schemaTableList.value = res.data ? res.data : [];
            }
        });
};
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
/**
 * 获取客户端实例
 */
const getClientInstanceList = () => {
    // 获取客户端实例
    VE_API.system.clientInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.clientId;
            item.value = item.clientId;
        });
        clientList.value = res.data ? res.data : [];
    });
};
/**
 * 获取客户端常用路径
 */
const getClientInstanceAlwaysUserPathList = (clientId) => {
    VE_API.system.clientJavaPathList({ clientId: clientId }).then((res) => {
        if (res.code === 0 && res.data) {
            res.data.map((item) => {
                item.label = item.absolutePath;
                item.value = item.absolutePath;
            });
            clientJavaPathList.value = res.data ? res.data : [];
        }
    });
};
/**
 * @description:提交
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            console.log(form);
            res = VE_API.system.clientGenerateLocalJava({
                instanceId: form.instanceId,
                schemaName: form.schema,
                tableList: form.tableList,
                absolutePath: form.absolutePath,
                packageName: form.packageName,
                webArchitecture: form.webArchitecture,
                prefix: form.prefix,
                ormArchitecture: form.ormArchitecture,
                clientId: form.clientId,
            });
            const { code } = res;
            if (code === 0) {
                console.log(res);
            }
        } else {
            console.log("error submit!!");
            return false;
        }
    });
};
</script>

<style lang="scss" scoped></style>
