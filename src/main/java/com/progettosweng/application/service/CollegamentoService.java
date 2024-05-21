package com.progettosweng.application.service;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.repository.CollegamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegamentoService {

    @Autowired
    private CollegamentoRepository scenarioLinkRepository;

    public Collegamento saveCollegamento(Collegamento collegamento) {
        return scenarioLinkRepository.save(collegamento);
    }

    public void deleteCollegamento(int id) {
        scenarioLinkRepository.deleteById(id);
    }

    public Collegamento findCollegamentoById(int id) {
        return scenarioLinkRepository.findById(id).orElse(null);
    }
}
