<script setup>
</script>

<template>
  <header class="bg-white border-b border-gray-300">
    <!-- Логотип и навигация -->
    <div class="container mx-auto px-0 py-0 pt-1">
      <!-- Логотип слева -->
      <div class="flex justify-start-l items-center">
        <img src="@/assets/Greenkins.png" alt="Logo" class="h-10 pl3 mr-1" />
        <h1 class="text-2xl font-bold text-gray-900">cianit</h1>
      </div>

      <!-- Горизонтальный навбар -->
      <nav class="mt-2 flex space-x-6">
        <div class="nav-item">
          <RouterLink to="/" class="nav-link">Главная</RouterLink>
        </div>
        <div class="nav-item">
          <a href="https://github.com/greenkins" class="nav-link">О нас</a>
        </div>
        <div class="nav-item">
          <RouterLink to="/add" class="nav-link">Добавить квартиру</RouterLink>
        </div>
        <div class="nav-item">
          <RouterLink class="nav-link" to="/compare">Сравнение квартир</RouterLink>
        </div>
        <div class="nav-item">
          <RouterLink class="nav-link" to="/access">Доступность</RouterLink>
        </div>
      </nav>
    </div>
  </header>
</template>

<style scoped>
.nav-link {
  color: #333; /* Черный текст */
  text-decoration: none; /* Без подчеркивания */
  font-weight: 500; /* Немного выделить жирным */
  padding-bottom: 5px;
  transition: all 0s ease; /* Плавный переход */
}

.nav-link:hover {
  color: #333; /* Цвет текста не меняется */
}

.nav-item .nav-link{
  border-bottom: 3px solid transparent;
}
.nav-item:hover .nav-link{
  border-bottom: 3px solid #ccc; /* Толстое серое подчеркивание */
}
</style>
