package pl.zajavka.infrastructure.database.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.CarToBuyEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface CarToBuyJpaRepository extends JpaRepository<CarToBuyEntity, Integer> {

    Optional<CarToBuyEntity> findByVin(String vin);

//    @Query("""
//            SELECT car FROM CarToBuyEntity car
//            WHERE car.carId NOT IN (SELECT invoice.car.carToBuyId FROm InvoiceEntity invoice)
//            """)
    @Query("""
            SELECT car FROM CarToBuyEntity car
            LEFT JOIN FETCH car.invoice invoice
            WHERE invoice.car.carToBuyId IS NULL
            """)
    List<CarToBuyEntity> finaAvailableCars();
}

