package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.system.dao.LoginRecordMapper;
import com.cz.spring.system.model.LoginRecord;
import com.cz.spring.system.service.LoginRecordService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class LoginRecordServiceImpl extends ServiceImpl<LoginRecordMapper, LoginRecord> implements LoginRecordService {

    @Override
    public PageResult<LoginRecord> listFull(PageParam pageParam) {
        List<LoginRecord> records = baseMapper.listFull(pageParam);
        return new PageResult<>(records, pageParam.getTotal());
    }

    @Override
    public List<LoginRecord> listAll(Map pageData) {
        return baseMapper.listAll(pageData);
    }
}
