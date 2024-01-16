<template>
    <el-row :gutter="20" style="padding-bottom: 10px">
        <!--        <el-col :span="12">-->
        <!--            <div class="ve-card ve_card1">-->
        <!--                <el-icon>-->
        <!--                    <football />-->
        <!--                </el-icon>-->
        <!--                <div>-->
        <!--                    <p>菜单数量</p>-->
        <!--                    <span>{{ shortcutsData.menuNum }}</span>-->
        <!--                </div>-->
        <!--            </div>-->
        <!--        </el-col>-->
        <el-col :span="12">
            <div class="ve-card ve_card2">
                <el-icon>
                    <user />
                </el-icon>
                <div>
                    <p>用户数量</p>
                    <span>{{ shortcutsData.userNum }}</span>
                </div>
            </div>
        </el-col>
        <el-col :span="12">
            <div class="ve-card ve_card1">
                <el-icon>
                    <grape />
                </el-icon>
                <div>
                    <p>数据库实例数量</p>
                    <span>{{ shortcutsData.instanceNum }}</span>
                </div>
            </div>
        </el-col>
        <el-col style="padding: 20px">
            <!--    内存      -->

            <el-card
                shadow="always"
                class="box-card"
                style="
                    width: 100%;
                    /*flex-wrap: wrap;*/
                    /*float: left;*/
                    /*position: fixed;*/
                    /*display: flex;*/
                    /*flex-wrap: wrap;*/
                "
            >
                <el-progress
                    type="circle"
                    :width="140"
                    :stroke-width="15"
                    :color="colors"
                    :percentage="
                        Number(
                            shortcutsData.useMemory / shortcutsData.totalMemory,
                        ) * 100
                    "
                >
                    <span class="percentage-value"
                        >{{
                            shortcutsData.useMemory +
                            "/" +
                            shortcutsData.totalMemory
                        }}MB</span
                    >
                    <span class="percentage-label">JVM内存使用</span>
                    <span class="percentage-label">
                        <el-button @click="gc()">GC</el-button>
                    </span>
                </el-progress>
                <!--    cpu      -->
                <el-progress
                    type="circle"
                    :width="140"
                    :stroke-width="15"
                    :color="colors"
                    :percentage="cpuData.used"
                >
                    <span class="percentage-value">{{ cpuData.used }}%</span>
                    <span class="percentage-label">当前线程CPU使用</span>
                    <span class="percentage-label">
                        <el-button @click="getCpu()">cpu</el-button>
                    </span>
                </el-progress>
                <el-progress
                    type="circle"
                    :width="140"
                    :stroke-width="15"
                    :color="colors"
                    :percentage="cpuData.sys"
                >
                    <span class="percentage-value">{{ cpuData.sys }}%</span>
                    <span class="percentage-label">当前系统CPU使用</span>
                    <span class="percentage-label">
                        <el-button @click="getCpu()">cpu</el-button>
                    </span>
                </el-progress>
                <el-progress>
                    <span class="percentage-value"
                        >CUP名称: {{ cpuData.name }}</span
                    >
                    <span class="percentage-value"
                        >CUP架构: {{ cpuData.arch }}</span
                    >
                    <span class="percentage-value"
                        >CUP版本: {{ cpuData.version }}</span
                    >
                    <span class="percentage-value"
                        >CUP核心数: {{ cpuData.cpuNum }}核</span
                    >
                </el-progress>
            </el-card>
        </el-col>

        <!--        <el-col :span="12">-->
        <!--            <div class="ve-card ve_card3">-->
        <!--                <el-icon>-->
        <!--                    <ice-cream />-->
        <!--                </el-icon>-->
        <!--                <div>-->
        <!--                    <p>角色数量</p>-->
        <!--                    <span>{{ shortcutsData.roleNum }}</span>-->
        <!--                </div>-->
        <!--            </div>-->
        <!--        </el-col>-->
        <!--        <el-col :span="12">-->
        <!--            <div class="ve-card ve_card4">-->
        <!--                <el-icon>-->
        <!--                    <food />-->
        <!--                </el-icon>-->
        <!--                <div>-->
        <!--                    <p>资料数量</p>-->
        <!--                    <span>{{ shortcutsData.menuNum }}</span>-->
        <!--                </div>-->
        <!--            </div>-->
        <!--        </el-col>-->
    </el-row>
    <!--    <el-row :gutter="20" style="padding-top: 10px">-->
    <!--        <el-col :span="12">-->
    <!--            <div class="ve-card ve_card1">-->
    <!--                <el-icon>-->
    <!--                    <grape />-->
    <!--                </el-icon>-->
    <!--                <div>-->
    <!--                    <p>数据库实例数量</p>-->
    <!--                    <span>{{ shortcutsData.instanceNum }}</span>-->
    <!--                </div>-->
    <!--            </div>-->
    <!--        </el-col>-->
    <!--        <el-col :span="12">-->
    <!--            <div class="ve-card ve_card2">-->
    <!--                <el-icon>-->
    <!--                    <histogram />-->
    <!--                </el-icon>-->
    <!--                <div>-->
    <!--                    <p>数据库数量</p>-->
    <!--                    <span>{{ shortcutsData.schemaNum }}</span>-->
    <!--                </div>-->
    <!--            </div>-->
    <!--        </el-col>-->
    <!--        <el-col :span="12">-->
    <!--            <div class="ve-card ve_card3">-->
    <!--                <el-icon>-->
    <!--                    <management />-->
    <!--                </el-icon>-->
    <!--                <div>-->
    <!--                    <p>表数量</p>-->
    <!--                    <span>{{ shortcutsData.tableNum }}</span>-->
    <!--                </div>-->
    <!--            </div>-->
    <!--        </el-col>-->
    <!--        <el-col :span="12">-->
    <!--            <div class="ve-card ve_card4">-->
    <!--                <el-icon>-->
    <!--                    <position />-->
    <!--                </el-icon>-->
    <!--                <div>-->
    <!--                    <p>表列数量</p>-->
    <!--                    <span>{{ shortcutsData.tableColumnNum }}</span>-->
    <!--                </div>-->
    <!--            </div>-->
    <!--        </el-col>-->
    <!--    </el-row>-->
