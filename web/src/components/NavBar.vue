<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 10:42:45
 * @LastEditTime: 2025-03-29 12:05:19
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/components/NavBar.vue
-->

<template>
  <nav class="navbar navbar-expand-lg navbar-dark fancy-navbar">
    <div class="container">
      <!-- Brand Logo -->
      <router-link class="navbar-brand brand-glow" :to="{ name: 'home' }">👑 King of Bots</router-link>

      <div class="collapse navbar-collapse" id="navbarText">
        <!-- Left Menu -->
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item">
            <router-link :class="route_name === 'pk_index' ? 'nav-link active link-glow' : 'nav-link link-glow'" :to="{ name: 'pk_index' }">P.K.</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name === 'record_index' ? 'nav-link active link-glow' : 'nav-link link-glow'" :to="{ name: 'record_index' }">Matching List</router-link>
          </li>
          <li class="nav-item">
            <router-link :class="route_name === 'ranklist_index' ? 'nav-link active link-glow' : 'nav-link link-glow'" :to="{ name: 'ranklist_index' }">Rank List</router-link>
          </li>
        </ul>

        <!-- Right User Section -->
        <ul class="navbar-nav ms-3" v-if="$store.state.user.is_login">
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle user-glow" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
              {{ $store.state.user.username }}
            </a>
            <ul class="dropdown-menu dropdown-menu-dark">
              <li>
                <router-link class="dropdown-item" :to="{ name: 'user_bot_index' }">🤖 My Bots</router-link>
              </li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#" @click="logout">🚪 Exit</a></li>
            </ul>
          </li>
        </ul>

        <!-- Guest Section -->
        <ul class="navbar-nav ms-3" v-else-if="!$store.state.user.load_info">
          <li class="nav-item">
            <router-link class="nav-link link-glow" :to="{ name: 'user_account_login' }">Login</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link link-glow" :to="{ name: 'user_account_register' }">Register</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex'

export default {
  setup() {
    const store = useStore()
    const route = useRoute()
    const route_name = computed(() => route.name)
    const logout = () => {
      store.dispatch("logout")
    }

    return {
      route_name,
      logout,
    }
  }
}
</script>

<style scoped>
.fancy-navbar {
  background: linear-gradient(90deg, #0f0f0f, #1c1c2b);
  box-shadow: 0 0 10px rgba(100, 100, 255, 0.2);
  border-bottom: 2px solid #2e2e4d;
  padding: 12px 0;
}

.brand-glow {
  font-weight: bold;
  font-size: 24px;
  color: #61dafb !important;
  text-shadow: 0 0 5px #61dafb, 0 0 10px #61dafb;
}

.nav-link {
  font-size: 16px;
  font-weight: 500;
}

.link-glow {
  transition: all 0.3s ease;
}
.link-glow:hover {
  color: #00bfff !important;
  text-shadow: 0 0 5px #00bfff;
}

.nav-link.active {
  color: #ffc107 !important;
  text-shadow: 0 0 6px #ffc107;
}

.user-glow {
  color: #ffffff !important;
  font-weight: bold;
  transition: color 0.3s;
}
.user-glow:hover {
  color: #00ffff !important;
  text-shadow: 0 0 8px #00ffff;
}

.dropdown-menu-dark {
  background-color: #2c2c3c;
  border: 1px solid #444;
  border-radius: 10px;
}

.dropdown-item:hover {
  background-color: #3a3a4d;
  color: #00ffff;
}
</style>