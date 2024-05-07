package com.progettosweng.application.service;

import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    //salva utente
    public User saveUser(User user){
        return repository.save(user);
    }

    //ritorna utente cercando username
    public User getUser(String username){
        return repository.findById(username).orElse(null);
    }

    //true se esiste utente con username dato in input
    public boolean existsUserByUsername(String username) {
        return repository.existsByUsername(username);

    }
}
