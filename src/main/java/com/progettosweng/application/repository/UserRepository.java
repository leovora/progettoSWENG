package com.progettosweng.application.repository;

import com.progettosweng.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    //true se esiste utente con username dato in input
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
