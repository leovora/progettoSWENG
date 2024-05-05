package com.progettosweng.application.service;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.ScenarioRepository;
import com.progettosweng.application.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class StoriaService {

    @Autowired
    private StoriaRepository repository;

    @Autowired
    private ScenarioService scenarioService;


    public Storia saveStoria(Storia storia){
        return repository.save(storia);
    }

    @Transactional
    public void deleteStoria(Storia storia){
        scenarioService.deleteScenarioByIdStoria(storia);
        repository.delete(storia);
    }

    public Storia getStoria(int idStoria){
        return repository.findById(idStoria).orElse(null);
    }

    public Boolean existsStoria(int idStoria) {
        return repository.existsById(idStoria);
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public ArrayList<Storia> getAllStorie() {
        Collection<Storia> storie = repository.findAll();
        return new ArrayList<>(storie);
    }

    public User getCreatore(int idStoria){
        return repository.findById(idStoria).get().getCreatore();
    }

    public ArrayList<Storia> getStorieUtente(String user){
        return repository.findByUsername(user);
    }

}
