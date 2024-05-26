package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScenarioRepository extends JpaRepository<Scenario, Integer> {

    //elimina scenario associato a storia data in input
    void deleteByStoria(Storia storia);

    @Query("SELECT s FROM Scenario s JOIN s.storia st WHERE st.creatore.username = :username")
    List<Scenario> findAllByUserUsername(@Param("username") String username);

    List<Scenario> findByStoria(Storia storia);

    @Query("SELECT s FROM Scenario  s WHERE s.storia = :storia AND s.primoScenario = true")
    Scenario getPrimoScenario(Storia storia);

    @Query("SELECT s FROM Scenario s JOIN s.storia ss " +
            "WHERE ss.idStoria = :storia " +
            "AND LOWER(s.titolo) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Scenario> getScenariFiltro(String filtro, int storia);
}
