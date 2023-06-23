package pl.zajavka.buisness.DAO;

import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.CarToService;

import java.util.Optional;

public interface CarToBuyDAO {

    Optional<CarToBuy> findCarToBuyVin(String vin);

//    CarToBuy saveCarToBuy(CarToService car);
}
