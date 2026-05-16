package in.ashokit.rest;

import in.ashokit.dto.ApiResponse;
import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class AddressRestController {

    @Autowired
    private AddressService addrService;

    @PostMapping("/{userId}/shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> saveShippingAddress(@PathVariable Integer userId,
                                                                               @RequestBody ShippingAddressDto shippingAddressDto) {
        ShippingAddressDto savedAddress = addrService.saveAddress(userId, shippingAddressDto);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Shipping address saved successfully");
        response.setData(savedAddress);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{addrId}/shipping-address")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> updateAddress(@PathVariable Integer addrId,
                                                                               @RequestBody ShippingAddressDto shippingAddressDto) {
        ShippingAddressDto savedAddress = addrService.updateAddress(addrId, shippingAddressDto);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Shipping address updated successfully");
        response.setData(savedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/shipping-addresses/{userId}")
    public ResponseEntity<ApiResponse<List<ShippingAddressDto>>> getCustomerAddresses(@PathVariable Integer userId) {
        List<ShippingAddressDto> customerAddresses = addrService.getUserAddresses(userId);
        ApiResponse<List<ShippingAddressDto>> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Customer all addresses fetched successfully");
        response.setData(customerAddresses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/shipping-address/{addrId}")
    public ResponseEntity<ApiResponse<ShippingAddressDto>> deleteShippingAddress(@PathVariable Integer addrId) {
        ShippingAddressDto deletedAddress = addrService.deleteAddress(addrId);
        ApiResponse<ShippingAddressDto> response = new ApiResponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Shipping address deleted successfully");
        response.setData(deletedAddress);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
