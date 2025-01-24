package ru.netology.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Login {

    @JsonProperty("auth-token")
    private String authToken;
}