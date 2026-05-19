package in.ashokit.service.impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Refund;
import in.ashokit.service.RazorPayService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayServiceImpl implements RazorPayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private RazorpayClient razorpayClient;

    @Override
    public String createRazorpayOrder(Double amount) throws Exception {

        this.razorpayClient = new RazorpayClient(keyId, keySecret);

        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", amount * 100);
        orderReq.put("currency", "INR");
        orderReq.put("payment_capture", 1);

        Order order = razorpayClient.Orders.create(orderReq);

        return order.get("id");
    }

    @Override
    public String refundPayment(String paymentId, Double amount) throws Exception {

        this.razorpayClient = new RazorpayClient(keyId, keySecret);

        JSONObject refundRequest = new JSONObject();
        refundRequest.put("payment_id", paymentId);
        refundRequest.put("amount", amount);

        Refund refund = razorpayClient.Payments.refund(refundRequest);

        return refund.get("id)");
    }
}
