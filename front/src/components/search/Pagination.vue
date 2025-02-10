<script setup>
defineProps(["totalPages", "currPage"]);
defineEmits(["change-page"]);
</script>

<template>
  <div class="flex justify-center mt-4 space-x-2">
    <button
        v-for="page in totalPages"
        :key="page"
        @click="$emit('change-page', page)"
        :class="['px-4 py-2 border', currPage === page ? 'bg-blue-500 text-white' : 'bg-white']"
    >
      {{ page }}
    </button>
  </div>
</template>

<style scoped>

</style>