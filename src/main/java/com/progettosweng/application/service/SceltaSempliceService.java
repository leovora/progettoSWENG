package com.progettosweng.application.service;

import com.progettosweng.application.repository.SceltaSempliceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceltaSempliceService {

    @Autowired
    private SceltaSempliceRepository sceltaSempliceRepository;
}
