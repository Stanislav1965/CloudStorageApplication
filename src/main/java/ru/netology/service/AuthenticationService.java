package ru.netology.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.netology.dto.UserDto;
import ru.netology.entity.User;
import ru.netology.exception.InvalidCredentialsException;
import ru.netology.exception.UnauthorizedUserException;
import ru.netology.model.Login;
import ru.netology.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public Login login(UserDto userDto) {

        Optional<User> user = userRepository.findByLogin(userDto.getLogin());
        if (user.isEmpty()) {
            log.error("Незарегистрированный пользователь: {}", userDto.getLogin());
            throw new UnauthorizedUserException("Пользователь не найден!");
        }
        if (passwordEncoder.matches(userDto.getPassword(), user.get().getPassword())) {
            final String authToken = jwtService.generateAccessToken(user.get());
            log.info("Зарегистрировался пользователь: {}", userDto.getLogin());
            return new Login(authToken);
        } else {
            log.error("Неправильный пароль! Пользователь: {}", user.get().getLogin());
            throw new InvalidCredentialsException("Неправильный пароль!");
        }
    }

    public String getLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

