<template>
  <div class="filters mb-4">
    <form @submit.prevent="submitFilters">
      <div class="row">
        <div class="col-md-4">
          <label for="priceFrom">Цена от:</label>
          <input
              id="priceFrom"
              v-model.number="filters.priceFrom"
              type="number"
              class="form-control"
          />
        </div>
        <div class="col-md-4">
          <label for="priceTo">Цена до:</label>
          <input
              id="priceTo"
              v-model.number="filters.priceTo"
              type="number"
              class="form-control"
          />
        </div>
        <div class="col-md-4">
          <label for="rooms">Количество комнат:</label>
          <select
              id="rooms"
              v-model.number="filters.rooms"
              class="form-control"
          >
            <option value="">Все</option>
            <option value="1">1 комната</option>
            <option value="2">2 комнаты</option>
            <option value="3">3+ комнаты</option>
          </select>
        </div>
      </div>
      <button type="submit" class="btn btn-primary mt-3">Применить</button>
    </form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      filters: {
        priceFrom: null,
        priceTo: null,
        rooms: null,
      },
    };
  },
  methods: {
    submitFilters() {
      this.$emit("filter", this.filters);
    },
  },
};
</script>
