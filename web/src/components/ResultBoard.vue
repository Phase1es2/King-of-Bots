<!--
 * @Author: Hao Yang
 * @Date: 2025-03-31 18:15:29
 * @LastEditTime: 2025-03-31 19:16:46
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/components/ResultBoard.vue
-->
<template>
    <div class="result-board">
      <transition name="fade">
        <div class="result-board-text" v-if="!hidden">
          <template v-if="isDraw">🤝 It's a Draw!</template>
          <template v-else-if="isLoser">😢 You Lost</template>
          <template v-else>🏆 Winner Winner Chicken Dinner!</template>
        </div>
      </transition>
      <div class="result-board-btn">
        <button @click="nextgame" type="button" class="glow-btn">
          GG NEXT
        </button>
      </div>
    </div>
  </template>
  
  <script>
  import { computed, ref, onMounted } from "vue";
  import { useStore } from "vuex";
  
  export default {
    setup() {
      const store = useStore();
      const hidden = ref(true);
  
      const isDraw = computed(() => store.state.pk.loser === "all");
      const isLoser = computed(() => {
        const { loser, a_id, b_id } = store.state.pk;
        const myId = parseInt(store.state.user.id);
        return (loser === "A" && myId === a_id) || (loser === "B" && myId === b_id);
      });
  
      const nextgame = () => {
        store.commit("updateStatus", "matching");
        store.commit("updateLoser", "none");
        store.commit("updateOpponent", {
          username: "formidable opponent",
          photo:
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9N6MctzzI28Vg2TkQEcMZU86136_NpAWWAQ&s",
        });
      };
  
      onMounted(() => {
        setTimeout(() => {
          hidden.value = false;
        }, 200); // slight delay for fade-in
      });
  
      return {
        isDraw,
        isLoser,
        nextgame,
        hidden,
      };
    },
  };
  </script>
  
  <style scoped>
  .result-board {
    height: 35vh;
    width: 40vw;
    background: linear-gradient(135deg, rgba(40, 40, 40, 0.85), rgba(90, 90, 90, 0.85));
    backdrop-filter: blur(5px);
    position: absolute;
    top: 32.5vh;
    left: 30vw;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border-radius: 20px;
    box-shadow: 0 0 30px rgba(255, 255, 255, 0.2);
    animation: popIn 0.6s ease-out;
  }
  
  .result-board-text {
    color: #fff;
    font-size: 42px;
    font-weight: bold;
    font-style: italic;
    text-align: center;
    margin-bottom: 3vh;
    text-shadow: 0 0 10px #fff, 0 0 20px #ff00ff;
  }
  
  .result-board-btn button.glow-btn {
    padding: 12px 28px;
    font-size: 20px;
    font-weight: 600;
    color: #fff;
    background: linear-gradient(to right, #ff416c, #ff4b2b);
    border: none;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(255, 75, 43, 0.6);
    cursor: pointer;
    transition: all 0.3s ease;
  }
  
  .result-board-btn button.glow-btn:hover {
    transform: scale(1.1);
    box-shadow: 0 0 25px rgba(255, 75, 43, 0.9), 0 0 35px #ff4b2b;
  }
  
  /* Fade-in effect for text */
  .fade-enter-active, .fade-leave-active {
    transition: opacity 0.6s;
  }
  .fade-enter-from, .fade-leave-to {
    opacity: 0;
  }
  
  /* Subtle bounce on entry */
  @keyframes popIn {
    0% {
      transform: scale(0.9);
      opacity: 0;
    }
    100% {
      transform: scale(1);
      opacity: 1;
    }
  }
  </style>