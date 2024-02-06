let modules = {};
const files = require.context("./modules", true, /index.js$/);
files.keys().forEach((key) => {
    const fileName = key.split("/")[1];
    modules[fileName] = files(key).default;
});

import { createStore } from "vuex";
import getters from "./getters";
export default createStore({
    getters,
    modules,
});
