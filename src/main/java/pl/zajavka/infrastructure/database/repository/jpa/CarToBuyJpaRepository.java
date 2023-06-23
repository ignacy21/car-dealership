package pl.zajavka.infrastructure.database.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface CarToBuyJpaRepository extends JpaRepository<CarToBuyEntity, Integer> {

    Optional<CarToBuyEntity> findByVin(String vin);
}

