<script setup>
import { ref } from "vue";

const emit = defineEmits(["sort-change", "flatsOnPage-change"]);
const sort = ref("");
const flatsOnPage = ref("");

const applySort = () => {
  emit("sort-change", sort.value);
};
const applyFlatsOnPage = () => {
  emit("flatsOnPage-change", flatsOnPage.value);
}
</script>

<template>
  <div class="bg-gray-100 p-4 rounded-lg mb-4">
    <h3 class="font-bold text-lg">Сортировка</h3>
    <select v-model="sort" @change="applySort" class="p-2 border rounded w-full">
      <option value="">Без сортировки</option>
      <option value="name">По имени (А-Я)</option>
      <option value="-name">По имени (Я-А)</option>
      <option value="area">По площади (сначала меньше)</option>
      <option value="-area">По площади (сначала больше)</option>
      <option value="numberOfRooms">По кол-ву комнат (сначала меньше)</option>
      <option value="-numberOfRooms">По кол-ву комнат (сначала больше)</option>
      <option value="livingSpace">По жилому пространству (сначала меньше)</option>
      <option value="-livingSpace">По жилому пространству (сначала больше)</option>
      <option value="transport">По транспорту (сначала меньше)</option>
      <option value="-transport">По транспорту (сначала больше)</option>
      <option value="new">По новизне (сначала новее)</option>
      <option value="-new">По новизне (сначала старые)</option>
    </select>
    <select v-model="flatsOnPage" @change="applyFlatsOnPage" class="p-2 border rounded w-full">
      <option value="3">3</option>
      <option value="6">6</option>
      <option value="9">9</option>
      <option value="">15</option>
      <option value="21">21</option>
      <option value="60">60</option>
    </select>
  </div>
</template>

<style scoped>

</style>