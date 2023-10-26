package pl.zajavka.buisness;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.buisness.DAO.CarToBuyDAO;
import pl.zajavka.buisness.DAO.CarToServiceDAO;
import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.CarToService;
import pl.zajavka.domain.exception.NotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class CarService {

    private final CarToBuyDAO carToBuyDAO;
    private final CarToServiceDAO carToServiceDAO;

    @Transactional
    public List<CarToBuy> finAvailableCars() {
        List<CarToBuy> availableCars = carToBuyDAO.findAvailable();
        log.info("Available cars [{}]", availableCars.size());
        return availableCars;
    }

    @Transactional
    public CarToBuy findCarToBuy(String vin) {
        Optional<CarToBuy> carToBuyVin = carToBuyDAO.findCarToBuyByVin(vin);
        if (carToBuyVin.isEmpty()) {
            throw new NotFoundException("Could not find car by vin: [%s]".formatted(vin));
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

    public Collection<CarToService> findAllCarsWithHistory() {
        List<CarToService> allCars = carToServiceDAO.findAll();
        log.info("Cars to show history: [{}]", allCars.size());
        return allCars;
    }

    public CarHistory findCarHistoryByVin(String carVin) {
        return carToServiceDAO.findCarHistoryByVin(carVin);
    }
}
