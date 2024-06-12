package com.progettosweng.application.service;

import com.progettosweng.application.entity.SceltaSemplice;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.SceltaSempliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceltaSempliceService {

    @Autowired
    private SceltaSempliceRepository sceltaSempliceRepository;

    /**
     * Salva una scelta semplice nel database.
     * @param scelta la scelta semplice da salvare
     * @return la scelta semplice salvata
     */
    public SceltaSemplice saveSceltaSemplice(SceltaSemplice scelta) {
        return sceltaSempliceRepository.save(scelta);
    }

    /**
     * Elimina le scelte semplici associate a una storia dal database.
     * @param storia la storia di cui eliminare le scelte semplici
     */
    public void deleteSceltaSempliceByStoria(Storia storia) {
        sceltaSempliceRepository.deleteByStoria(storia);
    }
}
