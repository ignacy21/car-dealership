package pl.zajavka.infrastructure.database.repository.jpa;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zajavka.infrastructure.database.entity.MechanicEntity;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface MechanicJpaRepository extends JpaRepository<MechanicEntity, Integer> {
    Optional<MechanicEntity> findByPesel(String pesel);
}
