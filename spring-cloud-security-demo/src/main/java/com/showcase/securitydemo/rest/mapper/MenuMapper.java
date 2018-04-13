package com.showcase.securitydemo.rest.mapper;

import com.showcase.securitydemo.domain.base.Menu;
import com.showcase.securitydemo.rest.dto.menu.MenuCreateDTO;
import com.showcase.securitydemo.rest.dto.menu.MenuDTO;
import com.showcase.securitydemo.rest.dto.menu.MenuUpdateDTO;
import org.mapstruct.Mapper;

/**
 * Created by youpengfei on 2017/2/24.
 *
 */
@Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuDTO domainToDto(Menu menu);

    Menu updateDtoToDomain(MenuUpdateDTO menuUpdateDTO);

    Menu createDtoToDomain(MenuCreateDTO menuCreateDTO);
}
