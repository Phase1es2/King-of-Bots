<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 12:42:04
 * @LastEditTime: 2025-03-31 11:24:11
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/components/GameMap.vue
-->

<template>    
    <div ref="parent" class="gamemap">
        <!--Let the canvas caputre user's actions add tabindex-->
        <canvas ref="canvas" tabindex="0">
            
        </canvas>
    </div>
</template>

<script> 
import { GameMap } from '@/assets/scripts/GameMap';
import { ref, onMounted } from 'vue';
import { useStore } from 'vuex';

export default{
    setup() {
        const store = useStore();
        let parent = ref(null);
        let canvas = ref(null);

        onMounted(() => {
            store.commit(
                "updateGameObject", 
                new GameMap(canvas.value.getContext('2d'), parent.value, store)
            );
        });

        return {
            parent,
            canvas
        }
    }
}
</script>


<style scoped>
div.gamemap{
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>