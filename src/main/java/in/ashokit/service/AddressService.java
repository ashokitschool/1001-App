package in.ashokit.service;

import in.ashokit.dto.ShippingAddressDto;

import java.util.List;

public interface AddressService {

    public ShippingAddressDto saveAddress(Integer userId, ShippingAddressDto addressDto);

    public ShippingAddressDto updateAddress(Integer addrId, ShippingAddressDto addressDto);

    public List<ShippingAddressDto> getUserAddresses(Integer userId);
}
