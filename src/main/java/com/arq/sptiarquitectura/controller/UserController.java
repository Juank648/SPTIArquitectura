package com.arq.sptiarquitectura.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/userView")
    public String userForm(){
        return "user-form/user-view";
    }

}
