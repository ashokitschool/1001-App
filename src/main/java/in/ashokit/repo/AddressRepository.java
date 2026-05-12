package in.ashokit.repo;

import in.ashokit.entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<ShippingAddressEntity, Integer> {
}
