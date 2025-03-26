<!--
 * @Author: Hao Yang
 * @Date: 2025-03-25 13:18:07
 * @LastEditTime: 2025-03-26 11:55:48
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/views/user/account/UserAccountRegisterView.vue
-->
<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="register">
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
                    <div class="mb-3">
                        <label for="password" class="form-label">Confirmed Password</label>
                        <input v-model="confirmedPassword" type="password" class="form-control" id="confirmed password"
                            placeholder="Please type password again">
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
import { ref } from 'vue'
// import { useStore } from 'vuex';
import router from '@/router/index'
import $ from 'jquery'

export default {
    components: {
        ContentField
    },
    setup() {
        //const store = useStore();
        let username = ref('');
        let password = ref('');
        let confirmedPassword = ref('');
        let error_message = ref('');

        const register = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/register/",
                type: "post",
                data: {
                    username: username.value,
                    password: password.value,
                    confirmedPassword: confirmedPassword.value,
                },
                success(resp) {
                    // console.log(resp);
                    error_message.value = "";
                    if (resp.error_message === "success") {
                        router.push({name : "user_account_login"});
                    } else {
                        error_message.value = resp.error_message;
                    }
                    // console.log(resp);
                },
                error(resp) {
                    console.log(resp);
                }
            });
        }

        return {
            username,
            password,
            confirmedPassword,
            error_message,
            register,
        }
    }

}
</script>


<style scoped>
button {
    width: 100%;
}
div.error-message{
    color: red;
}
</style>