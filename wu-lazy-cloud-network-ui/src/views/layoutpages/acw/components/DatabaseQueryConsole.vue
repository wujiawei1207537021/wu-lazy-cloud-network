<template>
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form
            ref="queryForm"
            :inline="false"
            :model="params"
            :rules="rules"
            label-width="100px"
        >
            <el-form-item>
                <el-form-item label="数据库实例" prop="instanceId">
                    <el-select
                        v-model="params.instanceId"
                        @change="getSchemaList"
                        placeholder="数据库实例"
                        filterable
                    >
                        <el-option
                            v-for="item in serverList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        />
                    </el-select>
                </el-form-item>
                <el-form-item label="数据库名" prop="schemaName">
                    <el-select
                        clearable
                        filterable
                        v-model="params.schemaName"
                        @change="
                            getTableList(params.instanceId, params.schemaName)
                        "
                        placeholder="数据库名"
                    >
                        <el-option
                            v-for="item in schemaList"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
            </el-form-item>

            <el-row :gutter="24">
                <el-col :span="4">
                    <el-card>
                        <el-tree
                            :props="{
                                children: 'children',
                                label: 'tableName',
                            }"
                            :data="schemaTableList"
                        ></el-tree>
                    </el-card>
                </el-col>
                <el-col :span="20">
                    <el-card>
                        <div class="grid-content ep-bg-purple-light">
                            <el-form-item label="执行的sql" prop="sql">
                                <el-input
                                    type="textarea"
                                    :rows="20"
                                    v-model="sql"
                                ></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="sqlConsole()">
                                    {{ buttons.search.name }}
                                </el-button>
                            </el-form-item>
                            <el-form-item>
                                <el-button @click="downLoad()" type="text">
                                    {{ buttons.downLoad.name }}
                                </el-button>
                                <el-button
                                    @click="downLoadUpsert()"
                                    type="text"
                                >
                                    {{ buttons.downLoadUpsert.name }}
                                </el-button>
                            </el-form-item>

                            <template
                                v-for="(tableData, index) in tableDataList"
                                :key="index"
                            >
                                <el-check-tag
                                    :checked="tableData.checked"
                                    @change="changeResultDataTag(index)"
                                    >第{{ index + 1 }}个结果集合</el-check-tag
                                >
                            </template>
                            <div
                                v-for="(tableData, index) in tableDataList"
                                :key="index"
                                v-show="tableData.checked"
                            >
                                <!-- 列表 -->
                                <ve-table
                                    :table="{
                                        data: tableData,
                                    }"
                                    :pagination="{
                                        onSizeChange: (val) =>
                                            handleSizeChange(
                                                val,
                                                params,
                                                getDataList,
                                            ),
                                        onCurrentChange: (val) =>
                                            handleCurrentChange(
                                                val,
                                                params,
                                                getDataList,
                                            ),
                                        currentPage: current,
                                        pageSize: size,
                                        total: total,
                                    }"
                                >
                                    <el-table-column
                                        :prop="item"
                                        :label="item"
                                        v-for="(
                                            item, index
                                        ) in tableData.tableHeader"
                                        :key="index"
                                        sortable
                                    >
                                        <template v-slot="scope">
                                            <el-input
                                                v-model="scope.row[item]"
                                                placeholder="数据"
                                            ></el-input>
                                        </template>
                                    </el-table-column>
                                </ve-table>
                            </div>
                        </div>
                    </el-card>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>
