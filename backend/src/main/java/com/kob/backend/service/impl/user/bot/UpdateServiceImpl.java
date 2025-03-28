package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.bot.UpdateService;
import com.kob.backend.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> update(Map<String, String> data) {
        User user = UserUtil.getLoginUser();
        int bot_id = Integer.parseInt(data.get("bot_id"));
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");
        Map <String, String> map = new HashMap<>();

        if (title == null || title.isEmpty()) {
            map.put("error_message", "title is empty");
            return map;
        }

        if (title.length() > 100) {
            map.put("error_message", "Your title is too long, it should be within 100");
            return map;
        }

        if (description == null || description.isEmpty()) {
            description = "You lazy my friend";
        }

        if (description.length() > 300) {
            map.put("error_message", "Your description is too long, it should be within 300");
            return map;
        }

        if (content == null || content.isEmpty()) {
            map.put("error_message", "code is empty");
            return map;
        }

        if (content.length() > 10000) {
            map.put("error_message", "Your code is too long, it should be within 10000");
            return map;
        }
        Bot bot = botMapper.selectById(bot_id);
        if (bot == null || bot.getIsDeleted()) {
            map.put("error_message", "Bot does not exist or already delete");
            return map;
        }

        if(!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "Unauthorized User");
            return map;
        }
        Date now = new Date();
        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getRating(),
                bot.getCreatetime(),
                now,
                bot.getIsDeleted()
        );

        botMapper.updateById(new_bot);
        map.put("error_message", "success");
        return map;
    }
}
