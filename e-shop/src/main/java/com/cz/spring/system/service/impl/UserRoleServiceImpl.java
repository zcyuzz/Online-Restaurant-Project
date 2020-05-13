package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.system.dao.UserRoleMapper;
import com.cz.spring.system.model.UserRole;
import com.cz.spring.system.service.UserRoleService;

import org.springframework.stereotype.Service;


@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
