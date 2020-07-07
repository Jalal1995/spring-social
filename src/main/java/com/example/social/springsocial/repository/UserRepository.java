package com.example.social.springsocial.repository;

import com.example.social.springsocial.mode.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserInfo, Long> {


    Optional<UserInfo> findByEmailAndEnabled(String email, boolean enabled);

    Optional<UserInfo> findByEmail(String email);
}
