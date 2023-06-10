package pl.zajavka.buisness.management;

import jakarta.persistence.*;
import pl.zajavka.domain.CarServiceProcessingRequest;
import pl.zajavka.domain.CarServiceRequest;
import pl.zajavka.infrastructure.database.entity.*;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pl.zajavka.buisness.management.Keys.Constants.WHAT;
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

    public List<CarServiceRequest> createCarServiceRequests() {
        return InputDataCache.getInputData(SERVICE_REQUEST, this::prepareMap).stream()
                .map(this::createCarServiceRequest)
                .toList();
    }

    private CarServiceRequest createCarServiceRequest(Map<String, List<String>> inputData) {
        return CarServiceRequest.builder()
                .customer(createCustomer(inputData.get(CUSTOMER.name())))
                .car(createCar(inputData.get(CAR.name())))
                .customerComment(inputData.get(WHAT.name()).get(0))
                .build();
    }

    private CarServiceRequest.Car createCar(List<String> inputData) {
        if (inputData.size() == 1) {
            return CarServiceRequest.Car.builder()
                    .vin(inputData.get(0))
                    .build();
        }
        return CarServiceRequest.Car.builder()
                .vin(inputData.get(0))
                .brand(inputData.get(1))
                .model(inputData.get(2))
                .year(Integer.valueOf(inputData.get(3)))
                .build();
    }

    private CarServiceRequest.Customer createCustomer(List<String> inputData) {
        if (inputData.size() == 1) {
            return CarServiceRequest.Customer.builder()
                    .email(inputData.get(0))
                    .build();
        }
        return CarServiceRequest.Customer.builder()
                .name(inputData.get(0))
                .surname(inputData.get(1))
                .phone(inputData.get(2))
                .email(inputData.get(3))
                .address(CarServiceRequest.Address.builder()
                        .country(inputData.get(4))
                        .city(inputData.get(5))
                        .postalCode(inputData.get(6))
                        .address(inputData.get(7))
                        .build())
                .build();
    }

    public List<CarServiceProcessingRequest> prepareServiceRequestToProcess() {
        return InputDataCache.getInputData(DO_THE_SERVICE, this::prepareMap).stream()
                .map(this::createCarServiceRequestToProcess)
                .toList();
    }

    private CarServiceProcessingRequest createCarServiceRequestToProcess(Map<String, List<String>> inputData) {
        List<String> whats = inputData.get(WHAT.name());
        return CarServiceProcessingRequest.builder()
                .mechanicPesel(inputData.get(MECHANIC.name()).get(0))
                .carVin(inputData.get(CAR.name()).get(0))
                .partSerialNumber(Optional.ofNullable(whats.get(0)).filter(v -> !v.isBlank()).orElse(null))
                .partQuantity(Optional.ofNullable(whats.get(1)).filter(v -> !v.isBlank()).map(Integer::parseInt).orElse(null))
                .serviceCode(whats.get(2))
                .hours(Integer.valueOf(whats.get(3)))
                .comment(whats.get(4))
                .done(whats.get(5))
                .build();
    }

    private Map<String, List<String>> prepareMap(String line) {
        List<String> grouped = Arrays.stream(line.split("->")).map(String::trim).toList();

        return IntStream.iterate(0, i -> i + 2)
                .boxed()
                .limit(grouped.size() / 2)
                .collect(Collectors.toMap(grouped::get, i -> List.of(grouped.get(i + 1).split(";"))));
    }
}
