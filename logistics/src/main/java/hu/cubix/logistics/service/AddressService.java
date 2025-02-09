package hu.cubix.logistics.service;

import hu.cubix.logistics.entities.Address;
import hu.cubix.logistics.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static hu.cubix.logistics.specification.AddressSpecification.*;

@Service
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public Address createAddress(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAddressesBySearchCriteria(Address address, Pageable pageable) {
        Specification<Address> specifications = getSpecification(address);
        Page<Address> addressPage = addressRepository.findAll(specifications, pageable);
        return addressPage.getContent();
    }

    public long getTotalAddressCountBySearchCriteria(Address address) {
        Specification<Address> specifications = getSpecification(address);
        return addressRepository.count(specifications);
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow();
    }

    @Transactional
    public Address updateAddressById(Address address) {
        Address addressToUpdate = addressRepository.findById(address.getId()).orElseThrow();
        addressToUpdate.setIsoCode(address.getIsoCode());
        addressToUpdate.setCity(address.getCity());
        addressToUpdate.setStreet(address.getStreet());
        addressToUpdate.setZipCode(address.getZipCode());
        addressToUpdate.setHouseNumber(address.getHouseNumber());
        if (address.getLatitude() != null) {
            addressToUpdate.setLatitude(address.getLatitude());
        }
        if (address.getLongitude() != null) {
            addressToUpdate.setLongitude(address.getLongitude());
        }
        return addressToUpdate;
    }

    @Transactional
    public void deleteAddressById(Long id) {
        addressRepository.deleteById(id);
    }

    private Specification<Address> getSpecification(Address address) {
        Specification<Address> addressSpec = Specification.where(null);
        String isoCode = address.getIsoCode();
        String cityPrefix = address.getCity();
        String streetPrefix = address.getStreet();
        Integer zipCode = address.getZipCode();

        if (StringUtils.hasLength(isoCode)) {
            addressSpec = addressSpec.and(isoCodeMatches(isoCode));
        }
        if (StringUtils.hasLength(cityPrefix)) {
            addressSpec = addressSpec.and(cityStartsWith(cityPrefix));
        }
        if (StringUtils.hasLength(streetPrefix)) {
            addressSpec = addressSpec.and(streetStartsWith(streetPrefix));
        }
        if (zipCode != null) {
            addressSpec = addressSpec.and(zipCodeMatches(zipCode));
        }
        return addressSpec;
    }
}
