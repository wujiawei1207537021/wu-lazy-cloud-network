<template>
    <!--音乐列表-->
    <div class="ve_container">
        <!-- 搜索 -->
        <el-form ref="queryForm" :inline="true" :model="params">
            <el-form-item label="音乐名称" prop="name">
                <el-input
                    clearable
                    v-model="name"
                    placeholder="音乐名称"
                ></el-input>
            </el-form-item>
            <el-form-item>
                <el-button
                    type="primary"
                    @click="onSubmit(params, getDataList)"
                >
                    {{ buttons.search.name }}
                </el-button>
                <el-button @click="resetForm(queryForm, params, getDataList)">
                    重置
                </el-button>
            </el-form-item>
        </el-form>

        <!-- 列表 -->
        <ve-table
            :table="{
                data: tableData,
            }"
            :pagination="{
                onSizeChange: (val) =>
                    handleSizeChange(val, params, getDataList),
                onCurrentChange: (val) =>
                    handleCurrentChange(val, params, getDataList),
                currentPage: current,
                pageSize: size,
                total: total,
            }"
        >
            <template #tool_bar>
                <el-button
                    v-permission="['add']"
                    size="small"
                    type="primary"
                    @click="handleEdit(buttons.add.name)"
                >
                    {{ buttons.add.name }}
                </el-button>
            </template>

            <el-table-column prop="name" label="音乐名称"></el-table-column>
            <el-table-column prop="singer" label="歌手"></el-table-column>
            <el-table-column prop="album" label="专辑"></el-table-column>
            <el-table-column prop="duration" label="时长"></el-table-column>
            <el-table-column prop="musicUrl" label="音乐地址"></el-table-column>
            <el-table-column fixed="right" label="操作">
                <template v-slot:default="{ row }">
                    <el-button
                        v-permission="['edit']"
                        @click.prevent="handleEdit(buttons.edit.name, row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.edit.name }}
                    </el-button>
                    <el-button
                        v-permission="['del']"
                        @click.prevent="handleDel(row.id)"
                        type="danger"
                        size="small"
                    >
                        {{ buttons.del.name }}
                    </el-button>
                    <el-button
                        v-permission="['play']"
                        @click.prevent="handlePlay(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.play.name }}
                    </el-button>
                    <el-button
                        v-permission="['down']"
                        @click.prevent="handleDownMusic(row)"
                        type="primary"
                        size="small"
                    >
                        {{ buttons.down.name }}
                    </el-button>
                </template>
            </el-table-column>
        </ve-table>

        <!-- 编辑组件 -->
        <music-edit
            v-if="showDialog"
            :rowData="rowData"
            :title="dialogTitle"
            :showDialog="showDialog"
            @closeDialog="handelDialog($event)"
        />
    </div>

    <!--音乐播放器-->
    <div
        class="music-container"
        :class="{ 'music-active-switch': offsetThreshold }"
    >
        <div class="music-disk">
            <!--唱片图片-->
            <img
                class="music-disk-picture"
                :class="{ 'music-disk-playing-style': playState }"
                src="../../../static/music-default-pic.png"
                alt=""
            />
        </div>

        <!--进度条-->
        <div class="music-slider">
            <el-slider
                v-model="playTime"
                :format-tooltip="tooltipFormat"
                size="small"
                :max="sliderLength"
            />
        </div>

        <!--按钮组-->
        <div class="button-group">
            <!--上一曲 按钮-->
            <button class="play-button">
                <icon-go-start
                    theme="outline"
                    size="23"
                    fill="#939393"
                    :strokeWidth="3"
                    strokeLinejoin="miter"
                    strokeLinecap="butt"
                />
            </button>
            <!--播放 按钮-->
            <button class="play-button">
                <icon-play-one
                    v-if="!playState"
                    theme="outline"
                    size="23"
                    fill="#939393"
                    :strokeWidth="3"
                    strokeLinejoin="miter"
                    strokeLinecap="butt"
                />
                <icon-pause
                    v-if="playState"
                    theme="outline"
                    size="23"
                    fill="#939393"
                    :strokeWidth="3"
                    strokeLinejoin="miter"
                    strokeLinecap="butt"
                />
            </button>
            <!--下一曲 按钮-->
            <button class="play-button">
                <icon-go-end
                    theme="outline"
                    size="23"
                    fill="#939393"
                    :strokeWidth="3"
                    strokeLinejoin="miter"
                    strokeLinecap="butt"
                />
            </button>
            <!--音量按钮-->
            <div class="voice-container">
                <button class="voice-button">
                    <icon-volume-notice
                        v-if="!voiceMute"
                        theme="outline"
                        size="23"
                        fill="#939393"
                        :strokeWidth="3"
                        strokeLinejoin="miter"
                        strokeLinecap="butt"
                    />
                    <icon-volume-mute
                        v-if="voiceMute"
                        theme="outline"
                        size="23"
                        fill="#939393"
                        :strokeWidth="3"
                        strokeLinejoin="miter"
                        strokeLinecap="butt"
                    />
                </button>
                <div class="voice-slider">
                    <el-slider
                        v-model="voicePower"
                        :max="1"
                        :step="0.1"
                        size="small"
                    />
                </div>
            </div>
        </div>

        <div>{{ musicSource }}</div>
        <audio
            v-if="musicSource"
            ref="musicAudio"
            class="audio-component"
            controls="controls"
            :src="musicSource"
        ></audio>
    </div>
