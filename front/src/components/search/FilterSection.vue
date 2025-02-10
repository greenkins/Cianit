<script>
import { ref, onMounted } from "vue";
import { getTransportEnums } from "@/api";

export default {
  setup(_, { emit }) {
    const filters = ref({
      areaMin: "",
      areaMax: "",
      numberOfRooms: "",
      isNew: "",
      transport: [],
      livingSpaceMin: "",
      livingSpaceMax: "",
      yearMin: "",
      yearMax: "",
      houseName: "",
      floorsMin: "",
      floorsMax: "",
      liftsMin: "",
      liftsMax: "",
    });

    const transportOptions = ref([]);
    const showAdvancedFilters = ref(false);
    const showTransport = ref(false); // Скрываем транспортные фильтры по умолчанию

    const applyFilters = () => {
      const filterParams = Object.entries(filters.value)
          .filter(([_, value]) => value !== "" && value !== null && value !== undefined)
          .map(([key, value]) => {
            // Обработка разных типов фильтров
            switch (key) {
              case "areaMin":
                return `area>=${value}`;
              case "areaMax":
                return `area<=${value}`;
              case "numberOfRooms":
                return `numberOfRooms=${value}`;
              case "isNew":
                return `isNew=${value}`;
              case "transport":
                return `transport=${value.join(",")}`;
              case "livingSpaceMin":
                return `livingSpace>=${value}`;
              case "livingSpaceMax":
                return `livingSpace<=${value}`;
              case "yearMin":
                return `house.year>=${value}`;
              case "yearMax":
                return `house.year<=${value}`;
              case "houseName":
                return `house.name~${value}`;
              case "floorsMin":
                return `house.numberOfFloors>=${value}`;
              case "floorsMax":
                return `house.numberOfFloors<=${value}`;
              case "liftsMin":
                return `house.numberOfLifts>=${value}`;
              case "liftsMax":
                return `house.numberOfLifts<=${value}`;
              default:
                return "";
            }
          })
          .filter(param => param !== "") // Убираем пустые параметры
          .join(";");

      emit("updateFilters", filterParams || ""); // Передаём либо строку, либо ""
    };

    onMounted(async () => {
      try {
        transportOptions.value = await getTransportEnums();
      } catch (error) {
        console.error("Ошибка загрузки transport:", error);
      }
    });

    return { filters, transportOptions, showAdvancedFilters, showTransport, applyFilters };
  }
};
</script>

<template>
  <div class="filter-section">
    <div class="filter-row">
      <div class="filter-group">
        <label>Мин. площадь:</label>
        <input v-model="filters.areaMin" type="number" placeholder="м²" class="input-field"/>
      </div>
      <div class="filter-group">
        <label>Макс. площадь:</label>
        <input v-model="filters.areaMax" type="number" placeholder="м²" class="input-field"/>
      </div>

      <div class="filter-group">
        <label>Кол-во комнат:</label>
        <select v-model="filters.numberOfRooms" class="select-field">
          <option value="">Любое</option>
          <option v-for="n in 6" :key="n" :value="n">{{ n === 6 ? "6+" : n }}</option>
        </select>
      </div>

      <div class="filter-group">
        <label>Новостройка:</label>
        <select v-model="filters.isNew" class="select-field">
          <option value="">Любое</option>
          <option value="true">Да</option>
          <option value="false">Нет</option>
        </select>
      </div>
    </div>

    <!-- Кнопка для отображения транспорта -->
    <button @click="showTransport = !showTransport" class="btn">
      {{ showTransport ? "Скрыть транспорт" : "Выбрать транспорт" }}
    </button>

    <div v-if="showTransport" class="transport-options">
      <label v-for="option in transportOptions" :key="option" class="transport-label">
        <input type="checkbox" v-model="filters.transport" :value="option" />
        {{ option }}
      </label>
    </div>

    <button @click="showAdvancedFilters = !showAdvancedFilters" class="btn">
      {{ showAdvancedFilters ? "Скрыть доп. фильтры" : "Ещё фильтры" }}
    </button>

    <div v-if="showAdvancedFilters" class="advanced-filters">
      <div class="filter-row">
        <div class="filter-group">
          <label>Мин. жилая площадь:</label>
          <input v-model="filters.livingSpaceMin" type="number" placeholder="м²" class="input-field"/>
        </div>
        <div class="filter-group">
          <label>Макс. жилая площадь:</label>
          <input v-model="filters.livingSpaceMax" type="number" placeholder="м²" class="input-field"/>
        </div>
      </div>

      <div class="filter-row">
        <div class="filter-group">
          <label>Мин. год постройки:</label>
          <input v-model="filters.yearMin" type="number" placeholder="Год" class="input-field"/>
        </div>
        <div class="filter-group">
          <label>Макс. год постройки:</label>
          <input v-model="filters.yearMax" type="number" placeholder="Год" class="input-field"/>
        </div>
      </div>

      <div class="filter-group">
        <label>Название дома:</label>
        <input v-model="filters.houseName" type="text" placeholder="Название" class="input-field"/>
      </div>

      <div class="filter-row">
        <div class="filter-group">
          <label>Мин. этажей:</label>
          <input v-model="filters.floorsMin" type="number" placeholder="Этажей" class="input-field"/>
        </div>
        <div class="filter-group">
          <label>Макс. этажей:</label>
          <input v-model="filters.floorsMax" type="number" placeholder="Этажей" class="input-field"/>
        </div>
      </div>

      <div class="filter-row">
        <div class="filter-group">
          <label>Мин. лифтов:</label>
          <input v-model="filters.liftsMin" type="number" placeholder="Лифтов" class="input-field"/>
        </div>
        <div class="filter-group">
          <label>Макс. лифтов:</label>
          <input v-model="filters.liftsMax" type="number" placeholder="Лифтов" class="input-field"/>
        </div>
      </div>
    </div>

    <button @click="applyFilters" class="btn btn-apply">Применить</button>
  </div>
</template>

<style scoped>
.filter-section {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 15px;
}

.filter-row {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.filter-group {
  display: flex;
  flex-direction: column;
}

label {
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 4px;
}

.input-field, .select-field {
  padding: 6px;
  border: 1px solid gray;
  border-radius: 8px;
  transition: background 0.2s ease-in-out;
}

.input-field:focus, .select-field:hover {
  background: #f0f0f0;
}

.transport-options {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  padding: 10px;
  background: #f8f8f8;
  border-radius: 10px;
}

.transport-label {
  display: flex;
  align-items: center;
  gap: 5px;
}

.btn {
  padding: 8px 12px;
  border-radius: 8px;
  border: 2px solid rgba(76, 175, 80, 0);
  background: #dff0d8;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
}

.btn:hover {
  background: #c8e6c9;
}

.btn-apply {
  background: #4CAF50;
  color: white;
}

.btn-apply:hover {
  background: #45a049;
}
</style>
