package com.showcase.securitydemo.rest.mapper;

import com.showcase.securitydemo.domain.base.Role;
import com.showcase.securitydemo.rest.dto.role.RoleCreateDTO;
import com.showcase.securitydemo.rest.dto.role.RoleDTO;
import com.showcase.securitydemo.rest.dto.role.RoleUpdateDTO;
import org.mapstruct.Mapper;

/**
 * Created by youpengfei on 2017/2/24.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDTO domainToDto(Role role);

    Role domainToDto(RoleDTO roleDTO);

    Role updateDtoToDomain(RoleUpdateDTO roleUpdateDTO);

    Role createDtoToDomain(RoleCreateDTO roleCreateDTO);
}
