package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.common.exception.BusinessException;
import com.cz.spring.system.dao.RoleAuthoritiesMapper;
import com.cz.spring.system.model.RoleAuthorities;
import com.cz.spring.system.service.RoleAuthoritiesService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class RoleAuthoritiesServiceImpl extends ServiceImpl<RoleAuthoritiesMapper, RoleAuthorities> implements RoleAuthoritiesService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRoleAuth(Integer roleId, List<Integer> authIds) {
        baseMapper.delete(new UpdateWrapper<RoleAuthorities>().eq("role_id", roleId));
        if (authIds != null && authIds.size() > 0) {
            if (baseMapper.insertRoleAuths(roleId, authIds) < authIds.size()) {
                throw new BusinessException("error");
            }
        }
        return true;
    }

}
