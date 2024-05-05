package com.progettosweng.application.service;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class ScenarioService {

    @Autowired
    private ScenarioRepository repository;

    public Scenario saveScenario(Scenario scenario){ return repository.save(scenario); }

    public void deleteScenarioByIdStoria(Storia storia) {
        repository.deleteByStoria(storia);
    }

    public Scenario getScenario(int idScenario){
        return repository.findById(idScenario).orElse(null);
    }

    public Boolean existsScenario(int idScenario) { return repository.existsById(idScenario); }

    public ArrayList<Scenario> getAllScenari() {
        Collection<Scenario> scenari = repository.findAll();
        return new ArrayList<>(scenari);
    }
}
