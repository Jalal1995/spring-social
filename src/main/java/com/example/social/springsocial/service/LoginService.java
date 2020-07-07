package com.example.social.springsocial.service;

import com.example.social.springsocial.mode.UserInfo;
import com.example.social.springsocial.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByEmailAndEnabled(email, true)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("no user with email: %s", email)));
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        return new User(user.getEmail(),
                user.getPassword(),
                Collections.singletonList(authority)
                );
    }
}
