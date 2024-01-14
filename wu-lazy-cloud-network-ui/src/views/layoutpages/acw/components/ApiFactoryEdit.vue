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
            <el-form-item label="应用名称" prop="applicationId">
                <el-select
                    v-model="applicationId"
                    placeholder="应用名称"
                    @change="changeApplication"
                    clearable
                >
                    <el-option
                        v-for="item in applicationList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>
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
            <el-form-item label="接口分组" prop="tag">
                <el-input v-model="tag" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="接口描述" prop="description">
                <el-input
                    v-model="description"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="接口类型" prop="method">
                <el-select v-model="method" placeholder="" clearable>
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
            <el-form-item label="接口地址" prop="path">
                <el-input v-model="path" placeholder="" clearable></el-input>
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
                            >{{ option.label }} 【{{ option.columnComment }}】
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
                <span>路径参数</span>
                <el-button
                    @click="addItem(pathParamColumnIdList, 'PATH_PARAM_TYPE')"
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
                        v-model="pathParamColumnIdList[index].columnName"
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
                                    color: var(--el-text-color-secondary);
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
                    <el-button
                        @click="removeItem(pathParamColumnIdList, index)"
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
                        addItem(requestParamColumnIdList, 'REQUEST_PARAM_TYPE')
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
                        v-model="requestParamColumnIdList[index].columnName"
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
                                    color: var(--el-text-color-secondary);
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
                    <el-button
                        @click="removeItem(requestParamColumnIdList, index)"
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
                        v-model="requestBodyParamColumnIdList[index].columnName"
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
                                    color: var(--el-text-color-secondary);
                                    font-size: 13px;
                                "
                                >{{ item.tableName }}</span
                            ></el-option
                        >
                    </el-select>
                    <el-select
                        v-model="requestBodyParamColumnIdList[index].term"
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
                    <el-button
                        @click="removeItem(requestBodyParamColumnIdList, index)"
                        type="danger"
                        style="float: right"
                        >删除
                    </el-button>
                </el-form-item>
            </div>
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
    applicationId: [
        {
            required: true,
            message: "请选择应用名称",
            trigger: "blur",
        },
    ],
    tableNameList: [
        {
            required: true,
            message: "请选择表",
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
    description: [
        {
            required: true,
            message: "请输入接口描述",
            trigger: "change",
        },
    ],
    method: [
        {
            required: true,
            message: "请选择接口类型",
            trigger: "change",
        },
    ],
    path: [
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
const applicationList = ref(null);
const tableList = ref(null);
const tableColumnList = ref([]);

//  api 工厂穿梭数据
const apiFactoryTransferData = ref({
    leftValue: [],
});
const requestBodyParamColumnIdList = ref([]);

const pathParamColumnIdList = ref([]);

const requestParamColumnIdList = ref([]);

const application = ref();

const form = reactive({
    applicationId: "",
    tableNameList: [],
    tag: "",
    description: "",
    method: "",
    path: "",
    apiParamQoList: [],
});
const {
    applicationId,
    tableNameList,
    tag,
    description,
    method,
    path,
    apiParamQoList,
} = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((applicationId.value = rowData.value.applicationId),
    (tag.value = rowData.value.tag),
    (description.value = rowData.value.description),
    (method.value = rowData.value.method),
    (path.value = rowData.value.path),
    (apiParamQoList.value = rowData.value.apiParamQoList),
    (tableNameList.value = rowData.value.tableNameList));

const handleTransferChange = (transferItem) => {
    console.log(transferItem);
};
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    let apiParamQoList = [];
    apiParamQoList = apiParamQoList.concat(pathParamColumnIdList.value);
    apiParamQoList = apiParamQoList.concat(requestBodyParamColumnIdList.value);
    apiParamQoList = apiParamQoList.concat(requestParamColumnIdList.value);
    apiParamQoList = JSON.parse(JSON.stringify(apiParamQoList));
    form.apiParamQoList = apiParamQoList;
    form.apiResultList = apiFactoryTransferData.value.leftValue;
    console.log(apiFactoryTransferData);
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value === "添加") {
                res = await VE_API.system.apiAdd(form);
            } else {
                res = await VE_API.system.apiEdit({
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
onMounted(async () => {
    VE_API.system.applicationList().then((res) => {
        res.data.map((item) => {
            item.label = item.applicationName;
            item.value = item.applicationId;
        });
        applicationList.value = res.data ? res.data : [];

        let application = res.data.find(
            (item) => item.applicationId === applicationId.value,
        );
        if (application) {
            findTable(application.applicationId);
            findTableColumn(form.tableNameList);
        }
    });
    if (apiParamQoList.value !== null && apiParamQoList.value.length > 0) {
        pathParamColumnIdList.value = apiParamQoList.value.filter(
            (item) => item.type === "PATH_PARAM_TYPE",
        );
        requestBodyParamColumnIdList.value = apiParamQoList.value.filter(
            (item) => item.type === "REQUEST_BODY_PARAM_TYPE",
        );
        requestParamColumnIdList.value = apiParamQoList.value.filter(
            (item) => item.type === "REQUEST_PARAM_TYPE",
        );
    }
    //      选择字段列
    await findTableColumn(tableNameList);
});

const changeApplication = (applicationId) => {
    let applicationOne = applicationList.value.find(
        (item) => item.applicationId === applicationId,
    );
    application.value = applicationOne;
    console.log(applicationOne);
    findTable(applicationOne.applicationId);
};

const changeTable = (tableNameList) => {
    if (tableNameList == null || tableNameList.length === 0) {
        return;
    }
    findTableColumn(tableNameList);
};

const findTable = async (applicationId) => {
    if (null == applicationId) return;
    VE_API.system
        .applicationFindTables({ applicationId: applicationId })
        .then((res) => {
            res.data.map((item) => {
                item.label = item.tableName;
                item.value = item.tableName;
            });
            tableList.value = res.data ? res.data : [];
        });
};
const findTableColumn = async (tableNameList) => {
    console.log(tableNameList);
    console.log(application.value);
    if (
        tableNameList === undefined ||
        tableNameList.length === 0 ||
        tableNameList.length === undefined ||
        application.value === undefined
    ) {
        return;
    }
    let tableNames = tableNameList.join();
    VE_API.system
        .databaseTablesColumnList({
            instanceId: application.value.instanceId,
            schemaName: application.value.schemaName,
            tableNameList: tableNames,
        })
        .then((res) => {
            res.data.map((item) => {
                item.label = item.columnName;
                item.value = item.columnName;
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
