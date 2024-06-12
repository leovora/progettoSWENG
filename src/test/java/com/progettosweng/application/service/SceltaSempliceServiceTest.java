package com.progettosweng.application.service;

import com.progettosweng.application.entity.SceltaSemplice;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.SceltaSempliceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SceltaSempliceServiceTest {

    @Mock
    private SceltaSempliceRepository sceltaSempliceRepository;

    @InjectMocks
    private SceltaSempliceService sceltaSempliceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveSceltaSemplice() {
        SceltaSemplice scelta = new SceltaSemplice();
        when(sceltaSempliceRepository.save(scelta)).thenReturn(scelta);

        SceltaSemplice savedScelta = sceltaSempliceService.saveSceltaSemplice(scelta);

        verify(sceltaSempliceRepository, times(1)).save(scelta);
    }

    @Test
    public void testDeleteSceltaSempliceByStoria() {
        Storia storia = new Storia();

        sceltaSempliceService.deleteSceltaSempliceByStoria(storia);

        verify(sceltaSempliceRepository, times(1)).deleteByStoria(storia);
    }
}
