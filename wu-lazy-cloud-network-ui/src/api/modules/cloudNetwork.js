module.exports = {
    cloudClientFindPage: {
        url: "/netty/client/state/findPage",
        type: "get",
    },
    cloudClientFindList: {
        url: "/netty/client/state/findList",
        type: "get",
    },
    cloudClientDelete: {
        url: "/netty/client/state/remove",
        type: "delete",
    },
    visitorPage: {
        url: "/netty/server/visitor/findPage",
        type: "get",
    },
    visitorList: {
        url: "/netty/server/visitor/findList",
        type: "get",
    },
    visitorDelete: {
        url: "/netty/server/visitor/remove",
        type: "delete",
    },
    visitorSave: {
        url: "/netty/server/visitor/story",
        type: "post",
    },
    visitorUpdate: {
        url: "/netty/server/visitor/updateOne",
        type: "put",
    },

    networkMappingPage: {
        url: "/internal/network/penetration/mapping/findPage",
        type: "get",
    },
    networkMappingDelete: {
        url: "/internal/network/penetration/mapping/remove",
        type: "delete",
    },
    networkMappingSave: {
        url: "/internal/network/penetration/mapping/story",
        type: "post",
    },
    networkMappingUpdate: {
        url: "/internal/network/penetration/mapping/updateOne",
        type: "put",
    },
};
