package in.ashokit.service;

import in.ashokit.dto.OrderDto;

import java.util.List;

public interface NotificationService {

    // Order Confirmation after payment
    public OrderDto sendOrderConfirmation(Integer orderId);

    // Delivery Updates
    public List<OrderDto> sendDeliveryNotification(Integer orderId);

    // Order Cancelled
    public OrderDto cancelledOrderNotification(Integer orderId);

    // Offers
    public void sendOffersNotification();
}
