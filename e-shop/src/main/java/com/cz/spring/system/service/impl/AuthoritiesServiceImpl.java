package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.system.dao.AuthoritiesMapper;
import com.cz.spring.system.model.Authorities;
import com.cz.spring.system.service.AuthoritiesService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthoritiesServiceImpl extends ServiceImpl<AuthoritiesMapper, Authorities> implements AuthoritiesService {

    @Override
    public List<Authorities> listByUserId(Integer userId) {
        return baseMapper.listByUserId(userId);
    }

    @Override
    public List<Authorities> listByRoleId(Integer roleId) {
        if (roleId == null) {
            return new ArrayList<>();
        }
        return baseMapper.listByRoleId(roleId);
    }


    @Override
    public List<Authorities> listByRoleIds(List<Integer> roleIds) {
        if (roleIds == null || roleIds.size() == 0) {
            return new ArrayList<>();
        }
        return baseMapper.listByRoleIds(roleIds);
    }

}
