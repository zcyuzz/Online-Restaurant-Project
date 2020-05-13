package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.common.exception.BusinessException;
import com.cz.spring.system.dao.UserMapper;
import com.cz.spring.system.dao.UserRoleMapper;
import com.cz.spring.system.model.Role;
import com.cz.spring.system.model.User;
import com.cz.spring.system.model.UserRole;
import com.cz.spring.system.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    public User getByPhone(String phone) {

        return baseMapper.selectByPhone(phone);
    }

    @Override
    public PageResult<User> listUser(PageParam pageParam) {
        List<User> userList = baseMapper.listFull(pageParam);

        if (userList != null && userList.size() > 0) {
            List<UserRole> userRoles = userRoleMapper.selectByUserIds(getUserIds(userList));
            for (User one : userList) {
                List<Role> tempURs = new ArrayList<>();
                for (UserRole ur : userRoles) {
                    if (one.getUserId().equals(ur.getUserId())) {
                        tempURs.add(new Role(ur.getRoleId(), ur.getRoleName(), null));
                    }
                }
                one.setRoles(tempURs);
            }
        }
        return new PageResult<>(userList, pageParam.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addUser(User user, List<Integer> roleIds) {
        if (baseMapper.selectByUsername(user.getUsername()) != null) {
            throw new BusinessException("user:" + user.getUsername() + "already existed");
        }
        boolean result = baseMapper.insert(user) > 0;
        if (result) {
            if (userRoleMapper.insertBatch(user.getUserId(), roleIds) < roleIds.size()) {
                throw new BusinessException("failed to add");
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateUser(User user, List<Integer> roleIds) {
        user.setUsername(null);  
        boolean result = baseMapper.updateById(user) > 0;
        if (result) {
            userRoleMapper.delete(new UpdateWrapper<UserRole>().eq("user_id", user.getUserId()));
            if (userRoleMapper.insertBatch(user.getUserId(), roleIds) < roleIds.size()) {
                throw new BusinessException("failed to update");
            }
        }
        return result;
    }


    @Override
    public Integer selectNowAddUser() {

        return baseMapper.selectNowAddUser();
    }

    @Override
    public boolean checkOnLineState(Integer userId) {
        User user=userMapper.selectById(userId);
        if(user.getUserOnlineType()==0){
            return false;
        }
        return true;
    }


    private List<Integer> getUserIds(List<User> userList) {
        List<Integer> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        return userIds;
    }

}
