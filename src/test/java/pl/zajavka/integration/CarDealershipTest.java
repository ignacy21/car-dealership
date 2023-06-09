package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.buisness.CarPurchaseService;
import pl.zajavka.buisness.CarService;
import pl.zajavka.buisness.CustomerService;
import pl.zajavka.buisness.SalesmanService;
import pl.zajavka.buisness.management.CarDealerShipManagementService;
import pl.zajavka.buisness.management.CarServiceRequestService;
import pl.zajavka.buisness.management.FileDataPreparationService;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.repository.CarDealershipManagementRepository;
import pl.zajavka.infrastructure.database.repository.CarRepository;
import pl.zajavka.infrastructure.database.repository.CustomerRepository;
import pl.zajavka.infrastructure.database.repository.SalesmanRepository;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealerShipManagementService carDealerShipManagementService;
    private CarPurchaseService carPurchaseService;
    private CarServiceRequestService carServiceRequestService;



    @BeforeEach
    void beforeEach() {
        this.carDealerShipManagementService = new CarDealerShipManagementService(
                new CarDealershipManagementRepository(),
                new FileDataPreparationService()
        );
        this.carPurchaseService = new CarPurchaseService(
                new FileDataPreparationService(),
                new CustomerService(new CustomerRepository()),
                new CarService(new CarRepository()),
                new SalesmanService(new SalesmanRepository()));
        this.carServiceRequestService = new CarServiceRequestService(
                new FileDataPreparationService(),
                new CarService(new CarRepository()),
                new CustomerService(new CustomerRepository())
        );
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.closeSessionFactory();
    }

    @Test
    @Order(1)
    void purge() {
        log.info("### RUNNING ORDER 1");
        carDealerShipManagementService.purge();

    }

    @Test
    @Order(2)
    void init() {
        log.info("### RUNNING ORDER 2");
        carDealerShipManagementService.init();
    }

    @Test
    @Order(3)
    void purchase() {
        log.info("### RUNNING ORDER 3");
        carPurchaseService.purchase();
    }

    @Test
    @Order(4)
    void makeServiceRequest() {
        log.info("### RUNNING ORDER 4");
        carServiceRequestService.requestService();
    }

    @Test
    @Order(5)
    void processServiceRequest() {
        log.info("### RUNNING ORDER 5");
    }

    @Test
    @Order(6)
    void printCarHistory() {
        log.info("### RUNNING ORDER 6");
    }

}
