package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class SceltaSempliceRepositoryTest {

    @Mock
    private SceltaSempliceRepository sceltaSempliceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteByStoria() {
        Storia storia = new Storia();
        doNothing().when(sceltaSempliceRepository).deleteByStoria(storia);

        sceltaSempliceRepository.deleteByStoria(storia);

        verify(sceltaSempliceRepository, times(1)).deleteByStoria(storia);
    }
}
