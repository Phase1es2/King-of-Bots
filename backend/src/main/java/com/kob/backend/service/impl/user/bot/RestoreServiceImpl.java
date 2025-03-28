package com.kob.backend.service.impl.user.bot;


import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.bot.RestoreService;
import com.kob.backend.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RestoreServiceImpl implements RestoreService {
    @Autowired
    private BotMapper botMapper;


    @Override
    public Map<String, String> restore(Map<String, String> data) {
        User user = UserUtil.getLoginUser();
        int bot_id = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(bot_id);
        Map<String, String> map = new HashMap<>();

        if(!bot.getIsDeleted()) {
            map.put("error_message", "Bot is not deleted");
            return map;
        }

        if(!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "Unauthorized user");
            return map;
        }

        Date now = new Date();
        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                bot.getTitle(),
                bot.getDescription(),
                bot.getContent(),
                1500,
                bot.getCreatetime(),
                now,
                false
        );
        botMapper.updateById(new_bot);
        map.put("error_message", "success");

        return map;
    }
}