<script>
export default {
    data: () => ({
        description: "数据库查询控制台",
        buttons: {
            search: { name: "查询" },
            downLoad: { name: "导出查询结果为Excel" },
            downLoadUpsert: { name: "Upsert下载查询结果" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "IceCream",
        name: "数据库查询管理",
    }),
};
</script>

<script setup>
import { reactive, toRefs, ref, onMounted } from "vue";

const rules = {
    instanceId: [
        {
            required: true,
            message: "请选择数据库实例",
            trigger: "blur",
        },
    ],
    schemaName: [
        {
            required: true,
            message: "请选择数据库",
            trigger: "blur",
        },
    ],
    sql: [
        {
            required: true,
            message: "执行SQL不能为空",
            trigger: "blur",
        },
    ],
};
//?导入公共查询方法
import {
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";
const queryForm = ref(null);
const tableDataList = ref([]);
const schemaList = ref(null);
const schemaTableList = ref(null);
const serverList = ref(null);
// 当前选择的表
// const currentTableName = ref(null);
const params = reactive({
    schemaName: "",
    instanceId: "",
    instanceName: "",
    sql: "show tables",
    size: 10,
    current: 1,
    total: 0,
});
const { size, current, total, sql } = toRefs(params);

/**
 *  点击结果集tag 事件
 */
const changeResultDataTag = async (index) => {
    tableDataList.value.forEach((item, itemIndex) => {
        if (index == itemIndex) {
            item.checked = true;
        } else {
            item.checked = false;
        }
    });
    console.log(index);
};
/**
 * 文件下载
 */
const downLoad = async () => {
    queryForm.value.validate(async (valid) => {
        if (valid) {
            let res = await VE_API.system.sqlConsoleExport(params, {
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
            return false;
        }
    });
};

/**
 * 文件下载
 */
const downLoadUpsert = async () => {
    queryForm.value.validate(async (valid) => {
        if (valid) {
            let res = await VE_API.system.sqlConsoleUpsertExport(params, {
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
            return false;
        }
    });
};
/**
 * 执行sql
 */
const sqlConsole = async () => {
    queryForm.value.validate(async (valid) => {
        if (valid) {
            let res = await VE_API.system.sqlConsole(params);
            const { code } = res;
            if (code === 0) {
                tableDataList.value = res.data;
                if (res.data.length !== 0) {
                    tableDataList.value.map((item) => {
                        item.tableHeader = Object.keys(item[0]);
                        item.checked = false;
                    });
                    tableDataList.value[0].checked = true;
                    // tableHeader.value = Object.keys(res.data[0]);
                    // tableHeaderList.value = Object.keys(res.data[0]);
                }
                console.log(res);
            }
        } else {
            return false;
        }
    });
};

/**
 * 获取 数据库表
 * @param instanceId 数据库实例ID
 * @param schemaName 数据库名称
 * @returns {Promise<void>}
 */
const getTableList = async (instanceId, schemaName) => {
    let res;
    if (instanceId == null) {
        return;
    } else {
        res = await VE_API.system.tableList({
            instanceId: instanceId,
            schemaName: schemaName,
        });
    }
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaTableList.value = data;
        return schemaList.value;
    }
};
/**
 * 获取 数据库
 * @param instanceId 数据库实例ID
 * @returns {Promise<void>}
 */
const getSchemaList = async (instanceId) => {
    let res;
    if (instanceId == null) {
        return;
    } else {
        res = await VE_API.system.schemaList({
            instanceId,
        });
    }
    const { code, data } = res;
    if (code === 0) {
        data.map((item) => {
            item.label = item.schemaName;
            item.value = item.schemaName;
        });
        schemaList.value = data;
        if (schemaList.value) {
            // 默认第一个实例的第一个数据库
            params.schemaName = schemaList.value[0].schemaName;
            await getTableList(instanceId, params.schemaName);
        }
        return schemaList.value;
    }
};
// 查询数据库实例
const getServerInstanceList = async () => {
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
        // 默认第一个实例
        if (serverList.value && serverList.value.length > 0) {
            params.instanceId = serverList.value[0].id;
            getSchemaList(params.instanceId);
        }
        return serverList.value;
    });
};

onMounted(async () => {
    await getServerInstanceList();
});
</script>

<style lang="scss" scoped>
.el-tabs__content.el-tab-pane {
    display: block;
    background: #409eff;
}
</style>
