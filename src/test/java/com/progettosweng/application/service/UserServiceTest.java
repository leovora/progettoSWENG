package com.progettosweng.application.service;

import com.progettosweng.application.entity.User;
import com.progettosweng.application.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testGetUser() {
        User user = new User();
        when(userRepository.findByUsername("testUser")).thenReturn(user);

        User foundUser = userService.getUser("testUser");

        assertNotNull(foundUser);
        verify(userRepository, times(1)).findByUsername("testUser");
    }

    @Test
    public void testExistsUserByUsername() {
        when(userRepository.existsByUsername("testUser")).thenReturn(true);

        boolean exists = userService.existsUserByUsername("testUser");

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername("testUser");
    }
}

