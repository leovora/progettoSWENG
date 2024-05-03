package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScenarioRepository extends JpaRepository<Scenario, Integer> {
}
