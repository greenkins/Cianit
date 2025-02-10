import { createRouter, createWebHistory } from "vue-router";
import FlatSearch from "@/components/search/FlatSearch.vue";
import FlatDetail from "@/components/FlatDetail.vue";
import FlatForm from "@/components/FlatForm.vue";

const routes = [
    { path: "/", name: "Flats", component: FlatSearch },
    { path: "/flat/:id", name: "Flat", component: FlatDetail },
    {path: "/add", name: "AddFlat", component: FlatForm}

];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;
