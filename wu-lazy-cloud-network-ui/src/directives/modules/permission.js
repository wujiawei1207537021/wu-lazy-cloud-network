const permission = (el, binding, app, router, store) => {
    const { value } = binding;
    function checkArray(permission) {
        let path = app.config.globalProperties.$route.name;

        let _permission = permission.map((element) => {
            let url = path.replace(/-/g, "/") + "/" + element;
            return url;
        });
        let arr = store.getters.permissionList;
        return _permission.some((key) => arr.includes(key));
    }

    if (value && value.length > 0) {
        let hasPermission = checkArray(value);
        if (!hasPermission) {
            // 没有权限 移除Dom元素
            el.parentNode && el.parentNode.removeChild(el);
        }
    }
};

export default permission;
