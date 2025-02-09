<script setup>
import { ref } from "vue";

const emit = defineEmits(["filter-change"]);
const filters = ref({
  minArea: "",
  maxArea: "",
  minRooms: "",
  maxRooms: "",
  isNew: "",
});

const applyFilters = () => {
  emit("filter-change", { ...filters.value });
};
</script>

<template>
  <div class="bg-gray-100 p-4 rounded-lg mb-4">
    <h3 class="font-bold text-lg">Фильтры</h3>
    <div class="grid grid-cols-2 gap-4">
      <input v-model="filters.minArea" type="number" placeholder="Мин. площадь" class="p-2 border rounded" />
      <input v-model="filters.maxArea" type="number" placeholder="Макс. площадь" class="p-2 border rounded" />
      <input v-model="filters.minRooms" type="number" placeholder="Мин. комнаты" class="p-2 border rounded" />
      <input v-model="filters.maxRooms" type="number" placeholder="Макс. комнаты" class="p-2 border rounded" />
      <select v-model="filters.isNew" class="p-2 border rounded">
        <option value="">Любая</option>
        <option value="true">Новая</option>
        <option value="false">Старая</option>
      </select>
    </div>
    <button @click="applyFilters" class="mt-2 bg-blue-500 text-white p-2 rounded">Применить</button>
  </div>
</template>

<style scoped>

</style>