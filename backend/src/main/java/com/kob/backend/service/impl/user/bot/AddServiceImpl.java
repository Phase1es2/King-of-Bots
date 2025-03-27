package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.UserDetailsImpl;
import com.kob.backend.service.user.bot.AddService;
import com.kob.backend.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class AddServiceImpl implements AddService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        /*
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();*/
        User user = UserUtil.getLoginUser();

        /* in the database id is auto increase;
            we got user id above, rating is default as 1500;
            createtime and modifytime is now

            title, description, content
        */
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

        Date now = new Date();
        Bot bot = new Bot(null, user.getId(), title, description, content, 1500, now, now, false);

        // insert into database
        botMapper.insert(bot);
        map.put("error_message", "success");

        return map;
    }
}
