import { createRouter, createWebHistory } from "vue-router";
import FlatsList from "@/views/FlatsList.vue";

const routes = [
    {
        path: "/",
        name: "FlatsList",
        component: FlatsList,
    },
    {
        path: "/flat/:id",
        name: "FlatDetails",
        // Вы можете позже создать компонент для деталей квартиры
        component: () => import("@/views/FlatDetails.vue"),
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

export default router;