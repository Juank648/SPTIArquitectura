package com.arq.sptiarquitectura.controller;

import com.arq.sptiarquitectura.entity.UserSPTI;
import com.arq.sptiarquitectura.repository.RolRepository;
import com.arq.sptiarquitectura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RolRepository roleRepository;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/userForm")
    public String userForm(Model model){
        model.addAttribute("userForm", new UserSPTI());
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("listTab","active");
        return "user-form/user-view";
    }

}
