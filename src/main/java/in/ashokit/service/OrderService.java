package in.ashokit.service;

import in.ashokit.dto.OrderDto;
import in.ashokit.dto.PurchaseOrderRequestDto;
import in.ashokit.dto.PurchaseOrderResponseDto;

import java.util.List;

public interface OrderService {

    public PurchaseOrderResponseDto createOrder(PurchaseOrderRequestDto orderReqDto);

    public OrderDto updateOrder(Integer orderId, OrderDto orderDto);

    public OrderDto cancelOrder(Integer orderId);

    public OrderDto trackOrder(Integer orderId);

    public List<OrderDto> getCustomerOrders(String email);
}
