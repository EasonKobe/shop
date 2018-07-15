package com.sinoservices.codehelper.template;

import java.util.List;

public class Group {
	private String name;
	private List<Template> templateList;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Template> getTemplateList() {
		return this.templateList;
	}

	public void setTemplateList(List<Template> templateList) {
		this.templateList = templateList;
	}
}
