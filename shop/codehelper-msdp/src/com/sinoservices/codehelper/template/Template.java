package com.sinoservices.codehelper.template;

public class Template {
	private String name;
	private String desc;
	private String target;
	private String path;

	public Template cloneTemplate() {
		Template targetObj = new Template();
		targetObj.name = this.name;
		targetObj.desc = this.desc;
		targetObj.target = this.target;
		targetObj.path = this.path;
		return targetObj;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