</template>
<script>
import mediaMenu from "@/views/layoutpages/media/MediaMenu";

export default {
    data: () => ({
        description: "音乐播放器",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            play: { name: "播放" }, //topath:true 需要设置跳转路径
            down: { name: "下载" }, //topath:true 需要设置跳转路径
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "VideoPlay",
        name: "音乐管理",
        parentMenu: mediaMenu,
    }),
};
</script>

<script setup>
import MusicEdit from "@/views/layoutpages/media/components/MusicEdit";
import {
    reactive,
    toRefs,
    // eslint-disable-next-line no-unused-vars
    computed,
    ref,
    onMounted,
    onUnmounted,
    getCurrentInstance,
} from "vue";
// import { useRoute, useRouter } from "vue-router";
// import { useStore } from "vuex";
// import { findName } from "../common";

// *导入公共查询方法
import {
    onSubmit,
    resetForm,
    handleSizeChange,
    handleCurrentChange,
} from "@/views/layoutpages/common";

const { proxy } = getCurrentInstance();
// const route = useRoute();
// const router = useRouter();
// const store = useStore();
// const menuList = computed(() => store.getters.menuList).value;
//
const rowData = ref(null);
const dialogTitle = ref("");
const showDialog = ref(false);

// const queryForm = ref(null);
const tableData = ref([]);
const params = reactive({
    name: "",
    size: 10,
    current: 1,
    total: 0,
});
const { name, size, current, total } = toRefs(params);

//是否正在播放
const playState = ref(false);

//现在的播放时间
const playTime = ref(0.0);

//进度条长度
const sliderLength = ref(100);

//歌曲URL
// const musicUrl = ref("");

//播放器标签
const musicAudio = ref(null);

//实现音乐播放的标签
const musicSource = ref(null);

//是否静音
const voiceMute = ref(false);

//音量大小
const voicePower = ref(0.5);

const musicState = reactive({
    musicArr: [],
    musicCount: 0,
});

//是否达到阈值，达到阈值就显示播放器，反之
const offsetThreshold = ref(true);

//歌曲进度条文本提示
const tooltipFormat = (val) => {
    let strTime = playTime.value;

    let strMinute = parseInt(strTime / 60 + "");

    let strSecond = parseInt((strTime % 60) + "");

    return strMinute + ":" + strSecond;
};

//初始化歌曲源【将这里替换成自己的请求逻辑】
const getDataList = async () => {
    const { code, data } = await VE_API.system.playMusicPage(params);
    if (code === 0) {
        const { size, current, total, record } = data;
        params.size = size;
        params.current = current;
        params.total = total;
        tableData.value = record;
        musicState.musicArr = record;
        musicState.musicCount = record.length;
    }
};

