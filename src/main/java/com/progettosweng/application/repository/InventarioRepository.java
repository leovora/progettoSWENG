package com.progettosweng.application.repository;

import com.progettosweng.application.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Inventario i WHERE i.oggetto.storia = :storia")
    void deleteByStoria(Storia storia);

    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Inventario i WHERE i.user = :user AND i.oggetto = :oggetto")
    Boolean checkOggetto(AbstractUser user, Oggetto oggetto);
}
