package com.progettosweng.application.service;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.repository.StatoPartitaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatoPartitaService {

    @Autowired
    private static StatoPartitaRepository statoPartitaRepository;
    @Autowired
    private InventarioService inventarioService;

    @Autowired
    public StatoPartitaService(StatoPartitaRepository statoPartitaRepository) {
        StatoPartitaService.statoPartitaRepository = statoPartitaRepository;
    }

    public void saveStatoPartita(StatoPartita statoPartita) {
        statoPartitaRepository.save(statoPartita);
    }

    public StatoPartita getStatoPartitaByUserAndStoria(User user, Storia storia) {
        return statoPartitaRepository.findByUsernameAndStoria(user.getUsername(), storia);
    }


    public void deleteStatoPartitaByUserAndStoria(User user, Storia storia) {
        StatoPartita statoPartita = statoPartitaRepository.findByUsernameAndStoria(user.getUsername(), storia);
        if (statoPartita != null) {
            statoPartitaRepository.delete(statoPartita);
        }
    }
    public boolean existsByUserAndStoria(User user,Storia storia){
        return statoPartitaRepository.existsByUsernameAndStoria(user.getUsername(), storia);
    }
    @Transactional
    public void deleteStatoPartita(AbstractUser user, StatoPartita statoPartita) {
        inventarioService.deleteInventarioUser(user, statoPartita.getStoria());
        statoPartitaRepository.delete(statoPartita);
    }

    public List<StatoPartita> filtraStorie(String username, String filtro){
        return statoPartitaRepository.filterStoria(username, filtro);
    }


    public int getScenarioId(String Username,Storia storia) {
        return statoPartitaRepository.findScenarioIdById(Username,storia);
    }



    @Transactional
    public void setScenario(StatoPartita statoPartita, Scenario scenario) {
        statoPartita.setScenario(scenario);
    }

    public String getTitoloScenario(StatoPartita statoPartita){
        return statoPartita.getScenario().getTitolo();
    }

    public Storia getStoria(StatoPartita statoPartita){
        return statoPartita.getStoria();
    }

    public int getStoriaId(StatoPartita statoPartita){
        return statoPartita.getStoria().getIdStoria();
    }

}