package com.progettosweng.application.service;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.ScenarioRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ScenarioService {

    @Autowired
    private ScenarioRepository repository;

    /**
     * Salva uno scenario nel database.
     * @param scenario lo scenario da salvare
     * @return lo scenario salvato
     */
    public Scenario saveScenario(Scenario scenario) {
        return repository.save(scenario);
    }

    /**
     * Elimina uno scenario associato a una storia dal database.
     * @param storia la storia di cui eliminare lo scenario
     */
    public void deleteScenarioByIdStoria(Storia storia) {
        repository.deleteByStoria(storia);
    }

    /**
     * Ottiene uno scenario dato il suo ID.
     * @param idScenario l'ID dello scenario
     * @return lo scenario trovato, o null se non trovato
     */
    public Scenario getScenario(int idScenario) {
        return repository.findById(idScenario).orElse(null);
    }

    /**
     * Verifica se esiste uno scenario con l'ID dato.
     * @param idScenario l'ID dello scenario
     * @return true se esiste uno scenario con l'ID dato, altrimenti false
     */
    public Boolean existsScenario(int idScenario) {
        return repository.existsById(idScenario);
    }

    /**
     * Ottiene tutti gli scenari presenti nel database.
     * @return un'ArrayList contenente tutti gli scenari
     */
    public ArrayList<Scenario> getAllScenari() {
        Collection<Scenario> scenari = repository.findAll();
        return new ArrayList<>(scenari);
    }

    /**
     * Ottiene tutti gli scenari associati a una storia.
     * @param storia la storia di cui ottenere gli scenari
     * @return una lista di scenari associati alla storia
     */
    @Transactional
    public List<Scenario> getScenariByStoria(Storia storia) {
        return repository.findByStoria(storia);
    }

    /**
     * Imposta uno scenario come primo scenario di una storia nel database.
     * @param scenario lo scenario da impostare come primo
     */
    public void setPrimoScenario(Scenario scenario) {
        scenario.setPrimoScenario(true);
        repository.save(scenario);
    }

    /**
     * Ottiene il primo scenario di una storia nel database.
     * @param storia la storia di cui trovare il primo scenario
     * @return il primo scenario della storia
     */
    public Scenario getPrimoScenario(Storia storia) {
        return repository.getPrimoScenario(storia);
    }

    /**
     * Ottiene gli scenari filtrati per un dato testo e una data storia.
     * @param filtro il testo da usare come filtro
     * @param storia l'ID della storia a cui gli scenari devono appartenere
     * @return una lista di scenari che soddisfano i criteri di filtro
     */
    public List<Scenario> getScenariFiltro(String filtro, int storia) {
        return repository.getScenariFiltro(filtro, storia);
    }
}
