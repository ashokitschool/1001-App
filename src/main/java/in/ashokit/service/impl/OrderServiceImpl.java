package in.ashokit.service.impl;

import in.ashokit.dto.*;
import in.ashokit.entity.OrderEntity;
import in.ashokit.entity.OrderItemsEntity;
import in.ashokit.entity.ShippingAddressEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.mapper.AddressMapper;
import in.ashokit.mapper.OrderItemsMapper;
import in.ashokit.mapper.OrderMapper;
import in.ashokit.repo.AddressRepository;
import in.ashokit.repo.OrderItemsRepository;
import in.ashokit.repo.OrderRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.service.OrderService;
import in.ashokit.service.ProductService;
import in.ashokit.service.RazorPayService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepo;

    private OrderItemsRepository itemsRepo;

    private UserRepository userRepo;

    private AddressRepository addrRepo;

    private RazorPayService razorpayService;

    private ProductService productService;


    @Override
    public PurchaseOrderResponseDto createOrder(PurchaseOrderRequestDto orderReqDto) throws Exception{

        UserDto userDto = orderReqDto.getUser();
        UserEntity userEntity = userRepo.findByEmail(userDto.getEmail());

        ShippingAddressEntity addressEntity=null;

        ShippingAddressDto addrDto = orderReqDto.getShippingAddress();
        if(addrDto.getAddrId()==null){
            addressEntity = AddressMapper.dtoToEntity(addrDto);
            addressEntity.setUser(userEntity);
            addrRepo.save(addressEntity);
        }else{
            addressEntity = addrRepo.findById(addrDto.getAddrId()).orElse(null);
        }

        OrderDto orderDto = orderReqDto.getOrder();
        String razorpayOrderId = razorpayService.createRazorpayOrder(orderDto.getTotalPrice());

        OrderEntity orderEntity = OrderMapper.toEntity(orderDto);

        orderEntity.setOrderTrackingNum(generateOrderTrackingNumber());
        orderEntity.setOrderStatus("CREATED");
        orderEntity.setPaymentStatus("PENDING");
        orderEntity.setRazorpayOrderId(razorpayOrderId);

        orderEntity.setUser(userEntity);
        orderEntity.setShippingAddress(addressEntity);

        OrderEntity savedOrder = orderRepo.save(orderEntity);

        List<OrderItemsDto> orderItems = orderReqDto.getOrderItems();

        for(OrderItemsDto itemDto : orderItems){
            Integer productId = itemDto.getProductId();
            ProductDto productById = productService.getProductById(productId);
            OrderItemsEntity itemEntity = OrderItemsMapper.dtoToEntity(itemDto);
            itemEntity.setImageUrl(productById.getImageUrl());
            itemEntity.setOrder(savedOrder);
            itemsRepo.save(itemEntity);
        }

        //TO DO : Send email

        // final Response

        PurchaseOrderResponseDto responseDto = new PurchaseOrderResponseDto();
        responseDto.setOrderId(savedOrder.getOrderId());
        responseDto.setRazorpayOrderId(razorpayOrderId);
        responseDto.setOrderStatus(savedOrder.getOrderStatus());
        responseDto.setPaymentStatus(savedOrder.getPaymentStatus());
        responseDto.setOrderTrackingNumber(savedOrder.getOrderTrackingNum());

        return responseDto;
    }

    @Override
    public OrderDto updateOrder(Integer orderId, OrderDto orderDto) {
        Optional<OrderEntity> byId = orderRepo.findById(orderId);
        if(byId.isPresent()){
            OrderEntity orderEntity = byId.get();
            orderEntity.setOrderStatus(orderDto.getOrderStatus());
            orderEntity.setPaymentStatus(orderDto.getPaymentStatus());
            orderEntity.setRazorpayPaymentId(orderDto.getRazorpayPaymentId());
            OrderEntity updatedOrder = orderRepo.save(orderEntity);
            //TO DO : Send email
            return OrderMapper.toDto(updatedOrder);
        }
        return null;
    }

    @Override
    public OrderDto cancelOrder(Integer orderId)  throws Exception{
        Optional<OrderEntity> byId = orderRepo.findById(orderId);
        if(byId.isPresent()){
            OrderEntity orderEntity = byId.get();
            razorpayService.refundPayment(orderEntity.getRazorpayPaymentId(), orderEntity.getTotalPrice());
            orderEntity.setOrderStatus("CANCELLED");
            orderEntity.setPaymentStatus("REFUND-IN-PROGRESS");
            OrderEntity cancelledOrder = orderRepo.save(orderEntity);
            //TO DO : Send email
            return OrderMapper.toDto(cancelledOrder);
        }
        return null;
    }

    @Override
    public OrderDto trackOrder(Integer orderId) {
        Optional<OrderEntity> byId = orderRepo.findById(orderId);
        if(byId.isPresent()){
            OrderEntity orderEntity = byId.get();
            return OrderMapper.toDto(orderEntity);
        }
        return null;
    }

    @Override
    public List<OrderDto> getCustomerOrders(String email) {
        return orderRepo.findByUserEmail(email)
                        .stream()
                        .map(OrderMapper::toDto)
                        .collect(Collectors.toList());
    }

    private String generateOrderTrackingNumber(){
        return UUID.randomUUID().toString();
    }
}
