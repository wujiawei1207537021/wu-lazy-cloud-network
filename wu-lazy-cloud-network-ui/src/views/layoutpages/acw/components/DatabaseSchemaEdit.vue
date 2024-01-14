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
            <el-form-item label="数据库实例" prop="instanceId">
                <el-select v-model="instanceId" placeholder="数据库实例">
                    <el-option
                        v-for="item in serverList"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="数据库库名" prop="schemaName">
                <el-input
                    v-model="schemaName"
                    placeholder="数据库库名"
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="字符集" prop="characterSet">
                <el-select
                    v-model="characterSet"
                    placeholder="字符集"
                    filterable
                >
                    <el-option label="armscii8" value="armscii8" />
                    <el-option label="ascii" value="ascii" />
                    <el-option label="big5" value="big5" />
                    <el-option label="binary" value="binary" />
                    <el-option label="cp850" value="cp850" />
                    <el-option label="cp852" value="cp852" />
                    <el-option label="cp866" value="cp866" />
                    <el-option label="cp932" value="cp932" />
                    <el-option label="cp1250" value="cp1250" />
                    <el-option label="cp1251" value="cp1251" />
                    <el-option label="cp1256" value="cp1256" />
                    <el-option label="cp1257" value="cp1257" />
                    <el-option label="dec8" value="dec8" />
                    <el-option label="eucjpms" value="eucjpms" />
                    <el-option label="euckr" value="euckr" />
                    <el-option label="gb2312" value="gb2312" />
                    <el-option label="gb18030" value="gb18030" />
                    <el-option label="gbk" value="gbk" />
                    <el-option label="geostd8" value="geostd8" />
                    <el-option label="greek" value="greek" />
                    <el-option label="hebrew" value="hebrew" />
                    <el-option label="hp8" value="hp8" />
                    <el-option label="keybcs2" value="keybcs2" />
                    <el-option label="koi8r" value="koi8r" />
                    <el-option label="koi8u" value="koi8u" />
                    <el-option label="latin1" value="latin1" />
                    <el-option label="latin2" value="latin2" />
                    <el-option label="latin5" value="latin5" />
                    <el-option label="latin7" value="latin7" />
                    <el-option label="macce" value="macce" />
                    <el-option label="macroman" value="macroman" />
                    <el-option label="sjis" value="sjis" />
                    <el-option label="swe7" value="swe7" />
                    <el-option label="tis620" value="tis620" />
                    <el-option label="ucs2" value="ucs2" />
                    <el-option label="ujis" value="ujis" />
                    <el-option label="utf8" value="utf8" />
                    <el-option label="utf8mb4" value="utf8mb4" />
                    <el-option label="utf16" value="utf16" />
                    <el-option label="utf16le" value="utf16le" />
                    <el-option label="utf32" value="utf32" />
                </el-select>
            </el-form-item>
            <el-form-item label="排序规则" prop="sortingRules">
                <el-input
                    v-model="sortingRules"
                    placeholder=""
                    clearable
                ></el-input>
            </el-form-item>
            <el-form-item label="ext" prop="ext">
                <el-input v-model="ext" placeholder="" clearable></el-input>
            </el-form-item>
            <el-form-item
                label="是否初始化数据库到本地"
                prop="initializeToLocal"
            >
                <el-select
                    v-model="initializeToLocal"
                    placeholder="是否初始化数据库到本地"
                    clearable
                >
                    <el-option label="初始化数据库到本地" :value="true" />
                    <el-option label="不初始化数据库到本地" :value="false" />
                </el-select>
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
    schemaName: [
        {
            required: true,
            message: "请输入数据库库名",
            trigger: "blur",
        },
    ],
    instanceId: [
        {
            required: true,
            message: "请输入数据库服务器",
            trigger: "blur",
        },
    ],
    characterSet: [
        {
            required: true,
            message: "请输入字符集",
            trigger: "blur",
        },
    ],
    sortingRules: [
        {
            required: false,
            message: "请输入排序规则",
            trigger: "blur",
        },
    ],
    ext: [
        {
            required: false,
            message: "ext",
            trigger: "change",
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
    schemaName: "",
    instanceId: "",
    ext: "",
    characterSet: "",
    sortingRules: "",
    initializeToLocal: "",
});
const {
    schemaName,
    instanceId,
    ext,
    characterSet,
    sortingRules,
    initializeToLocal,
} = toRefs(form);
const serverList = ref(null);

/**
 * @description: 初始化
 * @param {*}
 * @return {*}
 */
rowData.value &&
    ((schemaName.value = rowData.value.schemaName),
    (instanceId.value = rowData.value.instanceId),
    (characterSet.value = rowData.value.characterSet),
    (sortingRules.value = rowData.value.sortingRules),
    (ext.value = rowData.value.ext),
    (initializeToLocal.value = rowData.value.initializeToLocal));

onMounted(async () => {
    // 查询数据库实例
    VE_API.system.databaseInstanceList().then((res) => {
        res.data.map((item) => {
            item.label = item.instanceName;
            item.value = item.id;
        });
        serverList.value = res.data ? res.data : [];
    });
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
            if (title.value == "添加") {
                res = await VE_API.system.schemaAdd(form);
            } else {
                res = await VE_API.system.schemaEdit({
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
