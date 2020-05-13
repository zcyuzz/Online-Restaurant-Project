package com.cz.spring.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.system.model.Visitor;


public interface VisitorService extends IService<Visitor> {


    Integer selectNowVisitor();
}
