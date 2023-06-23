package pl.zajavka.infrastructure.database.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.CarServiceRequestEntity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.DoubleStream;

@Repository
public interface CarServiceRequestJpaRepository extends JpaRepository<CarServiceRequestEntity, Integer> {

    @Query("""
            SELECT csr FROM CarServiceRequestEntity csr
            WHERE csr.completedDateTime IS NULL
            AND scr.car..vin = :carVin
            
            """
    )
    Set<CarServiceRequestEntity> findActiveServiceRequestsByCarVin(final @Param("carVin") String carVin);


}