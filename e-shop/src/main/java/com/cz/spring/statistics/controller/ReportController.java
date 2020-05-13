package com.cz.spring.statistics.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.common.BaseController;
import com.cz.spring.common.JsonResult;
import com.cz.spring.shop.model.Order;
import com.cz.spring.shop.service.OrderService;
import com.cz.spring.statistics.model.YearMonthModel;
import com.cz.spring.system.service.UserService;
import com.cz.spring.system.service.VisitorService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * 统计管理
 **/
@Controller
@RequestMapping("/statistics/report")
public class ReportController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private VisitorService visitorService;

    @Autowired
    private OrderService orderService;

    @RequiresPermissions("report:view")
    @RequestMapping()
    public String report(Model model) {
        Integer nowAddUserCount = userService.selectNowAddUser();
        Integer userAllCount = userService.count();
        Integer nowVisitorCount = visitorService.selectNowVisitor();
        Integer visitorAllCount = visitorService.count();
        BigDecimal monthMoney = orderService.selectMonthMoney();
        BigDecimal totalMoney = orderService.selectMoneyTotal();
        Integer monthOrderCount = orderService.selectMonthConut();
        Integer orderAllCount = orderService.count(new QueryWrapper<Order>().eq("order_status", 4));
        model.addAttribute("nowAddUserCount", nowAddUserCount);
        model.addAttribute("userAllCount", userAllCount);
        model.addAttribute("nowVisitorCount", nowVisitorCount);
        model.addAttribute("visitorAllCount", visitorAllCount);
        model.addAttribute("monthMoney", monthMoney);
        model.addAttribute("totalMoney", totalMoney);
        model.addAttribute("monthOrderCount", monthOrderCount);
        model.addAttribute("orderAllCount", orderAllCount);
        return "statistics/report.html";
    }


    @RequiresPermissions("report:view")
    @GetMapping("/getOrderCvr")
    @ResponseBody
    public JsonResult orderCvr() {
        Integer orderAllCount = orderService.count(new QueryWrapper<Order>().eq("order_status", 4));
        Integer visitorCount = visitorService.count();
        double cvr = 0.0;
        if (orderAllCount > 0 && visitorCount > 0) {
            cvr = new BigDecimal((float) orderAllCount / visitorCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() * 100;
        }
        return JsonResult.ok("serach successfully").put("data", cvr);
    }


    @RequiresPermissions("report:view")
    @GetMapping("/getYearMonthMoneyTotal")
    @ResponseBody
    public JsonResult getYearMonthMoneyTotal() {
        List<YearMonthModel> list = orderService.selectYearMonthMoneyTotal();
        return JsonResult.ok("serach successfully").put("data", list);
    }
}
