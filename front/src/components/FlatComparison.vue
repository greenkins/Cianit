<script setup>
import { ref } from "vue";
import { getCheapestFlat } from "@/api";
import { useToast } from "vue-toastification";
import { xml2json } from "xml-js"; // Преобразование XML в JSON

const toast = useToast();
const id1 = ref("");
const id2 = ref("");
const cheapestFlat = ref(null);
const isLoading = ref(false);
const error = ref(null);

const compareFlats = async () => {
  if (!id1.value || !id2.value) {
    toast.error("Введите оба ID для сравнения");
    return;
  }

  isLoading.value = true;
  error.value = null;

  try {
    const response = await getCheapestFlat(id1.value, id2.value);

    // Конвертируем XML в JSON
    const jsonData = JSON.parse(xml2json(response, { compact: true, spaces: 2 }));
    if (!jsonData.Flat) throw new Error("Некорректный ответ сервера");

    // Извлекаем данные
    cheapestFlat.value = {
      id: Number(jsonData.Flat.id._text),
      name: jsonData.Flat.name._text,
      area: Number(jsonData.Flat.area._text),
      numberOfRooms: Number(jsonData.Flat.numberOfRooms._text),
      transport: jsonData.Flat.transport._text,
      isNew: jsonData.Flat.new._text === "true",
    };
  } catch (err) {
    error.value = "Ошибка загрузки данных";
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
  <div class="container">
    <h1>Сравнение квартир</h1>
    <div class="input-group">
      <input v-model="id1" placeholder="ID квартиры 1" />
      <input v-model="id2" placeholder="ID квартиры 2" />
    </div>
    <button @click="compareFlats">Сравнить</button>

    <div v-if="isLoading">Загрузка...</div>
    <div v-if="error" class="error">{{ error }}</div>
    <div v-if="cheapestFlat">
      <h2>Самая дешевая квартира:</h2>
      <p><strong>ID:</strong> {{ cheapestFlat.id }}</p>
      <p><strong>Название:</strong> {{ cheapestFlat.name }}</p>
      <p><strong>Площадь:</strong> {{ cheapestFlat.area }} м²</p>
      <p><strong>Комнаты:</strong> {{ cheapestFlat.numberOfRooms }}</p>
      <p><strong>Новостройка:</strong> {{ cheapestFlat.isNew ? "Да" : "Нет" }}</p>
      <p><strong>Транспорт:</strong> {{ cheapestFlat.transport }}</p>
    </div>
  </div>
</template>

<style scoped>
.container { max-width: 500px; margin: auto; padding: 20px; }
.input-group { display: flex; gap: 10px; margin-bottom: 15px; }
input { padding: 8px; border: 1px solid #ccc; border-radius: 5px; }
button { padding: 10px 15px; background: green; color: white; border: none; cursor: pointer; }
.error { color: red; margin-top: 10px; }
</style>
