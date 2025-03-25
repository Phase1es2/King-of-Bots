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
// import $ from 'jquery'

export default {
  components: {
    NavBar
  },
  // 入口
  /*
  setup() {
    $.ajax({
      url: "http://127.0.0.1:3000/user/account/token/",
      type: "post",
      data: {
        username: 'yh',
        password: "pyh",
      },
      success(resp) {
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    });

    $.ajax({
      url: "http://127.0.0.1:3000/user/account/info/",
      type: "get",
      headers: {
        // in filter
        // "Bearer " + "token"
        Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhNTdmZjRjMmQyMWQ0YjE1OTcyOGQxYjY2NjZiOGI0MCIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTc0MjkxOTcyNywiZXhwIjoxNzQ0MTI5MzI3fQ.8ql2sDZmXHqaxxtoRx3HWJUf78L6VzJvyWCDpo0Rb-g"
      },
      success(resp) {
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    });

    $.ajax({
      url: "http://127.0.0.1:3000/user/account/register/",
      type: "post",
      data: {
        username: "yh2",
        password: "pyh2",
        confirmedPassword: "pyh2",
      },
      success(resp){
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    })
  }*/
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
