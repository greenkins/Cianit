import { createRouter, createWebHistory } from "vue-router";
import FlatSearch from "@/components/search/FlatSearch.vue";
import FlatDetail from "@/components/FlatDetail.vue";

const routes = [
    { path: "/", component: FlatSearch },
    { path: "/flat/:id", component: FlatDetail },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
