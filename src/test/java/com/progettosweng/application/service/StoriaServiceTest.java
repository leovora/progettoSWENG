package com.progettosweng.application.service;

import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.StoriaRepository;
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

public class StoriaServiceTest {

    @Mock
    private StoriaRepository storiaRepository;

    @Mock
    private ScenarioService scenarioService;

    @Mock
    private CollegamentoService collegamentoService;

    @Mock
    private OggettoService oggettoService;

    @Mock
    private InventarioService inventarioService;

    @Mock
    private SceltaIndovinelloService sceltaIndovinelloService;

    @Mock
    private SceltaSempliceService sceltaSempliceService;

    @InjectMocks
    private StoriaService storiaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveStoria() {
        Storia storia = new Storia();
        when(storiaRepository.save(storia)).thenReturn(storia);

        Storia savedStoria = storiaService.saveStoria(storia);

        assertNotNull(savedStoria);
        verify(storiaRepository, times(1)).save(storia);
    }

    @Test
    public void testGetStoria() {
        Storia storia = new Storia();
        when(storiaRepository.findById(1)).thenReturn(Optional.of(storia));

        Storia foundStoria = storiaService.getStoria(1);

        assertNotNull(foundStoria);
        verify(storiaRepository, times(1)).findById(1);
    }

    @Test
    public void testExistsStoria() {
        when(storiaRepository.existsById(1)).thenReturn(true);

        boolean exists = storiaService.existsStoria(1);

        assertTrue(exists);
        verify(storiaRepository, times(1)).existsById(1);
    }

    @Test
    public void testIsEmpty() {
        when(storiaRepository.count()).thenReturn(0L);

        boolean isEmpty = storiaService.isEmpty();

        assertTrue(isEmpty);
        verify(storiaRepository, times(1)).count();
    }

    @Test
    public void testGetAllStorie() {
        List<Storia> storie = new ArrayList<>();
        when(storiaRepository.findAll()).thenReturn(storie);

        ArrayList<Storia> allStorie = storiaService.getAllStorie();

        assertNotNull(allStorie);
        verify(storiaRepository, times(1)).findAll();
    }
}
