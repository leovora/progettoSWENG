package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface StoriaRepository extends JpaRepository<Storia, Integer> {

    //query che trova tutte le storie scritte da un utente
    @Query("SELECT s FROM Storia s JOIN s.creatore u WHERE u.username = ?1")
    ArrayList<Storia> findByUsername(String username);

    //query che filtra per titolo o per creatore di una storia
    @Query("SELECT s FROM Storia s " +
            "WHERE LOWER(s.titolo) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "OR LOWER(s.creatore) LIKE LOWER(CONCAT('%', :filtro, '%'))")
    List<Storia> search(@Param("filtro") String filtro);

    //query che filtra per titolo tra le storie di un determinato utente
    @Query("SELECT s FROM Storia s JOIN s.creatore u " +
            "WHERE LOWER(s.titolo) LIKE LOWER(CONCAT('%', :filtro, '%')) " +
            "AND u.username = :username")
    List<Storia> searchOwn(@Param("username") String username, @Param("filtro") String filtro);
}
