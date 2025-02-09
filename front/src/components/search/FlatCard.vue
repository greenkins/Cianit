<script setup>
defineProps(["flat"]);
</script>

<template>
  <div class="border rounded-lg p-4 shadow hover:shadow-lg cursor-pointer">
    <h3 class="text-lg font-semibold">{{ flat.name }}</h3>
    <p>Площадь: {{ flat.area }} м²</p>
    <p>Комнат: {{ flat.numberOfRooms }}</p>
    <p>Транспорт: {{ flat.transport }}</p>
  </div>
</template>

<style scoped>

</style>