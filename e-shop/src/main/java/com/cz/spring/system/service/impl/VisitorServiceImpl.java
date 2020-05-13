package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.system.dao.VisitorMapper;
import com.cz.spring.system.model.Visitor;
import com.cz.spring.system.service.VisitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Autowired
    private VisitorMapper visitorMapper;

    @Override
    public Integer selectNowVisitor() {

        return visitorMapper.selectNowVisitor();
    }
}
