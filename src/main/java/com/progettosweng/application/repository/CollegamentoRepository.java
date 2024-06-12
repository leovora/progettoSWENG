package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository per l'entità Collegamento.
 */
@Repository
public interface CollegamentoRepository extends JpaRepository<Collegamento, Integer> {

    /**
     * Trova tutti i collegamenti per lo scenario specificato.
     * @param scenario lo scenario per cui trovare i collegamenti
     * @return una lista di collegamenti
     */
    @Query("SELECT c FROM Collegamento c WHERE c.scenario1 = :scenario")
    List<Collegamento> findByScenario1(Scenario scenario);

    /**
     * Elimina tutti i collegamenti associati alla storia specificata.
     * @param storia la storia per cui eliminare i collegamenti
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Collegamento c WHERE c.scenario1 IN (SELECT s FROM Scenario s WHERE s.storia = :storia) OR c.scenario2 IN (SELECT s FROM Scenario s WHERE s.storia = :storia)")
    void deleteAllByStoria(Storia storia);
}
