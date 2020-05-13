package com.cz.spring.system.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.spring.common.JsonResult;
import com.cz.spring.system.model.SysConfig;
import com.cz.spring.system.service.SysConfigService;


@Controller
@RequestMapping("/system/config")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequiresPermissions("config:view")
    @RequestMapping()
    public String config(Model model) {
        SysConfig mails=sysConfigService.getById(1);
        model.addAttribute("mails", mails);
        return "system/config.html";
    }


    @RequiresPermissions("config:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(SysConfig config){
        if(sysConfigService.updateById(config)){
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }
}
