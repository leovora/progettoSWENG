package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.Oggetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OggettoRepository extends JpaRepository<Oggetto, Integer> {
}