onMounted(() => {
    getDataList();
});

onUnmounted(() => {});
/**
 * 音乐 下载
 * **/
const handleDownMusic = async (row) => {
    let res = await VE_API.system.playMusicFindData(
        { id: row.id },
        {
            responseType: "blob",
        },
    );

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
};

/**
 * 播放音乐
 * **/
const handlePlay = async (row) => {
    if (musicSource.value) {
        window.URL.revokeObjectURL(musicSource.value); // 释放内存
    }
    let res = await VE_API.system.playMusicFindData(
        { id: row.id },
        {
            responseType: "blob",
        },
    );
    console.log(res);
    musicSource.value = window.URL.createObjectURL(new Blob([res.data]));
    console.log(row.id);
    console.log(musicSource.value);
};
/**删除行数据
 * @description:
 * @param {*}
 * @return {*}
 */
const handleDel = (id) => {
    proxy
        .$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "error",
        })
        .then(async () => {
            const { code } = await VE_API.system.playMusicDelete({ id });
            if (code === 0) {
                getDataList();
            }
        })
        .catch(() => {
            proxy.$message({
                type: "info",
                message: "已取消删除",
            });
        });
};
/**
 * @description:添加or编辑事件
 * @param {*}
 * @return {*}
 */
const handleEdit = (title, row = null) => {
    showDialog.value = true;
    dialogTitle.value = title;
    rowData.value = row;
};
/**
 * @description: dialog事件
 * @param {*}
 * @return {*}
 */
const handelDialog = (e) => {
    showDialog.value = e;
    getDataList();
};
</script>

<style scoped>
.music-container {
    position: fixed;
    justify-content: center;
    width: 280px;
    height: 110px;
    background-color: white;
    border-radius: 15px;
    bottom: 65px;
    right: 30px;
    opacity: 0;
    transition: 0.5s;
}

.music-disk {
    position: absolute;
    width: 90px;
    height: 90px;
    left: 15px;
    top: 10px;
    border-radius: 50%;
}

.music-disk-picture {
    width: 90px;
    height: 90px;
    border-radius: 50%;
    /*设置图片不可点击*/
    pointer-events: none;
}

.music-disk-playing-style {
    animation: music-disk-rotate 5s linear infinite;
}

@keyframes music-disk-rotate {
    0% {
        transform: rotate(0deg);
    }
    100% {
        transform: rotate(360deg);
    }
}

.button-group {
    position: absolute;
    width: 330px;
    height: 38px;
    left: 90px;
    bottom: 13px;
    margin-left: 10px;
}

.button-group > button {
    margin-left: 10px;
}

.play-button {
    float: left;
    width: 31px;
    height: 31px;
    padding: 4px;
    /*margin: 0px;*/
    border: 0px;
    border-radius: 50%;
    margin: 7px 0px 0px 0px;
}

.voice-button {
    float: left;
    width: 31px;
    height: 31px;
    padding: 0px;
    /*margin: 0px;*/
    border: 0px;
    border-radius: 50%;
    margin: 7px 0px 0px 0px;
    background-color: transparent;
}

.music-slider {
    position: absolute;
    top: 20px;
    left: 120px;
    width: 50%;
}

.voice-container {
    float: left;
    margin-left: 12px;
    width: 31px;
    height: 38px;
    overflow: hidden !important;
    transition: 0.5s;
}

.voice-container:hover {
    width: 160px;
}

.voice-slider {
    position: relative;
    top: 2px;
    right: -30px;
    width: 90px;
    height: 35px;
    background-color: white;
    border-radius: 10px;
    padding: 0px 15px 0px 15px;
    transition: 0.2s;
}

.audio-component {
    width: 300px;
    height: 200px;
    top: 100px;
    display: none;
}

.music-active-switch {
    opacity: 1;
}
</style>
