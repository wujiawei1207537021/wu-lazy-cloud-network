export default {
    install: (app, { router, store }) => {
        const files = require.context("@/directives/modules", false, /\.js$/);
        files.keys().forEach((key) => {
            let name = key.replace(/(\.\/|\.js)/g, "");
            let method = files(key).default;
            app.directive(name, (el, binding) =>
                method(el, binding, app, router, store),
            );
        });
    },
};
