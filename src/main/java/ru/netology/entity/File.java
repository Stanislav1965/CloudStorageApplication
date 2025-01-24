package ru.netology.entity;

import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@EnableJpaRepositories("ru.netology")
@Getter
@Table(name = "Files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String fileName;

    @Column(name = "content",  nullable = false)
    private byte[] content;

    @Column(name = "size", nullable = false)
    private Long size;

    public File(String name, byte[] content, Long size){
        this.fileName = name;
        this.content = content;
        this.size = size;
    }
}
