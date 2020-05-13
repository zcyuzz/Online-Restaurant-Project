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
import com.cz.spring.common.utils.SnowflakeIdWorker;
import com.cz.spring.shop.model.Order;
import com.cz.spring.shop.service.OrderService;
import com.cz.spring.system.model.Role;
import com.cz.spring.system.service.RoleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/shop/order")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private RoleService roleService;

    @RequiresPermissions("order:view")
    @RequestMapping()
    public String order() {
        return "shop/order.html";
    }

 
    @RequiresPermissions("order:view")
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Order> list(HttpServletRequest request) {
    	PageParam pageParam = new PageParam(request);
        List<Role> roles = roleService.listByUserId(getLoginUserId());
        for (Role role : roles) {
        	if (role.getRoleId().equals(3)) {
            	pageParam.put("publish_id", getLoginUserId());
            }
		}
        //return new PageResult<>(orderService.page(pageParam, pageParam.getWrapper()).getRecords(), pageParam.getTotal());
        return orderService.listOrder(pageParam.setDefaultOrder(null, new String[]{"create_time"}));
    }


    @RequiresPermissions("order:update")
    @ResponseBody
    @RequestMapping("/updateExpress")
    public JsonResult updateExpress(Order order){
        SnowflakeIdWorker sfw = new SnowflakeIdWorker(0,0);
        Order orderInfo=new Order();
        orderInfo.setOrderId(order.getOrderId());  
        orderInfo.setExpressName(order.getExpressName());  
        orderInfo.setExpressNo("KD"+sfw.nextId()); 
        orderInfo.setDeliveryTime(new Date()); 
        if(orderService.updateById(orderInfo)){
            updateOrderStatus(order.getOrderId(),1);
            return JsonResult.ok("shipped successfully");
        }
        return JsonResult.error("error: shipping");
    }


    @RequiresPermissions("order:update")
    @ResponseBody
    @RequestMapping("/updateOrderStatus")
    public JsonResult updateOrderStatus(Integer orderId,Integer orderStatus){
        Order order=new Order();
        order.setOrderId(orderId);
        order.setOrderStatus(orderStatus);
        if(orderService.updateById(order)){
            return JsonResult.ok("update successfully");
        }
        return JsonResult.error("failed to update");
    }


    @RequiresPermissions("order:update")
    @ResponseBody
    @RequestMapping("/delete")
    public JsonResult delete(Integer orderId){
        if(orderService.removeById(orderId)){
            return JsonResult.ok("delete successfully");
        }
        return JsonResult.error("failed to delete");
    }
}
