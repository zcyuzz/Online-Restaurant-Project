package com.cz.spring.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.model.Appraise;


public interface AppraiseService extends IService<Appraise> {

    PageResult<Appraise> listFull(PageParam pageParam);
}
