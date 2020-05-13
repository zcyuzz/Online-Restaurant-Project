package com.cz.spring.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.system.model.Visitor;


public interface VisitorMapper extends BaseMapper<Visitor> {


    Integer selectNowVisitor();
}
