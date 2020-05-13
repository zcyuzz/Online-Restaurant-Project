package com.cz.spring.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.model.Goods;

import org.apache.ibatis.annotations.Param;


public interface GoodsService extends IService<Goods> {

	PageResult<Goods> list(@Param("page") PageParam page);
	
    PageResult<Goods> listByType(@Param("page") PageParam page);

    PageResult<Goods> listFull(@Param("page") PageParam page);
}
