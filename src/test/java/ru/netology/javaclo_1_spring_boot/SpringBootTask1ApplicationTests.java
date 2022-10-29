package ru.netology.javaclo_1_spring_boot;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootTask1ApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Container
    private static final GenericContainer<?> devappContainer = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    @Container
    private static final GenericContainer<?> prodappContainer = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devappContainer.start();
        prodappContainer.start();
    }


    @Test
    void contextLoadsDevApp() {
        String HOST = "http://192.168.99.100:";
        Integer port = devappContainer.getMappedPort(8080);
        System.out.println("port: " + port);
        ResponseEntity<String> forEntity = testRestTemplate
                .getForEntity(HOST + port, String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals("Current profile is dev", forEntity.getBody());
    }

    @Test
    void contextProdApp() {
        String HOST = "http://192.168.99.100:";
        Integer port = prodappContainer.getMappedPort(8081);
        System.out.println("port: " + port);
        ResponseEntity<String> forEntity = testRestTemplate
                .getForEntity(HOST + port, String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals("Current profile is production", forEntity.getBody());
    }

}