</template>

<script setup>
import { onMounted, onUnmounted, ref } from "vue";

const shortcutsData = ref({
    menuNum: 0,
    roleNum: 0,
    userNum: 0,
    instanceNum: 0,
    schemaNum: 0,
    tableNum: 0,
    tableColumnNum: 0,
    maxMemory: 0,
    totalMemory: 1,
    freeMemory: 0,
    useMemory: 0,
});
const cpuData = ref({
    cpuNum: "0",
    total: "0",
    sys: "0.00",
    used: "0.00",
    wait: "0.00",
    free: "0.00",
    name: "",
    arch: "",
    version: "",
});

const colors = [
    { color: "#f56c6c", percentage: 100 },
    { color: "#e6a23c", percentage: 80 },
    { color: "#5cb87a", percentage: 60 },
    { color: "#1989fa", percentage: 40 },
    { color: "#6f7ad3", percentage: 20 },
];

const timer = ref();

/**
 * 获取jvm信息
 * @returns {Promise<void>}
 */
const getJvmInfo = async () => {
    const { code, data } = await VE_API.system.jvmRunTimeMemory();
    if (code === 0) {
        console.log(data);
        shortcutsData.value.maxMemory = data.maxMemory;
        shortcutsData.value.totalMemory = data.totalMemory;
        shortcutsData.value.freeMemory = data.freeMemory;
        shortcutsData.value.useMemory = data.useMemory;
    }
};
/**
 * gc
 * @returns {Promise<void>}
 */
const gc = async () => {
    const { code, data } = await VE_API.system.jvmGC();
    if (code === 0) {
        console.log(data);
        await getJvmInfo();
    }
};

/**
 * gc
 * @returns {Promise<void>}
 */
const getCpu = async () => {
    const { code, data } = await VE_API.system.jvmCPU();
    if (code === 0) {
        cpuData.value = data;
        console.log(data);
    }
};
onMounted(async () => {
    // const { code, data } = await VE_API.system.shortcutsData();
    // if (code === 0) {
    //     console.log(data);
    //     shortcutsData.value = data;
    // }
    await getJvmInfo();
    await getCpu();
    timer.value = setTimeout(() => {
        //需要定时执行的代码
        getJvmInfo();
        getCpu();
    }, 3000);
});
onUnmounted(() => {
    clearTimeout(timer.value);
    timer.value = "";
});
</script>

<style lang="scss" scoped></style>

<style lang="scss" scoped>
.el-row {
    height: 50%;

    padding-right: 10px;
}

.ve-card {
    border-radius: 10px;
    height: 100%;
    display: flex;
    align-items: center;
    transition: all 500ms;
    color: #fff;

    &:hover {
        box-shadow: 3px 3px 6px 1px rgba(0, 0, 0, 0.2);
        background: #fff;
    }

    i {
        font-size: 100px;
        margin: 0 20px;
    }

    div {
        flex: 1;
        padding-right: 12px;

        p {
            margin: 0;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        span {
            font-size: 60px;
            font-weight: bold;
        }
    }
}

.ve_card1 {
    background: #409eff;

    &:hover {
        color: #409eff;
    }
}

.ve_card2 {
    background: #67c23a;

    &:hover {
        color: #67c23a;
    }
}

.ve_card3 {
    background: #e6a23c;

    &:hover {
        color: #e6a23c;
    }
}

.ve_card4 {
    background: #f56c6c;

    &:hover {
        color: #f56c6c;
    }
}

.percentage-value {
    display: block;
    margin-top: 10px;
    font-size: 14px;
}

.percentage-label {
    display: block;
    margin-top: 10px;
    font-size: 12px;
}

.el-progress--line {
    margin-bottom: 15px;
    width: 350px;
}

.el-progress--circle {
    margin-right: 15px;
}
</style>
