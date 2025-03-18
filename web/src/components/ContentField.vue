<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 11:43:11
 * @LastEditTime: 2025-03-18 11:53:41
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/components/ContentField.vue
-->
 
<template> 
    <div class="container content-field">
        <div class="card">
            <div class="card-body">
                <!--render content in the slot-->
                <slot></slot>
            </div>
        </div>
    </div>    
</template>

<script> </script>


<style scoped>

div.content-field{
    margin-top: 20px;
}
</style>