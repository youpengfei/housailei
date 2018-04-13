package com.showcase.securitydemo.rest.mapper;

import com.showcase.securitydemo.domain.base.User;
import com.showcase.securitydemo.rest.dto.user.UserCreateDTO;
import com.showcase.securitydemo.rest.dto.user.UserDTO;
import com.showcase.securitydemo.rest.dto.user.UserQueryDTO;
import com.showcase.securitydemo.rest.dto.user.UserUpdateDTO;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Created by youpengfei on 2017/2/22.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO domainToDto(User user);


    User dtoToDomain(UserDTO userDTO);
    User queryDtoToDomain(UserQueryDTO userQueryDTO);

    User updateDtoToDomain(UserUpdateDTO userUpdateDTO);

    User createDtoToDomain(UserCreateDTO userCreateDTO);

    List<UserDTO> domainsToDtos(List<User> users);
}
