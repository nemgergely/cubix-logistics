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

    private String city;

    private String street;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "house_number")
    private Integer houseNumber;

    @Column(columnDefinition = "numeric", precision = 4)
    private Double latitude;

    @Column(columnDefinition = "numeric", precision = 4)
    private Double longitude;
}
