package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository per l'entità StatoPartita.
 */
public interface StatoPartitaRepository extends JpaRepository<StatoPartita, Long> {

    /**
     * Trova lo stato della partita per un utente specifico e una storia specificata.
     * @param username l'username dell'utente
     * @param storia la storia
     * @return lo stato della partita trovato
     */
    @Query("SELECT sp FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    StatoPartita findByUsernameAndStoria(@Param("username") String username, @Param("storia") Storia storia);

    /**
     * Trova tutti gli stati della partita per un utente specifico.
     * @param username l'username dell'utente
     * @return una lista di stati della partita
     */
    @Query("SELECT sp FROM StatoPartita sp WHERE sp.username = :username")
    List<StatoPartita> findByUsername(@Param("username") String username);

    /**
     * Verifica se esiste uno stato della partita per un utente specifico e una storia specificata.
     * @param username l'username dell'utente
     * @param storia la storia
     * @return true se esiste, altrimenti false
     */
    @Query("SELECT COUNT(sp) > 0 FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    boolean existsByUsernameAndStoria(@Param("username") String username, @Param("storia") Storia storia);

    /**
     * Filtra gli stati della partita per titolo delle storie di un determinato utente utilizzando un filtro di testo.
     * @param username l'username dell'utente
     * @param filtro il filtro di testo
     * @return una lista di stati della partita filtrati
     */
    @Query("SELECT s FROM StatoPartita s WHERE LOWER(s.storia.titolo) LIKE LOWER(CONCAT('%', :filtro, '%')) AND s.username = :username")
    List<StatoPartita> filterStoria(@Param("username") String username, @Param("filtro") String filtro);

    /**
     * Trova l'ID dello scenario associato a uno stato della partita per un utente specifico e una storia specificata.
     * @param username l'username dell'utente
     * @param storia la storia
     * @return l'ID dello scenario
     */
    @Query("SELECT sp.scenario.idScenario FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    Integer findScenarioIdById(@Param("username") String username, @Param("storia") Storia storia);
}
