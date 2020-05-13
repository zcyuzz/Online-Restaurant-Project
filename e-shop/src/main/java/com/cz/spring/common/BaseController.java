package com.cz.spring.common;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cz.spring.system.model.User;

public class BaseController {

	public User getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Object object = subject.getPrincipal();
			if (object != null) {
				return (User) object;
			}
		}
		return null;
	}

	public Integer getLoginUserId() {
		User loginUser = getLoginUser();
		return loginUser == null ? null : loginUser.getUserId();
	}

}
