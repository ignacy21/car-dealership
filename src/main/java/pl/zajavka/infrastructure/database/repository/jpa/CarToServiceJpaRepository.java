package pl.zajavka.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.CarToServiceEntity;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface CarToServiceJpaRepository extends JpaRepository<CarToServiceEntity, Integer> {

    Optional<CarToServiceEntity> findByVin(String vin);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "carServiceRequests",
                    "carServiceRequests.serviceMechanics",
                    "carServiceRequests.serviceMechanics.service",
                    "carServiceRequests.serviceParts",
                    "carServiceRequests.serviceParts.part"
            }
    )
    Optional<CarToServiceEntity> findCarHistoryByVin(String vin);
}


