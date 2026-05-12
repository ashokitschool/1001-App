package in.ashokit.dto;

import lombok.Data;

import java.util.List;

@Data
public class PurchaseOrderRequestDto {

    public UserDto userDto;

    public ShippingAddressDto addrDto;

    public OrderDto orderDto;

    public List<OrderItemsDto> itemsList;
}
