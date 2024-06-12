package com.progettosweng.application.repository;

import com.progettosweng.application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository per l'entità User.
 */
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Verifica se esiste un utente con l'username specificato.
     * @param username l'username da verificare
     * @return true se esiste un utente con l'username specificato, altrimenti false
     */
    boolean existsByUsername(String username);

    /**
     * Trova un utente per username.
     * @param username l'username dell'utente da trovare
     * @return l'utente trovato
     */
    User findByUsername(String username);
}
