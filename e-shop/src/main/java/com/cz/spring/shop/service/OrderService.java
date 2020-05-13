package com.cz.spring.shop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.model.Order;
import com.cz.spring.statistics.model.YearMonthModel;

import java.math.BigDecimal;
import java.util.List;


public interface OrderService extends IService<Order> {

    PageResult<Order> listOrder(PageParam pageParam);


    Integer selectMonthConut();


    BigDecimal selectMonthMoney();


    BigDecimal selectMoneyTotal();


    List<YearMonthModel>  selectYearMonthMoneyTotal();
}
