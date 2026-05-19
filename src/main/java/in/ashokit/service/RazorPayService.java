package in.ashokit.service;

public interface RazorPayService {

    public String createRazorpayOrder(Double amount) throws Exception;

    public String refundPayment(String paymentId, Double amount) throws Exception ;
}
