package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.SceltaIndovinello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SceltaIndovinelloRepository extends JpaRepository<SceltaIndovinello, Integer> {
}
