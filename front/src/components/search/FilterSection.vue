<script>
export default {
  data() {
    return {
      filters: {
        areaMin: "",
        areaMax: "",
        numberOfRooms: "",
        isNew: "",
      },
    };
  },
  methods: {
    applyFilters() {
      const filterParams = Object.entries(this.filters)
          .filter(([_, value]) => value !== "")
          .map(([key, value]) => `${key}:${encodeURIComponent(value)}`) // Кодируем только значения
          .join(";");

      this.$emit("updateFilters", filterParams);
    },
  },
};
</script>

<template>
  <div class="filter-section pb-3 ">
    <label>Мин. площадь:<input v-model="filters.areaMin" type="number" placeholder="м²"/></label>
    <label>Макс. площадь:<input v-model="filters.areaMax" type="number" placeholder="м²"/></label>
    <label>Кол-во комнат:<select v-model="filters.numberOfRooms">
      <option value="">Любое</option>
      <option v-for="n in 5" :key="n" :value="n">{{n}}</option>
    </select></label>

    <label>Новостройка:<select v-model="filters.isNew">
      <option value="">Любое</option>
      <option value="true">Да</option>
      <option value="false">Нет</option>
    </select>
    </label>

    <button @click="applyFilters">Применить</button>
  </div>
</template>

<style scoped>
.filter-section {
  display: flex;
  gap: 10px;
}
input {
  margin: 0 10px;
}
</style>