package com.kob.backend.controller.user.account;

import com.kob.backend.service.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    /* The reason we inject an interface instead of a concrete class:
     *
     * Decoupling: Injecting interfaces instead of concrete classes helps decouple the code.
     * An interface defines a contract, and its implementation can change freely without affecting
     * the code that depends on it. This improves the flexibility and maintainability of the system.
     *
     * Replaceability: By injecting an interface, we can easily replace the concrete implementation
     * without modifying the dependent code.
     *
     */

    // @RequestParam is used to bind parameters from a web request to the parameters of a controller method.
// When an HTTP request is received, Spring uses the value of the @RequestParam annotation to extract
// the corresponding parameter from the request and convert it to the method’s argument.
    @PostMapping("/user/account/token/")
    public Map<String, String> getToken(@RequestParam Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        System.out.println(username + " " + password);
        return loginService.getToken(username, password);
    }

    /* test code in frontend
      setup() {
    $.ajax({
      url: "http://127.0.0.1:3000/user/account/token/",
      type: "post",
      data: {
        username: 'yh',
        password: "pyh",
      },
      success(resp) {
        console.log(resp);
      },
      error(resp){
        console.log(resp);
      }
    })
  }

     */

}
