//package com.progettosweng.application.service;
//
//import com.progettosweng.application.entity.Collegamento;
//import com.progettosweng.application.entity.SceltaOggetto;
//import com.progettosweng.application.repository.CollegamentoRepository;
//import com.progettosweng.application.repository.SceltaOggettoRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SceltaOggettoService {
//
//    @Autowired
//    private SceltaOggettoRepository sceltaOggettoRepository;
//
//    @Autowired
//    private CollegamentoRepository collegamentoRepository;
//
//    @Autowired
//    private CollegamentoService collegamentoService;
//
//    public SceltaOggetto saveSceltaOggetto(SceltaOggetto scelta){
//        Collegamento collegamento = collegamentoService.findCollegamentoById(scelta.getIdCollegamento());
//        collegamento.setOggettoRichiesto(scelta.getOggettoRichiesto());
//        collegamentoRepository.save(collegamento);
//        return sceltaOggettoRepository.save(scelta);
//    }
//}
