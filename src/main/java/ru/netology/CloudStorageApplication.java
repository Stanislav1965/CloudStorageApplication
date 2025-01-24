package ru.netology;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@SpringBootApplication
@Getter
@Setter
@ComponentScan("ru.netology")
@EnableJpaRepositories("ru.netology")
public class CloudStorageApplication {

    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    public static void main(String[] args) {
        SpringApplication.run(CloudStorageApplication.class, args);
    }
}
