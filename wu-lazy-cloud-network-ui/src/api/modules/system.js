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
        url: "/sys/user/retrieve",
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
        url: "/sys/user/export/List",
        type: "get",
    },
    userPage: {
        url: "/sys/user/findPage",
        type: "get",
    },
    userAdd: {
        url: "/sys/user/story",
        type: "post",
    },
    userEdit: {
        url: "/sys/user/updateOne",
        type: "put",
    },
    userDel: {
        url: "/sys/user/{{id}}",
        type: "deleteUrl",
    },
    userStatus: {
        url: "/sys/user/status",
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
        url: "/menu/findList",
        type: "get",
    },
    menuAdd: {
        url: "/menu/story",
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
        url: "/menu/update",
        type: "put",
    },
    menuUpsert: {
        url: "/menu/upsert",
        type: "post",
    },
    menuDel: {
        url: "/menu/delete",
        type: "delete",
    },

    roleList: {
        url: "/role/retrieve",
        type: "get",
    },
    roleOne: {
        url: "/role/findOne",
        type: "get",
    },
    rolePage: {
        url: "/role/retrieve/page",
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
        url: "/role/story",
        type: "post",
    },
    roleEdit: {
        url: "/role/updateOne",
        type: "put",
    },
    roleDel: {
        url: "/role/{{id}}",
        type: "deleteUrl",
    },

    userMenuList: {
        url: "/sys/user/findUserMenuList",
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
        url: "/token/access_token",
        type: "post",
    },
    resolvingUser: {
        url: "/token/user/{{accessToken}}",
        type: "getUrl",
    },
    register: {
        url: "/token/create_user",
        type: "post",
    },
    dictionaryPage: {
        url: "/dictionary/retrieve/page",
        type: "get",
    },
    dictionaryDelete: {
        url: "/dictionary/{{id}}",
        type: "deleteUrl",
    },
    dictionaryAdd: {
        url: "/dictionary/save",
        type: "post",
    },
    dictionaryEdit: {
        url: "/dictionary/update",
        type: "put",
    },
    dictionaryDataPage: {
        url: "/dictionary/data/retrieve/page",
        type: "get",
    },
    dictionaryDataList: {
        url: "/dictionary/data/retrieve",
        type: "get",
    },
    dictionaryDataDelete: {
        url: "/dictionary/data/{{id}}",
        type: "deleteUrl",
    },
    dictionaryDataAdd: {
        url: "/dictionary/data/save",
        type: "post",
    },
    dictionaryDataEdit: {
        url: "/dictionary/data/update",
        type: "put",
    },

    upsertAdd: {
        url: "/upsert/task/save",
        type: "post",
    },
    upsertPage: {
        url: "/upsert/task/retrieve/page",
        type: "get",
    },
    upsertEdit: {
        url: "/upsert/task/update",
        type: "put",
    },
    upsertDelete: {
        url: "/upsert/task/{{id}}",
        type: "deleteUrl",
    },
    databaseInstanceAdd: {
        url: "/database/instance/save",
        type: "post",
    },
    databaseInstanceTest: {
        url: "/database/instance/testConnection",
        type: "post",
    },
    databaseInstancePage: {
        url: "/database/instance/retrieve/page",
        type: "get",
    },
    databaseInstanceList: {
        url: "/database/instance/retrieve",
        type: "get",
    },
    databaseInstanceEdit: {
        url: "/database/instance/update",
        type: "put",
    },
    databaseInstanceDelete: {
        url: "/database/instance/{{id}}",
        type: "deleteUrl",
    },
    databaseInstanceReload: {
        url: "/database/instance/reload/{{id}}",
        type: "patchUrl",
    },

    acwTableAssociationRelationPage: {
        url: "/lazy/acw/table/association/relation/findPage",
        type: "get",
    },
    acwTableAssociationRelationList: {
        url: "/lazy/acw/table/association/relation/findList",
        type: "get",
    },
    acwTableAssociationRelationStory: {
        url: "/lazy/acw/table/association/relation/story",
        type: "post",
    },
    acwTableAssociationRelationDelete: {
        url: "/lazy/acw/table/association/relation/remove",
        type: "deleteUrl",
    },
    acwTableAssociationRelationReload: {
        url: "/lazy/acw/table/association/relation/reload/{{id}}",
        type: "patchUrl",
    },
    acwTableAssociationRelationAnalysisSchema: {
        url: "/lazy/acw/table/association/relation/analysisSchema",
        type: "put",
    },
    // 数据库备份API 开始
    databaseInstanceBackUpPage: {
        url: "//lazy/database/instance/back/ups/findPage",
        type: "get",
    },
    databaseInstanceBackUpDelete: {
        url: "//lazy/database/instance/back/ups/remove",
        type: "delete",
    },
    databaseInstanceBackUp: {
        url: "//lazy/database/instance/back/ups/backUps",
        type: "patch",
    },
    databaseSchemaBackUpPage: {
        url: "//lazy/database/schema/back/ups/findPage",
        type: "get",
    },
    databaseSchemaBackUp: {
        url: "//lazy/database/schema/back/ups/backUps",
        type: "patch",
    },
    databaseSchemaBackUpRemove: {
        url: "//lazy/database/schema/back/ups/remove",
        type: "delete",
    },
    // 数据库备份API 结束
    // redis api实例 开始
    redisInstancePage: {
        url: "/lazy/acw/redis/instance/findPage",
        type: "get",
    },
    redisInstanceDelete: {
        url: "/lazy/acw/redis/instance/remove",
        type: "delete",
    },
    redisInstanceStory: {
        url: "/lazy/acw/redis/instance/story",
        type: "post",
    },
    redisInstanceTest: {
        url: "/lazy/acw/redis/instance/test",
        type: "post",
    },
    redisInstanceConsoleFindDataBases: {
        url: "/lazy/acw/redis/console/findDataBases",
        type: "get",
    },
    redisInstanceConsoleFindKeys: {
        url: "/lazy/acw/redis/console/findKeys",
        type: "get",
    },

    redisInstanceConsoleFindKeysValues: {
        url: "/lazy/acw/redis/console/findKeysValues",
        type: "get",
    },
    redisInstanceConsoleFindKeyValue: {
        url: "/lazy/acw/redis/console/findKeyValue",
        type: "get",
    },
    redisInstanceConsoleSetKey: {
        url: "/lazy/acw/redis/console/setKey",
        type: "post",
    },
    redisInstanceConsoleRemoveKey: {
        url: "/lazy/acw/redis/console/removeKey",
        type: "put",
    },
    // redis api实例 结束
    schemaAdd: {
        url: "/schema/save",
        type: "post",
    },
    schemaPage: {
        url: "/schema/retrieve/page",
        type: "get",
    },
    schemaList: {
        url: "/schema/findList",
        type: "get",
    },
    schemaEdit: {
        url: "/schema/update",
        type: "put",
    },
    schemaDelete: {
        url: "/schema/{{id}}",
        type: "deleteUrl",
    },
    batchDeleteInstanceSchema: {
        url: "/schema/delete/batchDeleteInstanceSchema",
        type: "deleteBody",
    },
    batchExportSchemaData: {
        url: "/schema/batchExportSchemaData",
        type: "post",
    },
    schemaReload: {
        url: "/schema/reload/{{instanceId}}/{{schema}}",
        type: "patchUrl",
    },
    aceSchemaImportData: {
        url: "/schema/batchImportSchemaData",
        type: "post",
    },
    schemaDeriveView: {
        url: "/schema/schemaDeriveView",
        type: "post",
    },
    tableAutoStuffedRecordAdd: {
        url: "/database/table/auto/stuffed/record/save",
        type: "post",
    },
    tableAutoStuffedRecordBatchAdd: {
        url: "/database/table/auto/stuffed/record/batchSave",
        type: "post",
    },
    tableAutoStuffedRecordPage: {
        url: "/database/table/auto/stuffed/record/findPage",
        type: "get",
    },
    tableAutoStuffedRecordList: {
        url: "/database/table/auto/stuffed/record/findList",
        type: "get",
    },
    tableAutoStuffedRecordEdit: {
        url: "/database/table/auto/stuffed/record/update",
        type: "put",
    },
    tableAutoStuffedRecordDelete: {
        url: "/database/table/auto/stuffed/record/{{id}}",
        type: "deleteUrl",
    },
    tableAutoStuffedRecordBatchDelete: {
        url: "/database/table/auto/stuffed/record/batch",
        type: "deleteBody",
    },
    tableAdd: {
        url: "/table/storage",
        type: "post",
    },
    tablePage: {
        url: "/table/retrieve/page",
        type: "get",
    },
    tableList: {
        url: "/table/retrieve",
        type: "get",
    },
    tableDelete: {
        url: "/table/delete/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "deleteUrl",
    },
    batchDeleteTableDelete: {
        url: "/table/batchDelete",
        type: "deleteBody",
    },
    slqAuditPage: {
        url: "/sql/audit/findPage",
        type: "get",
    },
    slqAuditExport: {
        url: "/sql/audit/export/findList",
        type: "get",
    },
    batchDeleteSlqAudit: {
        url: "/sql/audit/batch",
        type: "deleteBody",
    },
    generateJavaModel: {
        url: "/table/generate/java/model/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    generateLocalJava: {
        url: "/table/generate/local/java",
        type: "post",
    },
    clientGenerateLocalJava: {
        url: "/acw/client/java/path/generate/local/java",
        type: "post",
    },
    tableExportInsertSql: {
        url: "/table/export/insert/sql",
        type: "get",
    },
    tableExportUpsertSql: {
        url: "/table/export/upsert/sql",
        type: "get",
    },
    exportTableStructureExcel: {
        url: "/table/export/table/structure/excel",
        type: "get",
    },
    exportTableStructureSql: {
        url: "/table/export/table/structure/sql",
        type: "get",
    },
    applicationAdd: {
        url: "/application/save",
        type: "post",
    },
    applicationPage: {
        url: "/application/retrieve/page",
        type: "get",
    },
    applicationList: {
        url: "/application/retrieve",
        type: "get",
    },
    applicationEdit: {
        url: "/application/update",
        type: "put",
    },
    applicationDelete: {
        url: "/application/{{id}}",
        type: "deleteUrl",
    },
    applicationFindTables: {
        url: "/application/findTables/{{applicationId}}",
        type: "getUrl",
    },
    projectAdd: {
        url: "/project/save",
        type: "post",
    },
    projectPage: {
        url: "/project/retrieve/page",
        type: "get",
    },
    projectList: {
        url: "/project/retrieve",
        type: "get",
    },
    projectEdit: {
        url: "/project/update",
        type: "put",
    },
    projectDelete: {
        url: "/project/{{id}}",
        type: "deleteUrl",
    },
    interfaceAdd: {
        url: "/interface/save",
        type: "post",
    },
    interfacePage: {
        url: "/interface/retrieve/page",
        type: "get",
    },
    interfaceList: {
        url: "/interface/retrieve",
        type: "get",
    },
    interfaceEdit: {
        url: "/interface/update",
        type: "put",
    },
    interfaceDelete: {
        url: "/interface/{{id}}",
        type: "deleteUrl",
    },
    apiAdd: {
        url: "/api/save",
        type: "post",
    },
    apiPage: {
        url: "/api/retrieve/page",
        type: "get",
    },
    apiList: {
        url: "/api/retrieve",
        type: "get",
    },
    apiEdit: {
        url: "/api/update",
        type: "put",
    },
    apiDelete: {
        url: "/api/{{id}}",
        type: "deleteUrl",
    },
    clientApiAdd: {
        url: "/lazy/interface_/info/story",
        type: "post",
    },
    clientApiSQLAdd: {
        url: "/lazy/interface_/info/story-sql",
        type: "post",
    },
    clientApiPage: {
        url: "/lazy/interface_/info/findPage",
        type: "get",
    },
    clientApiList: {
        url: "/lazy/interface_/info/findList",
        type: "get",
    },
    clientApiFindOne: {
        url: "/lazy/interface_/info/findOne",
        type: "get",
    },
    clientApiEdit: {
        url: "/lazy/interface_/info/updateOne",
        type: "put",
    },
    clientApiDelete: {
        url: "/lazy/interface_/info/remove",
        type: "deleteUrl",
    },
    clientApiDerivativeCode: {
        url: "/lazy/interface_/info/derivativeCode",
        type: "put",
    },

    clientInstancePage: {
        url: "/acw/client/instance/findPage",
        type: "get",
    },
    clientInstanceList: {
        url: "/acw/client/instance/findList",
        type: "get",
    },
    clientInstanceFindOne: {
        url: "/acw/client/instance/findOne",
        type: "get",
    },

    clientJavaPathPage: {
        url: "/acw/client/java/path/findPage",
        type: "get",
    },
    clientJavaPathList: {
        url: "/acw/client/java/path/findList",
        type: "get",
    },
    clientJavaPathFindOne: {
        url: "/acw/client/java/path/findOne",
        type: "get",
    },

    clientFindDefaultSchema: {
        url: "//current/project/dataSource/findDefaultSchema",
        type: "get",
    },

    clientFindSchemaList: {
        url: "//current/project/dataSource/findSchemaList",
        type: "get",
    },
    clientFindTableList: {
        url: "//current/project/dataSource/findTableList",
        type: "get",
    },
    clientFindColumnList: {
        url: "//current/project/dataSource/findColumnList",
        type: "get",
    },
    databaseTableColumnAdd: {
        url: "/database/table/column/save",
        type: "post",
    },
    databaseTableColumnPage: {
        url: "/database/table/column/retrieve/page",
        type: "get",
    },
    databaseTablesColumnList: {
        url: "/database/table/column/tableIds/{{instanceId}}/{{schemaName}}/{{tableNameList}}",
        type: "getUrl",
    },
    databaseTableColumnList: {
        url: "/database/table/column/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    findDatabaseTableColumnList: {
        url: "/database/table/column/findColumn/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    findInstanceSchemaColumnList: {
        url: "/database/table/column/findInstanceSchemaColumnList/{{instanceId}}/{{schemaName}}",
        type: "getUrl",
    },
    databaseTableColumnEdit: {
        url: "/database/table/column/update",
        type: "put",
    },
    databaseTableColumnDelete: {
        url: "/database/table/column/{{id}}",
        type: "deleteUrl",
    },
    playMusicInsertOrUpdate: {
        url: "/play/music/insertOrUpdate",
        type: "post",
    },
    playMusicPage: {
        url: "/play/music/retrieve/page",
        type: "get",
    },
    playMusicList: {
        url: "/play/music/retrieve",
        type: "get",
    },
    playMusicEdit: {
        url: "/play/music/update",
        type: "put",
    },
    playMusicDelete: {
        url: "/play/music/{{id}}",
        type: "deleteUrl",
    },
    playMusicFindData: {
        url: "/play/music/retrieve/data/{{id}}",
        type: "getUrl",
    },
    playFileAdd: {
        url: "/play/file/add",
        type: "post",
    },
    playFilePage: {
        url: "/play/file/retrieve/page",
        type: "get",
    },
    playFileList: {
        url: "/play/file/retrieve",
        type: "get",
    },
    playFileData: {
        url: "/play/file/retrieve/data/{{id}}",
        type: "getUrl",
    },
    playFileEdit: {
        url: "/play/file/update",
        type: "put",
    },
    playFileDelete: {
        url: "/play/file/{{id}}",
        type: "deleteUrl",
    },
    playFileResourceList: {
        url: "/play/file/getFileResourceList",
        type: "get",
    },
    downLocalFile: {
        url: "/play/file/retrieve/local/file",
        type: "get",
    },
    docPdfMerge: {
        url: "/doc/pdf/merge/merge",
        type: "post",
    },
    docJson2Excel: {
        url: "/doc/excel/json2Excel",
        type: "post",
    },
    sqlConsole: {
        url: "/console/execute/sql/statement",
        type: "get",
    },
    sqlConsoleExport: {
        url: "/console/execute/sql/statement/export",
        type: "get",
    },
    sqlConsoleUpsertExport: {
        url: "/console/execute/upsert/sql/statement/export",
        type: "get",
    },
    tableSqlConsole: {
        url: "/table/console/execute/table/sql/statement",
        type: "post",
    },
    tableColumnSqlConsole: {
        url: "/table/console/execute/table/sql/statement/column",
        type: "post",
    },
    tableRowStory: {
        url: "/table/console/execute/upsert/table/sql",
        type: "post",
    },
    tableRowDelete: {
        url: "/table/console/execute/delete/table/sql",
        type: "post",
    },
    tableRowBatchDelete: {
        url: "/table/console/execute/batch/delete/table/sql",
        type: "post",
    },
    tableSqlConsoleExport: {
        url: "/table/console/execute/table/sql/statement/export",
        type: "post",
    },
    tableSqlConsoleUpsertExport: {
        url: "/table/console/execute/upsert/table/sql/statement/export",
        type: "post",
    },
    tableSqlConsoleMdExport: {
        url: "/table/console/execute/md/table/sql/statement/export",
        type: "post",
    },
    translate: {
        url: "/translate",
        type: "get",
    },
    shortcutsData: {
        url: "/shortcuts/data",
        type: "get",
    },
    jvmRunTimeMemory: {
        url: "/jvm/runTimeMemory",
        type: "get",
    },
    jvmGC: {
        url: "/jvm/gc",
        type: "get",
    },
    jvmCPU: {
        url: "/jvm/cpu",
        type: "get",
    },
    //     自动化开始
    automationStory: {
        url: "/lazy/automation/story",
        type: "post",
    },
    automationFindPage: {
        url: "/lazy/automation/findPage",
        type: "get",
    },
    automationFindOne: {
        url: "/lazy/automation/findOne",
        type: "get",
    },
    automationRemove: {
        url: "/lazy/automation/remove",
        type: "delete",
    },

    automationNodeStory: {
        url: "/lazy/automation/node/story",
        type: "post",
    },
    automationNodeRemove: {
        url: "/lazy/automation/node/remove",
        type: "delete",
    },

    automationNodeHttpActionStory: {
        url: "/lazy/automation/node/http/action/story",
        type: "post",
    },
    automationNodeHttpActionRemove: {
        url: "/lazy/automation/node/http/action/remove",
        type: "delete",
    },

    ttsTimbreStory: {
        url: "/tts/timbre/story",
        type: "post",
    },
    ttsTimbreFindPage: {
        url: "/tts/timbre/findPage",
        type: "get",
    },
    ttsTimbreFindList: {
        url: "/tts/timbre/findList",
        type: "get",
    },
    ttsTimbreUpdateOne: {
        url: "/tts/timbre/updateOne",
        type: "put",
    },
    ttsTimbreRemove: {
        url: "/tts/timbre/remove",
        type: "delete",
    },

    ttsChineseCharactersStory: {
        url: "/tts/chinese/characters/story",
        type: "post",
    },
    ttsChineseCharactersFindPage: {
        url: "/tts/chinese/characters/findPage",
        type: "get",
    },
    ttsChineseCharactersFindList: {
        url: "/tts/chinese/characters/findList",
        type: "get",
    },
    ttsChineseCharactersUpdateOne: {
        url: "/tts/chinese/characters/updateOne",
        type: "put",
    },
    ttsChineseCharactersRemove: {
        url: "/tts/chinese/characters/remove",
        type: "delete",
    },

    ttsChineseCharactersTimbreStory: {
        url: "/tts/chinese/characters/timbre/story",
        type: "post",
    },
    ttsChineseCharactersTimbreFindPage: {
        url: "/tts/chinese/characters/timbre/findPage",
        type: "get",
    },
    ttsChineseCharactersTimbreFindList: {
        url: "/tts/chinese/characters/timbre/findList",
        type: "get",
    },
    ttsChineseCharactersTimbreUpdateOne: {
        url: "/tts/chinese/characters/timbre/updateOne",
        type: "put",
    },
    ttsChineseCharactersTimbreRemove: {
        url: "/tts/chinese/characters/timbre/remove",
        type: "delete",
    },
    ttsChineseCharactersTimbreTextToBytes: {
        url: "/tts/chinese/characters/timbre/textToBytes",
        type: "get",
    },
    automationNodeHttpActionExecuteOne: {
        url: "/lazy/automation/node/http/action/executeOne/{{actionId}}",
        type: "patchUrl",
    },
};
