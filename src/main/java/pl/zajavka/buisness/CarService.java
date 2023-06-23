package pl.zajavka.buisness;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.zajavka.buisness.DAO.CarToBuyDAO;
import pl.zajavka.buisness.DAO.CarToServiceDAO;
import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.CarToService;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CarService {

    private final CarToServiceDAO carToServiceDAO;
    private final CarToBuyDAO carToBuyDAO;

    @Transactional
    public CarToBuy findCarToBuy(String vin) {
        Optional<CarToBuy> carToBuyVin = carToBuyDAO.findCarToBuyVin(vin);
        if (carToBuyVin.isEmpty()) {
            throw new RuntimeException("Could not find car by vin: [%s]".formatted(vin));
        }
        return carToBuyVin.get();
    }
    @Transactional
    public Optional<CarToService> findCarToService(String vin) {
        return carToServiceDAO.findCarToServiceByVin(vin);
    }
    @Transactional
    public CarToService saveCarToService(CarToBuy carToBuy) {
        CarToService carToService = CarToService.builder()
                .vin(carToBuy.getVin())
                .brand(carToBuy.getBrand())
                .model(carToBuy.getModel())
                .year(carToBuy.getYear())
                .build();
        return carToServiceDAO.saveCarToService(carToService);
    }
    @Transactional
    public CarToService saveCarToService(CarToService car) {
        return carToServiceDAO.saveCarToService(car);
    }

    public void printCarHistory(String vin) {
        CarHistory carHistoryByVin = carToServiceDAO.findCarHistoryByVin(vin);
        log.info("###CAR HISTORY FOR VIN: [{}]", vin);
        carHistoryByVin.getCarServiceRequests().forEach(this::printServiceRequest);
    }

    void printServiceRequest(CarHistory.CarServiceRequest serviceRequest) {
        log.info("###SERVICE REQUEST: [{}]", serviceRequest);
        serviceRequest.getServices().forEach(service -> log.info("###SERVICE: [{}]", service));
        serviceRequest.getParts().forEach(part -> log.info("###PART: [{}]", part));

    }
}
