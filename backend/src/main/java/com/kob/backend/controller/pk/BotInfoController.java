package com.kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {

    @RequestMapping("getBotInfo/")
    public List<Map<String, String>> getBotInfo() {
        List<Map<String, String>> list = new LinkedList<>();
        Map<String, String> mp = new HashMap<>();
        mp.put("name", "tiger");
        mp.put("rank", "1500");
        Map<String, String> mp2 = new HashMap<>();
        mp2.put("name", "tiger12321");
        mp2.put("rank", "15001111");
        list.add(mp);
        list.add(mp2);
        return list;
    }
}
