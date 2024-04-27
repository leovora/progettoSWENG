package com.progettosweng.application.service;

import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User saveUser(User user){
        return repository.save(user);
    }

    public User getUser(String email){
        return repository.findById(email).orElse(null);
    }
}
