package com.example.social.springsocial.controller;

import com.example.social.springsocial.mode.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String home(Model model) {
        model.addAttribute("user", new UserInfo());
        model.addAttribute("errormessage", "");
        return "home";
    }

    @GetMapping("/loginfailure")
    public String loginFail(Model model) {
        model.addAttribute("user", new UserInfo());
        model.addAttribute("errormessage", "please enter correct  email and password");
        return "home";
    }

}
