package pl.zajavka.buisness.DAO;

import pl.zajavka.domain.Service;

import java.util.Optional;

public interface ServiceDAO {
    Optional<Service> findByServiceCode(String serviceCode);
}
