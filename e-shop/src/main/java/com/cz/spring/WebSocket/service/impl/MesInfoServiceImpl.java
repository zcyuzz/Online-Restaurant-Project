package com.cz.spring.WebSocket.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cz.spring.WebSocket.dao.MesInfoMapper;
import com.cz.spring.WebSocket.model.MesInfo;
import com.cz.spring.WebSocket.service.MesInfoService;

import org.springframework.stereotype.Service;

@Service
public class MesInfoServiceImpl extends ServiceImpl<MesInfoMapper, MesInfo> implements MesInfoService {
}
