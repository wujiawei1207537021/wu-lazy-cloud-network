import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
// import zhCn from "element-plus/es/locale/lang/zh-cn";
import zhCn from "element-plus/dist/locale/zh-cn.mjs";
import "dayjs/locale/zh-cn";
export default {
    install: (app) => {
        app.use(ElementPlus, {
            locale: zhCn,
            size: "default",
        });
    },
};
