/*
 * @Author: Hao Yang
 * @Date: 2025-03-29 09:21:14
 * @LastEditTime: 2025-03-29 11:50:27
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/store/pk.js
 */
export default {
    state: {
        status: "matching",  //matching, playing
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        gamemap: null,

    },
    getters: {
    },
    mutations: {
        updateSocket(state, socket) {
            state.socket = socket;
        },
        updateOpponent(state, opponent) {
            state.opponent_username = opponent.username;
            state.opponent_photo = opponent.photo;
        },
        updateStatus(state, status) {
            state.status = status;
        },
        updateGamemap(state, gamemap) {
            state.gamemap = gamemap;
        }

    },
    // if we want to change state, we sue actions
    actions: {
    },
    modules: {
    }
}