package com.cepheid.cloud.skel.service;

import com.cepheid.cloud.skel.model.User;
import com.cepheid.cloud.skel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    UserRepository repository;

    public User authenticate(String userEmail, String password) {
        return repository.findByEmailAndPassword(userEmail, password);
    }
}
