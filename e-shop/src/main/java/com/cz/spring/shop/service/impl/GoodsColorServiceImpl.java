package com.cz.spring.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.shop.dao.GoodsColorMapper;
import com.cz.spring.shop.model.GoodsColor;
import com.cz.spring.shop.service.GoodsColorService;

import org.springframework.stereotype.Service;

@Service
public class GoodsColorServiceImpl extends ServiceImpl<GoodsColorMapper, GoodsColor> implements GoodsColorService {
}
