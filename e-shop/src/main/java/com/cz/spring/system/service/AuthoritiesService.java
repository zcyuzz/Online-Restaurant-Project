package com.cz.spring.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.system.model.Authorities;

import java.util.List;


public interface AuthoritiesService extends IService<Authorities> {

    List<Authorities> listByUserId(Integer userId);

    List<Authorities> listByRoleIds(List<Integer> roleIds);

    List<Authorities> listByRoleId(Integer roleId);
}
