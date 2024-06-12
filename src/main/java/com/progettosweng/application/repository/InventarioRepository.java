package com.progettosweng.application.repository;

import com.progettosweng.application.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità Inventario.
 */
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    /**
     * Elimina tutti gli elementi dell'inventario associati alla storia specificata.
     * @param storia la storia per cui eliminare gli elementi dell'inventario
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Inventario i WHERE i.oggetto.storia = :storia")
    void deleteByStoria(Storia storia);

    /**
     * Verifica se un oggetto specifico esiste nell'inventario di un utente.
     * @param user l'utente
     * @param oggetto l'oggetto da verificare
     * @return true se l'oggetto esiste, altrimenti false
     */
    @Query("SELECT CASE WHEN COUNT(i) > 0 THEN true ELSE false END FROM Inventario i WHERE i.user = :user AND i.oggetto = :oggetto")
    Boolean checkOggetto(AbstractUser user, Oggetto oggetto);

    /**
     * Elimina gli elementi dell'inventario di un utente specifico per una storia specificata.
     * @param user l'utente
     * @param storia la storia
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Inventario i WHERE i.user = :user AND i.oggetto.storia = :storia")
    void deleteByUser(AbstractUser user, Storia storia);
}
