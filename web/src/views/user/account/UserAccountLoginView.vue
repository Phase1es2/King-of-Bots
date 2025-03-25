<!--
 * @Author: Hao Yang
 * @Date: 2025-03-25 13:17:47
 * @LastEditTime: 2025-03-25 14:26:35
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/views/user/account/UserAccountLogin.vue
-->
<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="username" class="form-label">Username</label>
                        <input v-model="username" type="text" class="form-control" id="username"
                            placeholder="Please type Username">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input v-model="password" type="password" class="form-control" id="password"
                            placeholder="Please type password">
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
        </div>
    </ContentField>
</template>

<script>
import ContentField from '@/components/ContentField.vue'
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '@/router/index'

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        const login = () => {
            // if we want to use actions' funciton, use dispatch;
            error_message.value = "";
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    // login then jump to home
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: 'home' });
                            console.log(store.state.user);
                        }
                    })
                },
                error(){
                    error_message.value = "Invalid username or password";
                }
            })
        }

        return {
            username,
            password,
            error_message,
            login,
        }
    }
}
</script>


<style scoped>
button{
    width: 100%;
}
div.error-message{
    color: red;
}
</style>