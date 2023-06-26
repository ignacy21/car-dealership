package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.zajavka.buisness.CarPurchaseService;
import pl.zajavka.buisness.CarService;
import pl.zajavka.buisness.CarServiceProcessingService;
import pl.zajavka.buisness.CarServiceRequestService;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;
import pl.zajavka.infrastructure.database.entity.ServiceMechanicEntity;
import pl.zajavka.infrastructure.database.entity.ServicePartEntity;
import pl.zajavka.infrastructure.database.repository.jpa.CarServiceRequestJpaRepository;
import pl.zajavka.infrastructure.database.repository.jpa.InvoiceJpaRepository;
import pl.zajavka.infrastructure.database.repository.jpa.ServiceMechanicJpaRepository;
import pl.zajavka.infrastructure.database.repository.jpa.ServicePartJpaRepository;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@Testcontainers
@SpringJUnitConfig(classes = {ApplicationConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CarDealershipTest {

    @Container
    static PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:alpine");
    // docker run --name postgres-1 -e POSTGRES_PASSWORD=postgresql -d -p 5432:5432 postgres:alpine

    @DynamicPropertySource
    @SuppressWarnings("unused")
    static void setPostgresqlContainerProperties(DynamicPropertyRegistry registry) {
        registry.add("jdbc.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("jdbc.user", POSTGRESQL_CONTAINER::getUsername);
        registry.add("jdbc.pass", POSTGRESQL_CONTAINER::getPassword);
    }

    private CarPurchaseService carPurchaseService;
    private CarServiceRequestService carServiceRequestService;
    private CarServiceProcessingService carServiceProcessingService;
    private CarService carService;

    private final InvoiceJpaRepository invoiceJpaRepository;
    private final CarServiceRequestJpaRepository carServiceRequestJpaRepository;
    private final ServiceMechanicJpaRepository serviceMechanicJpaRepository;
    private final ServicePartJpaRepository servicePartJpaRepository;


    @Test
    @Order(1)
    void purchase() {
        log.info("### RUNNING ORDER 1");
        carPurchaseService.purchase();
    }

    @Test
    @Order(2)
    void makeServiceRequest() {
        log.info("### RUNNING ORDER 2");
        carServiceRequestService.requestService();
    }

    @Test
    @Order(3)
    void processServiceRequest() {
        log.info("### RUNNING ORDER 3");
        carServiceProcessingService.process();
    }

    @Test
    @Order(4)
    void printCarHistory() {
        log.info("### RUNNING ORDER 4");
        carService.printCarHistory("2C3CDYAG2DH731952");
        carService.printCarHistory("1GCEC19X27Z109567");
    }

    @Test
    @Order(5)
    void verify() {

        assertEquals(6, invoiceJpaRepository.findAll().size());

        List<CarServiceRequestEntity> allServiceRequest = carServiceRequestJpaRepository.findAll();
        assertEquals(3, allServiceRequest.size());
        assertEquals(2, allServiceRequest.stream().filter(sr -> Objects.nonNull(sr.getCompletedDateTime())).count());

        List<ServiceMechanicEntity> allServiceMechanics = serviceMechanicJpaRepository.findAll();
        assertEquals(5, allServiceMechanics.size());

        List<ServicePartEntity> allServiceParts = servicePartJpaRepository.findAll();
        assertEquals(4, allServiceParts.size());
    }


}
