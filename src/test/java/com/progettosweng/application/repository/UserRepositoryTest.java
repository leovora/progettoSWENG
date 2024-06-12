package com.progettosweng.application.repository;

import com.progettosweng.application.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExistsByUsername() {
        String username = "testUser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        boolean exists = userRepository.existsByUsername(username);

        assertTrue(exists);
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    void testFindByUsername() {
        String username = "testUser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(user);

        User foundUser = userRepository.findByUsername(username);

        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findByUsername(username);
    }
}
