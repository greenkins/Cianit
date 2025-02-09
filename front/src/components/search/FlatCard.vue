<script setup>
defineProps(["flat"]);

const getValue = (field) => (field && field._text ? field._text : "Нет данных");
</script>

<template>
  <div class="border rounded-lg p-4 shadow hover:shadow-lg cursor-pointer">
    <h3 class="text-lg font-semibold">{{ getValue(flat.name) }}</h3>
    <p>Площадь: {{ getValue(flat.area) }} м²</p>
    <p>Комнат: {{ getValue(flat.numberOfRooms) }}</p>
    <p>Транспорт: {{ getValue(flat.transport) }}</p>
  </div>
</template>