package com.progettosweng.application.service;

import com.progettosweng.application.entity.AbstractUser;
import com.progettosweng.application.entity.Inventario;
import com.progettosweng.application.entity.Oggetto;
import com.progettosweng.application.entity.Storia;
import com.progettosweng.application.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveOggettoInventario() {
        Inventario inventario = new Inventario();
        when(inventarioRepository.save(inventario)).thenReturn(inventario);

        inventarioService.saveOggettoInventario(inventario);

        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    public void testDeleteInventarioByStoria() {
        Storia storia = new Storia();

        inventarioService.deleteInventarioByStoria(storia);

        verify(inventarioRepository, times(1)).deleteByStoria(storia);
    }

    @Test
    public void testDeleteInventarioUser() {
        AbstractUser user = new AbstractUser() {};
        Storia storia = new Storia();

        inventarioService.deleteInventarioUser(user, storia);

        verify(inventarioRepository, times(1)).deleteByUser(user, storia);
    }

    @Test
    public void testCheckOggettoInventario() {
        AbstractUser user = new AbstractUser() {};
        Oggetto oggetto = new Oggetto();
        when(inventarioRepository.checkOggetto(user, oggetto)).thenReturn(true);

        boolean exists = inventarioService.checkOggettoInventario(user, oggetto);

        assertTrue(exists);
        verify(inventarioRepository, times(1)).checkOggetto(user, oggetto);
    }
}
