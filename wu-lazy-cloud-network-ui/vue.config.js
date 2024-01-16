const config = require("./src/config");
const webpack = require("webpack");
const TerserPlugin = require("terser-webpack-plugin");
const CompressionWebpackPlugin = require("compression-webpack-plugin");
let scssVariables = require("./src/styles/variables.scss.js");

module.exports = {
    publicPath: "",
    productionSourceMap: false,

    devServer: {
        port: "28080", // 请求地址的端口号，在本项目是8080
        proxy: {
            "/wu-lazy-cloud-network/": {
                // 'demo'是请求地址中，以demo开头都适用这个
                // target: "http://47.92.27.215:520/", // 真实的服务器地址
                target: "http://127.0.0.1:6001", // 真实的服务器地址
                changeOrigin: true, //
                secure: false, // 默认情况下，不接受运行在 HTTPS 上，且使用了无效证书的后端服务器。如果你想要接受设为false
                ws: false, // 是否启用websockets
                // 是否重写
                pathRewrite: {
                    "^/wu-lazy-cloud-network": "/",
                },
            },
            "/": {
                // 'demo'是请求地址中，以demo开头都适用这个
                target: "http://127.0.0.1:6001", // 真实的服务器地址
                changeOrigin: true, //
                secure: false, // 默认情况下，不接受运行在 HTTPS 上，且使用了无效证书的后端服务器。如果你想要接受设为false
                ws: false, // 是否启用websockets
                // 是否重写
                pathRewrite: {
                    "^/demo": "/demo",
                },
            },
        },
        // setupMiddlewares: (middlewares, devServer) => {
        //     if (config.dev_mock) {
        //         const mock_server = require("./src/api/mock-server.js");
        //         mock_server(devServer.app);
        //     }
        //     return middlewares;
        // },
    },

    chainWebpack: (config) => {
        config.plugin("provide").use(webpack.ProvidePlugin, [
            {
                XE: "xe-utils",
            },
        ]);
        config.plugin("define").use(webpack.DefinePlugin, [
            {
                VE_ENV: {
                    MODE: JSON.stringify(process.env.NODE_ENV),
                },
            },
        ]);
        config.plugins.delete("prefetch");
        // config.plugins.delete("preload");
        // config.optimization.delete("splitChunks");
    },

    configureWebpack: () => {
        let baseConfig = {};
        let envConfig = {};
        if (process.env.NODE_ENV === "production") {
            // 为生产环境修改配置...
            envConfig = {
                optimization: {
                    splitChunks: {
                        chunks: "all",
                        // enforceSizeThreshold: 20000,
                        cacheGroups: {
                            echarts: {
                                name: "chunk-echarts",
                                priority: 20,
                                test: /[\\/]node_modules[\\/]_?echarts(.*)/,
                            },
                            elementPlus: {
                                name: "chunk-elementPlus",
                                priority: 20,
                                test: /[\\/]node_modules[\\/]_?element-plus(.*)/,
                            },
                            elementPlusIcon: {
                                name: "chunk-elementPlusIcon",
                                priority: 20,
                                test: /[\\/]node_modules[\\/]_?@element-plus[\\/]icons(.*)/,
                            },
                            mockjs: {
                                name: "chunk-mockjs",
                                priority: 20,
                                test: /[\\/]node_modules[\\/]_?mockjs(.*)/,
                            },
                        },
                    },
                },
                externals: {
                    // lodash: "_"
                },
                plugins: [
                    new TerserPlugin({
                        terserOptions: {
                            compress: {
                                drop_console: false,
                                drop_debugger: true,
                            },
                        },
                    }),
                    new CompressionWebpackPlugin({
                        filename: "[path][base].gz",
                        algorithm: "gzip",
                        // test: /\.js$|\.html$|\.json$|\.css/,
                        test: /\.js$|\.json$|\.css/,
                        threshold: 10240, // 只有大小大于该值的资源会被处理
                        minRatio: 0.8, // 只有压缩率小于这个值的资源才会被处理
                        // deleteOriginalAssets: true // 删除原文件
                    }),
                ],
            };
        }
        return Object.assign(baseConfig, envConfig);
    },

    css: {
        loaderOptions: {
            scss: {
                // 注意：在 sass-loader v8 中，这个选项名是 "prependData"
                // additionalData: `@import "~@/styles/imports.scss";`
                additionalData: Object.keys(scssVariables)
                    .map((k) => `$${k.replace("_", "-")}: ${scssVariables[k]};`)
                    .join("\n"),
            },
        },
    },
};
