package com.cz.spring.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.common.BaseController;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageResult;
import com.cz.spring.system.model.Authorities;
import com.cz.spring.system.service.AuthoritiesService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/system/authorities")
public class AuthoritiesController extends BaseController {
    @Autowired
    private AuthoritiesService authoritiesService;

    @RequiresPermissions("authorities:view")
    @RequestMapping()
    public String authorities(Model model) {
        List<Authorities> authorities = authoritiesService.list(new QueryWrapper<Authorities>().eq("is_menu", 0).orderByAsc("order_number"));
        model.addAttribute("authorities", authorities);
        return "system/authorities.html";
    }


    @RequiresPermissions("authorities:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Authorities> list() {
        List<Authorities> authorities = authoritiesService.list(new QueryWrapper<Authorities>().orderByAsc("order_number"));
        return new PageResult<>(authorities);
    }

    @RequiresPermissions("authorities:update")
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(Authorities authorities) {
        if (authoritiesService.save(authorities)) {
            return JsonResult.ok("add successfully");
        }
        return JsonResult.error("failed to add");
    }


    @RequiresPermissions("authorities:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Authorities authorities) {
        if (authoritiesService.updateById(authorities)) {
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }


    @RequiresPermissions("authorities:update")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer authorityId) {
        if (authoritiesService.removeById(authorityId)) {
            return JsonResult.ok("delete successfully");
        }
        return JsonResult.error("failed to delete");
    }

}
