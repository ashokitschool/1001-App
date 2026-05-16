package in.ashokit.service.impl;

import in.ashokit.constants.AppConstants;
import in.ashokit.dto.ShippingAddressDto;
import in.ashokit.entity.ShippingAddressEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.mapper.AddressMapper;
import in.ashokit.repo.AddressRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddresServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addrRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public ShippingAddressDto saveAddress(Integer userId, ShippingAddressDto shippingAddressDto) {

        ShippingAddressEntity addressEntity = AddressMapper.dtoToEntity(shippingAddressDto);

        Optional<UserEntity> user = userRepo.findById(userId);
        if (user.isPresent()) {
            UserEntity userEntity = user.get();
            addressEntity.setUser(userEntity); // user association
            addressEntity.setActiveSw(AppConstants.YES);
            ShippingAddressEntity savedAddrEntity = addrRepo.save(addressEntity);
            return AddressMapper.entityToDto(savedAddrEntity);
        }

        return null;
    }

    @Override
    public ShippingAddressDto updateAddress(Integer addrId, ShippingAddressDto shippingAddressDto) {

        Optional<ShippingAddressEntity> byId = addrRepo.findById(addrId);

        if(byId.isPresent()){
            ShippingAddressEntity addressEntity = byId.get();
            addressEntity.setCity(shippingAddressDto.getCity());
            addressEntity.setState(shippingAddressDto.getState());
            addressEntity.setCountry(shippingAddressDto.getCountry());
            addressEntity.setAddrType(shippingAddressDto.getAddrType());
            ShippingAddressEntity updatedAddr = addrRepo.save(addressEntity); // UPSERT
            return AddressMapper.entityToDto(updatedAddr);
        }

        return null;
    }

    @Override
    public ShippingAddressDto deleteAddress(Integer shippingAddressId) {

        Optional<ShippingAddressEntity> address = addrRepo.findById(shippingAddressId);
        if (address.isPresent()) {
            ShippingAddressEntity addressEntity = address.get();
            addressEntity.setActiveSw(AppConstants.NO);
            ShippingAddressEntity deletedAddr = addrRepo.save(addressEntity);
            return AddressMapper.entityToDto(deletedAddr);
        }
        return null;
    }

    @Override
    public List<ShippingAddressDto> getUserAddresses(Integer userId) {

        List<ShippingAddressEntity> entities = addrRepo.findByUserUserId(userId);

        return entities.stream()
                .map(AddressMapper::entityToDto)
                .collect(Collectors.toList());
    }


}
