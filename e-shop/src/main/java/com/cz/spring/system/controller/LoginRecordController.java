package com.cz.spring.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.common.utils.PoiUtil;
import com.cz.spring.system.model.LoginRecord;
import com.cz.spring.system.service.LoginRecordService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/system/loginRecord")
public class LoginRecordController {
    @Autowired
    private LoginRecordService loginRecordService;

    @RequiresPermissions("loginRecord:view")
    @RequestMapping()
    public String loginRecord() {
        return "system/loginRecord.html";
    }


    @RequiresPermissions("loginRecord:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<LoginRecord> list(HttpServletRequest request) {
        return loginRecordService.listFull(new PageParam(request).setDefaultOrder(null, new String[]{"create_time"}));
    }


    @RequiresPermissions("loginRecord:view")
    @ResponseBody
    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String[]> exportList = new ArrayList<>();
        String[] titles = new String[]{"user", "user role", "IP", "device", "type", "brower"};
        exportList.add(titles);
        List<LoginRecord> list = loginRecordService.listAll(new PageParam(request).getPageData());
        for (LoginRecord one : list) {
            String[] strs = new String[]{one.getUsername(), one.getNickName(), one.getIpAddress(), one.getDevice(), one.getOsName(), one.getBrowserType()};
            exportList.add(strs);
        }
        PoiUtil.exportData(exportList, "login record", response);
    }

}
