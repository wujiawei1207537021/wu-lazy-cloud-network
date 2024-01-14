<template>
    <!-- 表单 -->

    <div>
        <el-button @click="automationEditData.showWhat = 'info'"
            >基础信息</el-button
        >
        <el-button @click="automationEditData.showWhat = 'node'"
            >自动化节点</el-button
        >
    </div>
    <!--自动化信息-->
    <div v-if="automationEditData.showWhat === 'info'">
        <el-form
            :model="automationEditData.automation"
            ref="formRef"
            :rules="rules"
            label-width="80px"
            :inline="false"
        >
            <el-form-item label="自动化名称" prop="name">
                <el-input
                    v-model="automationEditData.automation.name"
                    placeholder="自动化名称"
                    clearable
                ></el-input>
            </el-form-item>

            <el-form-item
                label="处理时间间隔(空不定时处理)"
                prop="timeInterval"
            >
                <el-input
                    v-model="automationEditData.automation.timeInterval"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-switch
                    :loading="automationEditData.automation.status"
                    v-model="status"
                    :active-value="1"
                    :inactive-value="0"
                    active-color="#13ce66"
                    inactive-color="#ff4949"
                >
                </el-switch>
            </el-form-item>
        </el-form>
        <span>
            <el-button @click="closeDialog()">取消</el-button>
            <el-button type="primary" @click="onSubmitAutomation()"
                >确定</el-button
            >
        </span>
    </div>
    <!--node 列表-->
    <div v-if="automationEditData.showWhat === 'node'">
        <div style="height: 300px">
            <el-steps direction="vertical" :active="1">
                <el-step title="开始节点" :icon="Picture" />

                <el-step
                    v-for="node in automationEditData.automation
                        .automationNodeList"
                    :key="node.value"
                    :label="node.label"
                    :value="node.value"
                    :title="node.name"
                    @click="clickNode(node)"
                ></el-step>
                <el-step title="添加" :icon="Edit" @click="addNode">
                    <el-image
                        :src="require('../../../../static/redis.png')"
                        style="height: 100%"
                    ></el-image>
                </el-step>
                <el-step title="结束节点" :icon="Upload" />
            </el-steps>
        </div>
    </div>

    <!-- node 节点详情、动作 -->
    <el-drawer
        v-model="automationEditData.node.drawer"
        title="I am the title"
        :with-header="false"
    >
        <!--node节点详情-->
        <!--node 动作-->
        <div>
            <el-button @click="automationEditData.showNodeOrAction = 'node'"
                >节点信息</el-button
            >
            <el-button @click="automationEditData.showNodeOrAction = 'action'"
                >节点动作</el-button
            >
            <!--    节点信息      -->
            <div v-if="automationEditData.showNodeOrAction === 'node'">
                <el-form
                    :model="automationEditData.currentNode"
                    :rules="rules"
                    label-width="80px"
                    :inline="false"
                >
                    <el-form-item label="节点名称" prop="name">
                        <el-input
                            v-model="automationEditData.currentNode.name"
                            placeholder="节点名称"
                            clearable
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="节点排序" prop="sort">
                        <el-input
                            v-model="automationEditData.currentNode.sort"
                            placeholder="节点排序"
                            clearable
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="节点类型" prop="type">
                        <el-switch
                            v-model="automationEditData.currentNode.type"
                            :active-value="1"
                            :inactive-value="0"
                            active-color="#13ce66"
                            inactive-color="#ff4949"
                        >
                        </el-switch>
                    </el-form-item>
                </el-form>
                <span>
                    <el-button @click="removeAutomationNode()"
                        >删除节点</el-button
                    >
                    <el-button type="primary" @click="onSubmitAutomationNode()"
                        >确定</el-button
                    >
                </span>
            </div>
            <!--     节点动作     -->
            <div v-if="automationEditData.showNodeOrAction === 'action'">
                <el-form
                    :model="automationEditData.currentAction"
                    label-width="80px"
                    :inline="false"
                >
                    <el-form-item
                        label="动作类型"
                        prop="automationNodeActionType"
                    >
                        <el-select
                            v-model="
                                automationEditData.currentAction
                                    .automationNodeActionType
                            "
                            placeholder=""
                            clearable
                        >
                            <el-option label="HTTP" value="HTTP" />
                        </el-select>
                    </el-form-item>

                    <el-form-item label="请求方式" prop="httpMethod">
                        <el-select
                            v-model="
                                automationEditData.currentAction.httpMethod
                            "
                            placeholder=""
                            clearable
                        >
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
                    <el-form-item label="地址" prop="url">
                        <el-input
                            v-model="automationEditData.currentAction.url"
                            placeholder=""
                            clearable
                        ></el-input>
                    </el-form-item>
                    <div>
                        <span>请求参数</span>
                        <el-button
                            @click="
                                addRequestParams(
                                    automationEditData.currentAction,
                                )
                            "
                            style="float: right"
                            type="primary"
                            >增加
                        </el-button>
                        <el-empty
                            v-show="
                                automationEditData.currentAction.paramList
                                    .length === 0
                            "
                        ></el-empty>

                        <el-row
                            v-show="automationEditData.currentAction.paramList"
                            v-for="(item, index) in automationEditData
                                .currentAction.paramList"
                            :key="index"
                        >
                            <span>key</span>
                            <el-input
                                v-model="item.key"
                                placeholder=""
                                clearable
                            ></el-input>
                            <el-input
                                v-model="item.val"
                                placeholder=""
                                clearable
                            ></el-input>
                        </el-row>
                    </div>

                    <el-form-item label="请求体" prop="body">
                        <el-input
                            v-model="automationEditData.currentAction.body"
                            placeholder=""
                            clearable
                        ></el-input>
                    </el-form-item>
                    <div>
                        <span>请求头</span>
                        <el-button
                            @click="addRequestHeader()"
                            style="float: right"
                            type="primary"
                            >增加
                        </el-button>
                        <el-empty
                            v-show="
                                automationEditData.currentAction.headerList
                                    .length === 0
                            "
                        ></el-empty>

                        <el-row
                            v-show="automationEditData.currentAction.headerList"
                            v-for="(item, index) in automationEditData
                                .currentAction.headerList"
                            :key="index"
                        >
                            <span>key</span>
                            <el-input
                                v-model="item.key"
                                placeholder=""
                                clearable
                            ></el-input>
                            <el-input
                                v-model="item.val"
                                placeholder=""
                                clearable
                            ></el-input>
                        </el-row>
                    </div>
                </el-form>
                <el-row>
                    <span>
                        返回结果
                        {{ automationEditData.currentAction.result }}</span
                    >
                </el-row>

                <span>
                    <el-button @click="removeAutomationNodeAction()"
                        >删除节点动作</el-button
                    >
                    <el-button
                        type="primary"
                        @click="onSubmitAutomationNodeAction()"
                        >提交节点动作</el-button
                    >
                    <el-button
                        v-show="automationEditData.currentAction.id"
                        type="primary"
                        @click="executeAutomationNodeAction()"
                        >执行动作</el-button
                    >
                </span>
            </div>
        </div>
    </el-drawer>
