package com.showcase.securitydemo.rest;


import com.showcase.securitydemo.domain.base.Menu;
import com.showcase.securitydemo.rest.dto.menu.MenuCreateDTO;
import com.showcase.securitydemo.rest.dto.menu.MenuDTO;
import com.showcase.securitydemo.rest.dto.menu.MenuUpdateDTO;
import com.showcase.securitydemo.rest.mapper.MenuMapper;
import com.showcase.securitydemo.service.MenuService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by youpengfei on 2017/2/24.
 * 角色相关接口
 */
@RestController
@RequestMapping("/api/menu")
public class MenuResource {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuMapper menuMapper;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<MenuDTO> get(@PathVariable Long id) {
        final Menu menu = menuService.getMenuById(id);

        final MenuDTO menuDTO = menuMapper.domainToDto(menu);
        if (menuDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(menuDTO);
        }
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Page<MenuDTO>> get(String name, Integer menuType, Pageable pageable) {
        final Page<Menu> menus = menuService.findMenuPage(name, menuType, pageable);
        final Page<MenuDTO> menuDTOS = menus.map(menuMapper::domainToDto);
        return ResponseEntity.ok(menuDTOS);
    }


    /**
     * 获取菜单列表，树状结构
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @Secured("AUTH_P")
    public ResponseEntity<List<MenuDTO>> getTreeStructMenus() {
        return ResponseEntity.ok(menuService.findMenuTree());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> update(@RequestBody @Valid MenuUpdateDTO menuUpdateDTO) {
        Menu menu = menuMapper.updateDtoToDomain(menuUpdateDTO);
        return ResponseEntity.ok(menuService.updateMenu(menu));
    }


    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> create(@RequestBody @Valid MenuCreateDTO menuCreateDTO) {
        Menu menu = menuMapper.createDtoToDomain(menuCreateDTO);
        return ResponseEntity.ok(menuService.addMenu(menu));
    }
}
