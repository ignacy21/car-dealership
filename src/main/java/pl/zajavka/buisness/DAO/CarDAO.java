package pl.zajavka.buisness.DAO;

import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.CarToService;

import java.util.Optional;

public interface CarDAO {

    Optional<CarToBuy> findCarToBuyVin(String vin);

    Optional<CarToService> findCarToServiceByVin(String vin);

    CarToService saveCarToService(CarToService );

    CarHistory findCarHistoryByVin(String vin);
}
