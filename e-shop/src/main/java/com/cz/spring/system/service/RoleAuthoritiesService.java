package com.cz.spring.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.system.model.RoleAuthorities;

import java.util.List;


public interface RoleAuthoritiesService extends IService<RoleAuthorities> {
    boolean updateRoleAuth(Integer roleId, List<Integer> authIds);
}
