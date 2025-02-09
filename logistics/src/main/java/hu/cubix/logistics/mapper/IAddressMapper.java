package hu.cubix.logistics.mapper;

import hu.cubix.logistics.dto.AddressDto;
import hu.cubix.logistics.dto.AddressFilteringDto;
import hu.cubix.logistics.entities.Address;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IAddressMapper {

    AddressDto addressToDto(Address address);
    List<AddressDto> addressesToDtos(List<Address> addresses);
    Address dtoToAddress(AddressDto addressDto);
    List<Address> dtosToAddresses(List<AddressDto> addressDtos);

    Address filteringDtoToAddress(AddressFilteringDto addressFilteringDto);
}
