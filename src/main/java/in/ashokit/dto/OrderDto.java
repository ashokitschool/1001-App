package in.ashokit.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class OrderDto {

    private Integer orderId;
    private String orderTrackingNum;
    private Integer totalQuantity;
    private Double totalPrice;
    private String orderStatus;
    private LocalDateTime deliveryDate;
    private String paymentStatus;
    private String razorpayOrderId;
    private String razorpayPaymentId;
}
