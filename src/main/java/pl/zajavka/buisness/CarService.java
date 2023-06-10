package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.zajavka.buisness.DAO.CarDAO;
import pl.zajavka.domain.CarServiceRequest;
import pl.zajavka.infrastructure.database.entity.CarHistoryEntity;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CarService {

    private final CarDAO carDAO;

    public CarToBuyEntity findCarToBuy(String vin) {
        Optional<CarToBuyEntity> carToBuyVin = carDAO.findCarToBuyVin(vin);
        if (carToBuyVin.isEmpty()) {
            throw new RuntimeException("Could not find car by vin: [%s]".formatted(vin));
        }
        return carToBuyVin.get();
    }

    public Optional<CarToServiceEntity> findCarToService(String vin) {
        return carDAO.findCarToServiceByVin(vin);
    }

    public CarToServiceEntity saveCarToService(CarToBuyEntity carToBuy) {
        CarToServiceEntity entity = CarToServiceEntity.builder()
                .vin(carToBuy.getVin())
                .brand(carToBuy.getBrand())
                .model(carToBuy.getModel())
                .year(carToBuy.getYear())
                .build();
        return carDAO.saveCarToService(entity);
    }

    public CarToServiceEntity saveCarToService(CarServiceRequest.Car car) {
        CarToServiceEntity entity = CarToServiceEntity.builder()
                .vin(car.getVin())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .build();
        return carDAO.saveCarToService(entity);
    }

    public void printCarHistory(String vin) {
        CarHistoryEntity carHistoryByVin = carDAO.findCarHistoryByVin(vin);
        log.info("###CAR HISTORY FOR VIN: [{}]", vin);
        carHistoryByVin.getServiceRequests().forEach(this::printServiceRequest);
    }

    void printServiceRequest(CarHistoryEntity.ServiceRequest serviceRequest) {
        log.info("###SERVICE REQUEST: [{}]", serviceRequest);
        serviceRequest.services().forEach(service -> log.info("###SERVICE: [{}]", service));
        serviceRequest.parts().forEach(part -> log.info("###PART: [{}]", part));

    }
}
