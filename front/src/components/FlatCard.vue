<template>
  <div class="col-md-4 mb-4">
    <div class="card">
      <img :src="flat.imageUrl" class="card-img-top" alt="Квартира" />
      <div class="card-body">
        <h5 class="card-title">{{ flat.name }}</h5>
        <p class="card-text">
          Цена: {{ flat.price }} ₽<br />
          Комнат: {{ flat.rooms }}<br />
          Площадь: {{ flat.area }} м²
        </p>
        <router-link :to="`/flat/${flat.id}`" class="btn btn-primary">
          Подробнее
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    flat: {
      type: Object,
      required: true,
    },
  },
};
</script>
