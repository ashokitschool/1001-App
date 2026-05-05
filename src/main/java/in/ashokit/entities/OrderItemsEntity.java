package in.ashokit.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ORDER_ITEMS")
@Getter
@Setter
public class OrderItemsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Integer orderItemId;

	@Column(name = "image_url", length = 1000)
	private String imageUrl;

	@Column(name = "unit_price")
	private Double unitPrice;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "product_id")
	private Integer productId;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderEntity order;
}
