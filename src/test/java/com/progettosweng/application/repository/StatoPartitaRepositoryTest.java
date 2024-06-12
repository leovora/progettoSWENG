package com.progettosweng.application.repository;

import com.progettosweng.application.entity.StatoPartita;
import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StatoPartitaRepositoryTest {

    @Mock
    private StatoPartitaRepository statoPartitaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testByUsernameAndStoria() {
        String username = "testUser";
        Storia storia = new Storia();
        StatoPartita statoPartita = new StatoPartita();
        when(statoPartitaRepository.findByUsernameAndStoria(username, storia)).thenReturn(statoPartita);

        StatoPartita foundStatoPartita = statoPartitaRepository.findByUsernameAndStoria(username, storia);

        assertEquals(statoPartita, foundStatoPartita);
        verify(statoPartitaRepository, times(1)).findByUsernameAndStoria(username, storia);
    }

    @Test
    void testExistsByUsernameAndStoria() {
        String username = "testUser";
        Storia storia = new Storia();
        when(statoPartitaRepository.existsByUsernameAndStoria(username, storia)).thenReturn(true);

        boolean exists = statoPartitaRepository.existsByUsernameAndStoria(username, storia);

        assertTrue(exists);
        verify(statoPartitaRepository, times(1)).existsByUsernameAndStoria(username, storia);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        List<StatoPartita> statoPartitaList = List.of(new StatoPartita());
        when(statoPartitaRepository.findByUsername(username)).thenReturn(statoPartitaList);

        List<StatoPartita> foundList = statoPartitaRepository.findByUsername(username);

        assertEquals(statoPartitaList, foundList);
        verify(statoPartitaRepository, times(1)).findByUsername(username);
    }
}
