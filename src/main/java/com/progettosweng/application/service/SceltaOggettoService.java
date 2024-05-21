package com.progettosweng.application.service;

import com.progettosweng.application.repository.SceltaOggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceltaOggettoService {

    @Autowired
    private SceltaOggettoRepository sceltaOggettoRepository;
}
