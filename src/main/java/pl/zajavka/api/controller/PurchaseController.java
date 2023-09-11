package pl.zajavka.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.zajavka.api.dto.CarPurchaseDTO;
import pl.zajavka.api.dto.CarToBuyDTO;
import pl.zajavka.api.dto.mapper.CarMapper;
import pl.zajavka.api.dto.mapper.CarPurchaseMapper;
import pl.zajavka.buisness.CarPurchaseService;
import pl.zajavka.domain.Salesman;

import java.util.Map;

@Controller
@AllArgsConstructor
public class PurchaseController {

    private static final String PURCHASE = "/purchase";

    private final CarPurchaseService carPurchaseService;
    private final CarPurchaseMapper carPurchaseMapper;
    private final CarMapper carMapper;

    @GetMapping(value = PURCHASE)
    public ModelAndView carPurchasePage() {
        Map<String, ?> model = prepareCarPurchaseData();

        return new ModelAndView("car_purchase", model);
    }

    private Map<String, ?> prepareCarPurchaseData() {
        var availableCars = carPurchaseService.availableCars().stream()
                .map(carMapper::map)
                .toList();

        var availableCarVins = availableCars.stream()
                .map(CarToBuyDTO::getVin)
                .toList();

        var availableSalesmanPesels = carPurchaseService.availableSalesman().stream()
                .map(Salesman::getPesel)
                .toList();
        return Map.of(
          "availableCarDTOs", availableCars,
          "availableCarVins", availableCarVins,
          "availableSalesmanPesels", availableSalesmanPesels,
                "carPurchaseDTO", CarPurchaseDTO.buildDefaultData()
        );
    }
}
