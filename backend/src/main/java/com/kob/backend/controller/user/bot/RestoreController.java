package com.kob.backend.controller.user.bot;


import com.kob.backend.service.user.bot.RestoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RestoreController {
    @Autowired
    private RestoreService restoreService;

    @PostMapping("/user/bot/restore/")
    public Map<String, String> restore(@RequestParam Map<String, String> data) {
        return restoreService.restore(data);
    }

    /*
    *        $.ajax({
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
            })
    * */
}
