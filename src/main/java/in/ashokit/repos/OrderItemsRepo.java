package in.ashokit.repos;

import in.ashokit.entities.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepo extends JpaRepository<OrderItemsEntity, Integer> {
}
