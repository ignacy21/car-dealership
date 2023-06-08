package pl.zajavka.buisness.management;

import pl.zajavka.infrastructure.database.entity.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static pl.zajavka.buisness.management.Keys.Entity.*;
import static pl.zajavka.buisness.management.Keys.InputDataGroup.INIT;

public class FileDataPreparationService {

    public List<?> prepareInitData() {

        List<SalesmanEntity> salesman = InputDataCache
                .getInputData(INIT, SALESMAN, InputDataMapper::mapSalesman);

        List<MechanicEntity> mechanics = InputDataCache
                .getInputData(INIT, MECHANIC, InputDataMapper::mapMechanic);

        List<CarToBuyEntity> cars = InputDataCache
                .getInputData(INIT, CAR, InputDataMapper::mapCarToBuy);

        List<ServiceEntity> services = InputDataCache
                .getInputData(INIT, SERVICE, InputDataMapper::mapService);

        List<PartEntity> parts = InputDataCache
                .getInputData(INIT, PART, InputDataMapper::mapPart);


        List<?> list = Stream.of(salesman, mechanics, cars, services, parts)
                .flatMap(Collection::stream)
                .toList();
        return list;
    }
}
