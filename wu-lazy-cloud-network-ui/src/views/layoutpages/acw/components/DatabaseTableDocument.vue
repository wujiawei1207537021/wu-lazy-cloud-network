<template>
    <el-drawer
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        @close="closeDialog()"
    >
        <el-table ref="filterTable" :data="tableData" style="width: 100%">
            <el-table-column
                prop="date"
                label="日期"
                sortable
                width="180"
                column-key="date"
                :filters="[
                    { text: '2016-05-01', value: '2016-05-01' },
                    { text: '2016-05-02', value: '2016-05-02' },
                    { text: '2016-05-03', value: '2016-05-03' },
                    { text: '2016-05-04', value: '2016-05-04' },
                ]"
                :filter-method="filterHandler"
            >
            </el-table-column>
            <el-table-column prop="name" label="姓名" width="180">
            </el-table-column>
            <el-table-column prop="address" label="地址" :formatter="formatter">
            </el-table-column>
            <el-table-column
                prop="tag"
                label="标签"
                width="100"
                :filters="[
                    { text: '家', value: '家' },
                    { text: '公司', value: '公司' },
                ]"
                :filter-method="filterTag"
                filter-placement="bottom-end"
            >
                <template v-slot="scope">
                    <el-tag
                        :type="scope.row.tag === '家' ? 'primary' : 'success'"
                        disable-transitions
                        >{{ scope.row.tag }}</el-tag
                    >
                </template>
            </el-table-column>
        </el-table>
    </el-drawer>
</template>

<script>
export default {
    data() {
        return {
            tableData: [
                {
                    date: "2016-05-02",
                    name: "王小虎",
                    address: "上海市普陀区金沙江路 1518 弄",
                    tag: "家",
                },
                {
                    date: "2016-05-04",
                    name: "王小虎",
                    address: "上海市普陀区金沙江路 1517 弄",
                    tag: "公司",
                },
                {
                    date: "2016-05-01",
                    name: "王小虎",
                    address: "上海市普陀区金沙江路 1519 弄",
                    tag: "家",
                },
                {
                    date: "2016-05-03",
                    name: "王小虎",
                    address: "上海市普陀区金沙江路 1516 弄",
                    tag: "公司",
                },
            ],
        };
    },
    methods: {
        resetDateFilter() {
            this.$refs.filterTable.clearFilter("date");
        },
        clearFilter() {
            this.$refs.filterTable.clearFilter();
        },
        formatter(row, column) {
            console.log(column);
            return row.address;
        },
        filterTag(value, row) {
            return row.tag === value;
        },
        filterHandler(value, row, column) {
            const property = column["property"];
            return row[property] === value;
        },
    },
};
</script>
<script setup>
import { defineEmits, defineProps, onMounted, toRefs } from "vue";

const props = defineProps({
    showDialog: {
        type: Boolean,
        default: true,
    },
    title: {
        type: String,
        default: "数据库表文档",
    },
    rowData: {
        type: Object,
        default: null,
    },
});
const emit = defineEmits(["closeDialog"]);
const { title } = toRefs(props);
const closeDialog = () => {
    emit("closeDialog", false);
};
onMounted(async () => {});
</script>
