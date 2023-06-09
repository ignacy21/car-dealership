package pl.zajavka.buisness.management;

import jakarta.persistence.*;
import pl.zajavka.infrastructure.database.entity.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pl.zajavka.buisness.management.Keys.Entity.*;
import static pl.zajavka.buisness.management.Keys.InputDataGroup.*;

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

    public List<Map<String, List<String>>> prepareFirstTimePurchaseData() {
        return InputDataCache.getInputData(BUY_FIRST_TIME, this::prepareMap);
    }

    public List<Map<String, List<String>>> prepareSecondTimePurchaseData() {
        return InputDataCache.getInputData(BUY_AGAIN, this::prepareMap);
    }

    private Map<String, List<String>> prepareMap(String line) {
        List<String> grouped = Arrays.stream(line.split("->")).map(String::trim).toList();

        return IntStream.iterate(0, i -> i + 2)
                .boxed()
                .limit(3)
                .collect(Collectors.toMap(grouped::get, i -> List.of(grouped.get(i + 1).split(";"))));
    }

    public static CustomerEntity buildCustomerEntity(List<String> inputData, InvoiceEntity invoice) {
        return CustomerEntity.builder()
                .name(inputData.get(0))
                .surname(inputData.get(1))
                .phone(inputData.get(2))
                .email(inputData.get(3))
                .address(AddressEntity.builder()
                        .country(inputData.get(4))
                        .city(inputData.get(5))
                        .postalCode(inputData.get(6))
                        .address(inputData.get(7))
                        .build())
                .invoices(Set.of(invoice))
                .build();
    }
}
