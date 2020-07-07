package com.example.social.springsocial.controller;

import com.example.social.springsocial.mode.UserInfo;
import com.example.social.springsocial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/dashboard")
    public String userdashboard(Principal principal, Model model) {

        UserInfo dbUser = userService.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException(String.format("there is no user with email: %s", principal.getName())));
        model.addAttribute("user", dbUser);
        return "user-profile";
    }

}
