<template>
    <div id="myecharts" ref="chart"></div>
</template>
<script>
import mapMenu from "@/views/layoutpages/map/MapMenu";

export default {
    data: () => ({
        description: "Earchats地图",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "LocationInformation",
        name: "Earchats",
        parentMenu: mapMenu,
    }),
};
</script>

<script setup>
import * as echarts from "echarts";
import china from "@/assets/json/china.json";
import { ref, onMounted } from "vue";

const chart = ref();

onMounted(async () => {
    let myChart = echarts.init(chart.value);
    echarts.registerMap("anhui", china);
    let option = {
        geo: {
            type: "map",
            map: "anhui",
            //roam:true,
            //zoom:10 ,//平移
            label: {
                show: true,
            },
        },
    };
    myChart.setOption(option);
});
</script>

<style>
#myecharts {
    width: 600px;
    height: 500px;
}
</style>
