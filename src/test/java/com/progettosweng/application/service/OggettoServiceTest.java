package com.progettosweng.application.service;

import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Scenario;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.OggettoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;

public class OggettoServiceTest {

    @Mock
    private OggettoRepository oggettoRepository;

    @InjectMocks
    private OggettoService oggettoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOggetto() {
        Oggetto oggetto = new Oggetto();
        when(oggettoRepository.save(oggetto)).thenReturn(oggetto);

        oggettoService.saveOggetto(oggetto);

        verify(oggettoRepository, times(1)).save(oggetto);
    }

    @Test
    public void testGetOggettiStoria() {
        Storia storia = new Storia();
        List<Oggetto> oggetti = List.of(new Oggetto());
        when(oggettoRepository.findByStoria(storia)).thenReturn(oggetti);

        List<Oggetto> foundOggetti = oggettoService.getOggettiStoria(storia);

        verify(oggettoRepository, times(1)).findByStoria(storia);
    }

    @Test
    public void testGetOggettiScenario() {
        Scenario scenario = new Scenario();
        List<Oggetto> oggetti = List.of(new Oggetto());
        when(oggettoRepository.findByScenario(scenario)).thenReturn(oggetti);

        List<Oggetto> foundOggetti = oggettoService.getOggettiScenario(scenario);

        verify(oggettoRepository, times(1)).findByScenario(scenario);
    }

    @Test
    public void testDeleteOggettoByStoria() {
        Storia storia = new Storia();

        oggettoService.deleteOggettoByStoria(storia);

        verify(oggettoRepository, times(1)).deleteByStoria(storia);
    }
}
