package com.progettosweng.application.service;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.repository.CollegamentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegamentoService {

    @Autowired
    private CollegamentoRepository collegamentoRepository;

    public void deleteCollegamento(int id) {
        collegamentoRepository.deleteById(id);
    }

    @Transactional
    public void deleteCollegamentiByStoria(Storia storia) {
        collegamentoRepository.deleteAllByStoria(storia);
    }

    public Collegamento findCollegamentoById(int id) {
        return collegamentoRepository.findById(id).orElse(null);
    }

    public List<Collegamento> getCollegamentoByScenario(Scenario scenario) { return collegamentoRepository.findByScenario1(scenario);}

    public Scenario eseguiScelta(Collegamento collegamento){ return collegamento.eseguiScelta(); }

    public void setOggettoRichiesto(int idCollegamento, Oggetto oggetto) {
        Collegamento collegamento = findCollegamentoById(idCollegamento);
        collegamento.setOggettoRichiesto(oggetto);
        collegamentoRepository.save(collegamento);
    }

}
