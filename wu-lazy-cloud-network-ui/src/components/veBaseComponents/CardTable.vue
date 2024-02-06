<template>
    <div v-addClass class="ve_table">
        <!-- table工具条 -->
        <el-row v-delete class="ve_header_row_class_name ve_p_10">
            <slot name="tool_bar"></slot>
        </el-row>
        <div class="ve_table_page">
            <!-- 列表 -->
            <div class="ve_table_content" ref="tableBox">
                <el-row
                    :gutter="20"
                    style="margin-right: 15px; margin-left: -5px"
                    type="flex"
                    v-loading="loading"
                >
                    <el-col
                        v-for="(item, index) in $attrs.table"
                        :key="index"
                        :span="6"
                    >
                        <el-card
                            class="box-card"
                            shadow="always"
                            :body-style="{ padding: '0px' }"
                            @row-click="
                                (row, column, event) =>
                                    (ve_rowIndex = rowClick(event))
                            "
                            :row-class-name="
                                ({ rowIndex }) =>
                                    rowClassName(rowIndex, ve_rowIndex)
                            "
                            :cell-class-name="
                                ({ rowIndex }) =>
                                    cellClassName(rowIndex, ve_rowIndex)
                            "
                        >
                            <!--            展示具体数据              -->
                        </el-card>
                    </el-col>
                    <slot></slot>
                    <template #append>
                        <slot name="append"></slot>
                    </template>
                    <template #empty>
                        <el-empty description="暂无数据"></el-empty>
                    </template>
                </el-row>
                <el-table
                    ref="elTable"
                    height="100%"
                    stripe
                    border
                    highlight-current-row
                    header-row-class-name="ve_header_row_class_name"
                    header-cell-class-name="ve_header_cell_class_name"
                    style="width: 100%"
                    v-bind="$attrs.table"
                    @selection-change="selectionChange"
                >
                    <slot></slot>
                    <template #append>
                        <slot name="append"></slot>
                    </template>
                    <template #empty>
                        <el-empty description="暂无数据"></el-empty>
                    </template>
                </el-table>
            </div>
            <!-- 分页 -->
            <el-pagination
                background
                layout="total, sizes, prev, pager, next, jumper"
                :page-sizes="[
                    10, 20, 50, 100, 200, 500, 1000, 2000, 5000, 10000,
                ]"
                :hide-on-single-page="false"
                v-bind="$attrs.pagination"
            >
                <slot name="pagination"></slot>
            </el-pagination>
        </div>
    </div>
</template>

<script>
import { ref } from "vue";

export default {
    name: "CardTable",
    inheritAttrs: false,
    directives: {
        delete: (el) => {
            const length = el.children.length;
            if (length <= 0) {
                el.parentNode && el.parentNode.removeChild(el);
            }
        },
        addClass: (el) => {
            el.parentNode && el.parentNode.classList.add("ve_flex_col");
        },
    },
    setup(props, context) {
        const { emit } = context;
        const elTable = ref(null);
        const ve_rowIndex = ref(null);

        const tableBox = ref(null);
        /**
         * @description:行点击事件
         * @return {*}
         * @param event
         */
        const rowClick = (event) => {
            // return event.currentTarget.rowIndex;
            return event;
        };
        /**
         * @description:高亮当前行
         * @return {*}
         * @param rowIndex
         * @param ve_rowIndex
         */
        const rowClassName = (rowIndex, ve_rowIndex) => {
            if (rowIndex === ve_rowIndex) {
                return "ve_row_class_name";
            } else {
                return "";
            }
        };
        /**
         * @description:高亮单元格
         * @return {*}
         * @param rowIndex
         * @param ve_rowIndex
         */
        const cellClassName = (rowIndex, ve_rowIndex) => {
            if (rowIndex === ve_rowIndex) {
                return "ve_cell_class_name";
            } else {
                return "";
            }
        };
        const selectionChange = (val) => {
            // console.log(val);
            emit("selectionChange", val);
        };

        return {
            elTable,
            tableBox,

            ve_rowIndex,
            cellClassName,
            rowClassName,
            rowClick,
            selectionChange,
        };
    },
};
</script>

<style lang="scss" scoped>
.ve_table {
    flex: 1;
    display: flex;
    flex-direction: column;

    .ve_table_page {
        flex: 1;
        display: flex;
        flex-direction: column;

        .ve_table_content {
            flex: 1;
        }
    }
}
</style>
