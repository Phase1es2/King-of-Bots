package com.kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import com.kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "username is empty");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            map.put("error_message", "password or confirmed password is empty");
            return map;
        }
        username = username.trim();
        if (username.length() == 0) {
            map.put("error_message", "username is invalid");
            return map;
        }

        if (password.length() == 0 || confirmedPassword.length() == 0) {
            map.put("error_message", "password is empty");
            return map;
        }

        if (username.length() > 100) {
            map.put("error_message", "username is too long, should be limited in 100 characters");
            return map;
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "password is too long, should be limited in 100 characters");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "password does not match confirmed password, try again");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "username is exist");
            return map;
        }
        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pokemon.com%2Fus%2Fpokedex%2Fgengar&psig=AOvVaw2Uje3lxHZE4K2hZ6tG9nnZ&ust=1743001555428000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCJiemPbApYwDFQAAAAAdAAAAABAE";
        User user = new User(null, username, encodedPassword, photo);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;
    }
}
