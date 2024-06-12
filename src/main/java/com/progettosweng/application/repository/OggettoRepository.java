package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Oggetto.
 */
@Repository
public interface OggettoRepository extends JpaRepository<Oggetto, Integer> {

    /**
     * Trova tutti gli oggetti associati a una storia specificata.
     * @param storia la storia
     * @return una lista di oggetti
     */
    List<Oggetto> findByStoria(Storia storia);

    /**
     * Trova tutti gli oggetti associati a uno scenario specificato.
     * @param scenario lo scenario
     * @return una lista di oggetti
     */
    List<Oggetto> findByScenario(Scenario scenario);

    /**
     * Elimina tutti gli oggetti associati a una storia specificata.
     * @param storia la storia
     */
    void deleteByStoria(Storia storia);
}
