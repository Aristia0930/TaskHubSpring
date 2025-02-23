package org.example.todo;

import org.example.todo.entity.User;
import org.example.todo.repository.UserRepository;
import org.example.todo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testGetUserId() {
        // Given
        User user = new User("한국", "korea", "12345", "일반유저");
        user.setUserNumber(1l);

        when(userRepository.findByUserId("korea")).thenReturn(user);

        // When
        User findUser = userService.getUserById("korea");
        System.out.println(findUser);

        // Then
        assertEquals("한국", findUser.getName());
    }
}

