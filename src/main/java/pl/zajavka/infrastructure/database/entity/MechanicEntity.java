package pl.zajavka.infrastructure.database.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "mechanicId")
@ToString(of = {"mechanicId", "name", "surname", "pesel"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mechanic")
public class MechanicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mechanic_id")
    private Integer mechanicId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "pesel", nullable = false)
    private String pesel;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mechanic")
    private Set<ServiceMechanicEntity> serviceMechanics;
}
