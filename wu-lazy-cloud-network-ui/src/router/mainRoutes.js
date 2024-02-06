export default {
    path: "/",
    name: "AppMain",
    component: () => import("@/views/AppMain.vue"),
    redirect: { name: "Home" },
    children: [
        {
            path: "home",
            name: "Home",
            component: () => import("@/views/Home.vue"),
        },
    ],
};
