package com.showcase.securitydemo.rest.mapper;

import com.showcase.securitydemo.domain.base.Authority;
import com.showcase.securitydemo.rest.dto.authority.AuthorityCreateDTO;
import com.showcase.securitydemo.rest.dto.authority.AuthorityDTO;
import com.showcase.securitydemo.rest.dto.authority.AuthorityUpdateDTO;
import java.util.List;
import org.mapstruct.Mapper;

/**
 * Created by youpengfei on 2017/2/24.
 * 权限对象mapper
 */
@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    AuthorityDTO domainToDto(Authority authority);

    Authority dtoToDomain(AuthorityDTO authorityDTO);

    Authority updateDtoToDomain(AuthorityUpdateDTO authorityUpdateDTO);

    Authority createDtoToDomain(AuthorityCreateDTO authorityCreateDTO);

    List<AuthorityDTO> domainsToDtos(List<Authority> roles);
}
