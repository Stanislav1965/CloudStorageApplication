package ru.netology.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.dto.UserDto;
import ru.netology.exception.InvalidCredentialsException;
import ru.netology.exception.UnauthorizedUserException;
import ru.netology.model.Login;
import ru.netology.service.AuthenticationService;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody UserDto userDto)
            throws UnauthorizedUserException, InvalidCredentialsException {
        final Login token;
        token = authenticationService.login(userDto);
        return ResponseEntity.ok(token);
    }
}


