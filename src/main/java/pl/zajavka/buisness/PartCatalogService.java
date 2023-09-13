package pl.zajavka.buisness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajavka.buisness.DAO.PartDAO;
import pl.zajavka.domain.Part;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class PartCatalogService {

    private final PartDAO partDAO;

    @Transactional
    public Part findPart(String partSerialNumber) {
        Optional<Part> part = partDAO.findBySerialNumber(partSerialNumber);
        if (part.isEmpty()) {
            throw new RuntimeException("Could not find part by serial number: [%s]".formatted(partSerialNumber));
        }
        return part.get();
    }

    public List<Part> findAll() {
        List<Part> availableParts = partDAO.findAll();
        log.info("Available parts: [{}]", availableParts);
        return availableParts;
    }
}
