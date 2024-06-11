package com.progettosweng.application.service;

import com.progettosweng.application.entity.SceltaIndovinello;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.SceltaIndovinelloRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SceltaIndovinelloServiceTest {

    @Mock
    private SceltaIndovinelloRepository sceltaIndovinelloRepository;

    @InjectMocks
    private SceltaIndovinelloService sceltaIndovinelloService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSceltaIndovinello() {
        SceltaIndovinello scelta = new SceltaIndovinello();
        when(sceltaIndovinelloRepository.save(scelta)).thenReturn(scelta);

        SceltaIndovinello savedScelta = sceltaIndovinelloService.saveSceltaIndovinello(scelta);

        verify(sceltaIndovinelloRepository, times(1)).save(scelta);
    }

    @Test
    public void testGetSceltaIndovinelloCollegamento() {
        SceltaIndovinello scelta = new SceltaIndovinello();
        when(sceltaIndovinelloRepository.findByIdCollegamento(1)).thenReturn(scelta);

        SceltaIndovinello foundScelta = sceltaIndovinelloService.getSceltaIndovinelloCollegamento(1);

        verify(sceltaIndovinelloRepository, times(1)).findByIdCollegamento(1);
    }

    @Test
    public void testDeleteSceltaIndovinelloByStoria() {
        Storia storia = new Storia();

        sceltaIndovinelloService.deleteSceltaIndovinelloByStoria(storia);

        verify(sceltaIndovinelloRepository, times(1)).deleteSceltaIndovinelloByStoria(storia);
    }
}
