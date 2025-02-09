package hu.cubix.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;
    private String isoCode;
    private String city;
    private String street;
    private Integer zipCode;
    private Integer houseNumber;
    private Double latitude;
    private Double longitude;
}
