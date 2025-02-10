<script setup>
import { ref } from "vue";
import { getOrderedByTimeToMetro } from "@/api";
import { useToast } from "vue-toastification";
import { xml2json } from "xml-js"; // Конвертация XML в JSON

const toast = useToast();
const byTransport = ref(false);
const desc = ref(false);
const flats = ref([]);
const isLoading = ref(false);
const error = ref(null);

const fetchFlats = async () => {
  isLoading.value = true;
  error.value = null;

  try {
    const response = await getOrderedByTimeToMetro(byTransport.value, desc.value);

    // Преобразуем XML в JSON
    const jsonData = JSON.parse(xml2json(response, { compact: true, spaces: 2 }));
    if (!jsonData.Response || !jsonData.Response.Flat) {
      throw new Error("Некорректный ответ сервера");
    }

    // Обрабатываем одиночный и массив объектов
    flats.value = Array.isArray(jsonData.Response.Flat)
        ? jsonData.Response.Flat.map(flat => ({
          id: Number(flat.id._text),
          name: flat.name._text,
          area: Number(flat.area._text),
          numberOfRooms: Number(flat.numberOfRooms._text),
          transport: flat.transport._text,
        }))
        : [
          {
            id: Number(jsonData.Response.Flat.id._text),
            name: jsonData.Response.Flat.name._text,
            area: Number(jsonData.Response.Flat.area._text),
            numberOfRooms: Number(jsonData.Response.Flat.numberOfRooms._text),
            transport: jsonData.Response.Flat.transport._text,
          },
        ];
  } catch (err) {
    error.value = "Ошибка загрузки данных";
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
  <div class="container">
    <h1>Доступность квартир</h1>

    <div class="filters">
      <label>
        <input type="checkbox" v-model="byTransport" />
        Сортировать по транспорту
      </label>
      <label>
        <input type="checkbox" v-model="desc" />
        По убыванию
      </label>
    </div>

    <button @click="fetchFlats">Показать</button>

    <div v-if="isLoading">Загрузка...</div>
    <div v-if="error" class="error">{{ error }}</div>
    <ul v-if="flats.length">
      <li v-for="flat in flats" :key="flat.id">
        <strong>ID:</strong> {{ flat.id }} — <strong>{{ flat.name }}</strong>
        ({{ flat.numberOfRooms }} комн., {{ flat.area }} м²) — {{ flat.transport }}
      </li>
    </ul>
  </div>
</template>

<style scoped>
.container { max-width: 500px; margin: auto; padding: 20px; }
.filters { display: flex; gap: 15px; margin-bottom: 15px; }
button { padding: 10px 15px; background: blue; color: white; border: none; cursor: pointer; }
.error { color: red; margin-top: 10px; }
</style>
