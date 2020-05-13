package com.cz.spring.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.system.dao.SysConfigMapper;
import com.cz.spring.system.model.SysConfig;
import com.cz.spring.system.service.SysConfigService;

import org.springframework.stereotype.Service;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {
}
