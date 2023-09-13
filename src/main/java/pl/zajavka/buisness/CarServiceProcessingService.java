package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.buisness.DAO.ServiceRequestProcessingDAO;
import pl.zajavka.domain.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class CarServiceProcessingService {

    private final CarService carService;
    private final MechanicService mechanicService;
    private final PartCatalogService partCatalogService;
    private final ServiceCatalogService serviceCatalogService;
    private final CarServiceRequestService carServiceRequestService;
    private final ServiceRequestProcessingDAO serviceRequestProcessingDAO;


    @Transactional
    public void process(CarServiceProcessingRequest request) {
        Mechanic mechanic = mechanicService.findMechanic(request.getMechanicPesel());
        carService.findCarToService(request.getCarVin()).orElseThrow();
        CarServiceRequest serviceRequest = carServiceRequestService.findAnyActiveRequests(request.getCarVin());

        Service service = serviceCatalogService.findService(request.getServiceCode());

        ServiceMechanic serviceMechanic = buildServiceMechanic(request, mechanic, serviceRequest, service);

        if (request.getDone()) {
            serviceRequest = serviceRequest.withCompletedDateTime(OffsetDateTime.now());
        }

        if (request.partNotIncluded()) {
            serviceRequestProcessingDAO.process(serviceRequest, serviceMechanic);
        } else {
            Part part = partCatalogService.findPart(request.getPartSerialNumber());
            ServicePart servicePart = buildServicePart(request, serviceRequest, part);
            serviceRequestProcessingDAO.process(serviceRequest, serviceMechanic, servicePart);
        }
    }

    private ServiceMechanic buildServiceMechanic(
            CarServiceProcessingRequest request,
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
            CarServiceProcessingRequest request,
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
