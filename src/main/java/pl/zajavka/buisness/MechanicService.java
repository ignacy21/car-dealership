package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.buisness.DAO.MechanicDAO;
import pl.zajavka.domain.Mechanic;
import pl.zajavka.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MechanicService {

    private final MechanicDAO mechanicDAO;

    @Transactional
    public Mechanic findMechanic(String pesel) {
        Optional<Mechanic> mechanic = mechanicDAO.findByPesel(pesel);
        if (mechanic.isEmpty()) {
            throw new NotFoundException("Could not find mechanic by pesel: [%s]".formatted(pesel));
        }
        return mechanic.get();
    }

    @Transactional
    public List<Mechanic> findAvailableMechanics() {
        List<Mechanic> availableMechanic = mechanicDAO.findAvailable();
        log.info("Available mechanic [{}]", availableMechanic.size());
        return availableMechanic;
    }
}
