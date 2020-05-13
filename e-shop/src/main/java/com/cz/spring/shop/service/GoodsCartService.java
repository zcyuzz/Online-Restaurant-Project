package com.cz.spring.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.shop.model.GoodsCart;

import java.util.List;


public interface GoodsCartService extends IService<GoodsCart> {

    List<GoodsCart> listByUser(Integer userId);

    Integer countByUser(Integer userId);
}
