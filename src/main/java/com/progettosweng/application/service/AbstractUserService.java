package com.progettosweng.application.service;

import com.progettosweng.application.entity.AbstractUser;
import com.progettosweng.application.repository.AbstractUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbstractUserService {

    @Autowired
    private AbstractUserRepository abstractUserRepository;

    /**
     * Salva un utente astratto nel repository.
     * @param user l'utente da salvare
     * @return l'utente salvato
     */
    public AbstractUser saveUser(AbstractUser user){return abstractUserRepository.save(user);}

    /**
     * Elimina un utente astratto dal repository.
     * @param user l'utente da eliminare
     */
    public void deleteUser(AbstractUser user) {abstractUserRepository.delete(user);}
}
