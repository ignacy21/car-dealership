package pl.zajavka.buisness.management;

import org.springframework.stereotype.Service;
import pl.zajavka.domain.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.zajavka.buisness.management.Keys.Constants.WHAT;
import static pl.zajavka.buisness.management.Keys.Domain.*;
import static pl.zajavka.buisness.management.Keys.InputDataGroup.*;

@Service
public class FileDataPreparationService {

    public List<CarPurchaseRequestInputData> prepareFirstTimePurchaseData() {
        return InputDataCache.getInputData(BUY_FIRST_TIME, this::prepareMap).stream()
                .map(this::prepareFirstTimePurchaseData)
                .toList();
    }


    public List<CarPurchaseRequestInputData> prepareSecondTimePurchaseData() {
        return InputDataCache.getInputData(BUY_AGAIN, this::prepareMap).stream()
                .map(this::prepareSecondTimePurchaseData)
                .toList();
    }

    private CarPurchaseRequestInputData prepareFirstTimePurchaseData(Map<String, List<String>> inputData) {
        List<String> customerData = inputData.get(CUSTOMER.toString());
        return CarPurchaseRequestInputData.builder()
                .customerName(customerData.get(0))
                .customerSurname(customerData.get(1))
                .customerPhone(customerData.get(2))
                .customerEmail(customerData.get(3))
                .customerAddressCountry(customerData.get(4))
                .customerAddressCity(customerData.get(5))
                .customerAddressPostalCode(customerData.get(6))
                .customerAddressStreet(customerData.get(7))
                .carVin(inputData.get(CAR.toString()).get(0))
                .salesmanPesel(inputData.get(SALESMAN.toString()).get(0))
                .build();
    }


    private CarPurchaseRequestInputData prepareSecondTimePurchaseData(Map<String, List<String>> inputData) {
        return CarPurchaseRequestInputData.builder()
                .customerEmail(inputData.get(CUSTOMER.toString()).get(0))
                .carVin(inputData.get(CAR.toString()).get(0))
                .salesmanPesel(inputData.get(SALESMAN.toString()).get(0))
                .build();
    }

    public static Customer buildCustomer(CarPurchaseRequestInputData inputData, Invoice invoice) {
        return Customer.builder()
                .name(inputData.getCustomerName())
                .surname(inputData.getCustomerSurname())
                .phone(inputData.getCustomerPhone())
                .email(inputData.getCustomerEmail())
                .address(Address.builder()
                        .country(inputData.getCustomerAddressCountry())
                        .city(inputData.getCustomerAddressCity())
                        .postalCode(inputData.getCustomerAddressPostalCode())
                        .address(inputData.getCustomerAddressStreet())
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

    private Customer createCustomer(List<String> inputData) {
        if (inputData.size() == 1) {
            return Customer.builder()
                    .email(inputData.get(0))
                    .build();
        }
        return Customer.builder()
                .name(inputData.get(0))
                .surname(inputData.get(1))
                .phone(inputData.get(2))
                .email(inputData.get(3))
                .address(Address.builder()
                        .country(inputData.get(4))
                        .city(inputData.get(5))
                        .postalCode(inputData.get(6))
                        .address(inputData.get(7))
                        .build())
                .build();
    }

    private CarToService createCar(List<String> inputData) {
        if (inputData.size() == 1) {
            return CarToService.builder()
                    .vin(inputData.get(0))
                    .build();
        }
        return CarToService.builder()
                .vin(inputData.get(0))
                .brand(inputData.get(1))
                .model(inputData.get(2))
                .year(Integer.valueOf(inputData.get(3)))
                .build();
    }

    public List<CarServiceProcessingInputData> prepareServiceRequestToProcess() {
        return InputDataCache.getInputData(DO_THE_SERVICE, this::prepareMap).stream()
                .map(this::createCarServiceRequestToProcess)
                .toList();
    }

    private CarServiceProcessingInputData createCarServiceRequestToProcess(Map<String, List<String>> inputData) {
        List<String> whats = inputData.get(WHAT.name());
        return CarServiceProcessingInputData.builder()
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
