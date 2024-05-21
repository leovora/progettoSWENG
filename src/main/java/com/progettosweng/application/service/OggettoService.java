package com.progettosweng.application.service;

import com.progettosweng.application.repository.OggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OggettoService {

    @Autowired
    private OggettoRepository oggettoRepository;
}
