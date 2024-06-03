package com.progettosweng.application.service;

import com.progettosweng.application.entity.AbstractUser;
import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.StatoPartitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatoPartitaService {

    @Autowired
    private StatoPartitaRepository statoPartitaRepository;

    @Autowired
    public StatoPartitaService(StatoPartitaRepository statoPartitaRepository) {
        this.statoPartitaRepository = statoPartitaRepository;
    }

    public void saveStatoPartita(StatoPartita statoPartita) {
        statoPartitaRepository.save(statoPartita);
    }

    public StatoPartita getStatoPartitaByUserAndStoria(User user, Storia storia) {
        return statoPartitaRepository.findByUsernameAndStoria(user.getUsername(), storia);
    }
    public List<StatoPartita> getStorieInCorsoByUser(String username) {
        return statoPartitaRepository.findByUsername(username);
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
    public void deleteStatoPartita(StatoPartita statoPartita) {
        statoPartitaRepository.delete(statoPartita);
    }


}
