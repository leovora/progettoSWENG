package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Collegamento;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CollegamentoRepositoryTest {

    @Mock
    private CollegamentoRepository collegamentoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByScenario1() {
        Scenario scenario = new Scenario();
        List<Collegamento> collegamenti = List.of(new CollegamentoConcreto());
        when(collegamentoRepository.findByScenario1(scenario)).thenReturn(collegamenti);

        List<Collegamento> foundCollegamenti = collegamentoRepository.findByScenario1(scenario);

        assertEquals(collegamenti, foundCollegamenti);
        verify(collegamentoRepository, times(1)).findByScenario1(scenario);
    }

    @Test
    void testDeleteAllByStoria() {
        Storia storia = new Storia();
        doNothing().when(collegamentoRepository).deleteAllByStoria(storia);

        collegamentoRepository.deleteAllByStoria(storia);

        verify(collegamentoRepository, times(1)).deleteAllByStoria(storia);
    }

    static class CollegamentoConcreto extends Collegamento {
        @Override
        public Scenario eseguiScelta() {
            return getScenario1();
        }
    }
}
