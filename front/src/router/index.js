import { createRouter, createWebHistory } from "vue-router";
import FlatSearch from "@/components/search/FlatSearch.vue";

const routes = [
    { path: "/", component: FlatSearch },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
