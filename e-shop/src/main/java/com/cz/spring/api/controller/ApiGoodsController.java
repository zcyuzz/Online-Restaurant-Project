package com.cz.spring.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.common.JsonResult;
import com.cz.spring.common.PageParam;
import com.cz.spring.common.PageResult;
import com.cz.spring.shop.model.Goods;
import com.cz.spring.shop.service.GoodsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//goods api
@RequestMapping("/api/goods")
@RestController
public class ApiGoodsController {

	@Autowired
	private GoodsService goodsService;

	// list goods
	@ResponseBody
	@RequestMapping("/list")
	public PageResult<Goods> list(HttpServletRequest request) {

		return goodsService.listFull(new PageParam(request).setDefaultOrder(null, new String[] { "create_time" }));
	}

	@ResponseBody
	@RequestMapping("/listByType")
	public PageResult<Goods> listByType(HttpServletRequest request) {

		return goodsService.listByType(new PageParam(request).setDefaultOrder(null, new String[] { "create_time" }));
	}

	@ResponseBody
	@RequestMapping("/details")
	public JsonResult details(String goodsSn) {
		List<Goods> list = goodsService.list(new QueryWrapper<Goods>().eq("goods_sn", goodsSn));
		return JsonResult.ok(0, "success").put("data", list);
	}

}
