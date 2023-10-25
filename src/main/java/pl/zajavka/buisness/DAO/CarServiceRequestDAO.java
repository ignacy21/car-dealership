package pl.zajavka.buisness.DAO;

import pl.zajavka.domain.CarServiceRequest;

import java.util.Set;

public interface CarServiceRequestDAO {
    Set<CarServiceRequest> findActiveServiceRequestsByCarVin(String carVin);

    Set<CarServiceRequest> findAvailable();
}
