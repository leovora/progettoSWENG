package com.progettosweng.application.service;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.repository.CollegamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegamentoService {

    @Autowired
    private CollegamentoRepository collegamentoRepository;

    /**
     * Elimina un collegamento dal database dato il suo ID.
     * @param id l'ID del collegamento da eliminare
     */
    public void deleteCollegamento(int id) {
        collegamentoRepository.deleteById(id);
    }

    /**
     * Elimina tutti i collegamenti associati a una storia dal database.
     * @param storia la storia di cui eliminare i collegamenti
     */
    @Transactional
    public void deleteCollegamentiByStoria(Storia storia) {
        collegamentoRepository.deleteAllByStoria(storia);
    }

    /**
     * Trova un collegamento dato il suo ID.
     * @param id l'ID del collegamento da trovare
     * @return il collegamento trovato, o null se non trovato
     */
    public Collegamento findCollegamentoById(int id) {
        return collegamentoRepository.findById(id).orElse(null);
    }

    /**
     * Ottiene tutti i collegamenti associati a uno scenario.
     * @param scenario lo scenario di cui ottenere i collegamenti
     * @return la lista dei collegamenti associati allo scenario
     */
    public List<Collegamento> getCollegamentoByScenario(Scenario scenario) {
        return collegamentoRepository.findByScenario1(scenario);
    }

    /**
     * Esegue la scelta associata a un collegamento.
     * @param collegamento il collegamento di cui eseguire la scelta
     * @return lo scenario risultante dalla scelta
     */
    public Scenario eseguiScelta(Collegamento collegamento) {
        return collegamento.eseguiScelta();
    }

    /**
     * Imposta l'oggetto richiesto per un collegamento.
     * @param idCollegamento l'ID del collegamento
     * @param oggetto l'oggetto da impostare come richiesto
     */
    public void setOggettoRichiesto(int idCollegamento, Oggetto oggetto) {
        Collegamento collegamento = findCollegamentoById(idCollegamento);
        collegamento.setOggettoRichiesto(oggetto);
        collegamentoRepository.save(collegamento);
    }
}
