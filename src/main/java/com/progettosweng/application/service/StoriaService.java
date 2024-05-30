package com.progettosweng.application.service;

import com.progettosweng.application.entity.SceltaIndovinello;
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

    @Autowired
    private StoriaRepository repository;

    @Autowired
    private ScenarioService scenarioService;

    @Autowired
    private CollegamentoService collegamentoService;

    @Autowired
    private OggettoService oggettoService;

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private SceltaIndovinelloService sceltaIndovinelloService;

    @Autowired SceltaSempliceService sceltaSempliceService;


    //salva storia
    public Storia saveStoria(Storia storia){
        return repository.save(storia);
    }

    //elimina storia e tutti i suoi scenari
    @Transactional
    public void deleteStoria(Storia storia){
        inventarioService.deleteInventarioByStoria(storia);
        sceltaSempliceService.deleteSceltaSempliceByStoria(storia);
        sceltaIndovinelloService.deleteSceltaIndovinelloByStoria(storia);
        collegamentoService.deleteCollegamentiByStoria(storia);
        oggettoService.deleteOggettoByStoria(storia);
        scenarioService.deleteScenarioByIdStoria(storia);
        repository.delete(storia);
    }

    //ritorna storia dal suo ID
    public Storia getStoria(int idStoria){
        return repository.findById(idStoria).orElse(null);
    }

    //true se storia con quell'ID esiste
    public Boolean existsStoria(int idStoria) {
        return repository.existsById(idStoria);
    }

    //true se nessuna storia presente
    public boolean isEmpty() {
        return repository.count() == 0;
    }

    //ritorna tutte le storie
    public ArrayList<Storia> getAllStorie() {
        Collection<Storia> storie = repository.findAll();
        return new ArrayList<>(storie);
    }

    //ritorna tutte le storie se nessun filtro, altrimenti filtra
    public List<Storia> findAllStorie(String filtro) {
        if(filtro == null || filtro.isEmpty()){
            return repository.findAll();
        } else{
            return repository.search(filtro);
        }
    }

    //ritorna tutte le storie dell'utente loggato se nessun filtro, altrimenti filtra
    public List<Storia> findAllStorieScritte(String username, String filtro) {
        if(filtro == null || filtro.isEmpty()){
            return repository.findByUsername(username);
        } else{
            return repository.searchOwn(username, filtro);
        }
    }

    //ritorna creatore di una storia
    public User getCreatore(int idStoria){
        Storia storia = getStoria(idStoria);
        return storia.getCreatore();
    }

    //ritorna tutte le storie scritte da un utente
    public ArrayList<Storia> getStorieUtente(String user){
        return repository.findByUsername(user);
    }

    public Storia findStoriaById(int idStoria) {
        Optional<Storia> optionalStoria = repository.findById(idStoria);
        return optionalStoria.orElse(null);
    }

    public void setNScenari(Storia storia, int numeroStato) {
        storia.setNumeroStato(numeroStato);
        repository.save(storia);
    }

    public int getId(Storia storia) {
        return storia.getIdStoria();
    }
}
