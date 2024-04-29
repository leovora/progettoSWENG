package com.progettosweng.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.progettosweng.application.service.UserService;
import com.progettosweng.application.repository.UserRepository;
import com.progettosweng.application.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest{

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user = new User("test@example.com", "123", "leo", "vora");

    //Test che verifica la chiamata del repository (non l'effettivo salvataggio nel database)
    @Test
    public void saveUserTest(){
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.saveUser(user));
    }

    //Test che verifica che il metodo getUser faccia uso corretto di UserRepository restituendo l'utente corrispondente
    @Test
    public void getUserTest() {
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.ofNullable(user));
        assertEquals(user, userService.getUser(user.getUsername()));
    }

    //Test che verifica che il metodo existsUserByUsername faccia uso corretto di UserRepository restituendo true
    @Test
    public void checkExistingUser() {
        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
        assertTrue(userService.existsUserByUsername(user.getUsername()));
    }



}