package com.cz.spring.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.system.model.Role;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {

    List<Role> listByUserId(@Param("userId") Integer userId);

    Role getByUserId(@Param("userId") Integer userId);
}
