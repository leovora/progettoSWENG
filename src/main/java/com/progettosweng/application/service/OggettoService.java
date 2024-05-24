package com.progettosweng.application.service;

import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.OggettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OggettoService {

    @Autowired
    private OggettoRepository oggettoRepository;

    public List<Oggetto> getOggettiStoria(Storia storia) {return oggettoRepository.findByStoria(storia);}
}
