package hu.cubix.logistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressFilteringDto {

    private String country;
    private String city;
    private String street;
    private String zipCode;
}
