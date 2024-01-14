<template>
    <el-dialog
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        :width="'80%'"
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
            <el-form-item label="数据库" prop="schema">
                <el-select
                    v-model="schema"
                    placeholder="数据库"
                    @change="changeSchema"
                    clearable
                >
                    <el-option
                        v-for="item in schemaList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
            <el-form-item label="接口分组" prop="tag">
                <el-input v-model="tag" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="接口描述" prop="apiComment">
                <el-input
                    v-model="apiComment"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="接口类型" prop="apiMethod">
                <el-select v-model="apiMethod" placeholder="" clearable>
                    <el-option label="GET" value="GET" />
                    <el-option label="HEAD" value="HEAD" />
                    <el-option label="POST" value="POST" />
                    <el-option label="PUT" value="PUT" />
                    <el-option label="PATCH" value="PATCH" />
                    <el-option label="DELETE" value="DELETE" />
                    <el-option label="OPTIONS" value="OPTIONS" />
                    <el-option label="TRACE" value="TRACE" />
                </el-select>
            </el-form-item>
            <el-form-item label="api返回结果类型" prop="apiResultType">
                <el-select v-model="apiResultType" placeholder="" clearable>
                    <el-option label="单个对象" :value="0" />
                    <el-option label="集合对象" :value="1" />
                    <el-option label="分页对象" :value="2" />
                    <el-option label="空" :value="3" />
                </el-select>
            </el-form-item>
            <el-form-item label="执行类型" prop="executeType">
                <el-select v-model="executeType" placeholder="" clearable>
                    <el-option label="查询数据" value="select" />
                    <el-option label="更新数据" value="update" />
                    <el-option label="删除数据" value="delete" />
                    <el-option label="插入数据" value="insert" />
                    <el-option label="插入或者更新数据" value="upsert" />
                </el-select>
            </el-form-item>
            <el-form-item label="接口地址" prop="apiPath">
                <el-input v-model="apiPath" placeholder="" clearable></el-input>
            </el-form-item>
            <el-tabs v-model="apiType" @tab-click="handleClick">
                <el-tab-pane label="手动选择" name="nanual">
                    <el-form-item label="应用表" prop="tableNameList">
                        <el-select
                            v-model="tableNameList"
                            placeholder="应用表"
                            @change="changeTable"
                            multiple
                            clearable
                        >
                            <el-option
                                v-for="item in tableList"
                                :key="item.value"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                    </el-form-item>
                    <div style="text-align: center" class="api_transfer_result">
                        <el-transfer
                            v-model="apiFactoryTransferData.leftValue"
                            style="text-align: left; display: inline-block"
                            filterable
                            :left-default-checked="[2, 3]"
                            :right-default-checked="[1]"
                            :titles="['原始数据', '接口返回数据']"
                            :button-texts="['移除返回数据', '添加返回数据']"
                            :data="tableColumnList"
                            :props="{ key: 'value', label: 'desc' }"
                            :format="{
                                noChecked: '${total}',
                                hasChecked: '${checked}/${total}',
                            }"
                            @change="handleTransferChange"
                        >
                            <template #default="{ option }">
                                <span style="float: left"
                                    >{{ option.label }} 【{{
                                        option.columnComment
                                    }}】
                                </span>
                                <span style="float: right">
                                    {{ option.tableName }}
                                </span>
                            </template>
                            <template #left-footer>
                                <el-button class="transfer-footer" size="small"
                                    >Operation</el-button
                                >
                            </template>
                            <template #right-footer>
                                <el-button class="transfer-footer" size="small"
                                    >Operation</el-button
                                >
                            </template>
                        </el-transfer>
                    </div>
                    <div>
                        <span>静态参数</span>
                        <el-button
                            @click="
                                addItem(
                                    staticParamColumnIdList,
                                    'STATIC_PARAM_TYPE',
                                )
                            "
                            type="primary"
                            style="float: right"
                            >增加
                        </el-button>
                        <el-empty v-show="staticParamColumnIdList.length === 0">
                        </el-empty>
                        <el-form-item
                            v-for="(it, index) in staticParamColumnIdList"
                            :key="index"
                        >
                            <el-select
                                v-model="staticParamColumnIdList[index].name"
                                placeholder="静态参数选择"
                            >
                                <el-option
                                    v-for="item in tableColumnList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                    <span style="float: left"
                                        >{{ item.label }}【{{
                                            item.columnComment
                                        }}】</span
                                    >
                                    <span
                                        style="
                                            float: right;
                                            color: var(
                                                --el-text-color-secondary
                                            );
                                            font-size: 13px;
                                        "
                                        >{{ item.tableName }}</span
                                    ></el-option
                                >
                            </el-select>
                            <el-select
                                v-model="staticParamColumnIdList[index].term"
                                placeholder="条件(大于、等于、模糊)"
                            >
                                <el-option label="小等于" value="<" />
                                <el-option label="等于" value="=" />
                                <el-option label="大于等于" value=">=" />
                                <el-option label="小于等于" value="<=" />
                                <el-option label="不等于" value="!=" />
                                <el-option label="模糊查询" value="like" />
                                <el-option label="in" value="in" />
                            </el-select>

                            <el-input
                                class="w-50 m-2"
                                v-model="
                                    staticParamColumnIdList[index].defaultValue
                                "
                            />
                            <el-button
                                @click="
                                    removeItem(staticParamColumnIdList, index)
                                "
                                type="danger"
                                style="float: right"
                                >删除
                            </el-button>
                        </el-form-item>
                    </div>
                    <div>
                        <span>路径参数</span>
                        <el-button
                            @click="
                                addItem(
                                    pathParamColumnIdList,
                                    'PATH_PARAM_TYPE',
                                )
                            "
                            type="primary"
                            style="float: right"
                            >增加
                        </el-button>
                        <el-empty v-show="pathParamColumnIdList.length === 0">
                        </el-empty>
                        <el-form-item
                            v-for="(it, index) in pathParamColumnIdList"
                            :key="index"
                        >
                            <el-select
                                v-model="pathParamColumnIdList[index].name"
                                placeholder="路径参数选择"
                            >
                                <el-option
                                    v-for="item in tableColumnList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                    <span style="float: left"
                                        >{{ item.label }}【{{
                                            item.columnComment
                                        }}】</span
                                    >
                                    <span
                                        style="
                                            float: right;
                                            color: var(
                                                --el-text-color-secondary
                                            );
                                            font-size: 13px;
                                        "
                                        >{{ item.tableName }}</span
                                    ></el-option
                                >
                            </el-select>
                            <el-select
                                v-model="pathParamColumnIdList[index].term"
                                placeholder="条件(大于、等于、模糊)"
                            >
                                <el-option label="小等于" value="<" />
                                <el-option label="等于" value="=" />
                                <el-option label="大于等于" value=">=" />
                                <el-option label="小于等于" value="<=" />
                                <el-option label="不等于" value="!=" />
                                <el-option label="模糊查询" value="like" />
                                <el-option label="in" value="in" />
                            </el-select>
                            <el-select
                                v-model="
                                    pathParamColumnIdList[index]
                                        .defaultValueType
                                "
                                placeholder="参数类型"
                            >
                                <el-option label="静态参数" :value="1" />
                                <el-option label="动态参数" :value="2" />
                                <el-option label="关联参数" :value="3" />
                            </el-select>

                            <el-input
                                class="w-50 m-2"
                                v-if="
                                    pathParamColumnIdList[index]
                                        .defaultValueType == 1
                                "
                                v-model="
                                    pathParamColumnIdList[index].defaultValue
                                "
                            />
                            <el-input
                                class="w-50 m-2"
                                v-if="
                                    pathParamColumnIdList[index]
                                        .defaultValueType == 2
                                "
                                :placeholder="
                                    '${' +
                                    pathParamColumnIdList[index].name +
                                    '}'
                                "
                                v-model="
                                    pathParamColumnIdList[index].defaultValue
                                "
                            />
                            <el-select
                                v-if="
                                    pathParamColumnIdList[index]
                                        .defaultValueType == 3
                                "
                                v-model="
                                    pathParamColumnIdList[index].defaultValue
                                "
                                placeholder="请选择关联的表字段"
                            >
                                <el-option
                                    v-for="item in tableColumnList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                    <span style="float: left"
                                        >{{ item.label }}【{{
                                            item.columnComment
                                        }}】</span
                                    >
                                    <span
                                        style="
                                            float: right;
                                            color: var(
                                                --el-text-color-secondary
                                            );
                                            font-size: 13px;
                                        "
                                        >{{ item.tableName }}</span
                                    ></el-option
                                >
                            </el-select>
                            <el-button
                                @click="
                                    removeItem(pathParamColumnIdList, index)
                                "
                                type="danger"
                                style="float: right"
                                >删除
                            </el-button>
                        </el-form-item>
                    </div>
                    <div>
                        <span>请求参数</span>
                        <el-button
                            @click="
                                addItem(
                                    requestParamColumnIdList,
                                    'REQUEST_PARAM_TYPE',
                                )
                            "
                            style="float: right"
                            type="primary"
                            >增加
                        </el-button>
                        <el-empty
                            v-show="requestParamColumnIdList.length === 0"
                        ></el-empty>
                        <el-form-item
                            v-for="(it, index) in requestParamColumnIdList"
                            :key="index"
                        >
                            <el-select
                                v-model="requestParamColumnIdList[index].name"
                                placeholder="请求参数选择"
                            >
                                <el-option
                                    v-for="item in tableColumnList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                    <span style="float: left"
                                        >{{ item.label }}【{{
                                            item.columnComment
                                        }}】</span
                                    >
                                    <span
                                        style="
                                            float: right;
                                            color: var(
                                                --el-text-color-secondary
                                            );
                                            font-size: 13px;
                                        "
                                        >{{ item.tableName }}</span
                                    ></el-option
                                >
                            </el-select>
                            <el-select
                                v-model="requestParamColumnIdList[index].term"
                                placeholder="条件(大于、等于、模糊)"
                            >
                                <el-option label="小等于" value="<" />
                                <el-option label="等于" value="=" />
                                <el-option label="大于等于" value=">=" />
                                <el-option label="小于等于" value="<=" />
                                <el-option label="不等于" value="!=" />
                                <el-option label="模糊查询" value="like" />
                                <el-option label="in" value="in" />
                            </el-select>
                            <el-select
                                v-model="
                                    requestParamColumnIdList[index]
                                        .defaultValueType
                                "
                                placeholder="参数类型"
                            >
                                <el-option label="静态参数" :value="1" />
                                <el-option label="动态参数" :value="2" />
                                <el-option label="关联参数" :value="3" />
                            </el-select>
                            <el-input
                                class="w-50 m-2"
                                v-if="
                                    requestParamColumnIdList[index]
                                        .defaultValueType == 1
                                "
                                v-model="
                                    requestParamColumnIdList[index].defaultValue
                                "
                            />
                            <el-input
                                class="w-50 m-2"
                                v-if="
                                    requestParamColumnIdList[index]
                                        .defaultValueType == 2
                                "
                                :placeholder="
                                    '${' +
                                    requestParamColumnIdList[index].name +
                                    '}'
                                "
                                v-model="
                                    requestParamColumnIdList[index].defaultValue
                                "
                            />
                            <el-select
                                v-if="
                                    requestParamColumnIdList[index]
                                        .defaultValueType == 3
                                "
                                v-model="
                                    requestParamColumnIdList[index].defaultValue
                                "
                                placeholder="请选择关联的表字段"
                            >
                                <el-option
                                    v-for="item in tableColumnList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                    <span style="float: left"
                                        >{{ item.label }}【{{
                                            item.columnComment
                                        }}】</span
                                    >
                                    <span
                                        style="
                                            float: right;
                                            color: var(
                                                --el-text-color-secondary
                                            );
                                            font-size: 13px;
                                        "
                                        >{{ item.tableName }}</span
                                    ></el-option
                                >
                            </el-select>
                            <el-button
                                @click="
                                    removeItem(requestParamColumnIdList, index)
                                "
                                style="float: right"
                                type="danger"
                                >删除
                            </el-button>
                        </el-form-item>
                    </div>
                    <div>
                        <span>请求体参数</span>
                        <el-button
                            @click="
                                addItem(
                                    requestBodyParamColumnIdList,
                                    'REQUEST_BODY_PARAM_TYPE',
                                )
                            "
                            style="float: right"
                            type="primary"
                            >增加
                        </el-button>
                        <el-empty
                            v-show="requestBodyParamColumnIdList.length === 0"
                        ></el-empty>
                        <el-form-item
                            v-for="(it, index) in requestBodyParamColumnIdList"
                            :key="index"
                        >
                            <el-select
                                v-model="
                                    requestBodyParamColumnIdList[index].name
                                "
                                placeholder="请选择"
                            >
                                <el-option
                                    v-for="item in tableColumnList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                    <span style="float: left"
                                        >{{ item.label }}【{{
                                            item.columnComment
                                        }}】</span
                                    >
                                    <span
                                        style="
                                            float: right;
                                            color: var(
                                                --el-text-color-secondary
                                            );
                                            font-size: 13px;
                                        "
                                        >{{ item.tableName }}</span
                                    ></el-option
                                >
                            </el-select>
                            <el-select
                                v-model="
                                    requestBodyParamColumnIdList[index].term
                                "
                                placeholder="条件(大于、等于、模糊)"
                            >
                                <el-option label="大于" value=">" />
                                <el-option label="小等于" value="<" />
                                <el-option label="等于" value="=" />
                                <el-option label="大于等于" value=">=" />
                                <el-option label="小于等于" value="<=" />
                                <el-option label="不等于" value="!=" />
                                <el-option label="模糊查询" value="like" />
                                <el-option label="in" value="in" />
                            </el-select>
                            <el-select
                                v-model="
                                    requestBodyParamColumnIdList[index]
                                        .defaultValueType
                                "
                                placeholder="参数类型"
                            >
                                <el-option label="静态参数" :value="1" />
                                <el-option label="动态参数" :value="2" />
                                <el-option label="关联参数" :value="3" />
                            </el-select>
                            <el-input
                                class="w-50 m-2"
                                v-if="
                                    requestBodyParamColumnIdList[index]
                                        .defaultValueType == 1
                                "
                                v-model="
                                    requestBodyParamColumnIdList[index]
                                        .defaultValue
                                "
                            />
                            <el-input
                                class="w-50 m-2"
                                v-if="
                                    requestBodyParamColumnIdList[index]
                                        .defaultValueType == 2
                                "
                                :placeholder="
                                    '${' +
                                    requestBodyParamColumnIdList[index].name +
                                    '}'
                                "
                                v-model="
                                    requestBodyParamColumnIdList[index]
                                        .defaultValue
                                "
                            />
                            <el-select
                                v-if="
                                    requestBodyParamColumnIdList[index]
                                        .defaultValueType == 3
                                "
                                v-model="
                                    requestBodyParamColumnIdList[index]
                                        .defaultValue
                                "
                                placeholder="请选择关联的表字段"
                            >
                                <el-option
                                    v-for="item in tableColumnList"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                >
                                    <span style="float: left"
                                        >{{ item.label }}【{{
                                            item.columnComment
                                        }}】</span
                                    >
                                    <span
                                        style="
                                            float: right;
                                            color: var(
                                                --el-text-color-secondary
                                            );
                                            font-size: 13px;
                                        "
                                        >{{ item.tableName }}</span
                                    ></el-option
                                >
                            </el-select>
                            <el-button
                                @click="
                                    removeItem(
                                        requestBodyParamColumnIdList,
                                        index,
                                    )
                                "
                                type="danger"
                                style="float: right"
                                >删除
                            </el-button>
                        </el-form-item>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="SQL" name="SQL">
                    <el-input v-model="form.sql"></el-input>
                </el-tab-pane>
            </el-tabs>
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
    schema: [
        {
            required: true,
            message: "请选择数据库",
            trigger: "blur",
        },
    ],
    tag: [
        {
            required: true,
            message: "请输入接口分组",
            trigger: "blur",
        },
    ],
    apiComment: [
        {
            required: true,
            message: "请输入接口描述",
            trigger: "change",
        },
    ],
    apiMethod: [
        {
            required: true,
            message: "请选择接口类型",
            trigger: "change",
        },
    ],
    apiResultType: [
        {
            required: true,
            message: "请选择返回结果类型",
            trigger: "change",
        },
    ],
    executeType: [
        {
            required: true,
            message: "请选择执行类型",
            trigger: "change",
        },
    ],
    apiPath: [
        {
            required: true,
            message: "请选择接口地址",
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
const schemaList = ref(null);
const tableList = ref(null);
const tableColumnList = ref([]);

//  api 工厂穿梭数据
const apiFactoryTransferData = ref({
    leftValue: [],
});
// 请求体
const requestBodyParamColumnIdList = ref([]);
//  路径参数
const pathParamColumnIdList = ref([]);
// 请求参数
const requestParamColumnIdList = ref([]);
// 静态参数
const staticParamColumnIdList = ref([]);

const form = reactive({
    apiId: null,
    applicationId: "",
    tableNameList: [],
    schema: "",
    tag: "default",
    apiComment: "apiComment",
    apiMethod: "GET",
    apiPath: "",
    interfaceInParamCommandList: [], // 接口参数
    interfaceOutParamCommandList: [], // 返回参数
    // api返回结果类型 0单个对象，1集合对象，2分页对象
    apiResultType: 0,
    //  状态：0草稿，1发布，2有变更，3禁用
    apiStatus: 0,
    // 脚本类型：SQL、DataQL
    apiType: "SQL",
    // 执行类型: select、update、delete、insert、upsert
    executeType: "select",
    interfaceTableCommandList: [],
});
const {
    apiId,
    applicationId,
    schema,
    tableNameList,
    interfaceOutParamCommandList,
    tag,
    apiComment,
    apiMethod,
    apiResultType,
    executeType,
    apiPath,
    apiType,
    interfaceInParamCommandList,
} = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((apiId.value = rowData.value.apiId),
    (applicationId.value = rowData.value.applicationId),
    (schema.value = rowData.value.schema),
    (tag.value = rowData.value.tag),
    (apiComment.value = rowData.value.apiComment),
    (apiMethod.value = rowData.value.apiMethod),
    (apiType.value = rowData.value.apiType),
    (apiResultType.value = rowData.value.apiResultType),
    (executeType.value = rowData.value.executeType),
    (apiPath.value = rowData.value.apiPath),
    (interfaceInParamCommandList.value =
        rowData.value.interfaceInParamCommandList),
    (tableNameList.value = rowData.value.tableNameList));

const handleClick = () => {
    console.log("");
};

const handleTransferChange = (transferItem) => {
    console.log(transferItem);
};
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    if (apiType.value == "nanual") {
        let interfaceInParamCommandList = [];
        interfaceInParamCommandList = interfaceInParamCommandList.concat(
            pathParamColumnIdList.value,
        );
        interfaceInParamCommandList = interfaceInParamCommandList.concat(
            requestBodyParamColumnIdList.value,
        );
        interfaceInParamCommandList = interfaceInParamCommandList.concat(
            requestParamColumnIdList.value,
        );
        interfaceInParamCommandList = JSON.parse(
            JSON.stringify(interfaceInParamCommandList),
        );
        form.interfaceInParamCommandList = interfaceInParamCommandList;
        form.interfaceOutParamCommandList =
            apiFactoryTransferData.value.leftValue.map((item) => {
                return { name: item, mappingName: item };
            });
        form.interfaceTableCommandList = form.tableNameList.map((item) => {
            return { tableName: item };
        });
        console.log(apiFactoryTransferData);
        formRef.value.validate(async (valid) => {
            if (valid) {
                let res;
                if (title.value === "添加") {
                    res = await VE_API.system.clientApiAdd(form);
                } else {
                    res = await VE_API.system.clientApiEdit({
                        id: rowData.value.id,
                        ...form,
                    });
                }
                const { code } = res;
                console.log(res);
                if (code === 0) {
                    closeDialog();
                }
            } else {
                console.log("error submit!!");
                return false;
            }
        });
    } else {
        // 存储sql
        if (title.value === "添加") {
            VE_API.system.clientApiSQLAdd(form).then((res) => {
                const { code } = res;
                console.log(res);
                if (code === 0) {
                    closeDialog();
                }
            });
        } else {
            VE_API.system
                .clientApiSQLAdd({
                    id: rowData.value.id,
                    ...form,
                })
                .then((res) => {
                    const { code } = res;
                    console.log(res);
                    if (code === 0) {
                        closeDialog();
                    }
                });
        }
    }
};
onMounted(async () => {
    // 获取当前服务所有schema

    VE_API.system.clientFindSchemaList().then((res) => {
        res.data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaList.value = res.data ? res.data : [];
    });
    if (apiId.value) {
        // 根据ID获取接口信息
        const { code, data } = await VE_API.system.clientApiFindOne({
            apiId: apiId.value,
        });
        if (code === 0) {
            console.log(data);
            schema.value = data.schema;

            interfaceInParamCommandList.value = data.interfaceInParamList; // 接口请求参数
            interfaceOutParamCommandList.value = data.interfaceOutParamList; // 接口返回参数
            // 接口返回参数
            apiFactoryTransferData.value.leftValue =
                data.interfaceOutParamList.map((item) => item.name);
            tableNameList.value = data.interfaceTableList.map(
                (item) => item.tableName,
            );
        }
    }
    if (!schema.value) {
        // 查询默认schema
        const { code, data } = await VE_API.system.clientFindDefaultSchema();
        if (code === 0) {
            schema.value = data;
        }
    }

    // 查询默认schema对应的表
    changeSchema(schema.value);
    // 查询表对应的 字段

    console.log(tableNameList);
    //      选择字段列
    await findTableColumn(tableNameList.value);

    if (
        interfaceInParamCommandList.value &&
        interfaceInParamCommandList.value.length > 0
    ) {
        pathParamColumnIdList.value = interfaceInParamCommandList.value.filter(
            (item) => item.type === "PATH_PARAM_TYPE",
        );
        requestBodyParamColumnIdList.value =
            interfaceInParamCommandList.value.filter(
                (item) => item.type === "REQUEST_BODY_PARAM_TYPE",
            );
        requestParamColumnIdList.value =
            interfaceInParamCommandList.value.filter(
                (item) => item.type === "REQUEST_PARAM_TYPE",
            );
    }
});

/**
 * 获取数据库表信息
 * @param schema
 */
const changeSchema = (schema) => {
    console.log(schema);
    tableList.value = [];
    VE_API.system.clientFindTableList({ schema: schema }).then((res) => {
        res.data.map((item) => {
            item.label = item.tableName;
            item.value = item.tableName;
        });
        tableList.value = res.data ? res.data : [];
    });
};

const changeTable = (tableNameList) => {
    tableColumnList.value = [];
    if (tableNameList == null || tableNameList.length === 0) {
        return;
    }
    findTableColumn(tableNameList);
};

/**
 * 获取表字段
 * @param tableNameList
 * @returns {Promise<void>}
 */
const findTableColumn = async (tableNameList) => {
    if (
        tableNameList === undefined ||
        tableNameList.length === 0 ||
        tableNameList.length === undefined
    ) {
        return;
    }
    let tableNames = tableNameList.join();
    VE_API.system
        .clientFindColumnList({
            schema: schema.value,
            tableNameList: tableNames,
        })
        .then((res) => {
            res.data.map((item) => {
                item.label = item.columnName;
                // item.value = item.columnName;
                item.value = item.tableName + "." + item.columnName;
            });
            tableColumnList.value = res.data ? res.data : [];
        });
    tableNameList.value = tableNameList;
    console.log(tableColumnList);
};

const addItem = (paramColumnList, type = null) => {
    // 1。这里为什么改变list的大小就能实现动态增加呢？因为 el-form-item 遍历的是 list,list 中的每一项都是一个 el-form-item
    // 也就是说因为刚开始 list:[{"oneId":''}] 中,只有一个对象,所以才会只出现一个 el-form-item
    // 不信可以自己在初始化时 list 中多加入几个对象进行尝试(一定要理解，这里 list 集合的大小与 el-form-item 之间的关系)
    // 2、第二个问题:el-form-item 是动态增加了,但是如果 el-select 那里写的是 v-model="oneId" 呢？会发生什么？结果你会发现,只要增加一项 el-form-item ,每一项绑定的值都是你所选中的那一个值.为什么呢？因为每一项的 el-option的 :value 值都绑定在 el-select 的 v-model 上,但这是一个全局唯一值,当下一个 el-form-item 产生后,它里面的 el-select 中绑定的 v-model 还是那个 oneId 的值,因此才会出现这样的问题.好了,我们既然找到了原因,那就要来解决一下了,怎么解决呢？很简单:因为我前面说了,每一个 list 的遍历对象,都是一项 el-form-item,即 el-form-item 项数是和 list 的下标(里面存的对象的索引下标)相关联的,而这个下标,在每一个 el-form-item 中肯定是不一样的,因此我们只需要将 oneId 与这个 下标(即此处的 index) 发生关系即可,因此我们这里将 oneId 声明为了一个数组,当你每选中一个 option 时,都将这个 option 的value放入 oneId[当前el-form-item项数下标] 数组中
    console.log(type);
    console.log(pathParamColumnIdList.value);
    console.log(requestBodyParamColumnIdList.value);
    console.log(requestParamColumnIdList.value);
    if ("PATH_PARAM_TYPE" === type) {
        pathParamColumnIdList.value.push({ type: type });
    } else if ("REQUEST_BODY_PARAM_TYPE" === type) {
        requestBodyParamColumnIdList.value.push({ type: type });
    } else if ("REQUEST_PARAM_TYPE" === type) {
        requestParamColumnIdList.value.push({ type: type });
    } else if ("STATIC_PARAM_TYPE" === type) {
        staticParamColumnIdList.value.push({ type: type });
    }

    // paramColumnList.push({ type: type });
};

const removeItem = (paramColumnList, index) => {
    // 删除时,我们带两个参数,这个 it 可用可不用,因为我当时只是想看到删除的这个对象的信息,故而带上了; index 是 list 中该对象对应的下标,也是 el-form-item 的项数
    // 根据这个 index 下标删除 list 中 的该对象
    paramColumnList.splice(index, 1);
};
</script>
<style scoped>
/* 必需有scoped */
.api_transfer_result >>> .el-transfer-panel {
    width: 450px;
}
</style>
