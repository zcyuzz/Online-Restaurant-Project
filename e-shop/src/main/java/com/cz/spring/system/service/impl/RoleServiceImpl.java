package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.system.dao.RoleMapper;
import com.cz.spring.system.model.Role;
import com.cz.spring.system.service.RoleService;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> listByUserId(Integer userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public Role getByUserId(Integer userId) {
        return baseMapper.getByUserId(userId);
    }
}
