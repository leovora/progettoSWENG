package com.progettosweng.application.service;

import com.progettosweng.application.entity.AbstractUser;
import com.progettosweng.application.repository.AbstractUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbstractUserService {

    @Autowired
    private AbstractUserRepository abstractUserRepository;

    public AbstractUser saveUser(AbstractUser user){return abstractUserRepository.save(user);}
}
