<script setup>
import { ref, onMounted } from "vue";
import FilterSection from "@/components/search/FilterSection.vue";
import SortSection from "@/components/search/SortSection.vue";
import FlatList from "@/components/search/FlatList.vue";
import Pagination from "@/components/search/Pagination.vue";
import { getFlats } from "@/api";
import { useToast } from 'vue-toastification';

const flats = ref([]);
const totalPages = ref(1);
const currPage = ref(1);
const filters = ref({});
const sort = ref("");
const flatsOnPage = ref(15);

const fetchFlats = async (page = 1) => {
  try {
    let filterString = (typeof filters.value === "string" && filters.value.trim() !== "") ? filters.value : "";
    const response = await getFlats({
      filter: filterString,
      sort: sort.value,
      page: page,
      size: flatsOnPage.value
    });

    // Убедитесь, что response.Flat существует и является массивом
    flats.value = Array.isArray(response.Flat) ? response.Flat : [];

    // Получаем значения totalPages и currPage
    totalPages.value = Number(response.totalPages._text);
    currPage.value = Number(response.currPage._text);
  } catch (error) {
    const toast = useToast();
    // Парсим XML-ответ
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(error.response.data, 'text/xml');
    // Извлекаем сообщение об ошибке
    const errorCode = error.code;
    const errorMessage = xmlDoc.querySelector('error')?.textContent || 'Произошла ошибка';
    const details = xmlDoc.querySelector('details')?.textContent || '';
    // Показываем уведомление
    toast.error(`${errorCode}' '${errorMessage}${details ? `: ${details}` : ''}`, {
      timeout: 3000,
    });
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
const updateFlatsOnPage = (newFlatsOnPage) => {
  flatsOnPage.value = newFlatsOnPage;
  fetchFlats();
}
</script>

<template>
  <div class="container mx-auto p-4">
    <FilterSection @updateFilters="updateFilters" />
    <SortSection @sort-change="updateSort" @flatsOnPage-change="updateFlatsOnPage"/>
    <FlatList :flats="flats" />
    <Pagination :totalPages="totalPages" :currPage="currPage" @change-page="fetchFlats" />
  </div>
</template>

<style scoped>

</style>