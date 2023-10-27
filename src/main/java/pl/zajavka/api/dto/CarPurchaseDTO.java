package pl.zajavka.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPurchaseDTO {

    @Email
    private String existingCustomerEmail;

    private String customerName;
    private String customerSurname;
    @Size
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String customerPhone;
    @Email
    private String customerEmail;
    private String customerAddressCountry;
    private String customerAddressCity;
    private String customerAddressPostalCode;
    private String customerAddressStreet;

    private String carVin;
    private String salesmanPesel;

    public static CarPurchaseDTO buildDefaultData() {
        return CarPurchaseDTO.builder()
                .customerName("Aflerd")
                .customerSurname("Samochodowy")
                .customerPhone("+48 123 123 123")
                .customerEmail("alf.samoch@gmail.com")
                .customerAddressCountry("Polska")
                .customerAddressCity("Wrocław")
                .customerAddressPostalCode("50-001")
                .customerAddressStreet("Bokserska 15")
                .build();
    }

    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        Optional.ofNullable(customerName).ifPresent(val -> result.put("customerName", val));
        Optional.ofNullable(customerSurname).ifPresent(val -> result.put("customerSurname", val));
        Optional.ofNullable(customerPhone).ifPresent(val -> result.put("customerPhone", val));
        Optional.ofNullable(existingCustomerEmail).ifPresent(val -> result.put("existingCustomerEmail", val));
        Optional.ofNullable(customerAddressCountry).ifPresent(val -> result.put("customerAddressCountry", val));
        Optional.ofNullable(customerAddressCity).ifPresent(val -> result.put("customerAddressCity", val));
        Optional.ofNullable(customerAddressPostalCode).ifPresent(val -> result.put("customerAddressPostalCode", val));
        Optional.ofNullable(customerAddressStreet).ifPresent(val -> result.put("customerAddressStreet", val));
        Optional.ofNullable(carVin).ifPresent(val -> result.put("carVin", val));
        Optional.ofNullable(salesmanPesel).ifPresent(val -> result.put("salesmanPesel", val));
        return result;
    }

}
