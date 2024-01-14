<template>
    <el-dialog
        :title="title"
        append-to-body
        destroy-on-close
        :model-value="showDialog"
        @close="closeDialog()"
    >
        <!-- 表单 -->
        <el-form
            :model="form"
            ref="formRef"
            :rules="rules"
            label-width="80px"
            :inline="false"
        >
            <el-form-item label="汉字" prop="word">
                <el-input
                    v-model="word"
                    placeholder="汉字"
                    clearable
                ></el-input>
            </el-form-item>

            <el-form-item label="拼音" prop="pinYin">
                <el-input v-model="pinYin" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="笔划" prop="strokes">
                <el-input v-model="strokes" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item label="部首" prop="radicals">
                <el-input
                    v-model="radicals"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="繁体字" prop="oldWord">
                <el-input v-model="oldWord" placeholder="" clearable></el-input>
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
    onMounted,
    ref,
    defineProps,
    defineEmits,
} from "vue";

const rules = {
    word: [
        {
            required: true,
            message: "请输入汉字",
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
const closeDialog = () => {
    emit("closeDialog", false);
};
const formRef = ref(null);
const form = reactive({
    word: "",
    pinYin: "",
    strokes: "",
    radicals: "",
    oldWord: "",
});
const { word, pinYin, strokes, radicals, oldWord } = toRefs(form);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((word.value = rowData.value.word),
    (pinYin.value = rowData.value.pinYin),
    (strokes.value = rowData.value.strokes),
    (radicals.value = rowData.value.radicals),
    (oldWord.value = rowData.value.oldWord));

onMounted(async () => {
    // console.log("123");
});

/**
 * @description:提交
 * @param {*}
 * @return {*}
 */
const onSubmit = () => {
    formRef.value.validate(async (valid) => {
        if (valid) {
            let res;
            if (title.value === "添加") {
                res = await VE_API.system.ttsChineseCharactersStory(form);
            } else {
                res = await VE_API.system.ttsChineseCharactersUpdateOne({
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
</script>

<style lang="scss" scoped></style>
