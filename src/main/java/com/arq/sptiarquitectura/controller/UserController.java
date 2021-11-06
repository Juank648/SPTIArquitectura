package com.arq.sptiarquitectura.controller;
import javax.validation.Valid;

import com.arq.sptiarquitectura.ChangePasswordForm;
import com.arq.sptiarquitectura.entity.UserSPTI;
import com.arq.sptiarquitectura.repository.RolRepository;
import com.arq.sptiarquitectura.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


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

    @GetMapping("/editUser/{id}")
    public String getEditUserForm(Model model, @PathVariable(name = "id") Long id) throws Exception{
        UserSPTI userToEdit = userService.getUserById(id);
        model.addAttribute("userForm", userToEdit);
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("formTab","active");
        model.addAttribute("editMode", "true");
        model.addAttribute("passwordForm",new ChangePasswordForm(id));
        return "user-form/user-view";
    }

    @PostMapping("/editUser")
    public String postEditUserForm(@Valid @ModelAttribute("userForm")UserSPTI userSPTI, BindingResult result, ModelMap model) throws Exception{
        if (result.hasErrors()){
            model.addAttribute("userForm", userSPTI);
            model.addAttribute("formTab","active");
            model.addAttribute("editMode", "true");
            model.addAttribute("passwordForm",new ChangePasswordForm(userSPTI.getId()));
        } else {
            try {
                userService.updateUser(userSPTI);
                model.addAttribute("userForm", new UserSPTI());
                model.addAttribute("listTab","active");
            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("userForm", userSPTI);
                model.addAttribute("formTab","active");
                model.addAttribute("userList", userService.getAllUsers());
                model.addAttribute("roles",roleRepository.findAll());
                model.addAttribute("editMode", "true");
                model.addAttribute("passwordForm",new ChangePasswordForm(userSPTI.getId()));
            }

        }

        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("roles",roleRepository.findAll());
        return "user-form/user-view";

    }

    @GetMapping("/userForm/cancel")
    public String cancelEditUser(ModelMap model){
        return "redirect:/userForm";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(Model model, @PathVariable(name = "id") Long id) {
        try{
            userService.deleteUser(id);
        } catch (Exception e){
            model.addAttribute("listErrorMessage", e.getMessage());
        }
        return userForm(model);
    }

    @PostMapping("/editUser/changePassword")
    public ResponseEntity postEditUseChangePassword(@Valid @RequestBody ChangePasswordForm form, Errors errors) {
        try {
            //If error, just return a 400 bad request, along with the error message
            if (errors.hasErrors()) {
                String result = errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(""));

                throw new Exception(result);
            }
            userService.changePassword(form);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("success");
    }

}
