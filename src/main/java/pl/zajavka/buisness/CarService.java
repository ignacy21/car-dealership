package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import pl.zajavka.buisness.DAO.CarDAO;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.util.Optional;

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
}
