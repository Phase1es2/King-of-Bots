<!--
 * @Author: Hao Yang
 * @Date: 2025-03-29 10:06:00
 * @LastEditTime: 2025-03-29 11:32:50
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/components/MatchingPool.vue
-->
<template>    
    <div class="matchingpool">
        <div class="players">
            <div class="player-card">
                <img :src="$store.state.user.photo" alt="Your photo" class="avatar" />
                <div class="username">{{ $store.state.user.username }}</div>
            </div>

            <div class="vs-text">VS</div>

            <div class="player-card">
                <img :src="$store.state.pk.opponent_photo" alt="Opponent photo" class="avatar" />
                <div class="username">{{ $store.state.pk.opponent_username }}</div>
            </div>
        </div>

        <div class="match-button-wrapper">
            <button @click="match_start" class="match-btn">
                {{ match_info }}
            </button>
        </div>
    </div>
</template>

<script> 
import { ref } from 'vue';
import { useStore } from 'vuex';
export default {
    setup() {
        const store = useStore();
        const match_info = ref("Find a match");

        const match_start = () => {
            if (match_info.value === "Find a match") {
                match_info.value = "Cancel";
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                }));
            } else {
                match_info.value = "Find a match";
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
            }
        }

        return {
            match_info,
            match_start
        }
    }
}
</script>

<style scoped>
.matchingpool {
    width: 60vw;
    height: 70vh;
    margin: 60px auto;
    background: linear-gradient(to bottom right, #1e1e2f, #2c2c3c);
    border-radius: 20px;
    box-shadow: 0 0 25px rgba(0, 0, 0, 0.4);
    padding: 40px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    animation: fadeIn 0.5s ease;
}

.players {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-top: 4vh;
}

.player-card {
    text-align: center;
    flex: 1;
}

.avatar {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    border: 4px solid #61dafb;
    box-shadow: 0 0 15px #61dafb;
    transition: transform 0.3s ease;
}
.avatar:hover {
    transform: scale(1.05);
}

.username {
    font-size: 22px;
    font-weight: bold;
    color: #f0f0f0;
    margin-top: 15px;
}

.vs-text {
    font-size: 28px;
    font-weight: bold;
    color: #ff6b81;
    padding: 0 20px;
    animation: pulse 1s infinite;
}

.match-button-wrapper {
    text-align: center;
    margin-top: 20px;
}

.match-btn {
    padding: 15px 20px;
    font-size: 20px;
    font-weight: bold;
    color: white;
    background: linear-gradient(145deg, #4e8cff, #3461d1);
    border: none;
    border-radius: 12px;
    box-shadow: 0 0 15px rgba(78, 140, 255, 0.5);
    cursor: pointer;
    transition: all 0.3s ease;
}
.match-btn:hover {
    background: linear-gradient(145deg, #3461d1, #4e8cff);
    transform: scale(1.05);
}

/* Animations */
@keyframes fadeIn {
    from { opacity: 0; transform: translateY(20px); }
    to { opacity: 1; transform: translateY(0); }
}

@keyframes pulse {
    0% { transform: scale(1); opacity: 1; }
    50% { transform: scale(1.1); opacity: 0.7; }
    100% { transform: scale(1); opacity: 1; }
}
</style>