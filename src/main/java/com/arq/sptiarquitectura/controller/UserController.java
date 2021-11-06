package com.arq.sptiarquitectura.controller;
import javax.validation.Valid;

import com.arq.sptiarquitectura.entity.UserSPTI;
import com.arq.sptiarquitectura.repository.RolRepository;
import com.arq.sptiarquitectura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




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

    @PostMapping("/userForm")
    public String createUser(@Valid @ModelAttribute("userForm")UserSPTI userSPTI, BindingResult result, ModelMap model){
        if (result.hasErrors()){
            model.addAttribute("userForm", userSPTI);
            model.addAttribute("formTab","active");
        } else {
            try {
                userService.createUser(userSPTI);
                model.addAttribute("userForm", new UserSPTI());
                model.addAttribute("listTab","active");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", userSPTI);
                model.addAttribute("formTab","active");
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("roles",roleRepository.findAll());
            }

        }

        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles",roleRepository.findAll());
        return "user-form/user-view";

    }

}
