package com.progettosweng.application.service;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.StoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StoriaService {

    private final StoriaRepository repository;
    private final ScenarioService scenarioService;
    private final CollegamentoService collegamentoService;
    private final OggettoService oggettoService;
    private final InventarioService inventarioService;
    private final SceltaIndovinelloService sceltaIndovinelloService;
    private final SceltaSempliceService sceltaSempliceService;
    private final StatoPartitaService statoPartitaService;

    @Autowired
    public StoriaService(StoriaRepository storiaRepository,
                         ScenarioService scenarioService,
                         CollegamentoService collegamentoService,
                         OggettoService oggettoService,
                         InventarioService inventarioService,
                         SceltaIndovinelloService sceltaIndovinelloService,
                         SceltaSempliceService sceltaSempliceService,
                         StatoPartitaService statoPartitaService){
        this.repository = storiaRepository;
        this.scenarioService = scenarioService;
        this.collegamentoService = collegamentoService;
        this.oggettoService = oggettoService;
        this.inventarioService = inventarioService;
        this.sceltaIndovinelloService = sceltaIndovinelloService;
        this.sceltaSempliceService = sceltaSempliceService;
        this.statoPartitaService = statoPartitaService;
    }

    /**
     * Salva una storia nel database.
     * @param storia la storia da salvare
     * @return la storia salvata
     */
    public Storia saveStoria(Storia storia) {
        return repository.save(storia);
    }

    /**
     * Elimina una storia e tutti i suoi componenti associati dal database.
     * @param storia la storia da eliminare
     */
    @Transactional
    public void deleteStoria(Storia storia) {
        statoPartitaService.deleteStatoPartitaByStoria(storia);
        inventarioService.deleteInventarioByStoria(storia);
        sceltaSempliceService.deleteSceltaSempliceByStoria(storia);
        sceltaIndovinelloService.deleteSceltaIndovinelloByStoria(storia);
        collegamentoService.deleteCollegamentiByStoria(storia);
        oggettoService.deleteOggettoByStoria(storia);
        scenarioService.deleteScenarioByIdStoria(storia);
        repository.delete(storia);
    }

    /**
     * Ottiene una storia dato il suo ID.
     * @param idStoria l'ID della storia
     * @return la storia trovata, o null se non trovata
     */
    public Storia getStoria(int idStoria) {
        return repository.findById(idStoria).orElse(null);
    }

    /**
     * Verifica se esiste una storia con l'ID dato.
     * @param idStoria l'ID della storia
     * @return true se esiste una storia con l'ID dato, altrimenti false
     */
    public Boolean existsStoria(int idStoria) {
        return repository.existsById(idStoria);
    }

    /**
     * Verifica se non ci sono storie nel database.
     * @return true se non ci sono storie nel database, altrimenti false
     */
    public boolean isEmpty() {
        return repository.count() == 0;
    }

    /**
     * Ottiene tutte le storie presenti nel database.
     * @return un'ArrayList contenente tutte le storie
     */
    public ArrayList<Storia> getAllStorie() {
        Collection<Storia> storie = repository.findAll();
        return new ArrayList<>(storie);
    }

    /**
     * Trova tutte le storie nel database con o senza un filtro.
     * @param filtro il filtro da applicare alle storie (può essere null o vuoto)
     * @return una lista di storie che soddisfano il filtro, o tutte le storie se il filtro è vuoto o null
     */
    public List<Storia> findAllStorie(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return repository.findAll();
        } else {
            return repository.search(filtro);
        }
    }

    /**
     * Trova tutte le storie scritte da un utente nel database, con o senza un filtro.
     * @param username lo username dell'utente di cui trovare le storie
     * @param filtro il filtro da applicare alle storie (può essere null o vuoto)
     * @return una lista di storie scritte dall'utente che soddisfano il filtro, o tutte le storie scritte dall'utente se il filtro è vuoto o null
     */
    public List<Storia> findAllStorieScritte(String username, String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            return repository.findByUsername(username);
        } else {
            return repository.searchOwn(username, filtro);
        }
    }


    /**
     * Imposta il numero di scenari di una storia nel database.
     * @param storia la storia di cui impostare il numero di scenari
     * @param numeroStato il numero di scenari da impostare
     */
    public void setNScenari(Storia storia, int numeroStato) {
        storia.setNumeroStato(numeroStato);
        repository.save(storia);
    }
}
