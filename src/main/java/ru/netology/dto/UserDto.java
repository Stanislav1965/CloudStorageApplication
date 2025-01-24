package ru.netology.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String login;
    private String password;
}

