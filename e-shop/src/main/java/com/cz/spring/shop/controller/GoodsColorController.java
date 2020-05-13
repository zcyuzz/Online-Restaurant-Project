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
import com.cz.spring.shop.model.GoodsColor;
import com.cz.spring.shop.service.GoodsColorService;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/shop/goodsColor")
public class GoodsColorController extends BaseController {

    @Autowired
    private GoodsColorService goodsColorService;


   
    @SuppressWarnings("unchecked")
	@RequiresPermissions("goods:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<GoodsColor> list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);
        return new PageResult<>(goodsColorService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
    }

 
    @RequiresPermissions("goods:update")
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(GoodsColor goodsColor) {
        if (goodsColorService.save(goodsColor)) {
            return JsonResult.ok("add successfully");
        }
        return JsonResult.error("failed to add");
    }

 
    @RequiresPermissions("goods:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(GoodsColor goodsColor) {
        if (goodsColorService.updateById(goodsColor)) {
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }

 
    @RequiresPermissions("order:update")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer colorId){
        if(goodsColorService.removeById(colorId)){
            return JsonResult.ok("delete successfully");
        }
        return JsonResult.error("failed to delete");
    }
}
