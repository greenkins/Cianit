<script setup>
import { ref, onMounted } from "vue";
import FilterSection from "@/components/search/FilterSection.vue";
import SortSection from "@/components/search/SortSection.vue";
import FlatList from "@/components/search/FlatList.vue";
import Pagination from "@/components/search/Pagination.vue";
import { getFlats } from "@/api";

const flats = ref([]);
const totalPages = ref(1);
const currPage = ref(1);
const filters = ref({});
const sort = ref("");

const fetchFlats = async (page = 1) => {
  try {
    const response = await getFlats({ ...filters.value, sort: sort.value, page });
    flats.value = Array.isArray(response.Flat) ? response.Flat : [response.Flat]; // Делаем массив, даже если 1 объект
    totalPages.value = Number(response.totalPages._text);
    currPage.value = Number(response.currPage._text);
  } catch (error) {
    console.error("Ошибка загрузки:", error);
  }
};

onMounted(() => fetchFlats());

const updateFilters = (newFilters) => {
  filters.value = newFilters;
  fetchFlats();
};

const updateSort = (newSort) => {
  sort.value = newSort;
  fetchFlats();
};
</script>

<template>
  <div class="container mx-auto p-4">
    <FilterSection @filter-change="updateFilters" />
    <SortSection @sort-change="updateSort" />
    <FlatList :flats="flats" />
    <Pagination :totalPages="totalPages" :currPage="currPage" @change-page="fetchFlats" />
  </div>
</template>

<style scoped>

</style>