package pl.zajavka.buisness;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.zajavka.buisness.DAO.MechanicDAO;
import pl.zajavka.domain.Mechanic;

import java.util.Optional;

@AllArgsConstructor
public class MechanicService {

    private final MechanicDAO mechanicDAO;

    @Transactional
    public Mechanic findMechanic(String pesel) {
        Optional<Mechanic> mechanic = mechanicDAO.findByPesel(pesel);
        if (mechanic.isEmpty()) {
            throw new RuntimeException("Could not find mechanic by pesel: [%s]".formatted(pesel));
        }
        return mechanic.get();
    }
}
