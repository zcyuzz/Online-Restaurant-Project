package com.cz.spring.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.system.model.LoginRecord;

import java.util.List;
import java.util.Map;


public interface LoginRecordService extends IService<LoginRecord> {

    PageResult<LoginRecord> listFull(PageParam page);


    List<LoginRecord> listAll(Map pageData);

}
