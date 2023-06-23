package pl.zajavka.buisness;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.zajavka.buisness.DAO.ServiceRequestProcessingDAO;
import pl.zajavka.buisness.management.FileDataPreparationService;
import pl.zajavka.domain.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

import static pl.zajavka.buisness.management.Keys.Constants.FINISHED;

@AllArgsConstructor
public class CarServiceProcessingService {

    private final FileDataPreparationService fileDataPreparationService;
    private final MechanicService mechanicService;
    private final CarService carService;
    private final ServiceCatalogService serviceCatalogService;
    private final PartCatalogService partCatalogService;
    private final CarServiceRequestService carServiceRequestService;
    private final ServiceRequestProcessingDAO serviceRequestProcessingDAO;

    @Transactional
    public void process() {
        List<CarServiceProcessingInputData> toProcess = fileDataPreparationService.prepareServiceRequestToProcess();
        toProcess.forEach(this::processRequest);
    }

    private void processRequest(CarServiceProcessingInputData request) {
        Mechanic mechanic = mechanicService.findMechanic(request.getMechanicPesel());
        carService.findCarToService(request.getCarVin()).orElseThrow();
        CarServiceRequest serviceRequest = carServiceRequestService.findAnyActiveRequests(request.getCarVin());

        Service service = serviceCatalogService.findService(request.getServiceCode());

        ServiceMechanic serviceMechanic = buildServiceMechanic(
                request, mechanic, serviceRequest, service);

        if (FINISHED.toString().equals(request.getDone())) {
            serviceRequest = serviceRequest.withCompletedDateTime(OffsetDateTime.now());
        }

        if (Objects.isNull(request.getPartSerialNumber()) || Objects.isNull(request.getPartQuantity())) {
            serviceRequestProcessingDAO.process(serviceRequest, serviceMechanic);
        } else {
            Part part = partCatalogService.findPart(request.getPartSerialNumber());
            ServicePart servicePart = buildServicePart(request, serviceRequest, part);
            serviceRequestProcessingDAO.process(serviceRequest, serviceMechanic, servicePart);
        }
    }

    private ServiceMechanic buildServiceMechanic(
            CarServiceProcessingInputData request,
            Mechanic mechanic,
            CarServiceRequest serviceRequest,
            Service service
    ) {
        return ServiceMechanic.builder()
                .hours(request.getHours())
                .comment(request.getComment())
                .carServiceRequest(serviceRequest)
                .mechanic(mechanic)
                .service(service)
                .build();
    }

    private ServicePart buildServicePart(
            CarServiceProcessingInputData request,
            CarServiceRequest serviceRequest,
            Part part
    ) {
        return ServicePart.builder()
                .quantity(request.getPartQuantity())
                .carServiceRequest(serviceRequest)
                .part(part)
                .build();

    }
}
