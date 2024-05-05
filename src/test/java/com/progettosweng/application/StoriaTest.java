package com.progettosweng.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.StoriaRepository;
import com.progettosweng.application.service.StoriaService;
import com.progettosweng.application.entity.Storia;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StoriaTest {

    @Autowired
    private StoriaService storiaService;

    @MockBean
    private StoriaRepository storiaRepository;

    private User user = new User(
            "User",
            "123",
            "Nome",
            "Cognome"
    );

    private Storia storia1 = new Storia(
            "Titolo della prima storia",
            "Descrizione della prima storia",
            10,
            user
    );
    private Storia storia2 = new Storia(
            "Titolo della seconda storia",
            "Descrizione della seconda storia",
            15,
            user
    );

    private List<Storia> lista = Arrays.asList(storia1, storia2);

    //Test che verifica la chiamata del repository
    @Test
    public void saveStoriaTest(){
        when(storiaRepository.save(storia1)).thenReturn(storia1);
        assertEquals(storia1, storiaService.saveStoria(storia1));
    }

    //Test che verifica che il metodo getStoria faccia uso corretto di StoriaRepository restituendo la storia corrispondente
    @Test
    public void getStoriaTest() {
        when(storiaRepository.findById(storia1.getIdStoria())).thenReturn(Optional.ofNullable(storia1));
        assertEquals(storia1, storiaService.getStoria(storia1.getIdStoria()));
    }

    //Test che verifica che il metodo existsUserByUsername faccia uso corretto di UserRepository restituendo true
    @Test
    public void checkExistingStoriaTest() {
        when(storiaRepository.existsById(storia1.getIdStoria())).thenReturn(true);
        assertTrue(storiaService.existsStoria(storia1.getIdStoria()));
    }

    @Test
    public void getAllStorieTest() {
        when(storiaRepository.findAll()).thenReturn(new ArrayList<>(lista));
        assertEquals(storiaService.getAllStorie(), lista);

    }
}
