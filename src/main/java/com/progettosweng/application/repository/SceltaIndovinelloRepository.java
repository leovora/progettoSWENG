package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.SceltaIndovinello;
import com.progettosweng.application.entity.Storia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità SceltaIndovinello.
 */
@Repository
public interface SceltaIndovinelloRepository extends JpaRepository<SceltaIndovinello, Integer> {

    /**
     * Trova una scelta indovinello per ID collegamento.
     * @param idCollegamento l'ID del collegamento
     * @return la scelta indovinello trovata
     */
    SceltaIndovinello findByIdCollegamento(int idCollegamento);

    /**
     * Elimina tutte le scelte indovinello associate alla storia specificata.
     * @param storia la storia per cui eliminare le scelte indovinello
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM SceltaIndovinello s WHERE s.scenario1.storia = :storia OR s.scenario2.storia = :storia")
    void deleteSceltaIndovinelloByStoria(Storia storia);
}
