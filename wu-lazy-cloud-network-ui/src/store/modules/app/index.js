import {
    TOGGLE_SLIDER,
    SET_TOKEN,
    SET_UNAME,
    SET_MENU_LIST,
    SET_PERMISSION_LIST,
    SET_REFRESH_TOKEN,
    SET_ACCESS_TOKEN,
    SET_ZONE_ID,
} from "./type.js";
export default {
    namespaced: true,
    state: {
        slider: {
            opened: JSON.parse(sessionStorage.getItem("opened")),
        },
        token: sessionStorage.getItem("token") || "",
        access_token: sessionStorage.getItem("access_token") || "",
        refresh_token: sessionStorage.getItem("refresh_token") || "",
        zone_id: sessionStorage.getItem("zone_id") || "",
        menuList: null,
        permissionList: [],
        uname: sessionStorage.getItem("uname") || "",
    },
    mutations: {
        [TOGGLE_SLIDER](state) {
            state.slider.opened = !state.slider.opened;
            sessionStorage.setItem(
                "opened",
                JSON.stringify(state.slider.opened),
            );
        },
        [SET_TOKEN](state, token) {
            state.token = token;
            sessionStorage.setItem("token", state.token);
        },
        [SET_UNAME](state, uname) {
            state.uname = uname;
            sessionStorage.setItem("uname", state.uname);
        },
        [SET_ACCESS_TOKEN](state, access_token) {
            state.access_token = access_token;
            sessionStorage.setItem("access_token", state.access_token);
        },
        [SET_REFRESH_TOKEN](state, refresh_token) {
            state.refresh_token = refresh_token;
            sessionStorage.setItem("refresh_token", state.refresh_token);
        },
        [SET_ZONE_ID](state, zone_id) {
            state.zone_id = zone_id;
            sessionStorage.setItem("zone_id", state.zone_id);
        },
        [SET_MENU_LIST](state, menuList) {
            state.menuList = menuList;
        },
        [SET_PERMISSION_LIST](state, permissionList) {
            state.permissionList = permissionList;
        },
    },
    actions: {
        [TOGGLE_SLIDER]({ commit }) {
            commit(TOGGLE_SLIDER);
        },
        [SET_TOKEN]({ commit }, token) {
            commit(SET_TOKEN, token);
        },
        [SET_ACCESS_TOKEN]({ commit }, access_token) {
            commit(SET_ACCESS_TOKEN, access_token);
        },
        [SET_REFRESH_TOKEN]({ commit }, refresh_token) {
            commit(SET_REFRESH_TOKEN, refresh_token);
        },
        [SET_ZONE_ID]({ commit }, zone_id) {
            commit(SET_ZONE_ID, zone_id);
        },
        [SET_UNAME]({ commit }, uname) {
            commit(SET_UNAME, uname);
        },
        [SET_MENU_LIST]({ commit }, menuList) {
            commit(SET_MENU_LIST, menuList);
        },
        [SET_PERMISSION_LIST]({ commit }, menuList) {
            let allMenus = XE.filterTree(menuList, (item) => item.type === 1, {
                sortKey: "sort",
                key: "code",
                parentKey: "parentCode",
            });
            let permissionList = [];
            allMenus.forEach((item) => {
                if (item.children && item.children.length > 0) {
                    item.children.forEach((menu) => {
                        permissionList.push(
                            `${item.url}/${item.code}/${menu.menu}`,
                        );
                    });
                }
            });
            commit(SET_PERMISSION_LIST, permissionList);
        },
    },
};
