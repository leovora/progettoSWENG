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

    //salva scenario
    public Scenario saveScenario(Scenario scenario){ return repository.save(scenario); }

    //elimina scenario associato a storia data in input
    public void deleteScenarioByIdStoria(Storia storia) {
        repository.deleteByStoria(storia);
    }

    //ritorna scenario cercando ID oppure null se non è presente
    public Scenario getScenario(int idScenario){
        return repository.findById(idScenario).orElse(null);
    }

    //true se esiste scenario con ID dato in input
    public Boolean existsScenario(int idScenario) { return repository.existsById(idScenario); }

    //ritorna arrayList con tutti gli scenari
    public ArrayList<Scenario> getAllScenari() {
        Collection<Scenario> scenari = repository.findAll();
        return new ArrayList<>(scenari);
    }
}