</template>

<script setup>
import { toRefs, onMounted, ref, defineProps, defineEmits } from "vue";
import { useRoute } from "vue-router";
import { Edit, Picture, Upload } from "@element-plus/icons-vue";
const rules = {
    name: [
        {
            required: true,
            message: "请输入名称",
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

const formRef = ref(null);
const closeDialog = () => {
    emit("closeDialog", false);
};

const automationEditData = ref({
    showWhat: "info",
    automation: {},
    nodeList: [],
    node: { drawer: false },
    showNodeOrAction: "node",
    currentNode: {},
    currentAction: {
        id: "",
        paramList: [],
        params: {},
        headerList: [],
        headers: {},
        result: "",
    },
});

/**
 * 添加header
 */
const addRequestHeader = () => {
    if (automationEditData.value.currentAction.headerList) {
        automationEditData.value.currentAction.headerList.push({});
    } else {
        automationEditData.value.currentAction.paraheaderListmList = [];
    }
};
/**
 * 添加参数
 */
const addRequestParams = () => {
    console.log(automationEditData.value);
    if (automationEditData.value.currentAction.paramList) {
        automationEditData.value.currentAction.paramList.push({});
    } else {
        automationEditData.value.currentAction.paramList = [];
    }
};
/**
 * 执行节点动作
 */
const executeAutomationNodeAction = async () => {
    const { code, data } =
        await VE_API.system.automationNodeHttpActionExecuteOne({
            actionId: automationEditData.value.currentAction.id,
        });
    if (code === 0) {
        automationEditData.value.currentAction.result = data;
        console.log(data);
    }
};
/**
 * 删除动作节点
 * @returns {Promise<void>}
 */
const removeAutomationNodeAction = async () => {
    const { code } = await VE_API.system.automationNodeHttpActionRemove({
        id: automationEditData.value.currentAction.id,
    });
    if (code === 0) {
        await findAutomationById(automationEditData.value.automation.id);
    }
};
/**
 * 新增或者修改节点动作
 */
const onSubmitAutomationNodeAction = async () => {
    automationEditData.value.currentAction.automationNodeId =
        automationEditData.value.currentNode.id;

    for (let item of automationEditData.value.currentAction.paramList) {
        console.log(item.val);
        automationEditData.value.currentAction.params[item.key] = item.val;
    }
    for (let item of automationEditData.value.currentAction.headerList) {
        automationEditData.value.currentAction.headers[item.key] = item.val;
    }

    const { code } = await VE_API.system.automationNodeHttpActionStory(
        automationEditData.value.currentAction,
    );
    if (code === 0) {
        await findAutomationById(automationEditData.value.automation.id);
    }
};

/**
 * 删除节点
 */
const removeAutomationNode = async () => {
    const { code } = await VE_API.system.automationNodeRemove({
        id: automationEditData.value.currentNode.id,
    });
    if (code === 0) {
        await findAutomationById(automationEditData.value.automation.id);
    }
};
/**
 * 保存自动化节点
 */
const onSubmitAutomationNode = async () => {
    const { code } = await VE_API.system.automationNodeStory(
        automationEditData.value.currentNode,
    );
    if (code === 0) {
        await findAutomationById(automationEditData.value.automation.id);
    }
};
/**
 * 点击node
 * @param node
 */
const clickNode = (node) => {
    console.log(node);
    automationEditData.value.node.drawer = true;
    automationEditData.value.currentNode = node;
    if (node && node.automationNodeHttpAction) {
        automationEditData.value.currentAction = node.automationNodeHttpAction;

        // 请求参数转换
        automationEditData.value.currentAction.paramList = [];

        if (node.automationNodeHttpAction.params) {
            console.log(node.automationNodeHttpAction.params);
            Object.keys(node.automationNodeHttpAction.params).forEach((key) => {
                automationEditData.value.currentAction.paramList.push({
                    key: key,
                    val: node.automationNodeHttpAction.params[key],
                });
            });
        } else {
            automationEditData.value.currentAction.params = {};
        }
        // 请求头转换
        automationEditData.value.currentAction.headerList = [];
        if (node.automationNodeHttpAction.headers) {
            Object.keys(node.automationNodeHttpAction.headers).forEach(
                (key) => {
                    automationEditData.value.currentAction.headerList.push({
                        key: key,
                        val: node.automationNodeHttpAction.headers[key],
                    });
                },
            );
        } else {
            automationEditData.value.currentAction.headers = {};
        }
    } else {
        automationEditData.value.currentAction = {
            automationNodeId: automationEditData.value.currentNode.id,
            paramList: [],
            headerList: [],
            params: {},
            headers: {},
        };
    }
    console.log(automationEditData.value);
};
/**
 * 添加节点
 */
const addNode = async () => {
    let automationNode = {
        automationId: automationEditData.value.automation.id,
        actionType: "HTTP",
        status: "0",
        name: "节点",
    };
    // 添加节点
    const { code } = await VE_API.system.automationNodeStory(automationNode);
    if (code === 0) {
        await findAutomationById(automationEditData.value.automation.id);
    }
};

const findAutomationById = async (id) => {
    const { code, data } = await VE_API.system.automationFindOne({ id: id });
    if (code === 0) {
        automationEditData.value.automation = data;
        console.log(data);
    }
};

onMounted(async () => {
    let route = useRoute();
    let id = route.query.id;
    if (id) {
        console.log(id);
        await findAutomationById(id);
    }
});
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmitAutomation = async () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value === "添加") {
                res = await VE_API.system.automationStory(
                    automationEditData.value.automation,
                );
            } else {
                res = await VE_API.system.automationStory({
                    id: rowData.value.id,
                    ...automationEditData.value.automation,
                });
            }
            const { code } = res;
            if (code === 0) {
                closeDialog();
            }
        }
    });
};
</script>

<style lang="scss" scoped>
.el-row {
    margin-bottom: 20px;
}
.el-row:last-child {
    margin-bottom: 0;
}
.el-col {
    border-radius: 4px;
}

.grid-content {
    border-radius: 4px;
    min-height: 36px;
}
</style>
