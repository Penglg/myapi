package com.lgp.myapinterface.controller;

import com.lgp.myapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : lgp
 */
@RestController
@RequestMapping("/name")
public class NameController {
        @GetMapping("/get")
        public String getNameByGet(String name) {
            return "GET 你的名字是" + name;
        }

        @PostMapping("/post")
        public String getNameByPost(@RequestParam String name){
            return "POST 获取的名字" + name ;
        }

        @PostMapping("/user")
        public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
            return "POST 用户名字是" + user.getUsername();
        }
}
