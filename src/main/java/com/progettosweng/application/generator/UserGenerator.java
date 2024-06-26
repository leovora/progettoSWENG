package com.progettosweng.application.generator;

import com.progettosweng.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.progettosweng.application.entity.User;

/**
 * Classe che genera nel database degli utenti di prova
 */

@Component
public class UserGenerator implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        //generateUsers();
    }


    private void generateUsers() {

        User user1 = new User("admin", "123", "Admin", "Admin");
        User user2 = new User("user", "123", "User", "User");

        userService.saveUser(user1);
        userService.saveUser(user2);

        System.out.println("Utenti generati e salvati nel database.");
    }
}

