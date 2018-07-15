package com.sinoservices.codehelper.model;

public class ColumnModel {
	private String name;
	private String comment;
	private String type;
	private Boolean isPk;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsPk() {
		return this.isPk;
	}

	public void setIsPk(Boolean isPk) {
		this.isPk = isPk;
	}
}
