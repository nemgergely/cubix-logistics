package hu.cubix.logistics.service;

import hu.cubix.logistics.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    public Address createAddress(Address address) {
        return null;
    }

    public Page<Address> getAddressesBySearchCriteria(Address address, Pageable pageable) {
        return null;
    }

    public List<Address> getAllAddresses() {
        return null;
    }

    public Address updateAddressById(Integer id, Address address) {
        return null;
    }

    public void deleteAddressById(Integer id) {

    }

    public Address getAddressById(Integer id) {
        return null;
    }
}
