package pl.zajavka.buisness;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.zajavka.buisness.management.FileDataPreparationService;
import pl.zajavka.domain.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class CarPurchaseService {

    private final FileDataPreparationService fileDataPreparationService;
    private final CustomerService customerService;
    private final CarService carService;
    private final SalesmanService salesmanService;

    @Transactional
    public void purchase() {
        var firstTimePurchaseData = fileDataPreparationService.prepareFirstTimePurchaseData();
        var secondTimePurchaseData = fileDataPreparationService.prepareSecondTimePurchaseData();

        List<Customer> firstTimeCustomers  = firstTimePurchaseData.stream()
                .map(this::createFirstTimeToBuyCustomer)
                .toList();
        firstTimeCustomers.forEach(customerService::issueInvoice);

        List<Customer> nextTimeCustomers  = secondTimePurchaseData.stream()
                .map(this::createNextTimeToBuyCustomer)
                .toList();
        nextTimeCustomers.forEach(customerService::issueInvoice);
    }


    private Customer createFirstTimeToBuyCustomer(CarPurchaseRequestInputData inputData) {
        CarToBuy car = carService.findCarToBuy(inputData.getCarVin());
        Salesman salesman = salesmanService.findSalesman(inputData.getSalesmanPesel());
        Invoice invoice = buildInvoice(car, salesman);

        return FileDataPreparationService.buildCustomer(inputData, invoice);
    }


    private Customer createNextTimeToBuyCustomer(CarPurchaseRequestInputData inputData) {
        Customer existingCustomer = customerService.findCustomer(inputData.getCustomerEmail());
        CarToBuy car = carService.findCarToBuy(inputData.getCarVin());
        Salesman salesman = salesmanService.findSalesman(inputData.getSalesmanPesel());
        Invoice invoice = buildInvoice(car, salesman);
        existingCustomer.getInvoices().add(invoice);
        return existingCustomer;
    }

    private Invoice buildInvoice(CarToBuy car, Salesman salesman) {
        return Invoice.builder()
                .invoiceNumber(UUID.randomUUID().toString())
                .dateTime(OffsetDateTime.now())
                .car(car)
                .salesman(salesman)
                .build();
    }
}
