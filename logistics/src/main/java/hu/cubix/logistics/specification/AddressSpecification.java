package hu.cubix.logistics.specification;

import hu.cubix.logistics.entities.Address;
import hu.cubix.logistics.entities.Address_;
import org.springframework.data.jpa.domain.Specification;

public class AddressSpecification {

    private AddressSpecification() {}

    public static Specification<Address> cityStartsWith(String cityPrefix) {
        return (root, cq, cb) ->
            cb.like(cb.lower(root.get(Address_.city)), cityPrefix.toLowerCase() + "%");
    }

    public static Specification<Address> streetStartsWith(String streetPrefix) {
        return (root, cq, cb) ->
            cb.like(cb.lower(root.get(Address_.street)), streetPrefix.toLowerCase() + "%");
    }

    public static Specification<Address> countryMatches(String country) {
        return (root, cq, cb) ->
            cb.equal(root.get(Address_.country), country);
    }

    public static Specification<Address> zipCodeMatches(String zipCode) {
        return (root, cq, cb) ->
            cb.equal(root.get(Address_.zipCode), zipCode);
    }
}
