package pl.zajavka.buisness.DAO;

import pl.zajavka.domain.Salesman;

import java.util.List;
import java.util.Optional;

public interface SalesmanDAO {
    Optional<Salesman> findByPesel(String pesel);

    List<Salesman> findAvailable();
}
