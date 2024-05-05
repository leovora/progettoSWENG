package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface StoriaRepository extends JpaRepository<Storia, Integer> {

    @Query("SELECT s FROM Storia s JOIN s.creatore u WHERE u.username = ?1")
    ArrayList<Storia> findByUsername(String username);
}
