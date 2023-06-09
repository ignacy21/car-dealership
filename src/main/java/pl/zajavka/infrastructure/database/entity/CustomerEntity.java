package pl.zajavka.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@ToString(of = {"customerId", "name", "surname", "phone", "email"})
@EqualsAndHashCode(of = "customerId")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", unique = true)
    private String email;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<InvoiceEntity> invoices;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private Set<CarServiceRequestEntity> carServiceRequests;
}

