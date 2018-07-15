package com.sinoservices.codehelper.model.layout;

public class TemplateLayout {
	private String name;
	private String descript;
	private String templateDir;
	private String saveDir;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

	public String getTemplateDir() {
		return this.templateDir;
	}

	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}

	public String getSaveDir() {
		return this.saveDir;
	}

	public void setSaveDir(String saveDir) {
		this.saveDir = saveDir;
	}
}
