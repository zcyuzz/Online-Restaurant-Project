package com.cz.spring.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.common.PageParam;
import com.cz.spring.shop.model.Goods;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GoodsMapper extends BaseMapper<Goods> {

	List<Goods> list(@Param("page") PageParam page);

    List<Goods> listFull(@Param("page") PageParam page);

    List<Goods> listByType(@Param("page") PageParam page);
}
