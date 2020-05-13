package com.cz.spring.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring.common.PageParam;
import com.cz.spring.system.model.LoginRecord;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;



public interface LoginRecordMapper extends BaseMapper<LoginRecord> {


    List<LoginRecord> listFull(@Param("page") PageParam page);

    List<LoginRecord> listAll(@Param("pageData") Map pageData);

}
