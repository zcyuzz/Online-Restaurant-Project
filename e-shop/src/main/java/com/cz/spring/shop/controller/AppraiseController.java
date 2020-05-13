package com.cz.spring.shop.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.spring.common.BaseController;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.model.Appraise;
import com.cz.spring.shop.service.AppraiseService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/shop/appraise")
public class AppraiseController extends BaseController {

    @Autowired
    private AppraiseService appraiseService;

    @RequiresPermissions("appraise:view")
    @RequestMapping()
    public String goods() {
        return "shop/appraise.html";
    }

  
    @RequiresPermissions("appraise:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Appraise> list(HttpServletRequest request) {

        return appraiseService.listFull(new PageParam(request).setDefaultOrder(null, new String[]{"appraise_time"}));
    }

   
    @RequiresPermissions("appraise:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Appraise appraise) {
        if (appraiseService.updateById(appraise)) {
            return JsonResult.ok("修改成功");
        }
        return JsonResult.error("修改失败");
    }

 
    @RequiresPermissions("appraise:update")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer appraiseId) {
        if (appraiseService.removeById(appraiseId)) {
            return JsonResult.ok("delete successfully");
        }
        return JsonResult.error("failed to delete");
    }
}
