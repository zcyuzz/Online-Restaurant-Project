package com.cz.spring.api.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.common.exception.BusinessException;
import com.cz.spring.common.utils.MailUtil;
import com.cz.spring.common.utils.SnowflakeIdWorker;
import com.cz.spring.common.utils.StringUtil;
import com.cz.spring.shop.model.GoodsCart;
import com.cz.spring.shop.model.Order;
import com.cz.spring.shop.service.GoodsCartService;
import com.cz.spring.shop.service.OrderService;
import com.cz.spring.system.model.User;
import com.cz.spring.system.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * order api
 */
@RequestMapping("/api/order")
@RestController
@Slf4j
public class ApiOrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private GoodsCartService goodsCartService;

	@Autowired
	private UserService userService;

	@Autowired
	private MailUtil mailUtil;

	@GetMapping("/listByUser")
	public PageResult<Order> listByUser(HttpServletRequest request) {

		return orderService.listOrder(new PageParam(request).setDefaultOrder(null, new String[] { "create_time" }));
	}

	@GetMapping("/countByUser")
	public JsonResult countByUser(Integer userId) {
		Integer count = orderService.count(new QueryWrapper<Order>().eq("user_id", userId));
		return JsonResult.ok("success").put("data", count);
	}

	@PostMapping("/addOrder")
	public JsonResult addOrder(Order order) {
		SnowflakeIdWorker sfw = new SnowflakeIdWorker(0, 0);
		order.setOrderNo("E" + sfw.nextId());
		if (orderService.save(order)) {
			try {
				User user = userService.getByUsername("admin");
				if (StringUtil.isBlank(user.getEmail())) {
				} else {
					User user2 = userService.getById(order.getUserId());
					order.setPhone(user2.getPhone());
					order.setNickName(user2.getNickName());
					mailUtil.thymeleafEmail(user.getEmail(), "you have new order information", user.getNickName(),
							order);
				}
				return JsonResult.ok("place order successful");
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return JsonResult.error("failed to place order");
	}

	@Transactional(rollbackFor = Exception.class)
	@PostMapping("/addAll")
	public JsonResult addOrderAll(String arrStr) {
		List<Order> list = JSON.parseArray(arrStr, Order.class);
		SnowflakeIdWorker sfw = new SnowflakeIdWorker(0, 0);
		for (Order o : list) {
			o.setOrderNo("E" + sfw.nextId());
			if (!orderService.save(o)) {
				throw new BusinessException("failed to place order");
			}

			if (!goodsCartService.remove(new QueryWrapper<GoodsCart>().eq("user_id", o.getUserId())
					.eq("goods_sn", o.getGoodsSn()).eq("made_logo", o.getMadeLogo()).eq("made_text", o.getMadeText())
					.eq("goods_color", o.getGoodsColor()))) {
				throw new BusinessException("try again");
			}
		}

		return JsonResult.ok("place order successful");
	}

	@PostMapping("/updateOrder")
	public JsonResult updateOrder(Order order) {
		if (orderService.updateById(order)) {
			return JsonResult.ok("update order successfully");
		}
		return JsonResult.error("failed to update");
	}

	@PostMapping("/receive")
	public JsonResult receive(Order order) {

		order.setReceiveTime(new Date());
		if (orderService.updateById(order)) {
			return JsonResult.ok("receive oder successfully");
		}
		return JsonResult.error("failed, please try again");
	}
}
