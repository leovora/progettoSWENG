package com.progettosweng.application.repository;

import com.progettosweng.application.entity.SceltaIndovinello;
import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SceltaIndovinelloRepositoryTest {

    @Mock
    private SceltaIndovinelloRepository sceltaIndovinelloRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteSceltaIndovinelloByStoria() {
        Storia storia = new Storia();
        doNothing().when(sceltaIndovinelloRepository).deleteSceltaIndovinelloByStoria(storia);

        sceltaIndovinelloRepository.deleteSceltaIndovinelloByStoria(storia);

        verify(sceltaIndovinelloRepository, times(1)).deleteSceltaIndovinelloByStoria(storia);
    }

    @Test
    void testFindByIdCollegamento() {
        int idCollegamento = 1;
        SceltaIndovinello scelta = new SceltaIndovinello();
        when(sceltaIndovinelloRepository.findByIdCollegamento(idCollegamento)).thenReturn(scelta);

        SceltaIndovinello foundScelta = sceltaIndovinelloRepository.findByIdCollegamento(idCollegamento);

        assertEquals(scelta, foundScelta);
        verify(sceltaIndovinelloRepository, times(1)).findByIdCollegamento(idCollegamento);
    }
}
