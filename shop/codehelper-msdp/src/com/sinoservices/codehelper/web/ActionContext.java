package com.sinoservices.codehelper.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String action;
	private String json;

	public ActionContext(HttpServletRequest request,
			HttpServletResponse response, String action, String json) {
		this.request = request;
		this.response = response;
		this.action = action;
		this.json = json;
	}

	public HttpServletRequest getRequest() {
		return this.request;
	}

	public HttpServletResponse getResponse() {
		return this.response;
	}

	public String getAction() {
		return this.action;
	}

	public String getJson() {
		return this.json;
	}

	public String getParam(String name) {
		return this.request.getParameter(name);
	}

	public JSONObject getJSONObject() {
		return JSONObject.parseObject(this.json);
	}

	public <T> T parseJSON(Class<T> clazz) {
		return JSON.parseObject(this.json, clazz);
	}

	public JSONArray getJSONArray() {
		return JSON.parseArray(this.json);
	}
}
