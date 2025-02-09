<script setup>
import { ref } from "vue";

const emit = defineEmits(["sort-change"]);
const sort = ref("");

const applySort = () => {
  emit("sort-change", sort.value);
};
</script>

<template>
  <div class="bg-gray-100 p-4 rounded-lg mb-4">
    <h3 class="font-bold text-lg">Сортировка</h3>
    <select v-model="sort" @change="applySort" class="p-2 border rounded w-full">
      <option value="">Без сортировки</option>
      <option value="area">По площади</option>
      <option value="-area">По убыванию площади</option>
      <option value="transport">По транспорту</option>
    </select>
  </div>
</template>

<style scoped>

</style>