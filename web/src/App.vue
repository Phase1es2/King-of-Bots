<template>
  <NavBar />
  <!--Use @/router/index.js to find the path-->
  <router-view/>
</template>

<script> 
/* import NavBar from components */
import NavBar from '@/components/NavBar.vue'
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap/dist/js/bootstrap"

export default {
  components: {
    NavBar
  }
}
</script>

<style>
body {
  background-image: url("@/assets/images/seattle.jpg");
  background-size: cover;
  background-position: center; /* Centers the image */
  background-repeat: no-repeat; /* Prevents tiling */
  height: 100vh; /* Ensures the body takes the full viewport height */
  margin: 0; /* Removes default body margin */
  overflow: hidden; /* Prevents scrolling issues */
}
</style>
