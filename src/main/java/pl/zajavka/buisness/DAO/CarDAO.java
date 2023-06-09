package pl.zajavka.buisness.DAO;

import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.util.Optional;

public interface CarDAO {

    Optional<CarToBuyEntity> findCarToBuyVin(String vin);
}
