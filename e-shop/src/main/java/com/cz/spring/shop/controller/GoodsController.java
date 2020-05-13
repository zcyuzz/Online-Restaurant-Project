package com.cz.spring.shop.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cz.spring.common.BaseController;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.common.utils.SnowflakeIdWorker;
import com.cz.spring.shop.model.Goods;
import com.cz.spring.shop.model.GoodsColor;
import com.cz.spring.shop.service.GoodsColorService;
import com.cz.spring.shop.service.GoodsService;
import com.cz.spring.system.model.Role;
import com.cz.spring.system.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/shop/goods")
public class GoodsController extends BaseController {

    @Autowired
    private GoodsService goodsService;
    
    @Autowired
    private RoleService roleService;
    

    @RequiresPermissions("goods:view")
    @RequestMapping()
    public String goods(Model model) {
        return "shop/goods.html";
    }

   
    @RequiresPermissions("goods:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Goods> list(HttpServletRequest request) {
        PageParam pageParam = new PageParam(request);

        List<Role> roles = roleService.listByUserId(getLoginUserId());
        for (Role role : roles) {
        	if (role.getRoleId().equals(3)) {
            	pageParam.put("publish_id", getLoginUserId());
            }
		}

    	return goodsService.list(pageParam.setDefaultOrder(null, new String[]{"create_time"}));
    }

 
    @RequiresPermissions("goods:update")
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(Goods goods) {
        System.out.println(goods);
        SnowflakeIdWorker sfw = new SnowflakeIdWorker(0,0);
        goods.setGoodsSn("CX"+sfw.nextId());   
        goods.setPublishId(getLoginUserId());
        if (goodsService.save(goods)) {
            return JsonResult.ok("add successfully");
        }
        return JsonResult.error("field to add");
    }

    /**
     * 修改商品
     **/
    @RequiresPermissions("goods:update")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(Goods goods) {
        if (goodsService.updateById(goods)) {
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }

    /**
     * 删除商品
     **/
    @RequiresPermissions("goods:update")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer goodsId) {
        if (goodsService.removeById(goodsId)) {
            return JsonResult.ok("delete successfully");
        }
        return JsonResult.error("failed to delete");
    }

    /**
     * 修改商品上架状态
     **/
    @RequiresPermissions("goods:update")
    @ResponseBody
    @RequestMapping("/updateState")
    public JsonResult updateState(Goods goods){
        if(goodsService.updateById(goods)){
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }
}
