package com.example.social.springsocial.controller;

import com.example.social.springsocial.mode.UserInfo;
import com.example.social.springsocial.service.FacebookService;
import com.example.social.springsocial.service.SecurityService;
import com.example.social.springsocial.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
public class SpringFacebookController {

    private final FacebookService facebookService;
    private final UserService userService;
    private final SecurityService securityService;

    @GetMapping("/facebooklogin")
    public RedirectView facebookLogin() {
        RedirectView redirectView = new RedirectView();
        String url = facebookService.facebookLogin();
        log.info(url);
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping("/facebook")
    public String facebook(@RequestParam("code") String code) {
        String accessToken = facebookService.getFacebookAccessToken(code);
        return "redirect:/facebookprofiledata/" + accessToken;

    }

    @GetMapping("/facebookprofiledata/{accessToken:.+}")
    public String facebookProfileData(@PathVariable String accessToken, Model model, HttpServletRequest req) {

        User user = facebookService.getFacebookUserProfile(accessToken);
        Optional<UserInfo> optionalUserDb = userService.findByEmail(user.getEmail());
        String role = "USER";
        if (optionalUserDb.isPresent()) {
            UserInfo dbUser = optionalUserDb.get();
            dbUser.setFirstName(user.getFirstName());
            dbUser.setLastName(user.getLastName());
            userService.update(dbUser);
            role = dbUser.getRole();
            model.addAttribute("user", dbUser);
        } else {
            UserInfo userInfo = UserInfo.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .enabled(true)
                    .role("USER")
                    .build();
            userService.save(userInfo);
            role = userInfo.getRole();
            model.addAttribute("user", userInfo);
        }

        securityService.autoLogin(user.getEmail(), null, role, req); // TODO why password is null?

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Collection<? extends GrantedAuthority> grantedAuthorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = grantedAuthorities.iterator();
        while (iterator.hasNext()) {
            log.info(iterator.next());
        }
        log.info(name);
        return "redirect:/redirectdashboard";
    }
}
