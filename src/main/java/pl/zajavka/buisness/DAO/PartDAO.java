package pl.zajavka.buisness.DAO;

import pl.zajavka.infrastructure.database.entity.PartEntity;

import java.util.Optional;

public interface PartDAO {
    Optional<PartEntity> findBySerialNumber(String serialNumber);
}
