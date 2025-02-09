# 🏠 Cianit Frontend

Фронтенд-приложение для проекта **CianIT**, написанное на **Vue 3 + Vite**.
Подключается к сервисам **Housing** и **Agency**, предоставляя удобный интерфейс для работы с недвижимостью.

## 🚀 Функциональность
- Отображение списка квартир (модуль **Housing**)
- Подключение к API через **Axios**
- Маршрутизация с **Vue Router**
- Модульная архитектура для масштабируемости
- Конфигурация API через **.env**
- Использование **TailwindCSS** для стилизации

## 📌 Стек технологий
- **Vue 3** + Vite
- **Vue Router** (маршрутизация)
- **Axios** (работа с API)
- **TailwindCSS** (стилизация)
- **Pinia** (если потребуется глобальное хранилище)

---

## 📂 Структура проекта
```
cianit-frontend/
│── public/               # Статика
│── src/
│   ├── api/              # Логика запросов к API
│   ├── components/       # Общие компоненты (кнопки, карточки и т.д.)
│   ├── modules/          # Отдельные модули (housing, agency)
│   │   ├── housing/      # Модуль Housing
│   │   ├── agency/       # Модуль Agency
│   ├── layouts/          # Базовые шаблоны (Header, Footer)
│   ├── router/           # Настройка маршрутов
│   ├── store/            # Глобальное состояние (если нужно)
│   ├── views/            # Страницы (вьюшки)
│   ├── App.vue           # Корневой компонент
│   ├── main.js           # Точка входа
│── .env                  # Конфигурация API
│── vite.config.js        # Конфигурация Vite
│── package.json          # Зависимости проекта
```

---

## 🔧 Установка и запуск

### 1️⃣ Установка зависимостей
```sh
npm install
```

### 2️⃣ Запуск проекта (режим разработки)
```sh
npm run dev
```
Проект откроется по адресу: **http://localhost:5173**

### 3️⃣ Сборка проекта (продакшн)
```sh
npm run build
```

### 4️⃣ Запуск продакшн-сборки
```sh
npm run preview
```

---

## 🌐 Конфигурация API

API-адреса задаются в файле **.env**:
```
VITE_HOUSING_API_URL=http://localhost:8081/housing-1.0-SNAPSHOT/api/cian/housing
VITE_AGENCY_API_URL=http://localhost:8082/agency-1.0-SNAPSHOT/api/cian/agency
```

Если нужно сменить сервер — просто обновите **`.env`** и перезапустите проект.

---

## 📌 Маршрутизация

Файл **`router/index.js`** содержит маршруты:
```js
import { createRouter, createWebHistory } from "vue-router";
import HousingList from "@/modules/housing/HousingList.vue";

const routes = [
  { path: "/", component: HousingList },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
```

---

## 🏠 Базовый шаблон (Layout)
Файл **`layouts/BaseLayout.vue`**:
```vue
<template>
  <div class="min-h-screen flex flex-col">
    <header class="bg-blue-500 p-4 text-white text-center">CianIT</header>
    <main class="flex-grow p-4">
      <slot></slot>
    </main>
    <footer class="bg-gray-800 text-white p-4 text-center">
      &copy; 2025 CianIT
    </footer>
  </div>
</template>
```

Используется в **`App.vue`**:
```vue
<script setup>
import BaseLayout from "@/layouts/BaseLayout.vue";
import { RouterView } from "vue-router";
</script>

<template>
  <BaseLayout>
    <RouterView />
  </BaseLayout>
</template>
```

---

## 🔥 Что дальше?
✅ Добавить **детальную страницу квартиры** (`HousingDetail.vue`)
✅ Создать **модуль для Agency**
✅ Подключить **поиск и фильтры**
✅ Добавить **авторизацию (JWT)**


🚀 Готово! Теперь можно легко расширять и добавлять новые модули. 🎉


