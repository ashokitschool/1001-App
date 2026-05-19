package in.ashokit.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderRequestDto {

    public UserDto user;

    public ShippingAddressDto shippingAddress;

    public OrderDto order;

    public List<OrderItemsDto> orderItems;
}
