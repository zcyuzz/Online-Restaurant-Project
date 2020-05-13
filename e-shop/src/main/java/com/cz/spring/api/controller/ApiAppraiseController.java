package com.cz.spring.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.common.exception.BusinessException;
import com.cz.spring.shop.model.Appraise;
import com.cz.spring.shop.model.Order;
import com.cz.spring.shop.service.AppraiseService;
import com.cz.spring.shop.service.OrderService;

import javax.servlet.http.HttpServletRequest;

// comment api
@RequestMapping("/api/appraise")
@RestController
public class ApiAppraiseController {

    @Autowired
    private AppraiseService appraiseService;

    @Autowired
    private OrderService orderService;

    //check comment information
    @ResponseBody
    @RequestMapping("/list")
    public PageResult<Appraise> list(HttpServletRequest request) {

        return appraiseService.listFull(new PageParam(request).setDefaultOrder(null, new String[]{"appraise_time"}));
    }
    // add comment information
    @ResponseBody
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public JsonResult add(Appraise appraise,Integer orderId){
        if(appraiseService.save(appraise)){
            //change corder status 
            Order order=new Order();
            order.setOrderId(orderId);
            order.setOrderStatus(4);
            if(!orderService.updateById(order)){
                throw new BusinessException("error, try again");
            }
            return JsonResult.ok("success");
        }
        return JsonResult.error("error");
    }
}
