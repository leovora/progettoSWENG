package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OggettoRepository extends JpaRepository<Oggetto, Integer> {

    List<Oggetto> findByStoria(Storia storia);

}
