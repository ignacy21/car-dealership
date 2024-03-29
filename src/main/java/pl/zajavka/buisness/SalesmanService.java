package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.buisness.DAO.SalesmanDAO;
import pl.zajavka.domain.Salesman;
import pl.zajavka.domain.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class SalesmanService {

    private final SalesmanDAO salesmanDAO;

    @Transactional
    public Salesman findSalesman(String pesel) {
        Optional<Salesman> salesman = salesmanDAO.findByPesel(pesel);
        if (salesman.isEmpty()) {
            throw new NotFoundException("Could not find salesman by pesel: [%s]".formatted(pesel));
        }
        return salesman.get();
    }

    public List<Salesman> findAvailableSalesman() {
        List<Salesman> availableSalesman = salesmanDAO.findAvailable();
        log.info("Available salesmen [{}]", availableSalesman.size());
        return availableSalesman;
    }
}
