package com.cz.spring.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.shop.model.GoodsCart;

import java.util.List;


public interface GoodsCartMapper extends BaseMapper<GoodsCart> {

    List<GoodsCart> listByUser(Integer userId);

    Integer countByUser(Integer userId);
}
