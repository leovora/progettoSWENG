package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OggettoRepositoryTest {

    @Mock
    private OggettoRepository oggettoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByStoria() {
        Storia storia = new Storia();
        List<Oggetto> oggetti = List.of(new Oggetto());
        when(oggettoRepository.findByStoria(storia)).thenReturn(oggetti);

        List<Oggetto> foundOggetti = oggettoRepository.findByStoria(storia);

        assertEquals(oggetti, foundOggetti);
        verify(oggettoRepository, times(1)).findByStoria(storia);
    }

    @Test
    void testFindByScenario() {
        Scenario scenario = new Scenario();
        List<Oggetto> oggetti = List.of(new Oggetto());
        when(oggettoRepository.findByScenario(scenario)).thenReturn(oggetti);

        List<Oggetto> foundOggetti = oggettoRepository.findByScenario(scenario);

        assertEquals(oggetti, foundOggetti);
        verify(oggettoRepository, times(1)).findByScenario(scenario);
    }

    @Test
    void testDeleteByStoria() {
        Storia storia = new Storia();
        doNothing().when(oggettoRepository).deleteByStoria(storia);

        oggettoRepository.deleteByStoria(storia);

        verify(oggettoRepository, times(1)).deleteByStoria(storia);
    }
}
