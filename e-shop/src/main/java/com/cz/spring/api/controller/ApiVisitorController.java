package com.cz.spring.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.common.utils.UserAgentGetter;
import com.cz.spring.system.model.Visitor;
import com.cz.spring.system.service.VisitorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/api/visitor")
@RestController
public class ApiVisitorController {

    @Autowired
    private VisitorService visitorService;


    @PostMapping("/add")
    public void addVisitor(HttpServletRequest request){
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        String ip=agentGetter.getIpAddr();

        Visitor v=visitorService.getOne(new QueryWrapper<Visitor>()
                .eq("visitor_ip",ip));
        if(v==null){
            Visitor v2=new Visitor();
            v2.setVisitorIp(ip);
            visitorService.save(v2);
        }
    }
}
