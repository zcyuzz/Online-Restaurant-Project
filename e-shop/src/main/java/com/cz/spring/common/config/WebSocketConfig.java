package com.cz.spring.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.cz.spring.WebSocket.WebSocketController;
import com.cz.spring.WebSocket.service.MesInfoService;
import com.cz.spring.system.service.UserService;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {

        return new ServerEndpointExporter();
    }

    @Autowired
    public void setUserService(UserService userService) {

        WebSocketController.userService = userService;
    }

    @Autowired
    public void setMesInfoService(MesInfoService mesInfoService) {

        WebSocketController.mesInfoService = mesInfoService;
    }
}

