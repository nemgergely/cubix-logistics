package hu.cubix.logistics.controller;

import hu.cubix.logistics.dto.AddressDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController {

    @PostMapping
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
        return null;
    }

    @PostMapping("/search")
    public ResponseEntity<List<AddressDto>> getAddressesBySearchCriteria(
        @RequestBody AddressDto addressDto,
        @PageableDefault(
            page = 0,
            size = Integer.MAX_VALUE,
            sort = "id",
            direction = Sort.Direction.ASC
        ) Pageable pageable) {

        return null;
    }

    @GetMapping
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Integer id) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Integer id, @RequestBody AddressDto addressDto) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable Integer id) {
        return null;
    }
}
