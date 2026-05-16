package in.ashokit.repo;

import in.ashokit.entity.ShippingAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<ShippingAddressEntity, Integer> {

    public List<ShippingAddressEntity> findByUserUserId(Integer userId);
}
