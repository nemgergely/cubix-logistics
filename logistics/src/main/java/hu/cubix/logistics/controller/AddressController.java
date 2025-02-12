package hu.cubix.logistics.controller;

import hu.cubix.logistics.dto.AddressDto;
import hu.cubix.logistics.dto.AddressFilteringDto;
import hu.cubix.logistics.entities.Address;
import hu.cubix.logistics.exception.RecordNotFoundException;
import hu.cubix.logistics.mapper.IAddressMapper;
import hu.cubix.logistics.service.AddressService;
import hu.cubix.logistics.validation.CreateAddressValidation;
import hu.cubix.logistics.validation.UpdateAddressValidation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;
    private final IAddressMapper addressMapper;

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(
        @RequestBody @Validated(CreateAddressValidation.class) AddressDto addressDto) {

        Address createdAddress = addressService.createAddress(addressMapper.dtoToAddress(addressDto));
        return new ResponseEntity<>(addressMapper.addressToDto(createdAddress), HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<AddressDto>> getAddressesBySearchCriteria(
        @RequestBody AddressFilteringDto addressFilteringDto,
        @PageableDefault(
            page = 0,
            size = Integer.MAX_VALUE,
            sort = "id",
            direction = Sort.Direction.ASC
        ) Pageable pageable) {

        Address addressCriteria = addressMapper.filteringDtoToAddress(addressFilteringDto);
        List<Address> filteredAddresses = addressService.getAddressesBySearchCriteria(addressCriteria, pageable);
        long totalAddressCount = addressService.getTotalAddressCountBySearchCriteria(addressCriteria);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Total-Count", String.valueOf(totalAddressCount));
        List<AddressDto> responseBody = addressMapper.addressesToDtos(filteredAddresses);

        return ResponseEntity
            .ok()
            .headers(responseHeaders)
            .body(responseBody);
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addressMapper.addressesToDtos(addresses), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        Address address = addressService.getAddressById(id);
        return new ResponseEntity<>(addressMapper.addressToDto(address), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(
        @PathVariable Long id, @RequestBody @Validated(UpdateAddressValidation.class) AddressDto addressDto) {

        Address updatedAddress = addressService.updateAddressById(id, addressMapper.dtoToAddress(addressDto));
        return new ResponseEntity<>(addressMapper.addressToDto(updatedAddress), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Long id) {
        addressService.deleteAddressById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
