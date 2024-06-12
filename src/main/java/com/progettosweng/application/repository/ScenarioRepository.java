package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository per l'entità Scenario.
 */
public interface ScenarioRepository extends JpaRepository<Scenario, Integer> {

    /**
     * Elimina tutti gli scenari associati a una storia specificata.
     * @param storia la storia
     */
    void deleteByStoria(Storia storia);

    /**
     * Trova tutti gli scenari creati da un utente con l'username specificato.
     * @param username l'username dell'utente
     * @return una lista di scenari
     */
    @Query("SELECT s FROM Scenario s JOIN s.storia st WHERE st.creatore.username = :username")
    List<Scenario> findAllByUserUsername(@Param("username") String username);

    /**
     * Trova tutti gli scenari associati a una storia specificata.
     * @param storia la storia
     * @return una lista di scenari
     */
    List<Scenario> findByStoria(Storia storia);

    /**
     * Trova il primo scenario di una storia specificata.
     * @param storia la storia
     * @return il primo scenario
     */
    @Query("SELECT s FROM Scenario s WHERE s.storia = :storia AND s.primoScenario = true")
    Scenario getPrimoScenario(Storia storia);

    /**
     * Filtra gli scenari di una storia specificata per un filtro di testo.
     * @param filtro il filtro di testo
     * @param storia l'ID della storia
     * @return una lista di scenari filtrati
     */
    @Query("SELECT s FROM Scenario s JOIN s.storia ss WHERE ss.idStoria = :storia AND LOWER(s.titolo) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Scenario> getScenariFiltro(String filtro, int storia);
}
