package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Storia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository per l'entità Storia.
 */
public interface StoriaRepository extends JpaRepository<Storia, Integer> {

    /**
     * Trova tutte le storie scritte da un utente con l'username specificato.
     * @param username l'username dell'utente
     * @return una lista di storie
     */
    @Query("SELECT s FROM Storia s JOIN s.creatore u WHERE u.username = ?1")
    ArrayList<Storia> findByUsername(String username);

    /**
     * Filtra le storie per titolo, creatore e lunghezza
     * @param filtro il filtro per il titolo o il creatore
     * @param lunghezza la lunghezza della storia (breve o lunga)
     * @return una lista di storie
     */
    @Query("SELECT s FROM Storia s WHERE " +
            "(LOWER(s.titolo) LIKE LOWER(CONCAT('%', :filtro, '%')) OR LOWER(s.creatore.nome) LIKE LOWER(CONCAT('%', :filtro, '%'))) " +
            "AND (:lunghezza IS NULL OR " +
            "(:lunghezza = 'breve' AND s.numeroStato <= 10) OR " +
            "(:lunghezza = 'lunga' AND s.numeroStato > 10))")
    List<Storia> search(@Param("filtro") String filtro, @Param("lunghezza") String lunghezza);

    /**
     * Filtra le storie di un utente specifico per titolo utilizzando un filtro di testo.
     * @param username l'username dell'utente
     * @param filtro il filtro di testo
     * @return una lista di storie filtrate
     */
    @Query("SELECT s FROM Storia s JOIN s.creatore u WHERE LOWER(s.titolo) LIKE LOWER(CONCAT('%', :filtro, '%')) AND u.username = :username")
    List<Storia> searchOwn(@Param("username") String username, @Param("filtro") String filtro);
}
