package com.progettosweng.application.service;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.repository.StatoPartitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatoPartitaService {

    private final StatoPartitaRepository statoPartitaRepository;
    private final InventarioService inventarioService;

    @Autowired
    public StatoPartitaService(StatoPartitaRepository statoPartitaRepository, InventarioService inventarioService) {
        this.statoPartitaRepository = statoPartitaRepository;
        this.inventarioService = inventarioService;
    }

    /**
     * Salva lo stato di una partita nel database.
     * @param statoPartita lo stato di una partita da salvare
     */
    public void saveStatoPartita(StatoPartita statoPartita) {
        statoPartitaRepository.save(statoPartita);
    }

    /**
     * Ottiene lo stato di una partita di un utente per una determinata storia.
     * @param user l'utente di cui trovare lo stato di partita
     * @param storia la storia per cui trovare lo stato di partita
     * @return lo stato di partita trovato
     */
    public StatoPartita getStatoPartitaByUserAndStoria(User user, Storia storia) {
        return statoPartitaRepository.findByUsernameAndStoria(user.getUsername(), storia);
    }

    /**
     * Elimina lo stato di una partita di un utente per una determinata storia dal database.
     * @param user l'utente di cui eliminare lo stato di partita
     * @param storia la storia per cui eliminare lo stato di partita
     */
    public void deleteStatoPartitaByUserAndStoria(User user, Storia storia) {
        StatoPartita statoPartita = statoPartitaRepository.findByUsernameAndStoria(user.getUsername(), storia);
        if (statoPartita != null) {
            statoPartitaRepository.delete(statoPartita);
        }
    }

    /**
     * Verifica se esiste lo stato di una partita di un utente per una determinata storia.
     * @param user l'utente di cui verificare lo stato di partita
     * @param storia la storia per cui verificare lo stato di partita
     * @return true se lo stato di partita esiste, altrimenti false
     */
    public boolean existsByUserAndStoria(User user, Storia storia) {
        return statoPartitaRepository.existsByUsernameAndStoria(user.getUsername(), storia);
    }

    /**
     * Elimina lo stato di una partita e l'inventario associato di un utente dal database.
     * @param user l'utente di cui eliminare lo stato di partita e l'inventario
     * @param statoPartita lo stato di partita da eliminare
     */
    @Transactional
    public void deleteStatoPartita(AbstractUser user, StatoPartita statoPartita) {
        inventarioService.deleteInventarioUser(user, statoPartita.getStoria());
        statoPartitaRepository.delete(statoPartita);
    }

    /**
     * Filtra le storie dello stato di partita di un utente per un dato testo.
     * @param username lo username dell'utente di cui filtrare le storie
     * @param filtro il testo da usare come filtro
     * @return una lista di storie filtrate
     */
    public List<StatoPartita> filtraStorie(String username, String filtro) {
        return statoPartitaRepository.filterStoria(username, filtro);
    }

    /**
     * Ottiene l'ID dello scenario di una storia nello stato di partita di un utente.
     * @param Username lo username dell'utente di cui trovare lo scenario
     * @param storia la storia di cui trovare lo scenario
     * @return l'ID dello scenario
     */
    public int getScenarioId(String Username, Storia storia) {
        return statoPartitaRepository.findScenarioIdById(Username, storia);
    }

    /**
     * Imposta lo scenario dello stato di partita.
     * @param statoPartita lo stato di partita di cui impostare lo scenario
     * @param scenario lo scenario da impostare
     */
    @Transactional
    public void setScenario(StatoPartita statoPartita, Scenario scenario) {
        statoPartita.setScenario(scenario);
    }

    /**
     * Ottiene il titolo dello scenario dello stato di partita.
     * @param statoPartita lo stato di partita di cui ottenere il titolo dello scenario
     * @return il titolo dello scenario
     */
    public String getTitoloScenario(StatoPartita statoPartita) {
        return statoPartita.getScenario().getTitolo();
    }

    /**
     * Ottiene la storia dello stato di partita.
     * @param statoPartita lo stato di partita di cui ottenere la storia
     * @return la storia
     */
    public Storia getStoria(StatoPartita statoPartita) {
        return statoPartita.getStoria();
    }

    /**
     * Ottiene l'ID della storia dello stato di partita.
     * @param statoPartita lo stato di partita di cui ottenere l'ID della storia
     * @return l'ID della storia
     */
    public int getStoriaId(StatoPartita statoPartita) {
        return statoPartita.getStoria().getIdStoria();
    }
}
