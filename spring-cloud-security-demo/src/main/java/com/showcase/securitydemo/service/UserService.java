package com.showcase.securitydemo.service;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.showcase.securitydemo.dao.base.BaseUserDao;
import com.showcase.securitydemo.domain.base.User;
import com.showcase.securitydemo.domain.base.UserExample;
import com.showcase.securitydemo.exception.ServiceUncheckedException;
import com.showcase.securitydemo.exception.SystemUncheckedException;
import com.showcase.securitydemo.rest.dto.user.UserDTO;
import com.showcase.securitydemo.rest.dto.user.UserUpdatePasswordDTO;
import com.showcase.securitydemo.rest.mapper.UserMapper;
import com.showcase.securitydemo.security.SecurityUtils;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private BaseUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增用户
     *
     * @return 新增用户产生的id
     */
    public Long addUser(User user) {

        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(user.getEmail());
        final long count = userDao.countByExample(userExample);
        if (count > 0) {
            throw new ServiceUncheckedException("用户邮箱重复").setProperty("email", user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(true);
        userDao.insertSelective(user);
        return user.getId();
    }


    /**
     * 删除用户，只能删除没有被删除的用户
     *
     * @param id 用户id
     */
    public void deleteUser(Long id) {
        final User user = userDao.selectByPrimaryKey(id);
        if (user != null && user.getFlag()) {
            user.setFlag(false);
            userDao.updateByPrimaryKeySelective(user);
        } else {
            throw new ServiceUncheckedException("没有这个有效的用户id").setProperty("userId", id);
        }
    }

    //冻结用户
    public int freezeUser(Long userId, Boolean isActive) {
        return 0;
    }


    //查询用户列表
    public Page<UserDTO> findUserPage(User user, Pageable pageable) {
        Preconditions.checkNotNull(pageable, "分页条件不能为空");
        UserExample userExample = new UserExample();


        final UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andFlagEqualTo(true);

        if (StringUtils.isNotBlank(user.getShowName())) {
            criteria.andShowNameLike("%" + user.getShowName() + "%");
        }

        if (user.getDepartmentId() != null) {
            criteria.andDepartmentIdEqualTo(user.getDepartmentId());
        }

        if (StringUtils.isNotBlank(user.getEmail())) {
            criteria.andEmailLike("%" + user.getEmail() + "%");
        }

        final long total = userDao.countByExample(userExample);
        if (total > 0) {
            userExample.setOffset(pageable.getOffset());
            userExample.setRows(pageable.getPageSize());
            final List<User> users = userDao.selectByExample(userExample);
            final List<UserDTO> userDTOS = userMapper.domainsToDtos(users);

            return new PageImpl<>(userDTOS, pageable, total);
        }
        return new PageImpl<>(Lists.newArrayList(), pageable, 0);
    }

    //查询用户信息
    public User getUserById(Long id) {
        Preconditions.checkNotNull(id, "用户id不能为空");
        final User user = userDao.selectByPrimaryKey(id);
        if (user != null && !user.getFlag()) {
            throw new SystemUncheckedException("用户已经被删除").setProperty("userId", id);
        }
        return user;
    }

    public int updateUser(User user) {
        final User user1 = userDao.selectByPrimaryKey(user.getId());
        if (user1 == null) {
            throw new ServiceUncheckedException("用户不存在").setProperty("userId", user.getId());
        }
        String username = user1.getEmail();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setEmail(user.getEmail());
        user1.setShowName(user.getShowName());
        user1.setLastModifiedBy(SecurityUtils.getCurrentUserLogin().orElse(""));
        user1.setLastModifiedDate(new Date());
        user1.setDepartmentId(user.getDepartmentId());
        int result = userDao.updateByPrimaryKeySelective(user1);

        return result;
    }


    /**
     * @param email    邮箱
     * @param password 密码 明码
     * @return 返回用户信息
     */
    public User getUserByEmailAndPassword(String email, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email);
        userExample.setRows(1);
        final List<User> users = userDao.selectByExample(userExample);

        if (CollectionUtils.isNotEmpty(users)) {
            final User user = users.get(0);

            final boolean checkpw = passwordEncoder.matches(password, user.getPassword());
            if (checkpw) {
                return user;
            }
        }
        return null;
    }

    /**
     * @param email 邮箱
     * @return 返回用户信息
     */
    public User getUserByEmail(String email) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andEmailEqualTo(email).andFlagEqualTo(Boolean.TRUE);
        userExample.setRows(1);
        final List<User> users = userDao.selectByExample(userExample);
        return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
    }

    /**
     * @return 返回当前登陆用户的详细信息
     */
    public User findUser() {
        return getUserByEmail(SecurityUtils.getCurrentUserLogin().orElse(null));
    }

    public int updatePassword(UserUpdatePasswordDTO userUpdatePasswordDTO) {
        final String username = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (!userUpdatePasswordDTO.getPassword().equals(userUpdatePasswordDTO.getConfirmPassword())) {
            throw new ServiceUncheckedException("俩次密码不一致");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(userUpdatePasswordDTO.getPassword()));
        user.setLastPasswordResetDate(DateTime.now().toDate());
        user.setLastModifiedBy(username);
        user.setLastModifiedDate(DateTime.now().toDate());
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(username);
        int result = userDao.updateByExampleSelective(user, example);

        return result;
    }
}
