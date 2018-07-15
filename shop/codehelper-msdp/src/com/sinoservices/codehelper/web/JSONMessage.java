package com.sinoservices.codehelper.web;

import java.io.Serializable;

public class JSONMessage implements Serializable {
	private Boolean result;
	private Boolean exception;
	private String msg;
	private Serializable data;

	public Boolean getResult() {
		return this.result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Serializable getData() {
		return this.data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}

	public Boolean getException() {
		return this.exception;
	}

	public void setException(Boolean exception) {
		this.exception = exception;
	}
}
