package ru.netology.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.User;
import ru.netology.repository.UserRepository;

import java.util.Optional;

import static ru.netology.TestData.USERNAME_1;
import static ru.netology.TestData.USER_1;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private final User user = USER_1;

    @Test
    public void testFindByLogin(){
        Mockito.when(userRepository.findByLogin(USERNAME_1)).thenReturn(Optional.of(USER_1));
        if (userService.getByLogin(user.getLogin()).isPresent()){
            User savedUser = userService.getByLogin(user.getLogin()).get();
            Assertions.assertNotNull(savedUser);
        }
    }
}
