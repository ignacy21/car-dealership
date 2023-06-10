package pl.zajavka.integration;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.zajavka.buisness.*;
import pl.zajavka.buisness.management.CarDealerShipManagementService;
import pl.zajavka.buisness.management.CarServiceRequestService;
import pl.zajavka.buisness.management.FileDataPreparationService;
import pl.zajavka.infrastructure.configuration.HibernateUtil;
import pl.zajavka.infrastructure.database.repository.*;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarDealershipTest {

    private CarDealerShipManagementService carDealerShipManagementService;
    private CarPurchaseService carPurchaseService;
    private CarServiceRequestService carServiceRequestService;
    private CarServiceProcessingService carServiceProcessingService;
    private CarService carService;


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
                new CustomerService(new CustomerRepository()),
                new CarServiceRequestRepository()
        );
        this.carServiceProcessingService = new CarServiceProcessingService(
                new FileDataPreparationService(),
                new MechanicService(new MechanicRepository()),
                new CarService(new CarRepository()),
                new ServiceCatalogService(new ServiceRepository()),
                new PartCatalogService(new PartRepository()),
                new CarServiceRequestService(
                        new FileDataPreparationService(),
                        new CarService(new CarRepository()),
                        new CustomerService(new CustomerRepository()),
                        new CarServiceRequestRepository()),
                new ServiceRequestProcessingRepository()
        );
        this.carService = new CarService(new CarRepository());
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
        carServiceProcessingService.process();
    }

    @Test
    @Order(6)
    void printCarHistory() {
        log.info("### RUNNING ORDER 6");
        carService.printCarHistory("2C3CDYAG2DH731952");
        carService.printCarHistory("1GCEC19X27Z109567");
    }

}
