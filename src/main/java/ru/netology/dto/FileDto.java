package ru.netology.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    @JsonProperty("name")
    private String fileName;
    private Long size;
}


