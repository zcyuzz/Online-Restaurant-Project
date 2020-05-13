package com.cz.spring.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cz.spring.common.JsonResult;
import com.cz.spring.common.shiro.EndecryptUtil;
import com.cz.spring.common.utils.StringUtil;
import com.cz.spring.system.model.User;
import com.cz.spring.system.service.UserService;

//login api
@RequestMapping("/api")
@RestController
public class ApiLoginController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public JsonResult login(String username, String password) {
		if (StringUtil.isBlank(username, password)) {
			return JsonResult.error("Username and Password cannot be blank");
		}
		User user = userService.getByUsername(username);
		if (user == null) {
			return JsonResult.error("Acoount doesn't exist");
		}
		if (user.getState() != 0) {
			return JsonResult.error("Account has been banned");
		}
		if (!EndecryptUtil.encrytMd5(password, 3).equals(user.getPassword())) {
			return JsonResult.error("Wrong password");
		}

		User u = new User();
		u.setUserId(user.getUserId());
		u.setUserOnlineType(1);
		userService.updateById(u);
		return JsonResult.ok("login success").put("user", user);
	}

	@PostMapping("/register")
	public JsonResult register(User user) {
		User userinfo = userService.getByUsername(user.getUsername());
		if (userinfo != null) {
			return JsonResult.error("username already existed");
		}
		user.setState(0);
		user.setUserType(1);
		// !!
		String avatar = "http://thirdqq.qlogo.cn/g?b=oidb&k=pK18ebvJeCnGundmu9GoAQ&s=640&t=1571929112";
		user.setAvatar(avatar);
		user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(), 3));
		if (userService.save(user)) {
			return JsonResult.ok("register successd");
		}
		return JsonResult.error("register failed");
	}

	@GetMapping("/getUserById")
	public JsonResult userInfo(User user) {

		return JsonResult.error("success").put("data", userService.getById(user.getUserId()));
	}

	@PostMapping("/updateMyInfo")
	public JsonResult updateMyInfo(User user) {
		if (userService.updateById(user)) {
			User newUser = userService.getById(user.getUserId());
			return JsonResult.ok("success").put("data", newUser);
		}
		return JsonResult.error("success");
	}

	@PostMapping("/logout")
	public JsonResult logout(User user) {
		if (userService.updateById(user)) {
			return JsonResult.ok("logout successfully");
		}
		return JsonResult.error("logout error");
	}

	@ResponseBody
	@RequestMapping("/updatePsw")
	public JsonResult updatePsw(Integer userId, String oldPsw, String newPsw) {
		if (StringUtil.isBlank(oldPsw, newPsw)) {
			return JsonResult.error("password can't be blank");
		}
		User user = userService.getById(userId);
		if (!user.getPassword().equals(EndecryptUtil.encrytMd5(oldPsw, 3))) {
			return JsonResult.error("old password is incorrect");
		}
		user.setUserId(userId);
		user.setPassword(EndecryptUtil.encrytMd5(newPsw, 3));
		if (userService.updateById(user)) {
			return JsonResult.ok("update successfully");
		}
		return JsonResult.error("update error");
	}

	@ResponseBody
	@RequestMapping("/resetPsw")
	public JsonResult resetPsw(String username, String surePsw, String newPsw, String phone) {
		if (StringUtil.isBlank(username, phone, newPsw, surePsw)) {
			return JsonResult.error("Fields can't be blank");
		}
		if (!surePsw.equals(newPsw)) {
			return JsonResult.error("Password is not same");
		}
		User user = userService.getByUsername(username);
		if (user == null) {
			return JsonResult.error("user error");
		}
		if (!user.getPhone().equals(phone)) {
			return JsonResult.error("phone error");
		}
		user.setPassword(EndecryptUtil.encrytMd5(newPsw, 3));
		if (userService.updateById(user)) {
			return JsonResult.ok("update successfully");
		}
		return JsonResult.error("update error");
	}
}
