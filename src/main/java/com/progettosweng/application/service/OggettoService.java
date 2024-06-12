package com.progettosweng.application.service;

import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.OggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OggettoService {

    @Autowired
    private OggettoRepository oggettoRepository;

    /**
     * Salva un oggetto nel database.
     * @param oggetto l'oggetto da salvare
     */
    public void saveOggetto(Oggetto oggetto) {
        oggettoRepository.save(oggetto);
    }

    /**
     * Ottiene tutti gli oggetti associati a una storia.
     * @param storia la storia di cui ottenere gli oggetti
     * @return la lista degli oggetti associati alla storia
     */
    public List<Oggetto> getOggettiStoria(Storia storia) {
        return oggettoRepository.findByStoria(storia);
    }

    /**
     * Ottiene tutti gli oggetti associati a uno scenario.
     * @param scenario lo scenario di cui ottenere gli oggetti
     * @return la lista degli oggetti associati allo scenario
     */
    public List<Oggetto> getOggettiScenario(Scenario scenario) {
        return oggettoRepository.findByScenario(scenario);
    }

    /**
     * Elimina gli oggetti associati a una storia dal database.
     * @param storia la storia di cui eliminare gli oggetti
     */
    public void deleteOggettoByStoria(Storia storia) {
        oggettoRepository.deleteByStoria(storia);
    }
}
