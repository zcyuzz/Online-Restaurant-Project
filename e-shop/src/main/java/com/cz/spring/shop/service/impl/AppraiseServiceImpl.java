package com.cz.spring.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.dao.AppraiseMapper;
import com.cz.spring.shop.model.Appraise;
import com.cz.spring.shop.service.AppraiseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AppraiseServiceImpl extends ServiceImpl<AppraiseMapper, Appraise> implements AppraiseService {

    @Autowired
    private AppraiseMapper appraiseMapper;

    @Override
    public PageResult<Appraise> listFull(PageParam pageParam) {

        List<Appraise> list = baseMapper.listFull(pageParam);

        return new PageResult<>(list, pageParam.getTotal());
    }
}
