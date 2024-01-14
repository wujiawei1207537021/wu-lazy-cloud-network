<template>
    <el-dialog
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
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
            <el-form-item label="音乐名称" prop="name">
                <el-input v-model="name" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="歌手" prop="singer">
                <el-input v-model="singer" placeholder="" clearable></el-input>
            </el-form-item>

            <el-form-item label="专辑" prop="album">
                <el-input v-model="album" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="时长" prop="duration">
                <el-input
                    v-model="duration"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="音乐地址" prop="musicUrl">
                <el-input
                    v-model="musicUrl"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="文件数据" prop="musicData">
                <el-upload
                    name="musicData"
                    :multiple="false"
                    :show-file-list="true"
                    :on-change="onChange"
                    limit="1"
                    v-model="musicData"
                    placeholder=""
                    clearable
                    :auto-upload="false"
                >
                    <el-button size="medium" type="primary">上传文件</el-button>
                </el-upload>
            </el-form-item>
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
    name: [
        {
            required: true,
            message: "请输入音乐名称",
            trigger: "blur",
        },
    ],
    singer: [
        {
            required: true,
            message: "请输入歌手",
            trigger: "blur",
        },
    ],
    album: [
        {
            required: true,
            message: "请输入专辑",
            trigger: "blur",
        },
    ],
    duration: [
        {
            required: true,
            message: "请输入时长",
            trigger: "change",
        },
    ],
    // musicUrl: [
    //     {
    //         required: true,
    //         message: "请输入音乐地址",
    //         trigger: "change",
    //     },
    // ],
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
const form = reactive({
    name: "",
    singer: "",
    album: "",
    duration: "",
    musicData: "",
    musicUrl: "",
});
const { name, singer, album, duration, musicData, musicUrl } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((name.value = rowData.value.name),
    (singer.value = rowData.value.singer),
    (album.value = rowData.value.album),
    (duration.value = rowData.value.duration),
    (musicData.value = rowData.value.musicData),
    (musicUrl.value = rowData.value.musicUrl));

onMounted(async () => {
    // 查询数据库实例
});

/**
 * 计算文件大小
 * @param fileSize
 * @returns {string}
 */
const formatFileSize = (fileSize) => {
    if (fileSize < 1024) {
        return fileSize + "B";
    } else if (fileSize < 1024 * 1024) {
        let temp = fileSize / 1024;
        temp = temp.toFixed(2);
        return temp + "KB";
    } else if (fileSize < 1024 * 1024 * 1024) {
        let temp = fileSize / (1024 * 1024);
        temp = temp.toFixed(2);
        return temp + "MB";
    } else {
        let temp = fileSize / (1024 * 1024 * 1024);
        temp = temp.toFixed(2);
        return temp + "GB";
    }
};
/**
 * 文件更改
 * */
const onChange = (file) => {
    var name = file.name;
    var size = file.size;
    form.name = name;
    form.uid = file.uid;
    if (size != null) {
        form.length = formatFileSize(size);
    }

    form.musicData = file.raw;
    if (name !== null) {
        var split = name.split(".");
        form.type = split[split.length - 1];
    }
};
/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let fd = new FormData();
            fd.append("musicData", form.musicData); //传文件
            fd.append("name", form.name);
            fd.append("singer", form.singer);
            fd.append("album", form.album);
            fd.append("duration", form.duration);
            fd.append("musicUrl", form.musicUrl);
            let res;
            if (title.value === "添加") {
                res = await VE_API.system.playMusicInsertOrUpdate(fd);
            } else {
                res = await VE_API.system.playMusicInsertOrUpdate({
                    id: rowData.value.id,
                    ...fd,
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
</script>

<style lang="scss" scoped></style>
