<script setup>
import FlatCard from "@/components/search/FlatCard.vue";

defineProps(["flats"]);
</script>

<template>
  <div class="grid grid-cols-3 gap-4">
    <FlatCard v-for="flat in flats" :key="flat.id" :flat="flat" />
  </div>
</template>

<style scoped>

</style>