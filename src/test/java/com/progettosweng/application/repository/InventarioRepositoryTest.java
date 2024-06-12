package com.progettosweng.application.repository;

import com.progettosweng.application.entity.AbstractUser;
import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InventarioRepositoryTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDeleteByStoria() {
        Storia storia = new Storia();
        doNothing().when(inventarioRepository).deleteByStoria(storia);

        inventarioRepository.deleteByStoria(storia);

        verify(inventarioRepository, times(1)).deleteByStoria(storia);
    }

    @Test
    void testCheckOggetto() {
        AbstractUser user = new UserConcreto();
        Oggetto oggetto = new Oggetto();
        when(inventarioRepository.checkOggetto(user, oggetto)).thenReturn(true);

        boolean exists = inventarioRepository.checkOggetto(user, oggetto);

        assertTrue(exists);
        verify(inventarioRepository, times(1)).checkOggetto(user, oggetto);
    }

    @Test
    void testDeleteByUserAndStoria() {
        AbstractUser user = new UserConcreto();
        Storia storia = new Storia();
        doNothing().when(inventarioRepository).deleteByUser(user, storia);

        inventarioRepository.deleteByUser(user, storia);

        verify(inventarioRepository, times(1)).deleteByUser(user, storia);
    }
    
    static class UserConcreto extends AbstractUser {

    }
}
