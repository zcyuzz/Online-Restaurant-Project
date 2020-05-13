package com.cz.spring.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.shop.dao.GoodsCartMapper;
import com.cz.spring.shop.model.GoodsCart;
import com.cz.spring.shop.service.GoodsCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsCartServiceImpl extends ServiceImpl<GoodsCartMapper, GoodsCart> implements GoodsCartService {

    @Autowired
    private GoodsCartMapper goodsCartMapper;

    @Override
    public List<GoodsCart> listByUser(Integer userId) {

        return goodsCartMapper.listByUser(userId);
    }

    @Override
    public Integer countByUser(Integer userId) {

        return goodsCartMapper.countByUser(userId);
    }
}
