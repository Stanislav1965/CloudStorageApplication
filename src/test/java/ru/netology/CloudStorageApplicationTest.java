package ru.netology;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.controller.AuthenticationController;
import ru.netology.dto.UserDto;
import ru.netology.entity.User;
import ru.netology.model.Login;
import ru.netology.repository.UserRepository;
import java.util.Objects;
import java.util.Optional;


@Testcontainers
@SpringBootTest()
@ContextConfiguration(initializers = CloudStorageApplicationTest.EnvInitializer.class)
public class CloudStorageApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationController authenticationController;

    private final User user = User.builder()
            .id(1L)
            .login("test@mail.ru")
            .password("$2a$10$7j8NHwiDXGI3P/uImfH6CeEaVBkGMfSYW081egEAz6gEQIorW2ZTK")
            .build();

    private static final PostgreSQLContainer<?> psql = new PostgreSQLContainer<>()
            .withUsername("postgres")
            .withPassword("root")
            .withExposedPorts(5432);

    static class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=jdbc:postgresql://localhost:5432/postgres",
                    "spring.datasource.username=postgres",
                    "spring.datasource.password=root"
            ).applyTo(applicationContext);
        }
    }

    @BeforeAll
    public static void setUp() {
        psql.withReuse(true);
        psql.start();
    }

    @AfterAll
    public static void tearDown() {
        psql.stop();
    }

    @BeforeEach
    void init() {
        Optional<User> findUser = userRepository.findByLogin(user.getLogin());
        if (findUser.isEmpty()) {
            userRepository.save(user);
        }
    }

   @AfterEach
    void clear() {
        userRepository.delete(user);
    }

    @Test
    void testLoginController() {
        ResponseEntity<Login> login = authenticationController.login(new UserDto("test@mail.ru", "user1user1"));
        Assertions.assertNotNull(Objects.requireNonNull(login.getBody()).getAuthToken());
    }

}

