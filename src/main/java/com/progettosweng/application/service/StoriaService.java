package com.progettosweng.application.service;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoriaService {

    @Autowired
    private StoriaRepository repository;

    public Storia saveStoria(Storia storia){
        return repository.save(storia);
    }

    public Storia getStoria(int idStoria){
        return repository.findById(idStoria).orElse(null);
    }

}
