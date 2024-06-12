package com.progettosweng.application.service;

import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.repository.ScenarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ScenarioServiceTest {

    @Mock
    private ScenarioRepository scenarioRepository;

    @InjectMocks
    private ScenarioService scenarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveScenario() {
        Scenario scenario = new Scenario();
        when(scenarioRepository.save(scenario)).thenReturn(scenario);

        Scenario savedScenario = scenarioService.saveScenario(scenario);

        assertNotNull(savedScenario);
        verify(scenarioRepository, times(1)).save(scenario);
    }

    @Test
    public void testGetScenario() {
        Scenario scenario = new Scenario();
        when(scenarioRepository.findById(1)).thenReturn(Optional.of(scenario));

        Scenario foundScenario = scenarioService.getScenario(1);

        assertNotNull(foundScenario);
        verify(scenarioRepository, times(1)).findById(1);
    }

    @Test
    public void testExistsScenario() {
        when(scenarioRepository.existsById(1)).thenReturn(true);

        boolean exists = scenarioService.existsScenario(1);

        assertTrue(exists);
        verify(scenarioRepository, times(1)).existsById(1);
    }

    @Test
    public void testGetAllScenari() {
        List<Scenario> scenari = new ArrayList<>();
        when(scenarioRepository.findAll()).thenReturn(scenari);

        ArrayList<Scenario> allScenari = scenarioService.getAllScenari();

        assertNotNull(allScenari);
        verify(scenarioRepository, times(1)).findAll();
    }
}
