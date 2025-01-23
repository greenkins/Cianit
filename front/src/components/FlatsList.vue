<template>
  <div class="container">
    <h1 class="my-4">Квартиры</h1>

    <!-- Панель фильтрации -->
    <Filters @filter="applyFilters" />

    <!-- Список квартир -->
    <div class="row">
      <FlatCard
          v-for="flat in flats"
          :key="flat.id"
          :flat="flat"
      />
    </div>

    <!-- Пагинация -->
    <Pagination
        :current-page="currentPage"
        :total-pages="totalPages"
        @page-change="changePage"
    />
  </div>
</template>

<script>
import Filters from "@/components/Filters.vue";
import FlatCard from "@/components/FlatCard.vue";
import Pagination from "@/components/Pagination.vue";
import axios from "@/plugins/axios";

export default {
  components: { Filters, FlatCard, Pagination },
  data() {
    return {
      flats: [],
      currentPage: 1,
      totalPages: 1,
      filters: {},
    };
  },
  methods: {
    async fetchFlats() {
      try {
        const params = { ...this.filters, page: this.currentPage };
        const response = await axios.get("/flats", { params });
        this.flats = response.data.items;
        this.totalPages = response.data.totalPages;
      } catch (error) {
        alert("Ошибка загрузки данных: " + error.message);
      }
    },
    applyFilters(filters) {
      this.filters = filters;
      this.currentPage = 1; // Сброс на первую страницу
      this.fetchFlats();
    },
    changePage(page) {
      this.currentPage = page;
      this.fetchFlats();
    },
  },
  mounted() {
    this.fetchFlats();
  },
};
</script>

<style scoped>
.container {
  max-width: 1200px;
}
</style>