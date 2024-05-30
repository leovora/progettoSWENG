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

    //@Query("SELECT s FROM Scenario s LEFT JOIN FETCH s.collegamentiDaScenario1 LEFT JOIN FETCH s.collegamentiDaScenario2 WHERE s.storia = :storia")
    List<Scenario> findByStoria(Storia storia);

}
