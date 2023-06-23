package pl.zajavka.buisness;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.zajavka.buisness.DAO.PartDAO;
import pl.zajavka.domain.Part;

import java.util.Optional;

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
}
