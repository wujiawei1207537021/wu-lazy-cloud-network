export default {
    opened: (state) => state.app.slider.opened,
    token: (state) => state.app.token,
    access_token: (state) => state.app.access_token,
    refresh_token: (state) => state.app.refresh_token,
    zone_id: (state) => state.app.zone_id,
    uname: (state) => state.app.uname,
    menuList: (state) => state.app.menuList,
    permissionList: (state) => state.app.permissionList,
};
