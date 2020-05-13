package com.cz.spring.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.system.model.Role;

import java.util.List;


public interface RoleService extends IService<Role> {

    List<Role> listByUserId(Integer userId);

    Role getByUserId(Integer userId);
}
