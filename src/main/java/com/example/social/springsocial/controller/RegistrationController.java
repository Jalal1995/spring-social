package com.example.social.springsocial.controller;

import com.example.social.springsocial.mode.UserInfo;
import com.example.social.springsocial.service.SecurityService;
import com.example.social.springsocial.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;


@Controller
@RequiredArgsConstructor
@Log4j2
public class RegistrationController {

    private final UserService userService;
    private final SecurityService securityService;

    @PostMapping("/register")
    public String registration(@ModelAttribute UserInfo userInfo, HttpServletRequest req, Model model) {
        String password = userInfo.getPassword();
        userInfo.setRole("ADMIN");
        UserInfo dbUser = userService.save(userInfo);
        securityService.autoLogin(dbUser.getEmail(), password, dbUser.getRole(), req);
        model.addAttribute("user", dbUser);

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> grantedAuthorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
        while (iterator.hasNext()) {
            log.info(iterator.next());
        }
        log.info(name);
        return "user-profile";

    }


}
