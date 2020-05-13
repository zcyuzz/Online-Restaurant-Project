package com.cz.spring.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.system.model.User;

import java.util.List;


public interface UserService extends IService<User> {

    User getByUsername(String username);

    User getByPhone(String phone);

    PageResult<User> listUser(PageParam pageParam);

    boolean addUser(User user, List<Integer> roleIds);

    boolean updateUser(User user, List<Integer> roleIds);


    Integer selectNowAddUser();

    boolean checkOnLineState(Integer userId);
}
