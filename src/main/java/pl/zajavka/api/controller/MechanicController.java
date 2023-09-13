package pl.zajavka.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.zajavka.api.dto.*;
import pl.zajavka.api.dto.mapper.CarServiceRequestMapper;
import pl.zajavka.api.dto.mapper.MechanicMapper;
import pl.zajavka.api.dto.mapper.PartMapper;
import pl.zajavka.api.dto.mapper.ServiceMapper;
import pl.zajavka.buisness.CarServiceProcessingService;
import pl.zajavka.buisness.CarServiceRequestService;
import pl.zajavka.buisness.PartCatalogService;
import pl.zajavka.buisness.ServiceCatalogService;
import pl.zajavka.domain.CarServiceProcessingRequest;
import pl.zajavka.domain.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@AllArgsConstructor
public class MechanicController {

    private static final String MECHANIC = "/mechanic";
    public static final String MECHANIC_WORK_UNIT = "/mechanic/workUnit";

    private final CarServiceRequestService carServiceRequestService;
    private final ServiceCatalogService serviceCatalogService;
    private final PartCatalogService partCatalogService;
    private final CarServiceProcessingService carServiceProcessingService;

    private final CarServiceRequestMapper carServiceRequestMapper;
    private final MechanicMapper mechanicMapper;
    private final ServiceMapper serviceMapper;
    private final PartMapper partMapper;

    @GetMapping(value = MECHANIC)
    public ModelAndView mechanicCheckPage() {
        Map<String, ?> data =  prepareNecessaryData();

        return new ModelAndView("mechanic_service", data);
    }

    private Map<String, ?> prepareNecessaryData() {
        var availableServiceRequests = getAvailableServiceRequests();
        var availableCarVins = getAvailableServiceRequests().stream().map(CarServiceRequestDTO::getCarVin).toList();
        var availableMechanics = getAvailableMechanics();
        var availableMechanicsPesels = availableMechanics.stream().map(MechanicDTO::getPesel).toList();
        var parts = findParts();
        var services = findServices();
        var partSerialNumbers = preparePartSerialNumbers(parts);
        var serviceCodes = services.stream().map(ServiceDTO::getServiceCode).toList();

        return Map.of(
                "availableServiceRequestsDTOs", availableServiceRequests,
                "availableCarVins", availableCarVins,
                "availableMechanicsDTOs", availableMechanics,
                "availableMechanicPesels", availableMechanicsPesels,
                "partsDTOs", parts,
                "servicesDTOs", services,
                "partSerialNumbers", partSerialNumbers,
                "serviceCodes", serviceCodes,
                "carServiceProcessDTO", CarServiceMechanicProcessingUnitDTO.buildDefault()
        );
    }

    @PostMapping(value = MECHANIC_WORK_UNIT)
    public String mechanicWorkUnit(
            @ModelAttribute("carServiceRequestProcessDTO") CarServiceMechanicProcessingUnitDTO dto,
            BindingResult bindingResult,
            ModelMap modelMap
    ) {
        if (bindingResult.hasErrors()) {
            return "error";
        }

        CarServiceProcessingRequest request = carServiceRequestMapper.map(dto);
        carServiceProcessingService.process(request);
        if (dto.getDone()) {
            return "mechanic_service_done";
        } else {
            modelMap.addAllAttributes(prepareNecessaryData());
            return "redirect:/mechanic";
        }
    }

    private List<CarServiceRequestDTO> getAvailableServiceRequests() {
        return carServiceRequestService.availableServiceRequests().stream()
                .map(carServiceRequestMapper::map)
                .toList();
    }

    private List<MechanicDTO> getAvailableMechanics() {
        return carServiceRequestService.availableMechanics().stream()
                .map(mechanicMapper::map)
                .toList();
    }

    private List<PartDTO> findParts() {
        return partCatalogService.findAll().stream()
                .map(partMapper::map)
                .toList();
    }

    private List<ServiceDTO> findServices() {
        return serviceCatalogService.findAll().stream()
                .map(serviceMapper::map)
                .toList();
    }

    private List<String> preparePartSerialNumbers(List<PartDTO> parts) {
        List<String> partSerialNumbers = new ArrayList<>(parts.stream()
                .map(PartDTO::getSerialNumber)
                .toList());
        partSerialNumbers.add(Part.NONE);
        return partSerialNumbers;
    }
}
