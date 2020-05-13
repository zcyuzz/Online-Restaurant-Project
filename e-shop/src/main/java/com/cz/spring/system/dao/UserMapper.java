package com.cz.spring.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.common.PageParam;
import com.cz.spring.system.model.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserMapper extends BaseMapper<User> {

    User selectByUsername(String username);

    User selectByPhone(String phone);

    List<User> listFull(@Param("page") PageParam page);

    Integer selectNowAddUser();
}
