package com.progettosweng.application.service;

import com.progettosweng.application.entity.SceltaIndovinello;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.SceltaIndovinelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceltaIndovinelloService {

    @Autowired
    private SceltaIndovinelloRepository sceltaIndovinelloRepository;

    /**
     * Salva una scelta indovinello nel database.
     * @param scelta la scelta indovinello da salvare
     * @return la scelta indovinello salvata
     */
    public SceltaIndovinello saveSceltaIndovinello(SceltaIndovinello scelta) {
        return sceltaIndovinelloRepository.save(scelta);
    }

    /**
     * Ottiene la scelta indovinello associata a un collegamento dato l'ID del collegamento.
     * @param idCollegamento l'ID del collegamento
     * @return la scelta indovinello associata al collegamento
     */
    public SceltaIndovinello getSceltaIndovinelloCollegamento(int idCollegamento) {
        return sceltaIndovinelloRepository.findByIdCollegamento(idCollegamento);
    }

    /**
     * Elimina le scelte indovinello associate a una storia dal database.
     * @param storia la storia di cui eliminare le scelte indovinello
     */
    public void deleteSceltaIndovinelloByStoria(Storia storia) {
        sceltaIndovinelloRepository.deleteSceltaIndovinelloByStoria(storia);
    }
}
