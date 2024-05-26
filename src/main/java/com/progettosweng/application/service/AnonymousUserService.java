package com.progettosweng.application.service;

import com.progettosweng.application.entity.AnonymousUser;
import com.progettosweng.application.repository.AnonymousUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnonymousUserService {

    @Autowired
    private AnonymousUserRepository anonymousUserRepository;

    public AnonymousUser saveUser(AnonymousUser anonymousUser){ return anonymousUserRepository.save(anonymousUser);}
}
