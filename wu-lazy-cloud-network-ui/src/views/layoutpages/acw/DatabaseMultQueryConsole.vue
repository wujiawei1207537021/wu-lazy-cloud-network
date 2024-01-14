<template>
    <el-tabs
        v-model="currentConsoleTableKey"
        type="card"
        closable
        @tab-remove="closeCurrentConsoleTable(currentConsoleTableKey)"
    >
        <el-tab-pane
            v-for="item in consoleTableList"
            :key="item.index"
            :label="item.title"
            :name="item.name"
        >
            <database-query-console />
        </el-tab-pane>

        <el-tab-pane
            key="lastConsoleTable"
            label="新增页签"
            name="新增页签name"
            @onclick="addConsoleTable(currentConsoleTableKey)"
        >
            <el-button
                size="small"
                @click="addConsoleTable(currentConsoleTableKey)"
            >
                新增页签
            </el-button>
        </el-tab-pane>
    </el-tabs>
</template>
<script>
import DatabaseQueryConsole from "@/views/layoutpages/acw/components/DatabaseQueryConsole.vue";
import acwMenu from "@/views/layoutpages/acw/AcwMenu";
export default {
    data: () => ({
        description: "数据库多表复杂查询控制台",
        buttons: {
            search: { name: "查询" },
            downLoad: { name: "导出查询结果为Excel" },
            downLoadUpsert: { name: "Upsert下载查询结果" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "IceCream",
        name: "数据库多表复杂查询控制台",
        parentMenu: acwMenu,
    }),
};
</script>

<script setup>
import { ref, onMounted } from "vue";

//  当前页
const currentConsoleTableKey = ref(0);
// 查询sql的所有列
const consoleTableList = ref([
    {
        index: 0,
        name: 0,
        title: "执行查询",
    },
]);

/**
 * 添加页签
 */
const addConsoleTable = (currentIndex) => {
    console.log("当前标签页🏷️" + currentIndex);
    currentConsoleTableKey.value = consoleTableList.value.length + 1;
    consoleTableList.value.push({
        name: currentConsoleTableKey.value,
        title: "执行查询 (" + currentConsoleTableKey.value + ")",
    });
};
/**
 * 关闭当前table
 */
const closeCurrentConsoleTable = (currentIndex) => {
    console.log("删除页签" + currentIndex);
    consoleTableList.value.splice(currentIndex - 1, 1);
};

onMounted(async () => {});
</script>

<style lang="scss" scoped></style>
