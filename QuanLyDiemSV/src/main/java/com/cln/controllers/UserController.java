/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cln.controllers;

import com.cln.pojo.Teacher;
import com.cln.pojo.Typegrade;
import com.cln.pojo.User;
import com.cln.services.TeacherSevice;
import com.cln.services.UserService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author LE NGAN
 */
@Controller
public class UserController {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private TeacherSevice teacherSevice;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String listUser(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @ModelAttribute(value = "user") User u) {
        String errMsg = "";
        if (u.getPassword().equals(u.getConfirmPassword())) {
            if (this.userDetailsService.registerUser(u) == true) {
                return "redirect:/login";
            } else {
                errMsg = "Lỗi!!!";
            }
        } else {
            errMsg = "Mật khẩu không khớp!";
        }

        model.addAttribute("errMsg", errMsg);
        return "register";
    }

    @GetMapping("/admin/users")
    public String listUser() {
        return "users";
    }

    @GetMapping("/admin/users/list")
    public String listUser(Model model, @RequestParam("role") String role) {
        model.addAttribute("users", this.userDetailsService.getUsersByRole(role));
        return "listuser";
    }

    @GetMapping("/admin/users/add")
    public String getUser(Model model) {
        model.addAttribute("user", new User());
        List<Teacher> teachers = this.teacherSevice.getTeachersWithoutUser();
        model.addAttribute("teachers", teachers);
        return "addOrUpdateUser";
    }

    @PostMapping("/admin/users/add")
    public String addUser(Model model, @ModelAttribute(value = "user") User u) {
        String errMsg = "";
        if (u.getPassword().equals(u.getConfirmPassword())) {
            if (this.userDetailsService.addUser(u) == true) {
                return "redirect:/users";
            } else {
                errMsg = "Lỗi!!!";
            }
        } else {
            errMsg = "Mật khẩu không khớp!";
        }
       
        model.addAttribute("errMsg", errMsg);
        return "redirect:/admin/users/add";
    }
}
