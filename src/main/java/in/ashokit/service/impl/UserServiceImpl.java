package in.ashokit.service.impl;

import in.ashokit.constants.AppConstants;
import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.UserDto;
import in.ashokit.entity.RoleEntity;
import in.ashokit.entity.UserEntity;
import in.ashokit.mapper.UserMapper;
import in.ashokit.repo.RoleRepository;
import in.ashokit.repo.UserRepository;
import in.ashokit.service.EmailService;
import in.ashokit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public UserDto saveUser(UserDto userDto) {

        String randomPwd = generateRandomPwd(5);
        userDto.setPwd(randomwPwd);
        userDto.setPwdUpdated(AppConstants.NO);
        UserEntity userEntity = UserMapper.dtoToEntity(userDto);

        Set<RoleEntity> roleSet = new HashSet<>();
        if (userDto.getRoleName() != null) {
            RoleEntity roleEntity = roleRepository.findByName(userDto.getRoleName());
            if (roleEntity != null) {
                roleSet.add(roleEntity);
            } else {
                throw new RuntimeException("Role Not Found : " + userDto.getRoleName());
            }
        }
        userEntity.setRoles(roleSet); // association roles to user
        UserEntity savedUser = userRepository.save(userEntity);
        if (savedUser != null) {
            String subject = "Your Account Created - MyMart";
            String body = "Your Temporary Password is : " + randowmPwd;
            emailService.sendEmail(userDto.getEmail(), subject, body);
        }
        return UserMapper.entityToDto(savedUser);
    }


    public UserDto login(String email, String pwd) {
        UserEntity userEntity = userRepository.findByEmailAndPwd(email, pwd);
        if (userEntity != null) {
            RoleEntity role = userEntity.getRoles().iterator().next();
            UserDto userDto = UserMapper.entityToDto(userEntity);
            userDto.setRoleName(role.getName());
            return userDto;
        }
        return null;
    }

    public UserDto getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            UserDto userDto = UserMapper.entityToDto(userEntity);
            return userDto;
        }
        return null;
    }

    public UserDto resetPwd(ResetPwdDto resetPwdDto){
        if (resetPwdDto == null || resetPwdDto.getEmail() == null) {
            return null;
        }
        UserEntity userEntity = userRepository.findByEmail(resetPwdDto.getEmail());
        if(userEntity!=null){
            userEntity.setPwd(resetPwdDto.getNewPwd());
            userEntity.setPwdUpdated(AppConstants.YES);
            UserEntity updatedUser = userRepository.save(userEntity);
            return UserMapper.entityToDto(updatedUser);
        }
        return null;
    }


    private String generateRandomPwd(int pwdLength) {
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
        StringBuilder buffer = new StringBuilder(pwdLength);
        for (int i = 0; i < pwdLength; i++) {
            int randomIndex = random.nextInt(chars.length());
            char charAt = chars.charAt(randomIndex);
            buffer.append(charAt);
        }
        return buffer.toString();
    }

}
