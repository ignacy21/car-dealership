package pl.zajavka.buisness.management;

import lombok.AllArgsConstructor;
import pl.zajavka.buisness.DAO.management.CarDealerShipManagementDAO;

import java.util.List;

@AllArgsConstructor
public class CarDealerShipManagementService {

    private final CarDealerShipManagementDAO carDealerShipManagementDAO;

    private final FileDataPreparationService fileDataPreparationService;

    public void purge() {
        carDealerShipManagementDAO.purge();
    }

    public void init() {
        List<?> entities = fileDataPreparationService.prepareInitData();
        carDealerShipManagementDAO.saveAll(entities);
    }

}
