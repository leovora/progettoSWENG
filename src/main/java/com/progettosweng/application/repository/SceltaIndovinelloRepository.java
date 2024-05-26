package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.SceltaIndovinello;
import com.progettosweng.application.entity.Storia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SceltaIndovinelloRepository extends JpaRepository<SceltaIndovinello, Integer> {
    SceltaIndovinello findByIdCollegamento(int idCollegamento);

    @Modifying
    @Transactional
    @Query("DELETE FROM SceltaIndovinello s WHERE s.scenario1.storia = :storia OR s.scenario2.storia = :storia")
    void deleteSceltaIndovinelloByStoria(Storia storia);
}
