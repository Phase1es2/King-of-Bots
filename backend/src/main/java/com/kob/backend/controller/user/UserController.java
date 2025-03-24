package com.kob.backend.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/user/all/")
    public List<User> getAll() {
        return userMapper.selectList(null);
    }
    // get user by id;
    @GetMapping("/user/{userId}/")
    public User getuser(@PathVariable int userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);
        // return userMapper.selectById(userId);
        return userMapper.selectOne(queryWrapper);
    }

    // insert use Post;
    @GetMapping("/user/add/{userId}/{username}/{password}/")
    public String addUser(@PathVariable int userId,
                          @PathVariable String username,
                          @PathVariable String password) {
        if (password.length() <= 6) {
            return "password is too short!";
        }

      /*  if (!strongPasswordChecker(password)) {
        String message =
            "It has at least 8 characters.\n" +
            "It contains at least one lowercase letter.\n" +
            "It contains at least one uppercase letter.\n" +
            "It contains at least one digit.\n" +
            "It contains at least one special character. The special characters are the characters in the following string: \"!@#$%^&*()-+\".";
            return message;
        }
        */

        // change the password to encoded password and store it in database
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        User user = new User(userId, username, encodedPassword);
        userMapper.insert(user);
        return "Add User Successfuly";
    }
    @GetMapping("/user/delete/{userId}/")
    public String deleteUser(@PathVariable int userId) {
        userMapper.deleteById(userId);
        return "del successfully";
    }

    public boolean strongPasswordChecker(String password) {
        if (password.length() < 8) return false;
        byte lowercase = 0, uppercase = 0, digit = 0, specialCharacter = 0;

        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (i < password.length() - 1) {
                if (ch == password.charAt(i + 1)) {
                    return false;
                }
            }
            if (ch >= 'a' && ch <= 'z') {
                lowercase++;
            }else if (ch >= 'A' && ch <= 'Z') {
                uppercase++;
            } else if (ch >= '0' && ch <= '9') {
                digit++;
            }else {
                specialCharacter++;
            }
        }
        return lowercase > 0 && uppercase > 0 && digit > 0 && specialCharacter > 0 ? true : false;
    }

}
