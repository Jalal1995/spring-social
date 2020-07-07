package com.example.social.springsocial.service;

import com.example.social.springsocial.mode.UserInfo;
import com.example.social.springsocial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserInfo save(UserInfo userInfo) {
        userInfo.setEnabled(true);
        if (StringUtils.hasText(userInfo.getPassword())) {
            userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        }
        return userRepository.save(userInfo);
    }

    public Optional<UserInfo> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void update(UserInfo dbUser) {
        userRepository.save(dbUser);
    }
}
