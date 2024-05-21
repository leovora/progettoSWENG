package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegamentoRepository extends JpaRepository<Collegamento, Integer> {
}