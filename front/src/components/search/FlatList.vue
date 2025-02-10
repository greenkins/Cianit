<script setup>
import FlatCard from "@/components/search/FlatCard.vue";

defineProps(["flats"]);
</script>

<template>
  <div class="grid grid-cols-3 gap-4">
    <FlatCard v-for="flat in flats" :key="flat.id" :flat="flat" v-if="flats.length > 0" />
    <p v-else class="">Квартиры не найдены.</p> <!-- Сообщение, если квартир нет -->
  </div>
</template>

<style scoped>

</style>