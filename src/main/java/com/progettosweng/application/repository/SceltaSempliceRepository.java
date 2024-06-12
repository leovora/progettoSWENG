package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.SceltaSemplice;
import com.progettosweng.application.entity.Storia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità SceltaSemplice.
 */
@Repository
public interface SceltaSempliceRepository extends JpaRepository<SceltaSemplice, Integer> {

    /**
     * Elimina tutte le scelte semplici associate alla storia specificata.
     * @param storia la storia per cui eliminare le scelte semplici
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM SceltaSemplice ss WHERE ss.scenario1.storia = :storia OR ss.scenario2.storia = :storia")
    void deleteByStoria(Storia storia);
}
