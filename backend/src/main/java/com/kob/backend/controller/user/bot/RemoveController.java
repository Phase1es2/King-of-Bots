package com.kob.backend.controller.user.bot;


import com.kob.backend.service.user.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveController {
    @Autowired
    private RemoveService removeService;

    @PostMapping("/user/bot/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeService.remove(data);
    }
    /*
    $.ajax({
        url: "http://127.0.0.1:3000/user/bot/remove/",
                type: "post",
                data: {
            bot_id: 4,
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

}
