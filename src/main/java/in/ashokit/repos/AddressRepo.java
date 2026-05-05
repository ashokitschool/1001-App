package in.ashokit.repos;

import in.ashokit.entities.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<ShippingAddressEntity, Integer> {
}
