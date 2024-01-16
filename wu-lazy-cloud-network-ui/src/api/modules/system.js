// const Mock = require("mockjs"); //引入
/**
 * @description: 列表查询
 * @param {*}
 * @return {*}
 */
const searchList = (sRole, sLimit, sPage, sList, sName) => {
    let list = sList;
    if (sRole !== null && sRole !== "" && sRole !== undefined) {
        list = sList.filter((item) => item[sName] + "" === sRole + "");
    }
    const res = {};
    res.total = list.length;
    res.page = sPage * 1;
    res.limit = sLimit * 1;
    res.list = list.slice((sPage - 1) * sLimit, (sPage - 1) * sLimit + sLimit);
    return {
        message: "查询成功!",
        code: "00",
        data: res,
    };
};
/**
 * @description: 列表添加
 * @param {*}
 * @return {*}
 */
const addList = (opt, list, name, sName) => {
    const flag = list.some((item) => {
        if (item.type !== "undefined") {
            if (item.type === 2) {
                return false;
            }
        }
        return item[sName] == name;
    });
    let res = {
        message: "添加成功!",
        code: "00",
    };
    if (flag) {
        res = {
            message: "名称已存在",
            code: "01",
        };
    } else {
        let item = {
            id: list[list.length - 1].id + 1,
            ...opt.body,
        };
        list.push(item);
    }
    return res;
};
const menuList = [
    {
        parentId: -1,
        id: 100,
        name: "系统设置", //看官网，这个名字是3-5之间的
        url: "",
        menu: "",
        type: 0,
        icon: "Setting",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 100,
        id: 1,
        name: "用户管理", //看官网，这个名字是3-5之间的
        url: "system/Users", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "",
        type: 1,
        icon: "UserFilled",
        sort: 2,
        iframe: 0,
    },
    {
        parentId: 1,
        id: 2,
        name: "查询", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "search",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 1,
        id: 3,
        name: "添加", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "add",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 1,
        id: 4,
        name: "编辑", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "edit",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 100,
        id: 5,
        name: "菜单管理", //看官网，这个名字是3-5之间的
        url: "system/Menus", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "",
        type: 1,
        icon: "Menu",
        sort: 1,
        iframe: 0,
    },
    {
        parentId: 5,
        id: 6,
        name: "查询", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "search",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 5,
        id: 7,
        name: "添加", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "add",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 5,
        id: 8,
        name: "编辑", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "edit",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 5,
        id: 9,
        name: "添加子级", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "addChild",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 5,
        id: 10,
        name: "添加按钮", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "addBtn",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 100,
        id: 11,
        name: "角色管理", //看官网，这个名字是3-5之间的
        url: "system/Roles", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "",
        type: 1,
        icon: "HelpFilled",
        sort: 3,
        iframe: 0,
    },
    {
        parentId: 11,
        id: 12,
        name: "查询", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "search",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 11,
        id: 13,
        name: "添加", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "add",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 11,
        id: 14,
        name: "编辑", //看官网，这个名字是3-5之间的
        url: "", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "edit",
        type: 2,
        icon: "",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: -1,
        id: 15,
        name: "参考资料", //看官网，这个名字是3-5之间的
        url: "",
        menu: "",
        type: 0,
        icon: "DocumentCopy",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 15,
        id: 16,
        name: "vue3.0", //看官网，这个名字是3-5之间的
        url: "https://www.vue3js.cn/docs/zh/", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "",
        type: 1,
        icon: "Promotion",
        sort: 1,
        iframe: 1,
    },
    {
        parentId: 15,
        id: 17,
        name: "element-plus", //看官网，这个名字是3-5之间的
        url: "https://element-plus.org/#/zh-CN", //这个类似上面的id一个，只是初始值是从100开始的
        menu: "",
        type: 1,
        icon: "ElemeFilled",
        sort: 1,
        iframe: 1,
    },
];
const userList = [
    {
        id: -1,
        name: "Administrator",
        userName: "超级管理员",
        password: "123456",
        role: -1,
        status: 1,
    },
    {
        id: 0,
        name: "admin",
        userName: "管理员",
        password: "123456",
        role: 0,
        status: 1,
    },
];
const roleList = [
    {
        id: -1,
        name: "super",
        roleName: "超级管理员",
        status: "1",
        role: menuList,
    },
    {
        id: 0,
        name: "ceshi",
        roleName: "测试",
        status: "0",
        role: menuList,
    },
];

