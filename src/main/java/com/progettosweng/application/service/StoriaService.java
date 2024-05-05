package com.progettosweng.application.service;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

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

    public Boolean existsStoria(int idStoria) { return repository.existsById(idStoria); }

    public ArrayList<Storia> getAllStorie() {
        Collection<Storia> storie = repository.findAll();
        return new ArrayList<>(storie);
    }

}
