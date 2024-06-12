package com.progettosweng.application.service;

import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    /**
     * Salva un utente nel repository.
     * @param user l'utente da salvare
     * @return l'utente salvato
     */
    public User saveUser(User user) {
        return repository.save(user);
    }

    /**
     * Trova un utente utilizzando lo username.
     * @param username lo username dell'utente da trovare
     * @return l'utente trovato
     */
    public User getUser(String username) {
        return repository.findByUsername(username);
    }

    /**
     * Verifica se esiste un utente con lo username specificato.
     * @param username lo username dell'utente da verificare
     * @return true se l'utente esiste, altrimenti false
     */
    public boolean existsUserByUsername(String username) {
        return repository.existsByUsername(username);
    }
}
