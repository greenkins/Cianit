<script setup>
import { ref } from "vue";
import { createFlat, updateFlat } from "@/api";
import { useRouter } from "vue-router";
import { useToast } from "vue-toastification";

const router = useRouter();
const toast = useToast();

const flat = ref({
  id: null, // НЕОБЯЗАТЕЛЬНОЕ ПОЛЕ
  name: "",
  coordinates: { x: null, y: null },
  area: null,
  numberOfRooms: null,
  livingSpace: null,
  isNew: false,
  transport: "FEW",
  house: { name: "", year: null, numberOfFloors: null, numberOfLifts: null },
});

const validateForm = () => {
  if (!flat.value.name || !flat.value.house.name) return "Заполните все поля!";
  if (flat.value.area <= 0 || flat.value.numberOfRooms <= 0) return "Некорректные данные!";
  return null;
};

const handleSubmit = async () => {
  const error = validateForm();
  if (error) return toast.error(error);

  try {
    if (flat.value.id) {
      await updateFlat(flat.value.id, flat.value);
      toast.success("Квартира успешно обновлена!");
    } else {
      await createFlat(flat.value);
      toast.success("Квартира успешно добавлена!");
    }
    router.push("/");
  } catch (e) {
    toast.error(e.message);
  }
};
</script>

<template>
  <div class="container">
    <h2>Добавить / Обновить квартиру</h2>

    <form @submit.prevent>
      <label>ID (только для обновления):
        <input v-model.number="flat.id" type="number" placeholder="Необязательно" />
      </label>
      <label>Название: <input v-model="flat.name" required /></label>
      <label>Координаты:
        X <input v-model.number="flat.coordinates.x" type="number" required />
        Y <input v-model.number="flat.coordinates.y" type="number" required />
      </label>
      <label>Площадь: <input v-model.number="flat.area" type="number" required /></label>
      <label>Комнат: <input v-model.number="flat.numberOfRooms" type="number" required /></label>
      <label>Жилая площадь: <input v-model.number="flat.livingSpace" type="number" required /></label>
      <label>Новостройка: <input v-model="flat.isNew" type="checkbox" /></label>
      <label>Транспорт:
        <select v-model="flat.transport">
          <option value="FEW">Мало</option>
          <option value="LITTLE">Немного</option>
          <option value="NORMAL">Нормально</option>
        </select>
      </label>

      <h3>Дом</h3>
      <label>Название дома: <input v-model="flat.house.name" required /></label>
      <label>Год постройки: <input v-model.number="flat.house.year" type="number" required /></label>
      <label>Этажи: <input v-model.number="flat.house.numberOfFloors" type="number" required /></label>
      <label>Лифты: <input v-model.number="flat.house.numberOfLifts" type="number" required /></label>

      <button class="btn-green mt-2" @click="handleSubmit">
        SEND
      </button>
    </form>
  </div>
</template>

<style scoped>
.container {
  max-width: 500px;
  margin: auto;
  background: white;
  padding: 20px;
  border-radius: 12px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

input, select {
  width: 100%;
  padding: 8px;
  margin-top: 5px;
  border: 1px solid gray;
  border-radius: 6px;
}

.btn-green {
  background: #8bc34a;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
}

.btn-green:hover {
  background: #7cb342;
}
</style>
