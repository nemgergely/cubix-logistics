package hu.cubix.logistics.dto;

import hu.cubix.logistics.validation.CreateAddressValidation;
import hu.cubix.logistics.validation.UpdateAddressValidation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @Null(groups = {CreateAddressValidation.class}, message = "ID must be null in case of creation")
    @NotNull(groups = {UpdateAddressValidation.class}, message = "ID must be filled in case of update")
    private Long id;

    @NotEmpty(
        groups = {CreateAddressValidation.class, UpdateAddressValidation.class},
        message = "The country ISO code cannot be null or empty")
    @Size(
        groups = {CreateAddressValidation.class, UpdateAddressValidation.class},
        min = 2, max = 2, message = "The ISO code must be a 2-character length text")
    private String isoCode;

    @NotEmpty(
        groups = {CreateAddressValidation.class, UpdateAddressValidation.class},
        message = "The city cannot be null or empty")
    private String city;

    @NotEmpty(
        groups = {CreateAddressValidation.class, UpdateAddressValidation.class},
        message = "The street cannot be null or empty")
    private String street;

    @NotNull(
        groups = {CreateAddressValidation.class, UpdateAddressValidation.class},
        message = "The ZIP code cannot be null")
    private Integer zipCode;

    @NotNull(
        groups = {CreateAddressValidation.class, UpdateAddressValidation.class},
        message = "The house number cannot be null")
    private Integer houseNumber;

    private Double latitude;
    private Double longitude;
}
