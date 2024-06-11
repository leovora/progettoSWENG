package com.progettosweng.application.repository;

import com.progettosweng.application.entity.Storia;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StoriaRepositoryTest {

    @Mock
    private StoriaRepository storiaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        ArrayList<Storia> storie = new ArrayList<>();
        when(storiaRepository.findByUsername(username)).thenReturn(storie);

        ArrayList<Storia> foundStories = storiaRepository.findByUsername(username);

        assertEquals(storie, foundStories);
        verify(storiaRepository, times(1)).findByUsername(username);
    }

    @Test
    void testSearch() {
        String filtro = "test";
        List<Storia> storie = new ArrayList<>();
        when(storiaRepository.search(filtro)).thenReturn(storie);

        List<Storia> foundStories = storiaRepository.search(filtro);

        assertEquals(storie, foundStories);
        verify(storiaRepository, times(1)).search(filtro);
    }

    @Test
    void testSearchOwn() {
        String username = "testUser";
        String filtro = "test";
        List<Storia> storie = new ArrayList<>();
        when(storiaRepository.searchOwn(username, filtro)).thenReturn(storie);

        List<Storia> foundStories = storiaRepository.searchOwn(username, filtro);

        assertEquals(storie, foundStories);
        verify(storiaRepository, times(1)).searchOwn(username, filtro);
    }
}
