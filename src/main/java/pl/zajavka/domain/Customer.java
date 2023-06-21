package pl.zajavka.domain;


import lombok.*;

import java.util.Set;


@With
@Value
@Builder
@ToString(of = {"customerId", "name", "surname", "phone", "email"})
@EqualsAndHashCode(of = "customerId")
public class Customer {
    Integer customerId;
    String name;
    String surname;
    String phone;
    String email;
    Address address;
    Set<Invoice> invoices;
    Set<CarServiceRequest> carServiceRequests;
}

