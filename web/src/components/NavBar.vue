<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 10:42:45
 * @LastEditTime: 2025-03-18 12:06:30
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/components/NavBar.vue
-->

<template>
    <!-- https://v5.bootcss.com/docs/5.3/components/navbar/#nav -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
    <!--<a class="navbar-brand" href="/">King of Bots</a>-->
    <!--prevent refresh the page after click on the page-->
    <router-link class="navbar-brand" :to="{name: 'home'}">King of Bots</router-link>
    <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <!--: before class for expression-->
          <router-link :class="route_name === 'pk_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'pk_index'}">P.K.</router-link>
        </li>
        <li class="nav-item">
          <router-link :class="route_name === 'record_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'record_index'}">Matching List</router-link>
        </li>
        <li class="nav-item">
          <router-link :class="route_name === 'ranklist_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'ranklist_index'}">Rank List</router-link>
        </li>
      </ul>
      <ul class="navbar-nav ms-3">  <!-- Adjusts left margin -->
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            User Name
            </a>
            <ul class="dropdown-menu">
            <li>
                <router-link class="dropdown-item" :to="{name: 'user_bot_index'}">My Bots</router-link>
            </li>
            <li><hr class="dropdown-divider"></li>
            <li><a class="dropdown-item" href="#">Exit</a></li>
            </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>
</template>


<script>
// get the router name real time, and highlight the class
import { useRoute } from 'vue-router'
import { computed } from 'vue';

export default {
  setup() {
    const route = useRoute();
    let route_name = computed(() => route.name)
    return {
      route_name
    }
  }
}
</script>

<style scoped>

</style>