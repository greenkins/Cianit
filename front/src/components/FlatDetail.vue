<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { getFlatById } from "@/api";
import { xml2json } from "xml-js"; // Импорт для преобразования XML в JSON
import { useToast } from "vue-toastification";

const route = useRoute();
const router = useRouter();
const toast = useToast();

const flat = ref(null);
const isLoading = ref(true);
const error = ref(null);

const fetchFlat = async () => {
  try {
    const response = await getFlatById(route.params.id);
    if (response == null || response.code >= 300) {
      flat.value = null;
      throw new Error("Данные о квартире не найдены");
    }
    // Преобразуем XML в JSON
    const jsonData = JSON.parse(xml2json(response, { compact: true, spaces: 2 }));
    if (!jsonData.Flat) {
      throw new Error("Данные о квартире не найдены");
    }

    // Извлекаем данные
    flat.value = {
      id: Number(jsonData.Flat.id._text),
      creationDate: jsonData.Flat.creationDate._text,
      name: jsonData.Flat.name._text,
      coordinates: {
        x: Number(jsonData.Flat.coordinates.x._text),
        y: Number(jsonData.Flat.coordinates.y._text),
      },
      area: Number(jsonData.Flat.area._text),
      numberOfRooms: Number(jsonData.Flat.numberOfRooms._text),
      livingSpace: Number(jsonData.Flat.livingSpace._text),
      isNew: jsonData.Flat.new._text === "true",
      transport: jsonData.Flat.transport._text,
      house: {
        name: jsonData.Flat.house.name._text,
        year: Number(jsonData.Flat.house.year._text),
        numberOfFloors: Number(jsonData.Flat.house.numberOfFloors._text),
        numberOfLifts: Number(jsonData.Flat.house.numberOfLifts._text),
      },
    };
  } catch (err) {
    error.value = err.message || "Ошибка загрузки";
    toast.error(`${response.code}' '${error.value}${details ? `: ${details}` : ''}`, {
      timeout: 3000,
    });
  } finally {
    isLoading.value = false;
  }
};

onMounted(fetchFlat);
</script>

<template>
  <div class="container mx-auto p-4">
    <button @click="router.back()" class="back-button">Назад</button>

    <div v-if="isLoading" class="loading">Загрузка...</div>
    <div v-else-if="error" class="error">{{ error }}</div>
    <div v-else class="flat-detail">
      <h1 class="title">{{ flat.name }}</h1>
      <p><strong>ID:</strong> {{ flat.id }}</p>
      <p><strong>Дата создания:</strong> {{ flat.creationDate }}</p>
      <p><strong>Координаты:</strong> ({{ flat.coordinates.x }}, {{ flat.coordinates.y }})</p>
      <p><strong>Площадь:</strong> {{ flat.area }} м²</p>
      <p><strong>Количество комнат:</strong> {{ flat.numberOfRooms }}</p>
      <p><strong>Жилая площадь:</strong> {{ flat.livingSpace }} м²</p>
      <p><strong>Новостройка:</strong> {{ flat.isNew ? "Да" : "Нет" }}</p>
      <p><strong>Транспорт:</strong> {{ flat.transport }}</p>

      <h3>🏢 Дом</h3>
      <p><strong>Название:</strong> {{ flat.house.name }}</p>
      <p><strong>Год постройки:</strong> {{ flat.house.year }}</p>
      <p><strong>Количество этажей:</strong> {{ flat.house.numberOfFloors }}</p>
      <p><strong>Лифты:</strong> {{ flat.house.numberOfLifts }}</p>
    </div>
  </div>
</template>

<style scoped>
.container {
  max-width: 600px;
  margin: 20px auto;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

.back-button {
  background: #8bc34a;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 8px;
  cursor: pointer;
  margin-bottom: 15px;
}

.back-button:hover {
  background: #7cb342;
}

.loading, .error {
  font-size: 18px;
  text-align: center;
  margin-top: 20px;
}

.title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}
</style>
