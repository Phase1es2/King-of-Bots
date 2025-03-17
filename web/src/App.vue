<template>
  <div>
    <div>Bots name: {{ bot_name }}</div>
    <div>Ranking: {{ bot_ranking }}</div>
  </div>
  <router-view/>
</template>

<script> 
// use jquery to get info from backend
import $ from 'jquery';
// declare variables
import { ref } from 'vue';


export default {
  name: "App",
  setup: () => {
    let bot_name = ref("");
    let bot_ranking = ref("");
    // Use .ajax to retrieve the funcitons endpoint URL
    $.ajax({
      url: "http://127.0.0.1:3000/pk/getBotInfo/",
      type: "get",

      success: resp => {
        // console.log(resp);
        // the name have to match to the backend
        bot_name.value = resp.name;
        bot_ranking.value = resp.ranking;
      }
    });

    return {
      bot_name,
      bot_ranking
    }
  }
}
</script>

<style>
body {
  background-image: url("@/assets/seattle.jpg");
  background-size: cover;
  background-position: center; /* Centers the image */
  background-repeat: no-repeat; /* Prevents tiling */
  height: 100vh; /* Ensures the body takes the full viewport height */
  margin: 0; /* Removes default body margin */
  overflow: hidden; /* Prevents scrolling issues */
}
</style>
