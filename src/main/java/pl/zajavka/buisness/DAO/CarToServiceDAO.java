package pl.zajavka.buisness.DAO;

import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToService;

import java.util.Optional;

public interface CarToServiceDAO {

    Optional<CarToService> findCarToServiceByVin(String vin);

    CarToService saveCarToService(CarToService car);

    CarHistory findCarHistoryByVin(String vin);
}
