package pl.zajavka.buisness;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajavka.domain.CarToBuy;
import pl.zajavka.domain.Salesman;

import java.util.List;

@Service
@AllArgsConstructor
public class CarPurchaseService {

    private final CarService carService;
    private final SalesmanService salesmanService;

    public List<CarToBuy> availableCars() {
        return carService.finAvailableCars();
    }
    public List<Salesman> availableSalesman() {
        return salesmanService.findAvailableSalesman();
    }
//
//    private final FileDataPreparationService fileDataPreparationService;
//    private final CustomerService customerService;
//    private final CarService carService;
//
//    public void purchase() {
//        var firstTimePurchaseData = fileDataPreparationService.prepareFirstTimePurchaseData();
//        var secondTimePurchaseData = fileDataPreparationService.prepareSecondTimePurchaseData();
//
//        List<Customer> firstTimeCustomers  = firstTimePurchaseData.stream()
//                .map(this::createFirstTimeToBuyCustomer)
//                .toList();
//        firstTimeCustomers.forEach(customerService::issueInvoice);
//
//        List<Customer> nextTimeCustomers  = secondTimePurchaseData.stream()
//                .map(this::createNextTimeToBuyCustomer)
//                .toList();
//        nextTimeCustomers.forEach(customerService::issueInvoice);
//    }
//
//
//    private Customer createFirstTimeToBuyCustomer(CarPurchaseRequestInputData inputData) {
//        CarToBuy car = carService.findCarToBuy(inputData.getCarVin());
//        Salesman salesman = salesmanService.findSalesman(inputData.getSalesmanPesel());
//        Invoice invoice = buildInvoice(car, salesman);
//
//        return fileDataPreparationService.buildCustomer(inputData, invoice);
//    }
//
//
//    private Customer createNextTimeToBuyCustomer(CarPurchaseRequestInputData inputData) {
//        Customer existingCustomer = customerService.findCustomer(inputData.getCustomerEmail());
//        CarToBuy car = carService.findCarToBuy(inputData.getCarVin());
//        Salesman salesman = salesmanService.findSalesman(inputData.getSalesmanPesel());
//        Invoice invoice = buildInvoice(car, salesman);
//        Set<Invoice> existingInvoices = existingCustomer.getInvoices();
//        existingInvoices.add(invoice);
//        return existingCustomer.withInvoices(existingInvoices);
//    }
//
//    private Invoice buildInvoice(CarToBuy car, Salesman salesman) {
//        return Invoice.builder()
//                .invoiceNumber(UUID.randomUUID().toString())
//                .dateTime(OffsetDateTime.now())
//                .car(car)
//                .salesman(salesman)
//                .build();
//    }
}
