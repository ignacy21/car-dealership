package pl.zajavka.infrastructure.database.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.PartEntity;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface PartJpaRepository extends JpaRepository<PartEntity, Integer> {
    Optional<PartEntity> findBySerialNumber(String serialNumber);
}