const user = {
    name: "",
    userId: "",
    roleId: "",
    menus: null,
};

module.exports = {
    userList: {
        url: "/wu-lazy-cloud-network/sys/user/retrieve",
        type: "get",
        mock: true,
        response: (opt) => {
            const {
                body: { role, limit, page },
            } = opt;
            return searchList(role, limit, page, userList, "role");
        },
    },
    userExportList: {
        url: "/wu-lazy-cloud-network/sys/user/export/List",
        type: "get",
    },
    userPage: {
        url: "/wu-lazy-cloud-network/sys/user/findPage",
        type: "get",
    },
    userAdd: {
        url: "/wu-lazy-cloud-network/sys/user/story",
        type: "post",
    },
    userEdit: {
        url: "/wu-lazy-cloud-network/sys/user/updateOne",
        type: "put",
    },
    userDel: {
        url: "/wu-lazy-cloud-network/sys/user/{{id}}",
        type: "deleteUrl",
    },
    userStatus: {
        url: "/wu-lazy-cloud-network/sys/user/status",
        type: "post",
        mock: true,
        response: (opt) => {
            const {
                body: { id, status },
            } = opt;
            if (id === -1) {
                return {
                    message: "系统超级管理员账户不能停用!",
                    code: "01",
                };
            }
            userList.find((item) => item.id === id).status = status;
            return {
                message: "切换成功!",
                code: "00",
            };
        },
    },

    menuList: {
        url: "/wu-lazy-cloud-network/menu/findList",
        type: "get",
    },
    menuAdd: {
        url: "/wu-lazy-cloud-network/menu/story",
        type: "post",
        mock: true,
        response: (opt) => {
            const {
                body: { name },
            } = opt;
            return addList(opt, menuList, name, "name");
        },
    },
    menuEdit: {
        url: "/wu-lazy-cloud-network/menu/update",
        type: "put",
    },
    menuUpsert: {
        url: "/wu-lazy-cloud-network/menu/upsert",
        type: "post",
    },
    menuDel: {
        url: "/wu-lazy-cloud-network/menu/delete",
        type: "delete",
    },

    roleList: {
        url: "/wu-lazy-cloud-network/role/retrieve",
        type: "get",
    },
    roleOne: {
        url: "/wu-lazy-cloud-network/role/findOne",
        type: "get",
    },
    rolePage: {
        url: "/wu-lazy-cloud-network/role/retrieve/page",
        type: "get",
        mock: true,
        response: (opt) => {
            const {
                body: { name, limit, page },
            } = opt;
            return searchList(name, limit, page, roleList, "name");
        },
    },
    roleAdd: {
        url: "/wu-lazy-cloud-network/role/story",
        type: "post",
    },
    roleEdit: {
        url: "/wu-lazy-cloud-network/role/updateOne",
        type: "put",
    },
    roleDel: {
        url: "/wu-lazy-cloud-network/role/{{id}}",
        type: "deleteUrl",
    },

    userMenuList: {
        url: "/wu-lazy-cloud-network/sys/user/findUserMenuList",
        type: "get",
        mock: true,
        response: () => {
            user.menus = roleList.find((item) => item.id == user.roleId).role;
            return {
                message: "查询成功!",
                code: "00",
                list: user.menus,
            };
        },
    },
    login: {
        url: "/wu-lazy-cloud-network/token/access_token",
        type: "post",
    },
    resolvingUser: {
        url: "/wu-lazy-cloud-network/token/user/{{accessToken}}",
        type: "getUrl",
    },
    register: {
        url: "/wu-lazy-cloud-network/token/create_user",
        type: "post",
    },
    dictionaryPage: {
        url: "/wu-lazy-cloud-network/dictionary/retrieve/page",
        type: "get",
    },
    dictionaryDelete: {
        url: "/wu-lazy-cloud-network/dictionary/{{id}}",
        type: "deleteUrl",
    },
    dictionaryAdd: {
        url: "/wu-lazy-cloud-network/dictionary/save",
        type: "post",
    },
    dictionaryEdit: {
        url: "/wu-lazy-cloud-network/dictionary/update",
        type: "put",
    },
    dictionaryDataPage: {
        url: "/wu-lazy-cloud-network/dictionary/data/retrieve/page",
        type: "get",
    },
    dictionaryDataList: {
        url: "/wu-lazy-cloud-network/dictionary/data/retrieve",
        type: "get",
    },
    dictionaryDataDelete: {
        url: "/wu-lazy-cloud-network/dictionary/data/{{id}}",
        type: "deleteUrl",
    },
    dictionaryDataAdd: {
        url: "/wu-lazy-cloud-network/dictionary/data/save",
        type: "post",
    },
    dictionaryDataEdit: {
        url: "/wu-lazy-cloud-network/dictionary/data/update",
        type: "put",
    },

    upsertAdd: {
        url: "/wu-lazy-cloud-network/upsert/task/save",
        type: "post",
    },
    upsertPage: {
        url: "/wu-lazy-cloud-network/upsert/task/retrieve/page",
        type: "get",
    },
    upsertEdit: {
        url: "/wu-lazy-cloud-network/upsert/task/update",
        type: "put",
    },
    upsertDelete: {
        url: "/wu-lazy-cloud-network/upsert/task/{{id}}",
        type: "deleteUrl",
    },
    databaseInstanceAdd: {
        url: "/wu-lazy-cloud-network/database/instance/save",
        type: "post",
    },
    databaseInstanceTest: {
        url: "/wu-lazy-cloud-network/database/instance/testConnection",
        type: "post",
    },
    databaseInstancePage: {
        url: "/wu-lazy-cloud-network/database/instance/retrieve/page",
        type: "get",
    },
    databaseInstanceList: {
        url: "/wu-lazy-cloud-network/database/instance/retrieve",
        type: "get",
    },
    databaseInstanceEdit: {
        url: "/wu-lazy-cloud-network/database/instance/update",
        type: "put",
    },
    databaseInstanceDelete: {
        url: "/wu-lazy-cloud-network/database/instance/{{id}}",
        type: "deleteUrl",
    },
    databaseInstanceReload: {
        url: "/wu-lazy-cloud-network/database/instance/reload/{{id}}",
        type: "patchUrl",
    },

    acwTableAssociationRelationPage: {
        url: "/wu-lazy-cloud-network/lazy/acw/table/association/relation/findPage",
        type: "get",
    },
    acwTableAssociationRelationList: {
        url: "/wu-lazy-cloud-network/lazy/acw/table/association/relation/findList",
        type: "get",
    },
    acwTableAssociationRelationStory: {
        url: "/wu-lazy-cloud-network/lazy/acw/table/association/relation/story",
        type: "post",
    },
    acwTableAssociationRelationDelete: {
        url: "/wu-lazy-cloud-network/lazy/acw/table/association/relation/remove",
        type: "deleteUrl",
    },
    acwTableAssociationRelationReload: {
        url: "/wu-lazy-cloud-network/lazy/acw/table/association/relation/reload/{{id}}",
        type: "patchUrl",
    },
    acwTableAssociationRelationAnalysisSchema: {
        url: "/wu-lazy-cloud-network/lazy/acw/table/association/relation/analysisSchema",
        type: "put",
    },
    // 数据库备份API 开始
    databaseInstanceBackUpPage: {
        url: "/wu-lazy-cloud-network//lazy/database/instance/back/ups/findPage",
        type: "get",
    },
    databaseInstanceBackUpDelete: {
        url: "/wu-lazy-cloud-network//lazy/database/instance/back/ups/remove",
        type: "delete",
    },
    databaseInstanceBackUp: {
        url: "/wu-lazy-cloud-network//lazy/database/instance/back/ups/backUps",
        type: "patch",
    },
    databaseSchemaBackUpPage: {
        url: "/wu-lazy-cloud-network//lazy/database/schema/back/ups/findPage",
        type: "get",
    },
    databaseSchemaBackUp: {
        url: "/wu-lazy-cloud-network//lazy/database/schema/back/ups/backUps",
        type: "patch",
    },
    databaseSchemaBackUpRemove: {
        url: "/wu-lazy-cloud-network//lazy/database/schema/back/ups/remove",
        type: "delete",
    },
    // 数据库备份API 结束
    // redis api实例 开始
    redisInstancePage: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/instance/findPage",
        type: "get",
    },
    redisInstanceDelete: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/instance/remove",
        type: "delete",
    },
    redisInstanceStory: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/instance/story",
        type: "post",
    },
    redisInstanceTest: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/instance/test",
        type: "post",
    },
    redisInstanceConsoleFindDataBases: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/console/findDataBases",
        type: "get",
    },
    redisInstanceConsoleFindKeys: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/console/findKeys",
        type: "get",
    },

    redisInstanceConsoleFindKeysValues: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/console/findKeysValues",
        type: "get",
    },
    redisInstanceConsoleFindKeyValue: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/console/findKeyValue",
        type: "get",
    },
    redisInstanceConsoleSetKey: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/console/setKey",
        type: "post",
    },
    redisInstanceConsoleRemoveKey: {
        url: "/wu-lazy-cloud-network/lazy/acw/redis/console/removeKey",
        type: "put",
    },
    // redis api实例 结束
    schemaAdd: {
        url: "/wu-lazy-cloud-network/schema/save",
        type: "post",
    },
    schemaPage: {
        url: "/wu-lazy-cloud-network/schema/retrieve/page",
        type: "get",
    },
    schemaList: {
        url: "/wu-lazy-cloud-network/schema/findList",
        type: "get",
    },
    schemaEdit: {
        url: "/wu-lazy-cloud-network/schema/update",
        type: "put",
    },
    schemaDelete: {
        url: "/wu-lazy-cloud-network/schema/{{id}}",
        type: "deleteUrl",
    },
    batchDeleteInstanceSchema: {
        url: "/wu-lazy-cloud-network/schema/delete/batchDeleteInstanceSchema",
        type: "deleteBody",
    },
    batchExportSchemaData: {
        url: "/wu-lazy-cloud-network/schema/batchExportSchemaData",
        type: "post",
    },
    schemaReload: {
        url: "/wu-lazy-cloud-network/schema/reload/{{instanceId}}/{{schema}}",
        type: "patchUrl",
    },
    aceSchemaImportData: {
        url: "/wu-lazy-cloud-network/schema/batchImportSchemaData",
        type: "post",
    },
    schemaDeriveView: {
        url: "/wu-lazy-cloud-network/schema/schemaDeriveView",
        type: "post",
    },
    tableAutoStuffedRecordAdd: {
        url: "/wu-lazy-cloud-network/database/table/auto/stuffed/record/save",
        type: "post",
    },
    tableAutoStuffedRecordBatchAdd: {
        url: "/wu-lazy-cloud-network/database/table/auto/stuffed/record/batchSave",
        type: "post",
    },
    tableAutoStuffedRecordPage: {
        url: "/wu-lazy-cloud-network/database/table/auto/stuffed/record/findPage",
        type: "get",
    },
    tableAutoStuffedRecordList: {
        url: "/wu-lazy-cloud-network/database/table/auto/stuffed/record/findList",
        type: "get",
    },
    tableAutoStuffedRecordEdit: {
        url: "/wu-lazy-cloud-network/database/table/auto/stuffed/record/update",
        type: "put",
    },
    tableAutoStuffedRecordDelete: {
        url: "/wu-lazy-cloud-network/database/table/auto/stuffed/record/{{id}}",
        type: "deleteUrl",
    },
    tableAutoStuffedRecordBatchDelete: {
        url: "/wu-lazy-cloud-network/database/table/auto/stuffed/record/batch",
        type: "deleteBody",
    },
    tableAdd: {
        url: "/wu-lazy-cloud-network/table/storage",
        type: "post",
    },
    tablePage: {
        url: "/wu-lazy-cloud-network/table/retrieve/page",
        type: "get",
    },
    tableList: {
        url: "/wu-lazy-cloud-network/table/retrieve",
        type: "get",
    },
    tableDelete: {
        url: "/wu-lazy-cloud-network/table/delete/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "deleteUrl",
    },
    batchDeleteTableDelete: {
        url: "/wu-lazy-cloud-network/table/batchDelete",
        type: "deleteBody",
    },
    slqAuditPage: {
        url: "/wu-lazy-cloud-network/sql/audit/findPage",
        type: "get",
    },
    slqAuditExport: {
        url: "/wu-lazy-cloud-network/sql/audit/export/findList",
        type: "get",
    },
    batchDeleteSlqAudit: {
        url: "/wu-lazy-cloud-network/sql/audit/batch",
        type: "deleteBody",
    },
    generateJavaModel: {
        url: "/wu-lazy-cloud-network/table/generate/java/model/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    generateLocalJava: {
        url: "/wu-lazy-cloud-network/table/generate/local/java",
        type: "post",
    },
    clientGenerateLocalJava: {
        url: "/wu-lazy-cloud-network/acw/client/java/path/generate/local/java",
        type: "post",
    },
    tableExportInsertSql: {
        url: "/wu-lazy-cloud-network/table/export/insert/sql",
        type: "get",
    },
    tableExportUpsertSql: {
        url: "/wu-lazy-cloud-network/table/export/upsert/sql",
        type: "get",
    },
    exportTableStructureExcel: {
        url: "/wu-lazy-cloud-network/table/export/table/structure/excel",
        type: "get",
    },
    exportTableStructureSql: {
        url: "/wu-lazy-cloud-network/table/export/table/structure/sql",
        type: "get",
    },
    applicationAdd: {
        url: "/wu-lazy-cloud-network/application/save",
        type: "post",
    },
    applicationPage: {
        url: "/wu-lazy-cloud-network/application/retrieve/page",
        type: "get",
    },
    applicationList: {
        url: "/wu-lazy-cloud-network/application/retrieve",
        type: "get",
    },
    applicationEdit: {
        url: "/wu-lazy-cloud-network/application/update",
        type: "put",
    },
    applicationDelete: {
        url: "/wu-lazy-cloud-network/application/{{id}}",
        type: "deleteUrl",
    },
    applicationFindTables: {
        url: "/wu-lazy-cloud-network/application/findTables/{{applicationId}}",
        type: "getUrl",
    },
    projectAdd: {
        url: "/wu-lazy-cloud-network/project/save",
        type: "post",
    },
    projectPage: {
        url: "/wu-lazy-cloud-network/project/retrieve/page",
        type: "get",
    },
    projectList: {
        url: "/wu-lazy-cloud-network/project/retrieve",
        type: "get",
    },
    projectEdit: {
        url: "/wu-lazy-cloud-network/project/update",
        type: "put",
    },
    projectDelete: {
        url: "/wu-lazy-cloud-network/project/{{id}}",
        type: "deleteUrl",
    },
    interfaceAdd: {
        url: "/wu-lazy-cloud-network/interface/save",
        type: "post",
    },
    interfacePage: {
        url: "/wu-lazy-cloud-network/interface/retrieve/page",
        type: "get",
    },
    interfaceList: {
        url: "/wu-lazy-cloud-network/interface/retrieve",
        type: "get",
    },
    interfaceEdit: {
        url: "/wu-lazy-cloud-network/interface/update",
        type: "put",
    },
    interfaceDelete: {
        url: "/wu-lazy-cloud-network/interface/{{id}}",
        type: "deleteUrl",
    },
    apiAdd: {
        url: "/wu-lazy-cloud-network/api/save",
        type: "post",
    },
    apiPage: {
        url: "/wu-lazy-cloud-network/api/retrieve/page",
        type: "get",
    },
    apiList: {
        url: "/wu-lazy-cloud-network/api/retrieve",
        type: "get",
    },
    apiEdit: {
        url: "/wu-lazy-cloud-network/api/update",
        type: "put",
    },
    apiDelete: {
        url: "/wu-lazy-cloud-network/api/{{id}}",
        type: "deleteUrl",
    },
    clientApiAdd: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/story",
        type: "post",
    },
    clientApiSQLAdd: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/story-sql",
        type: "post",
    },
    clientApiPage: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/findPage",
        type: "get",
    },
    clientApiList: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/findList",
        type: "get",
    },
    clientApiFindOne: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/findOne",
        type: "get",
    },
    clientApiEdit: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/updateOne",
        type: "put",
    },
    clientApiDelete: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/remove",
        type: "deleteUrl",
    },
    clientApiDerivativeCode: {
        url: "/wu-lazy-cloud-network/lazy/interface_/info/derivativeCode",
        type: "put",
    },

    clientInstancePage: {
        url: "/wu-lazy-cloud-network/acw/client/instance/findPage",
        type: "get",
    },
    clientInstanceList: {
        url: "/wu-lazy-cloud-network/acw/client/instance/findList",
        type: "get",
    },
    clientInstanceFindOne: {
        url: "/wu-lazy-cloud-network/acw/client/instance/findOne",
        type: "get",
    },

    clientJavaPathPage: {
        url: "/wu-lazy-cloud-network/acw/client/java/path/findPage",
        type: "get",
    },
    clientJavaPathList: {
        url: "/wu-lazy-cloud-network/acw/client/java/path/findList",
        type: "get",
    },
    clientJavaPathFindOne: {
        url: "/wu-lazy-cloud-network/acw/client/java/path/findOne",
        type: "get",
    },

    clientFindDefaultSchema: {
        url: "/wu-lazy-cloud-network//current/project/dataSource/findDefaultSchema",
        type: "get",
    },

    clientFindSchemaList: {
        url: "/wu-lazy-cloud-network//current/project/dataSource/findSchemaList",
        type: "get",
    },
    clientFindTableList: {
        url: "/wu-lazy-cloud-network//current/project/dataSource/findTableList",
        type: "get",
    },
    clientFindColumnList: {
        url: "/wu-lazy-cloud-network//current/project/dataSource/findColumnList",
        type: "get",
    },
    databaseTableColumnAdd: {
        url: "/wu-lazy-cloud-network/database/table/column/save",
        type: "post",
    },
    databaseTableColumnPage: {
        url: "/wu-lazy-cloud-network/database/table/column/retrieve/page",
        type: "get",
    },
    databaseTablesColumnList: {
        url: "/wu-lazy-cloud-network/database/table/column/tableIds/{{instanceId}}/{{schemaName}}/{{tableNameList}}",
        type: "getUrl",
    },
    databaseTableColumnList: {
        url: "/wu-lazy-cloud-network/database/table/column/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    findDatabaseTableColumnList: {
        url: "/wu-lazy-cloud-network/database/table/column/findColumn/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    findInstanceSchemaColumnList: {
        url: "/wu-lazy-cloud-network/database/table/column/findInstanceSchemaColumnList/{{instanceId}}/{{schemaName}}",
        type: "getUrl",
    },
    databaseTableColumnEdit: {
        url: "/wu-lazy-cloud-network/database/table/column/update",
        type: "put",
    },
    databaseTableColumnDelete: {
        url: "/wu-lazy-cloud-network/database/table/column/{{id}}",
        type: "deleteUrl",
    },
    playMusicInsertOrUpdate: {
        url: "/wu-lazy-cloud-network/play/music/insertOrUpdate",
        type: "post",
    },
    playMusicPage: {
        url: "/wu-lazy-cloud-network/play/music/retrieve/page",
        type: "get",
    },
    playMusicList: {
        url: "/wu-lazy-cloud-network/play/music/retrieve",
        type: "get",
    },
    playMusicEdit: {
        url: "/wu-lazy-cloud-network/play/music/update",
        type: "put",
    },
    playMusicDelete: {
        url: "/wu-lazy-cloud-network/play/music/{{id}}",
        type: "deleteUrl",
    },
    playMusicFindData: {
        url: "/wu-lazy-cloud-network/play/music/retrieve/data/{{id}}",
        type: "getUrl",
    },
    playFileAdd: {
        url: "/wu-lazy-cloud-network/play/file/add",
        type: "post",
    },
    playFilePage: {
        url: "/wu-lazy-cloud-network/play/file/retrieve/page",
        type: "get",
    },
    playFileList: {
        url: "/wu-lazy-cloud-network/play/file/retrieve",
        type: "get",
    },
    playFileData: {
        url: "/wu-lazy-cloud-network/play/file/retrieve/data/{{id}}",
        type: "getUrl",
    },
    playFileEdit: {
        url: "/wu-lazy-cloud-network/play/file/update",
        type: "put",
    },
    playFileDelete: {
        url: "/wu-lazy-cloud-network/play/file/{{id}}",
        type: "deleteUrl",
    },
    playFileResourceList: {
        url: "/wu-lazy-cloud-network/play/file/getFileResourceList",
        type: "get",
    },
    downLocalFile: {
        url: "/wu-lazy-cloud-network/play/file/retrieve/local/file",
        type: "get",
    },
    docPdfMerge: {
        url: "/wu-lazy-cloud-network/doc/pdf/merge/merge",
        type: "post",
    },
    docJson2Excel: {
        url: "/wu-lazy-cloud-network/doc/excel/json2Excel",
        type: "post",
    },
    sqlConsole: {
        url: "/wu-lazy-cloud-network/console/execute/sql/statement",
        type: "get",
    },
    sqlConsoleExport: {
        url: "/wu-lazy-cloud-network/console/execute/sql/statement/export",
        type: "get",
    },
    sqlConsoleUpsertExport: {
        url: "/wu-lazy-cloud-network/console/execute/upsert/sql/statement/export",
        type: "get",
    },
    tableSqlConsole: {
        url: "/wu-lazy-cloud-network/table/console/execute/table/sql/statement",
        type: "post",
    },
    tableColumnSqlConsole: {
        url: "/wu-lazy-cloud-network/table/console/execute/table/sql/statement/column",
        type: "post",
    },
    tableRowStory: {
        url: "/wu-lazy-cloud-network/table/console/execute/upsert/table/sql",
        type: "post",
    },
    tableRowDelete: {
        url: "/wu-lazy-cloud-network/table/console/execute/delete/table/sql",
        type: "post",
    },
    tableRowBatchDelete: {
        url: "/wu-lazy-cloud-network/table/console/execute/batch/delete/table/sql",
        type: "post",
    },
    tableSqlConsoleExport: {
        url: "/wu-lazy-cloud-network/table/console/execute/table/sql/statement/export",
        type: "post",
    },
    tableSqlConsoleUpsertExport: {
        url: "/wu-lazy-cloud-network/table/console/execute/upsert/table/sql/statement/export",
        type: "post",
    },
    tableSqlConsoleMdExport: {
        url: "/wu-lazy-cloud-network/table/console/execute/md/table/sql/statement/export",
        type: "post",
    },
    translate: {
        url: "/wu-lazy-cloud-network/translate",
        type: "get",
    },
    shortcutsData: {
        url: "/wu-lazy-cloud-network/shortcuts/data",
        type: "get",
    },
    jvmRunTimeMemory: {
        url: "/wu-lazy-cloud-network/jvm/runTimeMemory",
        type: "get",
    },
    jvmGC: {
        url: "/wu-lazy-cloud-network/jvm/gc",
        type: "get",
    },
    jvmCPU: {
        url: "/wu-lazy-cloud-network/jvm/cpu",
        type: "get",
    },
    //     自动化开始
    automationStory: {
        url: "/wu-lazy-cloud-network/lazy/automation/story",
        type: "post",
    },
    automationFindPage: {
        url: "/wu-lazy-cloud-network/lazy/automation/findPage",
        type: "get",
    },
    automationFindOne: {
        url: "/wu-lazy-cloud-network/lazy/automation/findOne",
        type: "get",
    },
    automationRemove: {
        url: "/wu-lazy-cloud-network/lazy/automation/remove",
        type: "delete",
    },

    automationNodeStory: {
        url: "/wu-lazy-cloud-network/lazy/automation/node/story",
        type: "post",
    },
    automationNodeRemove: {
        url: "/wu-lazy-cloud-network/lazy/automation/node/remove",
        type: "delete",
    },

    automationNodeHttpActionStory: {
        url: "/wu-lazy-cloud-network/lazy/automation/node/http/action/story",
        type: "post",
    },
    automationNodeHttpActionRemove: {
        url: "/wu-lazy-cloud-network/lazy/automation/node/http/action/remove",
        type: "delete",
    },

    ttsTimbreStory: {
        url: "/wu-lazy-cloud-network/tts/timbre/story",
        type: "post",
    },
    ttsTimbreFindPage: {
        url: "/wu-lazy-cloud-network/tts/timbre/findPage",
        type: "get",
    },
    ttsTimbreFindList: {
        url: "/wu-lazy-cloud-network/tts/timbre/findList",
        type: "get",
    },
    ttsTimbreUpdateOne: {
        url: "/wu-lazy-cloud-network/tts/timbre/updateOne",
        type: "put",
    },
    ttsTimbreRemove: {
        url: "/wu-lazy-cloud-network/tts/timbre/remove",
        type: "delete",
    },

    ttsChineseCharactersStory: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/story",
        type: "post",
    },
    ttsChineseCharactersFindPage: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/findPage",
        type: "get",
    },
    ttsChineseCharactersFindList: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/findList",
        type: "get",
    },
    ttsChineseCharactersUpdateOne: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/updateOne",
        type: "put",
    },
    ttsChineseCharactersRemove: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/remove",
        type: "delete",
    },

    ttsChineseCharactersTimbreStory: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/timbre/story",
        type: "post",
    },
    ttsChineseCharactersTimbreFindPage: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/timbre/findPage",
        type: "get",
    },
    ttsChineseCharactersTimbreFindList: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/timbre/findList",
        type: "get",
    },
    ttsChineseCharactersTimbreUpdateOne: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/timbre/updateOne",
        type: "put",
    },
    ttsChineseCharactersTimbreRemove: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/timbre/remove",
        type: "delete",
    },
    ttsChineseCharactersTimbreTextToBytes: {
        url: "/wu-lazy-cloud-network/tts/chinese/characters/timbre/textToBytes",
        type: "get",
    },
    automationNodeHttpActionExecuteOne: {
        url: "/wu-lazy-cloud-network/lazy/automation/node/http/action/executeOne/{{actionId}}",
        type: "patchUrl",
    },
};
