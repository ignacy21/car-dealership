package pl.zajavka.buisness.DAO.management;

import java.util.List;

public interface CarDealerShipManagementDAO {

    void purge();

    void saveAll(List<?> entities);
}
