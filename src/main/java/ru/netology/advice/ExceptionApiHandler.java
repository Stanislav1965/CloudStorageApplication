package ru.netology.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.exception.FileStorageException;
import ru.netology.exception.InvalidCredentialsException;
import ru.netology.exception.NotFoundException;
import ru.netology.exception.UnauthorizedUserException;
import ru.netology.model.ErrorMessage;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorMessage> invalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ErrorMessage> unauthorizedUser(UnauthorizedUserException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFound(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(e.getMessage()));
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorMessage> fileStorage(FileStorageException e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(e.getMessage()));
    }
}
