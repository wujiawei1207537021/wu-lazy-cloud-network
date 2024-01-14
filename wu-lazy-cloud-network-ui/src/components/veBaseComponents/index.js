export default {
    install: (app) => {
        const files = require.context(
            "@/components/veBaseComponents",
            false,
            /\.vue$/,
        );
        files.keys().forEach((key) => {
            // 获取组件配置
            const componentConfig = files(key);
            // 全局注册组件
            app.component(
                componentConfig.default.name,
                componentConfig.default,
            );
        });
    },
};
