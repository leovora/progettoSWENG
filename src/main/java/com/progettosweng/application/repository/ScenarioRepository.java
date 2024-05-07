package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Integer> {

    //elimina scenario associato a storia data in input
    void deleteByStoria(Storia storia);
}
