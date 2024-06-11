package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScenarioRepositoryTest {

    @Mock
    private ScenarioRepository scenarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteByStoria() {
        Storia storia = new Storia();
        doNothing().when(scenarioRepository).deleteByStoria(storia);

        scenarioRepository.deleteByStoria(storia);

        verify(scenarioRepository, times(1)).deleteByStoria(storia);
    }

    @Test
    void testFindAllByUserUsername() {
        String username = "testUser";
        List<Scenario> scenari = List.of(new Scenario());
        when(scenarioRepository.findAllByUserUsername(username)).thenReturn(scenari);

        List<Scenario> foundScenari = scenarioRepository.findAllByUserUsername(username);

        assertEquals(scenari, foundScenari);
        verify(scenarioRepository, times(1)).findAllByUserUsername(username);
    }

    @Test
    void testFindByStoria() {
        Storia storia = new Storia();
        List<Scenario> scenari = List.of(new Scenario());
        when(scenarioRepository.findByStoria(storia)).thenReturn(scenari);

        List<Scenario> foundScenari = scenarioRepository.findByStoria(storia);

        assertEquals(scenari, foundScenari);
        verify(scenarioRepository, times(1)).findByStoria(storia);
    }
}
