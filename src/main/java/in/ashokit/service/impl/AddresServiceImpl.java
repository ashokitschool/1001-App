package in.ashokit.service.impl;

import in.ashokit.repo.AddressRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddresServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addrRepo;

    @Autowired
    private UserRepository userRepo;
}
