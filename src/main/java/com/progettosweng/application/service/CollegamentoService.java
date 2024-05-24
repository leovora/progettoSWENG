package com.progettosweng.application.service;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.CollegamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegamentoService {

    @Autowired
    private CollegamentoRepository collegamentoRepository;

    public Collegamento saveCollegamento(Collegamento collegamento) {
        return collegamentoRepository.save(collegamento);
    }

    public void deleteCollegamento(int id) {
        collegamentoRepository.deleteById(id);
    }

    public Collegamento findCollegamentoById(int id) {
        return collegamentoRepository.findById(id).orElse(null);
    }
}
