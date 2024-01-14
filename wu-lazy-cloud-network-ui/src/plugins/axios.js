/* eslint-disable indent */
"use strict";

import axios from "axios";
// import Qs from "qs";
import NProgress from "nprogress";
import { SET_TOKEN } from "@/store/modules/app/type";

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
const install = (app, { router, store, opt }) => {
    let config = {
        Global: true,
        // baseURL: process.env.baseURL || process.env.apiUrl || ""
        // timeout: 60 * 1000, // Timeout
        // withCredentials: true, // Check cross-site Access-Control
        // {"userName":"Administrator","pwd":"123456"}
        // userName=Administrator&pwd=123456
    };
    // if (VE_ENV.MODE === "production") {
    //     console.log("VE_ENV.MODE:" + VE_ENV.MODE);
    //     config.transformRequest = [(data) => Qs.parse(data)];
    // }

    const _axios = axios.create(config);
    let ve_loading;
    let ve_message = null;
    let loadingCount = 0;
    // 请求拦截
    _axios.interceptors.request.use(
        (config) => {
            NProgress.done();
            if (config.Global) {
                NProgress.start();
                ve_loading = app.config.globalProperties.$loading({
                    lock: true,
                    text: "Loading",
                    spinner: "el-icon-loading",
                    background: "rgba(0,0,0,0.1)",
                });
            }
            loadingCount++;
            //*请求头添加token
            const token = store.getters.token;
            const access_token = store.getters.access_token;
            const refresh_token = store.getters.refresh_token;
            const zone_id = store.getters.zone_id;
            // debugger;
            token && (config.headers.Authorization = token);

            if (access_token) {
                config.headers.access_token = access_token;
            }

            if (refresh_token) {
                config.headers.refresh_token = refresh_token;
            }
            if (zone_id) {
                config.headers.zone_id = zone_id;
            }
            // Do something before request is sent
            return config;
        },
        (error) => {
            // Do something with request error
            return Promise.reject(error);
        },
    );

    // Add a response interceptor
    // 响应拦截
    _axios.interceptors.response.use(
        (response) => {
            // TODO 根据响应头更新token
            store.dispatch(`app/${SET_TOKEN}`, new Date().getTime());
            loadingCount--;
            if (loadingCount <= 0) {
                NProgress.done();
                ve_loading.close();
            }
            // eslint-disable-next-line no-unused-vars
            let contentType = response.headers["content-type"];
            if (
                "application/octet-stream" === contentType &&
                response.status === 200
            ) {
                return response;
            }
            let type = "success";
            if (response.data.code !== 0) {
                type = "error";
            }
            if (ve_message) {
                ve_message.close();
                ve_message = null;
            }
            ve_message = app.config.globalProperties.$message({
                type,
                message: response.data.message,
            });
            // Do something with response data
            return response.data;
        },
        (error) => {
            loadingCount--;
            if (loadingCount <= 0) {
                NProgress.done();
                ve_loading.close();
            }
            if (error && error.response) {
                let message = "";
                switch (error.response.status) {
                    case 400:
                        message = "请求错误";
                        break;
                    case 401: {
                        message = "未授权，请登录";
                        router.replace({
                            name: "Login",
                        });
                        break;
                    }
                    case 403:
                        message = "没有权限，拒绝访问";
                        break;
                    case 404:
                        message = `请求地址出错`;
                        break;
                    case 408:
                        message = "请求超时";
                        break;
                    case 500:
                        message = "服务器内部错误";
                        break;
                    case 501:
                        message = "服务未实现";
                        break;
                    case 502:
                        message = "网关错误";
                        break;
                    case 503:
                        message = "服务不可用";
                        break;
                    case 504:
                        message = "网关超时";
                        break;
                    case 505:
                        message = "HTTP版本不受支持";
                        break;
                    default:
                        break;
                }
                if (ve_message) {
                    ve_message.close();
                    ve_message = null;
                }
                ve_message = app.config.globalProperties.$message({
                    message,
                    type: "error",
                });
            }
            // Do something with response error
            return Promise.reject(error);
        },
    );

    /**
     * 字符串替换
     *
     *
     *
     var data = {
		name:"张三",
		age:23,
		sex:"男"
	}
     var text = "我叫{{name}},我今年{{age}}岁,我的性别是{{sex}}!".format(data);
     console.log(text);
     *
     *
     *
     * @returns {String}
     */
    String.prototype.format = function () {
        if (arguments.length === 0) return this;
        var obj = arguments[0];
        var s = this;
        for (var key in obj) {
            s = s.replace(new RegExp("\\{\\{" + key + "\\}\\}", "g"), obj[key]);
        }
        return s;
    };

    const method = {
        post: (url, p, config) => _axios.post(url, p, config),
        put: (url, p, config) => _axios.put(url, p, config),
        getUrl: (url, p, config) =>
            _axios.get(url.format(p), { params: p, data: p }, config),
        get: (url, p, config) =>
            _axios.get(url, Object.assign(config, { params: p })),
        delete: (url, p, config) =>
            _axios.delete(url.format(p), { params: p, data: p }, config),
        deleteUrl: (url, p, config) =>
            _axios.delete(url.format(p), { params: p, data: p }, config),
        deleteBody: (url, p, config) => _axios.delete(url, { data: p }, config),
        // patch: (url, p, config) =>
        //     _axios.patch(url, Object.assign(config, { params: p })),
        patch: (url, p, config) => _axios.patch(url, p, config),
        patchUrl: (url, p, config) =>
            _axios.patch(url.format(p), { params: p, data: p }, config),
    };

    let api = {};
    const files = require.context("@/api/modules", false, /\.js$/);
    files.keys().forEach((key) => {
        const fileName = key.replace(/(\.\/|\.js)/g, "");
        api[fileName] = {};
        let obj = files(key);
        Object.keys(obj).forEach((item) => {
            api[fileName][item] = (p, config = {}) =>
                method[obj[item].type](obj[item].url, p, config);
        });
    });

    window[opt] = api;
    app.config.globalProperties[opt] = api;
};

export default { install };
