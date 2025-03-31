<!--
 * @Author: Hao Yang
 * @Date: 2025-03-18 11:11:44
 * @LastEditTime: 2025-03-29 12:15:08
 * @LastEditors: MacBookPro
 * @Description: In User Settings Edit
 * @FilePath: /Java Final Project/web/src/views/user/UserBotIndexView.vue
-->
<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 30px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                    </div>
                </div>
            </div>

            <!-- Right side -->
            <div class="col-9">
                <div class="card" style="margin-top: 30px;">
                    <div class="card-header">
                        <span style="font-size: 140%;">My Bot</span>
                        <button v-if="!showingDeleted" type="button" class="btn btn-primary float-end"
                            style="margin-right: 10px;" data-bs-toggle="modal" data-bs-target="#add-bot-btn">
                            Create Bot
                        </button>
                        <button v-if="showingDeleted" type="button" class="btn btn-outline-secondary float-end"
                            style="margin-right: 10px;" @click="refresh_bots">
                            Back to My Bots
                        </button>
                        <button v-if="!showingDeleted" type="button" class="btn btn-secondary float-end" style="margin-right: 10px;"
                            @click="deleted_bots">
                            Deleted
                        </button>

                        <!-- Add Bot Modal -->
                        <div class="modal fade" id="add-bot-btn" tabindex="-1">
                            <div class="modal-dialog modal-xl">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h1 class="modal-title fs-5">Create Bot</h1>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="mb-3">
                                            <label for="add-bot-title" class="form-label">Bot Name</label>
                                            <input v-model="botadd.title" type="text" class="form-control"
                                                id="add-bot-title" placeholder="Please type bot name!">
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-description" class="form-label">Description</label>
                                            <textarea v-model="botadd.description" class="form-control"
                                                id="add-bot-description" rows="3"
                                                placeholder="Please describe your bot"></textarea>
                                        </div>
                                        <div class="mb-3">
                                            <label for="add-bot-code" class="form-label">Code</label>
                                            <VAceEditor v-model:value="botadd.content" @init="editorInit" lang="c_cpp"
                                                theme="textmate" style="height: 300px" :options="{
                                                    enableBasicAutocompletion: true,
                                                    enableSnippets: true,
                                                    enableLiveAutocompletion: true,
                                                    fontSize: 18,
                                                    tabSize: 4,
                                                    showPrintMargin: false,
                                                    highlightActiveLine: true,
                                                }" />
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <div class="error-message">{{ botadd.error_message }}</div>
                                        <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">Cancel</button>
                                        <button type="button" class="btn btn-primary" @click="add_bot">Create</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Bot List Table -->
                    <div class="card-body">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Created Time</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td style="font-size: 120%;">{{ bot.title }}</td>
                                    <td style="font-size: 120%;">{{ bot.createtime }}</td>
                                    <td>
                                        <template v-if="!showingDeleted">
                                            <button type="button" class="btn btn-warning" style="margin-right: 10px;"
                                                data-bs-toggle="modal"
                                                :data-bs-target="'#edit-bot-modal-' + bot.id">Modify</button>

                                            <!-- Edit Bot Modal -->
                                            <div class="modal fade" :id="'edit-bot-modal-' + bot.id" tabindex="-1">
                                                <div class="modal-dialog modal-xl">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h1 class="modal-title fs-5">Edit Bot</h1>
                                                            <button type="button" class="btn-close"
                                                                data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="mb-3">
                                                                <label class="form-label">Bot Name</label>
                                                                <input v-model="bot.title" type="text"
                                                                    class="form-control"
                                                                    placeholder="Please type bot name!">
                                                            </div>
                                                            <div class="mb-3">
                                                                <label class="form-label">Description</label>
                                                                <textarea v-model="bot.description" class="form-control"
                                                                    rows="3"
                                                                    placeholder="Please describe your bot"></textarea>
                                                            </div>
                                                            <div class="mb-3">
                                                                <label class="form-label">Code</label>
                                                                <VAceEditor v-model:value="bot.content"
                                                                    @init="editorInit" lang="c_cpp" theme="textmate"
                                                                    style="height: 300px" :options="{
                                                                        enableBasicAutocompletion: true,
                                                                        enableSnippets: true,
                                                                        enableLiveAutocompletion: true,
                                                                        fontSize: 18,
                                                                        tabSize: 4,
                                                                        showPrintMargin: false,
                                                                        highlightActiveLine: true,
                                                                    }" />
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <div class="error-message">{{ bot.error_message }}</div>
                                                            <button type="button" class="btn btn-secondary"
                                                                data-bs-dismiss="modal">Cancel</button>
                                                            <button type="button" class="btn btn-primary"
                                                                @click="edit_bot(bot)">Save</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <button type="button" class="btn btn-danger"
                                            data-bs-toggle="modal" data-bs-target="#delete-bot-btn">Delete</button>
                                            <!-- Modal -->
                                            <div class="modal fade" id="delete-bot-btn" tabindex="-1">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h1 class="modal-title fs-5" id="delete-bot-btn">Delete this {{ bot.title }}</h1>
                                                            <button type="button" class="btn-close"
                                                                data-bs-dismiss="modal" aria-label="Close"></button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <span color="red"> Are you sure you want to delete? </span>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary"
                                                                data-bs-dismiss="modal">Close</button>
                                                            <button type="button" class="btn btn-primary" @click="remove_bot(bot)">Delete</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </template>

                                        <template v-else>
                                            <button type="button" class="btn btn-success"
                                                @click="restore_bot(bot)">Restore</button>
                                        </template>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from 'vue'
