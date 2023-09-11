package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zajavka.domain.Mechanic;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceRequestService {

//    private final CarService carService;
//    private final CustomerService customerService;
//    private final CarServiceRequestDAO carServiceRequestDAO;

    private final MechanicService mechanicService;

    public List<Mechanic> availableMechanics() {
        return mechanicService.findAvailableMechanics();
    }

//
//    public void requestService() {
//        Map<Boolean, List<CarServiceRequest>> serviceRequests = fileDataPreparationService.createCarServiceRequests().stream()
//                .collect(Collectors.groupingBy(e -> e.getCar().carBoughtHere()));
//
//        serviceRequests.get(true).forEach(this::serviceRequestsForExistingCar);
//        serviceRequests.get(false).forEach(this::serviceRequestsForNewCar);
//
//    }
//
//    private void serviceRequestsForExistingCar(CarServiceRequest request) {
//        CarToService car = carService.findCarToService(request.getCar().getVin())
//                .orElse(findInCarToBuyAndSaveInCarToService(request.getCar()));
//        Customer customer = customerService.findCustomer(request.getCustomer().getEmail());
//
//        CarServiceRequest carServiceRequest = buildCarService(request, car, customer);
//        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
//        existingCarServiceRequests.add(carServiceRequest);
//        customerService.saveServiceRequest(customer.withCarServiceRequests(existingCarServiceRequests));
//    }
//
//    private CarServiceRequest buildCarService(
//            CarServiceRequest request,
//            CarToService car,
//            Customer customer
//    ) {
//        OffsetDateTime when = OffsetDateTime.now();
//        return CarServiceRequest.builder()
//                .carServiceRequestNumber(generateCarServiceRequestNumber(when))
//                .receivedDateTime(when)
//                .customerComment(request.getCustomerComment())
//                .customer(customer)
//                .car(car)
//                .build();
//    }
//
//    private String generateCarServiceRequestNumber(OffsetDateTime when) {
//        return "%s.%s.%s.-%s.%s.%s.%s".formatted(
//                when.getYear(),
//                when.getMonth().ordinal(),
//                when.getDayOfMonth(),
//                when.getHour(),
//                when.getMinute(),
//                when.getSecond(),
//                randomInt(10, 100)
//        );
//    }
//
//    private CarToService findInCarToBuyAndSaveInCarToService(CarToService car) {
//        CarToBuy carToBuy = carService.findCarToBuy(car.getVin());
//        return carService.saveCarToService(carToBuy);
//    }
//
//    private void serviceRequestsForNewCar(CarServiceRequest request) {
//        CarToService car = carService.saveCarToService(request.getCar());
//        Customer customer = customerService.saveCustomer(request.getCustomer());
//
//        CarServiceRequest carServiceRequest = buildCarService(request, car, customer);
//        Set<CarServiceRequest> existingCarServiceRequests = customer.getCarServiceRequests();
//        existingCarServiceRequests.add(carServiceRequest);
//        customerService.saveServiceRequest(customer.withCarServiceRequests(existingCarServiceRequests));
//    }
//
//    @SuppressWarnings("SameParameterValue")
//    private int randomInt(int min, int max) {
//        return new Random().nextInt(max - min) + min;
//    }
//
//    @Transactional
//    public CarServiceRequest findAnyActiveRequests(String carVin) {
//        Set<CarServiceRequest> serviceRequests = carServiceRequestDAO.findActiveServiceRequestsByCarVin(carVin);
//        if (serviceRequests.size() != 1) {
//            throw new RuntimeException(
//                    "There should be only one active service request at a time, car vin: [%s]".formatted(carVin));
//        }
//        return serviceRequests.stream()
//                .findAny()
//                .orElseThrow(() -> new RuntimeException(
//                        "Could not find any service requests, car vin: [%s]".formatted(carVin)));
//    }
}
