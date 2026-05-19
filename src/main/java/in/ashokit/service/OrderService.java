package in.ashokit.service;

import in.ashokit.dto.OrderDto;
import in.ashokit.dto.PurchaseOrderRequestDto;
import in.ashokit.dto.PurchaseOrderResponseDto;

import java.util.List;

public interface OrderService {

    public PurchaseOrderResponseDto createOrder(PurchaseOrderRequestDto orderReqDto) throws Exception;

    public OrderDto updateOrder(Integer orderId, OrderDto orderDto);

    public OrderDto cancelOrder(Integer orderId) throws Exception;

    public OrderDto trackOrder(Integer orderId);

    public List<OrderDto> getCustomerOrders(String email);
}
