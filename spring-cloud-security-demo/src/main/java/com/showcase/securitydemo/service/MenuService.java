package com.showcase.securitydemo.service;

import com.google.common.collect.Lists;
import com.showcase.securitydemo.dao.AuthorityDao;
import com.showcase.securitydemo.dao.MenuDao;
import com.showcase.securitydemo.domain.base.Authority;
import com.showcase.securitydemo.domain.base.Menu;
import com.showcase.securitydemo.domain.base.MenuExample;
import com.showcase.securitydemo.exception.ServiceUncheckedException;
import com.showcase.securitydemo.rest.dto.menu.MenuDTO;
import com.showcase.securitydemo.rest.mapper.MenuMapper;
import com.showcase.securitydemo.security.SecurityUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by youpengfei on 2017/2/20.
 * 菜单业务逻辑
 */
@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private AuthorityDao authorityDao;

	@Autowired
	private MenuMapper menuMapper;


	public Menu getMenuById(Long id) {
		final Menu menu = menuDao.selectByPrimaryKey(id);

		if (menu == null || !menu.getFlag()) {
			throw new ServiceUncheckedException("菜单已经被删除").setProperty("menuId", id);
		}

		return menu;
	}

	public Page<Menu> findMenuPage(String name, Integer menuType, Pageable pageable) {
		MenuExample menuExample = new MenuExample();
		final MenuExample.Criteria criteria = menuExample.createCriteria();
		if (StringUtils.isNotBlank(name)) {
			criteria.andNameLike("%" + name + "%");
		}

		if (Objects.nonNull(menuType)) {
			criteria.andMenuTypeEqualTo(menuType);
		}
		criteria.andFlagEqualTo(true);

		final long totalCount = menuDao.countByExample(menuExample);
		if (totalCount > 0) {
			menuExample.setOffset(pageable.getOffset());
			menuExample.setRows(pageable.getPageSize());
			final List<Menu> menus = menuDao.selectByExample(menuExample);
			if (CollectionUtils.isNotEmpty(menus)) {
				return new PageImpl<>(menus, pageable, totalCount);
			}
		}

		return new PageImpl<>(Lists.newArrayList(), pageable, 0);
	}

	public void deleteMenu(Long id) {
		final Menu menu = menuDao.selectByPrimaryKey(id);
		if (menu == null) {
			throw new ServiceUncheckedException("删除的菜单不存在").setProperty("id", id);
		}
		menuDao.deleteByPrimaryKey(id);
	}

	public int updateMenu(Menu menu) {
		final Menu menuFromDb = menuDao.selectByPrimaryKey(menu.getId());
		if (menuFromDb == null) {
			throw new ServiceUncheckedException("需要更新的菜单不存在").setProperty("id", menu.getId());
		}
		menuFromDb.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElse(null));
		menuFromDb.setLastModifiedDate(new Date());
		menuFromDb.setIcon(menu.getIcon());
		menuFromDb.setMenuType(menu.getMenuType());
		menuFromDb.setName(menu.getName());
		menuFromDb.setParentId(menu.getParentId());
		menuFromDb.setAuthority(menu.getAuthority());
		menuFromDb.setMenuImg(menu.getMenuImg());
		menuFromDb.setPath(menu.getPath());
		return menuDao.updateByPrimaryKey(menuFromDb);
	}

	public Long addMenu(Menu menu) {
		menu.setCreatedDate(new Date());
		menu.setCreatedBy(SecurityUtils.getCurrentUserLogin().orElse(null));
		menu.setFlag(true);
		MenuExample example = new MenuExample();
		example.setRows(1);
		example.createCriteria().andNameEqualTo(menu.getName().trim());
		List<Menu> menus = menuDao.selectByExample(example);
		if (CollectionUtils.isEmpty(menus)) {
			menuDao.insertSelective(menu);
		} else {
			menu.setId(menus.get(0).getId());
			menuDao.updateByPrimaryKeySelective(menu);
		}
		return menu.getId();
	}


	public List<Menu> getAllMenusByUserId(Long userId) {
		final List<Authority> authorities = authorityDao.getAuthByUserId(userId);
		final List<String> authName = authorities.stream().map(Authority::getName).collect(Collectors.toList());
		MenuExample menuExample = new MenuExample();
		menuExample.createCriteria().andFlagEqualTo(true).andAuthorityIn(authName);
		return menuDao.selectByExample(menuExample);
	}

	public List<MenuDTO> getAllMenuByAuths(List<String> auths) {
		if (CollectionUtils.isEmpty(auths)) {
			return Lists.newArrayList();
		}
		MenuExample menuExample = new MenuExample();
		menuExample.createCriteria().andFlagEqualTo(true).andAuthorityIn(auths);
		return getMenuDTOS(menuExample);
	}

	public List<MenuDTO> findMenuTree() {
		MenuExample menuExample = new MenuExample();
		return getMenuDTOS(menuExample);
	}

	private List<MenuDTO> getMenuDTOS(MenuExample menuExample) {
		final List<Menu> menus = menuDao.selectByExample(menuExample);
		List<MenuDTO> menuItemDTOs = new ArrayList<>();
		Map<Long, List<MenuDTO>> map = new HashMap<>();

		for (Menu menu : menus) {
			//如果parent_id 不为空 且 类型为菜单
			final MenuDTO menuDTO = menuMapper.domainToDto(menu);
			menuDTO.setHidden(false);
			if (menu.getParentId() == 0) {
				menuItemDTOs.add(menuDTO);
			} else {
				final List<MenuDTO> childItems = map.getOrDefault(menu.getParentId(), new ArrayList<>());
				childItems.add(menuDTO);
				map.put(menu.getParentId(), childItems);
			}
		}


		for (MenuDTO menuItemDTO : menuItemDTOs) {
			menuItemDTO.setChildren(map.get(menuItemDTO.getId()));
		}
		return menuItemDTOs;
	}

	/**
	 * 获取指定权限下所有的菜单
	 *
	 * @param authId 权限id
	 * @return 菜单id列表
	 */
	public List<Long> getMenuIdsByAuthId(Long authId) {
		final Authority authority = authorityDao.selectByPrimaryKey(authId);
		return menuDao.getMenuIdsByAuthCode(authority.getName());
	}
}
