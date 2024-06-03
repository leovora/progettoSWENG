package com.progettosweng.application.repository;

import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StatoPartitaRepository extends JpaRepository<StatoPartita, Long> {
    @Query("SELECT sp FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    StatoPartita findByUsernameAndStoria(@Param("username") String username, @Param("storia") Storia storia);

    @Query("SELECT sp FROM StatoPartita sp WHERE sp.username = :username")
    List<StatoPartita> findByUsername(@Param("username") String username);


    @Query("SELECT COUNT(sp) > 0 FROM StatoPartita sp WHERE sp.username = :username AND sp.storia = :storia")
    boolean existsByUsernameAndStoria(@Param("username") String username, @Param("storia") Storia storia);


}