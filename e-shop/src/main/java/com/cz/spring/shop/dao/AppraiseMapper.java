package com.cz.spring.shop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.common.PageParam;
import com.cz.spring.shop.model.Appraise;

import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AppraiseMapper extends BaseMapper<Appraise> {

    List<Appraise> listFull(@Param("page") PageParam page);
}
