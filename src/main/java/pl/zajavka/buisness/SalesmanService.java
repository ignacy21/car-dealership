package pl.zajavka.buisness;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.zajavka.buisness.DAO.SalesmanDAO;
import pl.zajavka.domain.Salesman;

import java.util.Optional;

@AllArgsConstructor
public class SalesmanService {

    private final SalesmanDAO salesmanDAO;

    @Transactional
    public Salesman findSalesman(String pesel) {
        Optional<Salesman> salesman = salesmanDAO.findByPesel(pesel);
        if (salesman.isEmpty()) {
            throw new RuntimeException("Could not find salesman by pesel: [%s]".formatted(pesel));
        }
        return salesman.get();
    }
}
