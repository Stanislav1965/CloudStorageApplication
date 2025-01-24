package ru.netology.service;

import lombok.AllArgsConstructor;
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

@Service
@AllArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public Login login(UserDto userDto) {

        User user = userRepository.findByLogin(userDto.getLogin())
                .orElseThrow(() -> new UnauthorizedUserException("Пользователь не найден"));
        if (passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            final String authToken = jwtService.generateAccessToken(user);
            return new Login(authToken);
        } else {
            throw new InvalidCredentialsException("Неправильный пароль");
        }
    }

    public String getLogin() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

