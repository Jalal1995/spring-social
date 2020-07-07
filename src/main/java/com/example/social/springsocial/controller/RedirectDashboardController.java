package com.example.social.springsocial.controller;

import com.example.social.springsocial.mode.UserInfo;
import com.example.social.springsocial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class RedirectDashboardController {

    private final UserService userService;

    @GetMapping("/redirectdashboard")
    public String redirectDash(Principal principal) {
        String redirectUrl = "";
        Optional<UserInfo> opDbUser = userService.findByEmail(principal.getName());

        if (opDbUser.isPresent() && StringUtils.hasText(opDbUser.get().getRole())) {
            if (opDbUser.get().getRole().equalsIgnoreCase("ADMIN")) {
                redirectUrl = "redirect:/admin/dashboard";
            } else if (opDbUser.get().getRole().equalsIgnoreCase("USER")) {
                redirectUrl = "redirect:/user/dashboard";
            }
        }
        return redirectUrl;
    }
}
