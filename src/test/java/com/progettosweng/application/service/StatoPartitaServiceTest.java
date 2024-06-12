package com.progettosweng.application.service;

import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.StatoPartitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StatoPartitaServiceTest {

    @Mock
    private StatoPartitaRepository statoPartitaRepository;

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private StatoPartitaService statoPartitaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveStatoPartita() {
        StatoPartita statoPartita = new StatoPartita();
        when(statoPartitaRepository.save(statoPartita)).thenReturn(statoPartita);

        statoPartitaService.saveStatoPartita(statoPartita);

        verify(statoPartitaRepository, times(1)).save(statoPartita);
    }

    @Test
    public void testGetStatoPartitaByUserAndStoria() {
        User user = new User();
        user.setUsername("testUser");
        Storia storia = new Storia();
        StatoPartita statoPartita = new StatoPartita();
        when(statoPartitaRepository.findByUsernameAndStoria("testUser", storia)).thenReturn(statoPartita);

        StatoPartita foundStatoPartita = statoPartitaService.getStatoPartitaByUserAndStoria(user, storia);

        assertNotNull(foundStatoPartita);
        verify(statoPartitaRepository, times(1)).findByUsernameAndStoria("testUser", storia);
    }

    @Test
    public void testExistsByUserAndStoria() {
        User user = new User();
        user.setUsername("testUser");
        Storia storia = new Storia();
        when(statoPartitaRepository.existsByUsernameAndStoria("testUser", storia)).thenReturn(true);

        boolean exists = statoPartitaService.existsByUserAndStoria(user, storia);

        assertTrue(exists);
        verify(statoPartitaRepository, times(1)).existsByUsernameAndStoria("testUser", storia);
    }
}
