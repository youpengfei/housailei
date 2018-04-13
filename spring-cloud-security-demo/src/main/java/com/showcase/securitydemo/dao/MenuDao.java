package com.showcase.securitydemo.dao;

import com.showcase.securitydemo.dao.base.BaseMenuDao;
import java.util.List;

/**
 * Created by youpengfei on 2017/5/3.
 * 菜单自定义dao
 */
public interface MenuDao extends BaseMenuDao {

    List<Long> getMenuIdsByAuthCode(String authCode);
}
