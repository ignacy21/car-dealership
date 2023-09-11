package pl.zajavka.buisness.DAO;

import pl.zajavka.domain.CarToBuy;

import java.util.List;
import java.util.Optional;

public interface CarToBuyDAO {

    Optional<CarToBuy> findCarToBuyByVin(String vin);

    List<CarToBuy> findAvailable();
}
