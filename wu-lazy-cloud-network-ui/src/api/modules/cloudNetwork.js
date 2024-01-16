module.exports = {
    cloudClientFindPage: {
        url: "/wu-lazy-cloud-network/netty/client/state/findPage",
        type: "get",
    },
    cloudClientFindList: {
        url: "/wu-lazy-cloud-network/netty/client/state/findList",
        type: "get",
    },
    cloudClientDelete: {
        url: "/wu-lazy-cloud-network/netty/client/state/remove",
        type: "delete",
    },
    visitorPage: {
        url: "/wu-lazy-cloud-network/netty/server/visitor/findPage",
        type: "get",
    },
    visitorList: {
        url: "/wu-lazy-cloud-network/netty/server/visitor/findList",
        type: "get",
    },
    visitorDelete: {
        url: "/wu-lazy-cloud-network/netty/server/visitor/remove",
        type: "delete",
    },
    visitorSave: {
        url: "/wu-lazy-cloud-network/netty/server/visitor/story",
        type: "post",
    },
    visitorUpdate: {
        url: "/wu-lazy-cloud-network/netty/server/visitor/updateOne",
        type: "put",
    },

    networkMappingPage: {
        url: "/wu-lazy-cloud-network/internal/network/penetration/mapping/findPage",
        type: "get",
    },
    networkMappingDelete: {
        url: "/wu-lazy-cloud-network/internal/network/penetration/mapping/remove",
        type: "delete",
    },
    networkMappingSave: {
        url: "/wu-lazy-cloud-network/internal/network/penetration/mapping/story",
        type: "post",
    },
    networkMappingUpdate: {
        url: "/wu-lazy-cloud-network/internal/network/penetration/mapping/updateOne",
        type: "put",
    },
};
