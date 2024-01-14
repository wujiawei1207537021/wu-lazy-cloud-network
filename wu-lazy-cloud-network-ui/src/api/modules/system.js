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
        url: "/acw-server/sys/user/retrieve",
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
        url: "/acw-server/sys/user/export/List",
        type: "get",
    },
    userPage: {
        url: "/acw-server/sys/user/findPage",
        type: "get",
    },
    userAdd: {
        url: "/acw-server/sys/user/story",
        type: "post",
    },
    userEdit: {
        url: "/acw-server/sys/user/updateOne",
        type: "put",
    },
    userDel: {
        url: "/acw-server/sys/user/{{id}}",
        type: "deleteUrl",
    },
    userStatus: {
        url: "/acw-server/sys/user/status",
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
        url: "/acw-server/menu/findList",
        type: "get",
    },
    menuAdd: {
        url: "/acw-server/menu/story",
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
        url: "/acw-server/menu/update",
        type: "put",
    },
    menuUpsert: {
        url: "/acw-server/menu/upsert",
        type: "post",
    },
    menuDel: {
        url: "/acw-server/menu/delete",
        type: "delete",
    },

    roleList: {
        url: "/acw-server/role/retrieve",
        type: "get",
    },
    roleOne: {
        url: "/acw-server/role/findOne",
        type: "get",
    },
    rolePage: {
        url: "/acw-server/role/retrieve/page",
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
        url: "/acw-server/role/story",
        type: "post",
    },
    roleEdit: {
        url: "/acw-server/role/updateOne",
        type: "put",
    },
    roleDel: {
        url: "/acw-server/role/{{id}}",
        type: "deleteUrl",
    },

    userMenuList: {
        url: "/acw-server/sys/user/findUserMenuList",
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
        url: "/acw-server/token/access_token",
        type: "post",
    },
    resolvingUser: {
        url: "/acw-server/token/user/{{accessToken}}",
        type: "getUrl",
    },
    register: {
        url: "/acw-server/token/create_user",
        type: "post",
    },
    dictionaryPage: {
        url: "/acw-server/dictionary/retrieve/page",
        type: "get",
    },
    dictionaryDelete: {
        url: "/acw-server/dictionary/{{id}}",
        type: "deleteUrl",
    },
    dictionaryAdd: {
        url: "/acw-server/dictionary/save",
        type: "post",
    },
    dictionaryEdit: {
        url: "/acw-server/dictionary/update",
        type: "put",
    },
    dictionaryDataPage: {
        url: "/acw-server/dictionary/data/retrieve/page",
        type: "get",
    },
    dictionaryDataList: {
        url: "/acw-server/dictionary/data/retrieve",
        type: "get",
    },
    dictionaryDataDelete: {
        url: "/acw-server/dictionary/data/{{id}}",
        type: "deleteUrl",
    },
    dictionaryDataAdd: {
        url: "/acw-server/dictionary/data/save",
        type: "post",
    },
    dictionaryDataEdit: {
        url: "/acw-server/dictionary/data/update",
        type: "put",
    },

    upsertAdd: {
        url: "/acw-server/upsert/task/save",
        type: "post",
    },
    upsertPage: {
        url: "/acw-server/upsert/task/retrieve/page",
        type: "get",
    },
    upsertEdit: {
        url: "/acw-server/upsert/task/update",
        type: "put",
    },
    upsertDelete: {
        url: "/acw-server/upsert/task/{{id}}",
        type: "deleteUrl",
    },
    databaseInstanceAdd: {
        url: "/acw-server/database/instance/save",
        type: "post",
    },
    databaseInstanceTest: {
        url: "/acw-server/database/instance/testConnection",
        type: "post",
    },
    databaseInstancePage: {
        url: "/acw-server/database/instance/retrieve/page",
        type: "get",
    },
    databaseInstanceList: {
        url: "/acw-server/database/instance/retrieve",
        type: "get",
    },
    databaseInstanceEdit: {
        url: "/acw-server/database/instance/update",
        type: "put",
    },
    databaseInstanceDelete: {
        url: "/acw-server/database/instance/{{id}}",
        type: "deleteUrl",
    },
    databaseInstanceReload: {
        url: "/acw-server/database/instance/reload/{{id}}",
        type: "patchUrl",
    },

    acwTableAssociationRelationPage: {
        url: "/acw-server/lazy/acw/table/association/relation/findPage",
        type: "get",
    },
    acwTableAssociationRelationList: {
        url: "/acw-server/lazy/acw/table/association/relation/findList",
        type: "get",
    },
    acwTableAssociationRelationStory: {
        url: "/acw-server/lazy/acw/table/association/relation/story",
        type: "post",
    },
    acwTableAssociationRelationDelete: {
        url: "/acw-server/lazy/acw/table/association/relation/remove",
        type: "deleteUrl",
    },
    acwTableAssociationRelationReload: {
        url: "/acw-server/lazy/acw/table/association/relation/reload/{{id}}",
        type: "patchUrl",
    },
    acwTableAssociationRelationAnalysisSchema: {
        url: "/acw-server/lazy/acw/table/association/relation/analysisSchema",
        type: "put",
    },
    // 数据库备份API 开始
    databaseInstanceBackUpPage: {
        url: "/acw-server//lazy/database/instance/back/ups/findPage",
        type: "get",
    },
    databaseInstanceBackUpDelete: {
        url: "/acw-server//lazy/database/instance/back/ups/remove",
        type: "delete",
    },
    databaseInstanceBackUp: {
        url: "/acw-server//lazy/database/instance/back/ups/backUps",
        type: "patch",
    },
    databaseSchemaBackUpPage: {
        url: "/acw-server//lazy/database/schema/back/ups/findPage",
        type: "get",
    },
    databaseSchemaBackUp: {
        url: "/acw-server//lazy/database/schema/back/ups/backUps",
        type: "patch",
    },
    databaseSchemaBackUpRemove: {
        url: "/acw-server//lazy/database/schema/back/ups/remove",
        type: "delete",
    },
    // 数据库备份API 结束
    // redis api实例 开始
    redisInstancePage: {
        url: "/acw-server/lazy/acw/redis/instance/findPage",
        type: "get",
    },
    redisInstanceDelete: {
        url: "/acw-server/lazy/acw/redis/instance/remove",
        type: "delete",
    },
    redisInstanceStory: {
        url: "/acw-server/lazy/acw/redis/instance/story",
        type: "post",
    },
    redisInstanceTest: {
        url: "/acw-server/lazy/acw/redis/instance/test",
        type: "post",
    },
    redisInstanceConsoleFindDataBases: {
        url: "/acw-server/lazy/acw/redis/console/findDataBases",
        type: "get",
    },
    redisInstanceConsoleFindKeys: {
        url: "/acw-server/lazy/acw/redis/console/findKeys",
        type: "get",
    },

    redisInstanceConsoleFindKeysValues: {
        url: "/acw-server/lazy/acw/redis/console/findKeysValues",
        type: "get",
    },
    redisInstanceConsoleFindKeyValue: {
        url: "/acw-server/lazy/acw/redis/console/findKeyValue",
        type: "get",
    },
    redisInstanceConsoleSetKey: {
        url: "/acw-server/lazy/acw/redis/console/setKey",
        type: "post",
    },
    redisInstanceConsoleRemoveKey: {
        url: "/acw-server/lazy/acw/redis/console/removeKey",
        type: "put",
    },
    // redis api实例 结束
    schemaAdd: {
        url: "/acw-server/schema/save",
        type: "post",
    },
    schemaPage: {
        url: "/acw-server/schema/retrieve/page",
        type: "get",
    },
    schemaList: {
        url: "/acw-server/schema/findList",
        type: "get",
    },
    schemaEdit: {
        url: "/acw-server/schema/update",
        type: "put",
    },
    schemaDelete: {
        url: "/acw-server/schema/{{id}}",
        type: "deleteUrl",
    },
    batchDeleteInstanceSchema: {
        url: "/acw-server/schema/delete/batchDeleteInstanceSchema",
        type: "deleteBody",
    },
    batchExportSchemaData: {
        url: "/acw-server/schema/batchExportSchemaData",
        type: "post",
    },
    schemaReload: {
        url: "/acw-server/schema/reload/{{instanceId}}/{{schema}}",
        type: "patchUrl",
    },
    aceSchemaImportData: {
        url: "/acw-server/schema/batchImportSchemaData",
        type: "post",
    },
    schemaDeriveView: {
        url: "/acw-server/schema/schemaDeriveView",
        type: "post",
    },
    tableAutoStuffedRecordAdd: {
        url: "/acw-server/database/table/auto/stuffed/record/save",
        type: "post",
    },
    tableAutoStuffedRecordBatchAdd: {
        url: "/acw-server/database/table/auto/stuffed/record/batchSave",
        type: "post",
    },
    tableAutoStuffedRecordPage: {
        url: "/acw-server/database/table/auto/stuffed/record/findPage",
        type: "get",
    },
    tableAutoStuffedRecordList: {
        url: "/acw-server/database/table/auto/stuffed/record/findList",
        type: "get",
    },
    tableAutoStuffedRecordEdit: {
        url: "/acw-server/database/table/auto/stuffed/record/update",
        type: "put",
    },
    tableAutoStuffedRecordDelete: {
        url: "/acw-server/database/table/auto/stuffed/record/{{id}}",
        type: "deleteUrl",
    },
    tableAutoStuffedRecordBatchDelete: {
        url: "/acw-server/database/table/auto/stuffed/record/batch",
        type: "deleteBody",
    },
    tableAdd: {
        url: "/acw-server/table/storage",
        type: "post",
    },
    tablePage: {
        url: "/acw-server/table/retrieve/page",
        type: "get",
    },
    tableList: {
        url: "/acw-server/table/retrieve",
        type: "get",
    },
    tableDelete: {
        url: "/acw-server/table/delete/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "deleteUrl",
    },
    batchDeleteTableDelete: {
        url: "/acw-server/table/batchDelete",
        type: "deleteBody",
    },
    slqAuditPage: {
        url: "/acw-server/sql/audit/findPage",
        type: "get",
    },
    slqAuditExport: {
        url: "/acw-server/sql/audit/export/findList",
        type: "get",
    },
    batchDeleteSlqAudit: {
        url: "/acw-server/sql/audit/batch",
        type: "deleteBody",
    },
    generateJavaModel: {
        url: "/acw-server/table/generate/java/model/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    generateLocalJava: {
        url: "/acw-server/table/generate/local/java",
        type: "post",
    },
    clientGenerateLocalJava: {
        url: "/acw-server/acw/client/java/path/generate/local/java",
        type: "post",
    },
    tableExportInsertSql: {
        url: "/acw-server/table/export/insert/sql",
        type: "get",
    },
    tableExportUpsertSql: {
        url: "/acw-server/table/export/upsert/sql",
        type: "get",
    },
    exportTableStructureExcel: {
        url: "/acw-server/table/export/table/structure/excel",
        type: "get",
    },
    exportTableStructureSql: {
        url: "/acw-server/table/export/table/structure/sql",
        type: "get",
    },
    applicationAdd: {
        url: "/acw-server/application/save",
        type: "post",
    },
    applicationPage: {
        url: "/acw-server/application/retrieve/page",
        type: "get",
    },
    applicationList: {
        url: "/acw-server/application/retrieve",
        type: "get",
    },
    applicationEdit: {
        url: "/acw-server/application/update",
        type: "put",
    },
    applicationDelete: {
        url: "/acw-server/application/{{id}}",
        type: "deleteUrl",
    },
    applicationFindTables: {
        url: "/acw-server/application/findTables/{{applicationId}}",
        type: "getUrl",
    },
    projectAdd: {
        url: "/acw-server/project/save",
        type: "post",
    },
    projectPage: {
        url: "/acw-server/project/retrieve/page",
        type: "get",
    },
    projectList: {
        url: "/acw-server/project/retrieve",
        type: "get",
    },
    projectEdit: {
        url: "/acw-server/project/update",
        type: "put",
    },
    projectDelete: {
        url: "/acw-server/project/{{id}}",
        type: "deleteUrl",
    },
    interfaceAdd: {
        url: "/acw-server/interface/save",
        type: "post",
    },
    interfacePage: {
        url: "/acw-server/interface/retrieve/page",
        type: "get",
    },
    interfaceList: {
        url: "/acw-server/interface/retrieve",
        type: "get",
    },
    interfaceEdit: {
        url: "/acw-server/interface/update",
        type: "put",
    },
    interfaceDelete: {
        url: "/acw-server/interface/{{id}}",
        type: "deleteUrl",
    },
    apiAdd: {
        url: "/acw-server/api/save",
        type: "post",
    },
    apiPage: {
        url: "/acw-server/api/retrieve/page",
        type: "get",
    },
    apiList: {
        url: "/acw-server/api/retrieve",
        type: "get",
    },
    apiEdit: {
        url: "/acw-server/api/update",
        type: "put",
    },
    apiDelete: {
        url: "/acw-server/api/{{id}}",
        type: "deleteUrl",
    },
    clientApiAdd: {
        url: "/acw-server/lazy/interface_/info/story",
        type: "post",
    },
    clientApiSQLAdd: {
        url: "/acw-server/lazy/interface_/info/story-sql",
        type: "post",
    },
    clientApiPage: {
        url: "/acw-server/lazy/interface_/info/findPage",
        type: "get",
    },
    clientApiList: {
        url: "/acw-server/lazy/interface_/info/findList",
        type: "get",
    },
    clientApiFindOne: {
        url: "/acw-server/lazy/interface_/info/findOne",
        type: "get",
    },
    clientApiEdit: {
        url: "/acw-server/lazy/interface_/info/updateOne",
        type: "put",
    },
    clientApiDelete: {
        url: "/acw-server/lazy/interface_/info/remove",
        type: "deleteUrl",
    },
    clientApiDerivativeCode: {
        url: "/acw-server/lazy/interface_/info/derivativeCode",
        type: "put",
    },

    clientInstancePage: {
        url: "/acw-server/acw/client/instance/findPage",
        type: "get",
    },
    clientInstanceList: {
        url: "/acw-server/acw/client/instance/findList",
        type: "get",
    },
    clientInstanceFindOne: {
        url: "/acw-server/acw/client/instance/findOne",
        type: "get",
    },

    clientJavaPathPage: {
        url: "/acw-server/acw/client/java/path/findPage",
        type: "get",
    },
    clientJavaPathList: {
        url: "/acw-server/acw/client/java/path/findList",
        type: "get",
    },
    clientJavaPathFindOne: {
        url: "/acw-server/acw/client/java/path/findOne",
        type: "get",
    },

    clientFindDefaultSchema: {
        url: "/acw-server//current/project/dataSource/findDefaultSchema",
        type: "get",
    },

    clientFindSchemaList: {
        url: "/acw-server//current/project/dataSource/findSchemaList",
        type: "get",
    },
    clientFindTableList: {
        url: "/acw-server//current/project/dataSource/findTableList",
        type: "get",
    },
    clientFindColumnList: {
        url: "/acw-server//current/project/dataSource/findColumnList",
        type: "get",
    },
    databaseTableColumnAdd: {
        url: "/acw-server/database/table/column/save",
        type: "post",
    },
    databaseTableColumnPage: {
        url: "/acw-server/database/table/column/retrieve/page",
        type: "get",
    },
    databaseTablesColumnList: {
        url: "/acw-server/database/table/column/tableIds/{{instanceId}}/{{schemaName}}/{{tableNameList}}",
        type: "getUrl",
    },
    databaseTableColumnList: {
        url: "/acw-server/database/table/column/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    findDatabaseTableColumnList: {
        url: "/acw-server/database/table/column/findColumn/{{instanceId}}/{{schemaName}}/{{tableName}}",
        type: "getUrl",
    },
    findInstanceSchemaColumnList: {
        url: "/acw-server/database/table/column/findInstanceSchemaColumnList/{{instanceId}}/{{schemaName}}",
        type: "getUrl",
    },
    databaseTableColumnEdit: {
        url: "/acw-server/database/table/column/update",
        type: "put",
    },
    databaseTableColumnDelete: {
        url: "/acw-server/database/table/column/{{id}}",
        type: "deleteUrl",
    },
    playMusicInsertOrUpdate: {
        url: "/acw-server/play/music/insertOrUpdate",
        type: "post",
    },
    playMusicPage: {
        url: "/acw-server/play/music/retrieve/page",
        type: "get",
    },
    playMusicList: {
        url: "/acw-server/play/music/retrieve",
        type: "get",
    },
    playMusicEdit: {
        url: "/acw-server/play/music/update",
        type: "put",
    },
    playMusicDelete: {
        url: "/acw-server/play/music/{{id}}",
        type: "deleteUrl",
    },
    playMusicFindData: {
        url: "/acw-server/play/music/retrieve/data/{{id}}",
        type: "getUrl",
    },
    playFileAdd: {
        url: "/acw-server/play/file/add",
        type: "post",
    },
    playFilePage: {
        url: "/acw-server/play/file/retrieve/page",
        type: "get",
    },
    playFileList: {
        url: "/acw-server/play/file/retrieve",
        type: "get",
    },
    playFileData: {
        url: "/acw-server/play/file/retrieve/data/{{id}}",
        type: "getUrl",
    },
    playFileEdit: {
        url: "/acw-server/play/file/update",
        type: "put",
    },
    playFileDelete: {
        url: "/acw-server/play/file/{{id}}",
        type: "deleteUrl",
    },
    playFileResourceList: {
        url: "/acw-server/play/file/getFileResourceList",
        type: "get",
    },
    downLocalFile: {
        url: "/acw-server/play/file/retrieve/local/file",
        type: "get",
    },
    docPdfMerge: {
        url: "/acw-server/doc/pdf/merge/merge",
        type: "post",
    },
    docJson2Excel: {
        url: "/acw-server/doc/excel/json2Excel",
        type: "post",
    },
    sqlConsole: {
        url: "/acw-server/console/execute/sql/statement",
        type: "get",
    },
    sqlConsoleExport: {
        url: "/acw-server/console/execute/sql/statement/export",
        type: "get",
    },
    sqlConsoleUpsertExport: {
        url: "/acw-server/console/execute/upsert/sql/statement/export",
        type: "get",
    },
    tableSqlConsole: {
        url: "/acw-server/table/console/execute/table/sql/statement",
        type: "post",
    },
    tableColumnSqlConsole: {
        url: "/acw-server/table/console/execute/table/sql/statement/column",
        type: "post",
    },
    tableRowStory: {
        url: "/acw-server/table/console/execute/upsert/table/sql",
        type: "post",
    },
    tableRowDelete: {
        url: "/acw-server/table/console/execute/delete/table/sql",
        type: "post",
    },
    tableRowBatchDelete: {
        url: "/acw-server/table/console/execute/batch/delete/table/sql",
        type: "post",
    },
    tableSqlConsoleExport: {
        url: "/acw-server/table/console/execute/table/sql/statement/export",
        type: "post",
    },
    tableSqlConsoleUpsertExport: {
        url: "/acw-server/table/console/execute/upsert/table/sql/statement/export",
        type: "post",
    },
    tableSqlConsoleMdExport: {
        url: "/acw-server/table/console/execute/md/table/sql/statement/export",
        type: "post",
    },
    translate: {
        url: "/acw-server/translate",
        type: "get",
    },
    shortcutsData: {
        url: "/acw-server/shortcuts/data",
        type: "get",
    },
    jvmRunTimeMemory: {
        url: "/acw-server/jvm/runTimeMemory",
        type: "get",
    },
    jvmGC: {
        url: "/acw-server/jvm/gc",
        type: "get",
    },
    jvmCPU: {
        url: "/acw-server/jvm/cpu",
        type: "get",
    },
    //     自动化开始
    automationStory: {
        url: "/acw-server/lazy/automation/story",
        type: "post",
    },
    automationFindPage: {
        url: "/acw-server/lazy/automation/findPage",
        type: "get",
    },
    automationFindOne: {
        url: "/acw-server/lazy/automation/findOne",
        type: "get",
    },
    automationRemove: {
        url: "/acw-server/lazy/automation/remove",
        type: "delete",
    },

    automationNodeStory: {
        url: "/acw-server/lazy/automation/node/story",
        type: "post",
    },
    automationNodeRemove: {
        url: "/acw-server/lazy/automation/node/remove",
        type: "delete",
    },

    automationNodeHttpActionStory: {
        url: "/acw-server/lazy/automation/node/http/action/story",
        type: "post",
    },
    automationNodeHttpActionRemove: {
        url: "/acw-server/lazy/automation/node/http/action/remove",
        type: "delete",
    },

    ttsTimbreStory: {
        url: "/acw-server/tts/timbre/story",
        type: "post",
    },
    ttsTimbreFindPage: {
        url: "/acw-server/tts/timbre/findPage",
        type: "get",
    },
    ttsTimbreFindList: {
        url: "/acw-server/tts/timbre/findList",
        type: "get",
    },
    ttsTimbreUpdateOne: {
        url: "/acw-server/tts/timbre/updateOne",
        type: "put",
    },
    ttsTimbreRemove: {
        url: "/acw-server/tts/timbre/remove",
        type: "delete",
    },

    ttsChineseCharactersStory: {
        url: "/acw-server/tts/chinese/characters/story",
        type: "post",
    },
    ttsChineseCharactersFindPage: {
        url: "/acw-server/tts/chinese/characters/findPage",
        type: "get",
    },
    ttsChineseCharactersFindList: {
        url: "/acw-server/tts/chinese/characters/findList",
        type: "get",
    },
    ttsChineseCharactersUpdateOne: {
        url: "/acw-server/tts/chinese/characters/updateOne",
        type: "put",
    },
    ttsChineseCharactersRemove: {
        url: "/acw-server/tts/chinese/characters/remove",
        type: "delete",
    },

    ttsChineseCharactersTimbreStory: {
        url: "/acw-server/tts/chinese/characters/timbre/story",
        type: "post",
    },
    ttsChineseCharactersTimbreFindPage: {
        url: "/acw-server/tts/chinese/characters/timbre/findPage",
        type: "get",
    },
    ttsChineseCharactersTimbreFindList: {
        url: "/acw-server/tts/chinese/characters/timbre/findList",
        type: "get",
    },
    ttsChineseCharactersTimbreUpdateOne: {
        url: "/acw-server/tts/chinese/characters/timbre/updateOne",
        type: "put",
    },
    ttsChineseCharactersTimbreRemove: {
        url: "/acw-server/tts/chinese/characters/timbre/remove",
        type: "delete",
    },
    ttsChineseCharactersTimbreTextToBytes: {
        url: "/acw-server/tts/chinese/characters/timbre/textToBytes",
        type: "get",
    },
    automationNodeHttpActionExecuteOne: {
        url: "/acw-server/lazy/automation/node/http/action/executeOne/{{actionId}}",
        type: "patchUrl",
    },
};
