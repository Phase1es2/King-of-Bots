package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.bot.RemoveService;
import com.kob.backend.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {
    @Autowired
    private BotMapper botMapper;
    @Override
    public Map<String, String> remove(Map<String, String> data) {
        User user = UserUtil.getLoginUser();
        int bot_id = Integer.parseInt(data.get("bot_id"));
        Bot bot = botMapper.selectById(bot_id);

        Map<String, String> map = new HashMap<>();

        if (bot == null || bot.getIsDeleted()) {
            map.put("error_message", "Bot does not exist, or already delete");
            return map;
        }

        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "UnAuthorized User");
            return map;
        }

        // botMapper.deleteById(bot_id);

        Date now = new Date();
        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                bot.getTitle(),
                bot.getDescription(),
                bot.getContent(),
                bot.getRating(),
                bot.getCreatetime(),
                now,
                true
        );
        botMapper.updateById(new_bot);
        map.put("error_message", "success");

        return map;
    }
}
