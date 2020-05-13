package com.cz.spring.common;

import java.util.HashMap;

public class JsonResult extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	private JsonResult() {
	}

	public static JsonResult ok() {
		return ok("success");
	}

	public static JsonResult ok(String message) {
		return ok(200, message);
	}

	public static JsonResult ok(int code, String message) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.put("code", code);
		jsonResult.put("msg", message);
		return jsonResult;
	}

	public static JsonResult error() {
		return error("error");
	}

	public static JsonResult error(String messag) {
		return error(500, messag);
	}

	public static JsonResult error(int code, String message) {
		return ok(code, message);
	}

	public JsonResult setCode(int code) {
		super.put("code", code);
		return this;
	}

	public JsonResult setMessage(String message) {
		super.put("msg", message);
		return this;
	}

	@Override
	public JsonResult put(String key, Object object) {
		super.put(key, object);
		return this;
	}
}