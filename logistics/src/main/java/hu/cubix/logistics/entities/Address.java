package hu.cubix.logistics.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iso_code", columnDefinition = "varchar(2)")
    private String isoCode;

    private String country;

    private String city;

    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "house_number")
    private Integer houseNumber;

    private Double latitude;

    private Double longitude;

    @OneToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;
}
