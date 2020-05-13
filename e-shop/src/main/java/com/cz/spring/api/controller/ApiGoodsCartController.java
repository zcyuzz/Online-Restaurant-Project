package com.cz.spring.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cz.spring.common.JsonResult;
import com.cz.spring.shop.model.GoodsCart;
import com.cz.spring.shop.service.GoodsCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//cart api
@RequestMapping("/api/cart")
@RestController
public class ApiGoodsCartController {

	@Autowired
	private GoodsCartService goodsCartService;

	@ResponseBody
	@GetMapping("/listByUser")
	public JsonResult listByUser(Integer userId) {
		List<GoodsCart> list = goodsCartService.listByUser(userId);
		return JsonResult.ok(0, "search success").put("data", list);
	}

	@ResponseBody
	@GetMapping("/countByUser")
	public JsonResult countByUser(Integer userId) {
		Integer count = goodsCartService.countByUser(userId);
		return JsonResult.ok(0, "success").put("data", count);
	}

	@ResponseBody
	@PostMapping("/add")
	public JsonResult addCart(GoodsCart goodsCart) {
		GoodsCart g = goodsCartService.getOne(new QueryWrapper<GoodsCart>().eq("user_id", goodsCart.getUserId())
				.eq("goods_sn", goodsCart.getGoodsSn()).eq("made_logo", goodsCart.getMadeLogo())
				.eq("made_text", goodsCart.getMadeText()).eq("goods_color", goodsCart.getGoodsColor()));
		if (g != null) {
			return JsonResult.error("Already in cart,don't add again");
		}
		if (goodsCartService.save(goodsCart)) {
			return JsonResult.ok("success");
		}
		return JsonResult.error("failed");
	}

	@ResponseBody
	@PostMapping("/delete")
	public JsonResult delete(Integer cartId) {
		if (goodsCartService.removeById(cartId)) {
			return JsonResult.ok("success");
		}
		return JsonResult.error("failed");
	}
}
