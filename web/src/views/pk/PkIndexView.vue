<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 11:09:42
 * @LastEditTime: 2025-03-31 18:34:27
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/views/pk/PkIndexView.vue
-->
<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'" />
    <MatchingPool v-if="$store.state.pk.status === 'matching'" />
    <ResultBoard v-if="$store.state.pk.loser != 'none'" />
</template>

<script>
import PlayGround from '@/components/PlayGround.vue'
import MatchingPool from '@/components/MatchingPool.vue'
import ResultBoard from '@/components/ResultBoard.vue'
import { onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'


export default {
    components: {
        PlayGround,
        MatchingPool,
        ResultBoard,

    },
    setup() {
        const store = useStore();
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}`;
        let socket = null;

        onMounted(() => {
            store.commit("updateOpponent", {
                username: "formidable opponent",
                photo: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT9N6MctzzI28Vg2TkQEcMZU86136_NpAWWAQ&s"
            })
            //js
            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                console.log("conncted!");
                store.commit("updateSocket", socket);
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                // console.log(data);
                // mathicng success
                if (data.event === "start-matching") {
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    /*
                    setTimeout(() => {
                        store.commit("updateStatus", "playing");

                    }, 3000);*/
                    setTimeout(() => {
                        store.commit("updateStatus", "playing");

                    }, 200);
                    // store.commit("updateGamemap", data.gamemap);
                    store.commit("updateGame", data.game);
                } else if (data.event === "move") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.a_direction);
                    snake1.set_direction(data.b_direction);

                } else if (data.event === "result") {
                    console.log(data);
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    if (data.loser === "all" || data.loser === "A") {
                        snake0.status = "die";
                    }
                    if (data.loser === "all" || data.loser === "B") {
                        snake1.status = "die";
                    }
                    store.commit("updateLoser", data.loser);
                }
            }

            socket.onclose = () => {
                console.log("disconnected!");
            }
        });

        onUnmounted(() => {
            socket.close();
            store.commit("updateStatus", "matching");
        });
    }
}
</script>


<style scoped></style>