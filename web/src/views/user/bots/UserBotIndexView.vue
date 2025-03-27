<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 11:11:44
 * @LastEditTime: 2025-03-27 12:26:22
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/views/user/UserBotIndexView.vue
-->
<template> 
    <ContentField>
        My Bots
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'
import $ from 'jquery'
import { useStore } from 'vuex'

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        /*
        $.ajax({
                url: "http://127.0.0.1:3000/user/bot/add/",
                type: "post",
                data: {
                    title: "Bot007",
                    description: "this is a test Bot",
                    content: "this is test code",
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            })
*/
            /*
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/remove/",
                type: "post",
                data: {
                    bot_id: 8,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            }) */

           /*
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/update/",
                type: "post",
                data: {
                    bot_id: 9,
                    title: "Bot009",
                    description: "test update after add soft delete",
                    content: "!!!!!!!!!! ",
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            }) 
                */
           
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            }) 
/*
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/deleted/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            })  
        */
        
       /*
        $.ajax({
                url: "http://127.0.0.1:3000/user/bot/restore/",
                type: "post",
                data: {
                    bot_id: 9,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            })*/
    }
}
</script>


<style scoped> </style>