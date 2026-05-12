package in.ashokit.service;

import in.ashokit.dto.ResetPwdDto;
import in.ashokit.dto.UserDto;

public interface UserService {

    public UserDto saveUser(UserDto userDto);

    public UserDto login(String email, String pwd);

    public UserDto resetPwd(ResetPwdDto resetPwdDto);

    public UserDto getUserByEmail(String email);

}
