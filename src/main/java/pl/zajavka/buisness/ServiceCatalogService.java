package pl.zajavka.buisness;


import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.buisness.DAO.ServiceDAO;
import pl.zajavka.domain.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceCatalogService {

    private final ServiceDAO serviceDAO;

    @Transactional
    public Service findService(String serviceCode) {
        Optional<Service> service = serviceDAO.findByServiceCode(serviceCode);
        if (service.isEmpty()) {
            throw new NotFoundException("Could not find service by service: [%s]".formatted(serviceCode));
        }
        return service.get();
    }

    public List<Service> findAll() {
        List<Service> availableParts = serviceDAO.findAll();
        log.info("Available parts: [{}]", availableParts);
        return availableParts;
    }
}
