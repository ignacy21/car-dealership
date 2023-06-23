package pl.zajavka.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.zajavka.buisness.DAO.CarToServiceDAO;
import pl.zajavka.domain.CarHistory;
import pl.zajavka.domain.CarToService;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;
import pl.zajavka.infrastructure.database.repository.jpa.CarToServiceJpaRepository;
import pl.zajavka.infrastructure.database.repository.mapper.CarToServiceEntityMapper;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class CarToServiceRepository implements CarToServiceDAO {

    private final CarToServiceJpaRepository carToServiceJpaRepository;
    private final CarToServiceEntityMapper carToServiceEntityMapper;

    @Override
    public Optional<CarToService> findCarToServiceByVin(String vin) {
        return carToServiceJpaRepository.findByVin(vin)
                .map(carToServiceEntityMapper::mapFromEntity);
    }

    @Override
    public CarToService saveCarToService(CarToService car) {
        CarToServiceEntity entityToSave = carToServiceEntityMapper.mapToEntity(car);
        CarToServiceEntity saved = carToServiceJpaRepository.save(entityToSave);
        return carToServiceEntityMapper.mapFromEntity(saved);
    }

    @Override
    public CarHistory findCarHistoryByVin(String vin) {
        CarToServiceEntity carHistoryByVin = carToServiceJpaRepository.findCarHistoryByVin(vin).orElseThrow();
        return carToServiceEntityMapper.mapFromEntity(vin, carHistoryByVin);
    }
}
