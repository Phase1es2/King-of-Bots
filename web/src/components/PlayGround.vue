<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 12:36:25
 * @LastEditTime: 2025-03-18 12:45:38
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/components/PlayGround.vue
-->

<template>    
    <div class="playground">
        <GameMap></GameMap>
    </div>
</template>

<script> 
import GameMap from '@/components/GameMap.vue';
export default {
    components: {
        GameMap,
    }
}
</script>


<style scoped>
div.playground {
    width: 60vw;
    height: 70vh;
    margin: 60px auto;
}
</style>