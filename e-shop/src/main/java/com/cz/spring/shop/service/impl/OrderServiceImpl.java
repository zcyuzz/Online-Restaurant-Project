package com.cz.spring.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.dao.OrderMapper;
import com.cz.spring.shop.model.Order;
import com.cz.spring.shop.service.OrderService;
import com.cz.spring.statistics.model.YearMonthModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public PageResult<Order> listOrder(PageParam pageParam) {

        List<Order> orderList = baseMapper.selectListFull(pageParam);

        return new PageResult<>(orderList, pageParam.getTotal());
    }

    @Override
    public Integer selectMonthConut() {

        return orderMapper.selectMonthConut();
    }

    @Override
    public BigDecimal selectMonthMoney() {

        return orderMapper.selectMonthMoney();
    }

    @Override
    public BigDecimal selectMoneyTotal() {

        return orderMapper.selectMoneyTotal();
    }

    @Override
    public List<YearMonthModel>  selectYearMonthMoneyTotal() {

        return orderMapper.selectYearMonthMoneyTotal();
    }
}
