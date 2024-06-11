package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatoPartitaRepository extends JpaRepository<StatoPartita, Long> {
    @Query("SELECT sp FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    StatoPartita ByUsernameAndStoria(@Param("username") String username, @Param("storia") Storia storia);

    @Query("SELECT sp FROM StatoPartita sp WHERE sp.username = :username")
    List<StatoPartita> findByUsername(@Param("username") String username);


    @Query("SELECT COUNT(sp) > 0 FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    boolean existsByUsernameAndStoria(@Param("username") String username, @Param("storia") Storia storia);

    //query che filtra per titolo tra le storie di un determinato utente
    @Query("SELECT s FROM StatoPartita s " +
            "WHERE LOWER(s.storia.titolo) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "AND s.username = :username")
    List<StatoPartita> filterStoria(@Param("username") String username, @Param("filtro") String filtro);

    @Query("SELECT sp.storia FROM StatoPartita sp WHERE sp = :statoPartita")
    Integer findStoriaIdByStatoPartita(@Param("statoPartita") StatoPartita statoPartita);


    @Query("SELECT sp.scenario.idScenario FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    Integer findScenarioIdById(@Param("username") String username, @Param("storia") Storia storia);


    @Modifying
    @Query("UPDATE StatoPartita sp SET sp.username = :username WHERE sp.id = :statoPartitaId")
    void updateUsernameById(@Param("statoPartitaId") long statoPartitaId, @Param("username") String username);


    @Query("SELECT sp.scenario.idScenario FROM StatoPartita sp WHERE sp.id = :statoPartitaId")
    Integer findScenarioIdById2(@Param("statoPartitaId") int statoPartitaId);


    @Modifying
    @Query("UPDATE StatoPartita sp SET sp.scenario.idScenario = :scenarioId WHERE sp.id = :statoPartitaId")
    void updateScenarioId(int statoPartitaId, int scenarioId);

    @Modifying
    @Query("UPDATE StatoPartita s SET s.username = :username WHERE s.storia = :storia")
    void setUsername(Storia storia, String username);




    @Query("SELECT sp FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    StatoPartita findByUsernameAndStoria(@Param("username") String username, @Param("storia") Storia storia);


}