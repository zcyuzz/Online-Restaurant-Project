package com.cz.spring.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.common.PageParam;
import com.cz.spring.shop.model.Order;
import com.cz.spring.statistics.model.YearMonthModel;

import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


public interface OrderMapper extends BaseMapper<Order> {

    List<Order> selectListFull(@Param("page") PageParam page);


    Integer selectMonthConut();


    BigDecimal selectMonthMoney();


    BigDecimal selectMoneyTotal();


    List<YearMonthModel> selectYearMonthMoneyTotal();
}
