module.exports = {
    cloudClientFindPage: {
        url: "/acw-server/netty/client/state/findPage",
        type: "get",
    },
    cloudClientFindList: {
        url: "/acw-server/netty/client/state/findList",
        type: "get",
    },
    cloudClientDelete: {
        url: "/acw-server/netty/client/state/remove",
        type: "delete",
    },
    visitorPage: {
        url: "/acw-server/netty/server/visitor/findPage",
        type: "get",
    },
    visitorList: {
        url: "/acw-server/netty/server/visitor/findList",
        type: "get",
    },
    visitorDelete: {
        url: "/acw-server/netty/server/visitor/remove",
        type: "delete",
    },
    visitorSave: {
        url: "/acw-server/netty/server/visitor/story",
        type: "post",
    },
    visitorUpdate: {
        url: "/acw-server/netty/server/visitor/updateOne",
        type: "put",
    },

    networkMappingPage: {
        url: "/acw-server/internal/network/penetration/mapping/findPage",
        type: "get",
    },
    networkMappingDelete: {
        url: "/acw-server/internal/network/penetration/mapping/remove",
        type: "delete",
    },
    networkMappingSave: {
        url: "/acw-server/internal/network/penetration/mapping/story",
        type: "post",
    },
    networkMappingUpdate: {
        url: "/acw-server/internal/network/penetration/mapping/updateOne",
        type: "put",
    },
};
