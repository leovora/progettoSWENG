package com.progettosweng.application.service;

import com.progettosweng.application.entity.*;
import com.progettosweng.application.repository.CollegamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CollegamentoServiceTest {

    @Mock
    private CollegamentoRepository collegamentoRepository;

    @InjectMocks
    private CollegamentoService collegamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteCollegamento() {
        int id = 1;
        doNothing().when(collegamentoRepository).deleteById(id);
        collegamentoService.deleteCollegamento(id);
        verify(collegamentoRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteCollegamentiByStoria() {
        Storia storia = new Storia();
        doNothing().when(collegamentoRepository).deleteAllByStoria(storia);
        collegamentoService.deleteCollegamentiByStoria(storia);
        verify(collegamentoRepository, times(1)).deleteAllByStoria(storia);
    }

    @Test
    void testFindCollegamentoById() {
        int id = 1;
        Collegamento collegamento = new CollegamentoConcreto();
        when(collegamentoRepository.findById(id)).thenReturn(Optional.of(collegamento));
        Collegamento found = collegamentoService.findCollegamentoById(id);
        assertEquals(collegamento, found);
    }

    @Test
    void testGetCollegamentoByScenario() {
        Scenario scenario = new Scenario();
        Collegamento collegamento1 = new CollegamentoConcreto();
        Collegamento collegamento2 = new CollegamentoConcreto();
        List<Collegamento> collegamenti = Arrays.asList(collegamento1, collegamento2);
        when(collegamentoRepository.findByScenario1(scenario)).thenReturn(collegamenti);
        List<Collegamento> result = collegamentoService.getCollegamentoByScenario(scenario);
        assertEquals(2, result.size());
    }

    @Test
    void testEseguiScelta() {
        Collegamento collegamento = new CollegamentoConcreto();
        Scenario scenario = new Scenario();
        collegamento.setScenario1(scenario);
        Scenario result = collegamentoService.eseguiScelta(collegamento);
        assertEquals(scenario, result);
    }

    @Test
    void testSetOggettoRichiesto() {
        int idCollegamento = 1;
        Oggetto oggetto = new Oggetto();
        Collegamento collegamento = new CollegamentoConcreto();
        when(collegamentoRepository.findById(idCollegamento)).thenReturn(Optional.of(collegamento));
        collegamentoService.setOggettoRichiesto(idCollegamento, oggetto);
        assertEquals(oggetto, collegamento.getOggettoRichiesto());
        verify(collegamentoRepository, times(1)).save(collegamento);
    }

    // Classe concreta per test
    static class CollegamentoConcreto extends Collegamento {
        @Override
        public Scenario eseguiScelta() {
            return getScenario1();
        }
    }
}
