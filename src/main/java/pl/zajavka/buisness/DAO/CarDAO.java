package pl.zajavka.buisness.DAO;

import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;

import java.util.Optional;

public interface CarDAO {

    Optional<CarToBuyEntity> findCarToBuyVin(String vin);

    Optional<CarToServiceEntity> findCarToServiceByVin(String vin);

    CarToServiceEntity saveCarToService(CarToServiceEntity entity);
}
