<script setup>
defineProps(["flat"]);
import { useRouter } from "vue-router";

const router = useRouter();

const goToFlatDetail = (flat) => {
  router.push(`/flat/${getValue(flat.id)}`);
};
const getValue = (field) => (field && field._text ? field._text : "Нет данных");
</script>

<template>
  <div @click="goToFlatDetail(flat)" class="border rounded-lg p-4 shadow hover:shadow-lg cursor-pointer">
    <h3 class="text-lg font-semibold">{{ getValue(flat.name) }}</h3>
    <p>Площадь: {{ getValue(flat.area) }} м²</p>
    <p>Комнат: {{ getValue(flat.numberOfRooms) }}</p>
    <p>Транспорт: {{ getValue(flat.transport) }}</p>
    <img src="https://media.gettyimages.com/id/1357529184/photo/3d-render-of-a-contemporary-living-room-interior.jpg?s=612x612&w=gi&k=20&c=dwggloKrUF1n4IkpRn8Osfw8FnLyld7mgEQ5MXlX8tU=" alt="flat img">
  </div>
</template>