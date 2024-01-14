<template>
    <div>
        <el-row>
            <el-col span="6">
                <el-tree
                    :props="resourceProps"
                    :load="loadNode"
                    :data="resourceData"
                    lazy
                    show-checkbox
                >
                    <template #default="{ data }">
                        <span class="custom-tree-node">
                            <img
                                v-if="data.isFile"
                                src="../../../../src/static/file.png"
                                style="width: 16px; height: 16px"
                            />
                            <img
                                v-if="!data.isFile"
                                src="../../../../src/static/folder.png"
                                style="width: 16px; height: 16px"
                            />
                            <span>
                                {{ data.name }}
                            </span>
                        </span>
                    </template>
                </el-tree>
            </el-col>
            <el-col span="18"> 展示的内容 </el-col>
        </el-row>
    </div>
</template>
<script>
import mediaMenu from "@/views/layoutpages/media/MediaMenu";

export default {
    data: () => ({
        description: "资源管理",
        buttons: {
            search: { name: "查询" },
            add: { name: "添加" },
            edit: { name: "编辑" },
            del: { name: "删除" },
            preview: { name: "预览", toPath: true }, //topath:true 需要设置跳转路径
            down: { name: "下载" }, //topath:true 需要设置跳转路径
        },
        // type 0:目录 1：菜单 2：按钮
        type: "1",
        icon: "UploadFilled",
        name: "资源管理",
        parentMenu: mediaMenu,
    }),
};
</script>

<script setup>
import { reactive, ref, onMounted } from "vue";

const resourceData = ref([]);
const params = reactive({
    path: "/",
});

const resourceProps = {
    label: "name",
    children: "children",
    isLeaf: "leaf",
};

const loadNode = async (resourceNode, resolve) => {
    if (resourceNode.level === 0) {
        return resolve([]);
    }

    resourceNode.loaded = true;
    var resourceNodeData = resourceNode.data;
    if (resourceNodeData.isFile) {
        return resolve([]);
    }
    console.log(resourceNodeData);
    const { code, data } = await VE_API.system.playFileResourceList({
        path:
            "/" === resourceNodeData.rootPath
                ? "/" + resourceNodeData.name
                : resourceNodeData.rootPath + "/" + resourceNodeData.name,
    });
    if (code === 0) {
        if (data == null) {
            return resolve([]);
        }
        // resourceNode.childNodes = data;
        console.log(resourceNode);
        return resolve(data);
    }
};

/**
 * @description: 获取列表数据
 * @param {*}
 * @return {*}
 */
const getDataList = async () => {
    const { code, data } = await VE_API.system.playFileResourceList(params);
    if (code === 0) {
        resourceData.value = data;
    }
};
onMounted(async () => {
    await getDataList();
});
</script>

<style lang="scss" scoped></style>