import $ from 'jquery'
import { useStore } from 'vuex'
import { VAceEditor } from 'vue3-ace-editor'
import ace from 'ace-builds'
import 'ace-builds/src-noconflict/mode-c_cpp'
import 'ace-builds/src-noconflict/mode-json'
import 'ace-builds/src-noconflict/theme-chrome'
import 'ace-builds/src-noconflict/ext-language_tools'
import { Modal } from 'bootstrap/dist/js/bootstrap'

export default {
    components: { VAceEditor },
    setup() {
        ace.config.set("basePath", "https://cdn.jsdelivr.net/npm/ace-builds@" + require("ace-builds").version + "/src-noconflict/")

        const store = useStore()
        const bots = ref([])
        const showingDeleted = ref(false)

        const botadd = reactive({
            title: "",
            description: "",
            content: "",
            error_message: "",
        })

        const refresh_bots = () => {
            showingDeleted.value = false
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp
                }
            })
        }

        const deleted_bots = () => {
            showingDeleted.value = true
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/deleted/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp
                }
            })
        }

        const restore_bot = (bot) => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/restore/",
                type: "post",
                data: { bot_id: bot.id },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        showingDeleted.value = false;
                        refresh_bots()
                    }
                }
            })
        }

        const add_bot = () => {
            botadd.error_message = ""
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/add/",
                type: "post",
                data: {
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        botadd.title = ""
                        botadd.description = ""
                        botadd.content = ""
                        Modal.getInstance("#add-bot-btn").hide()
                        refresh_bots()
                    } else {
                        botadd.error_message = resp.error_message
                    }
                },
                error(resp) {
                    console.log(resp)
                }
            })
        }

        const edit_bot = (bot) => {
            bot.error_message = ""
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/update/",
                type: "post",
                data: {
                    bot_id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance('#edit-bot-modal-' + bot.id).hide()
                        refresh_bots()
                    } else {
                        bot.error_message = resp.error_message
                    }
                },
                error(resp) {
                    console.log(resp)
                }
            })
        }

        const remove_bot = (bot) => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/remove/",
                type: "post",
                data: {
                    bot_id: bot.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance("#delete-bot-btn").hide()
                        refresh_bots()
                    }
                }
            })
        }

        refresh_bots()

        return {
            bots,
            botadd,
            showingDeleted,
            add_bot,
            remove_bot,
            edit_bot,
            restore_bot,
            refresh_bots,
            deleted_bots,
        }
    }
}
</script>

<style scoped>
.card {
    border-radius: 1rem;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
}

img.user-photo {
    border-radius: 1rem;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.card-header {
    background-color: #f8f9fa;
    border-bottom: 1px solid #dee2e6;
}

.card-body table th {
    font-size: 110%;
    color: #495057;
}

.card-body table td {
    vertical-align: middle;
}

.modal-header {
    background-color: #f1f3f5;
    border-bottom: 1px solid #dee2e6;
}

.modal-title {
    font-weight: 600;
    font-size: 1.25rem;
}

textarea, input {
    border-radius: 0.5rem;
}

div.error-message {
    color: #dc3545;
    font-weight: 500;
}

.btn + .btn {
    margin-left: 0.5rem;
}
</style>