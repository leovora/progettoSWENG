package com.progettosweng.application.service;

import com.progettosweng.application.entity.SceltaIndovinello;
import com.progettosweng.application.repository.SceltaIndovinelloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceltaIndovinelloService {

    @Autowired
    private SceltaIndovinelloRepository sceltaIndovinelloRepository;

    public SceltaIndovinello saveSceltaIndovinello(SceltaIndovinello scelta) {return sceltaIndovinelloRepository.save(scelta);}
}
