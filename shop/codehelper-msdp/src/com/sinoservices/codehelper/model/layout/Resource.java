package com.sinoservices.codehelper.model.layout;

public class Resource extends Layout {
	private String mode;
	private String origin;
	private String sql;
	private String selectedTable;
	private Field[] queryFields;
	private Field[] showFields;
	private Field[] editFields;

	public String getMode() {
		return this.mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getOrigin() {
		return this.origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSql() {
		return this.sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getSelectedTable() {
		return this.selectedTable;
	}

	public void setSelectedTable(String selectedTable) {
		this.selectedTable = selectedTable;
	}

	public Field[] getQueryFields() {
		return this.queryFields;
	}

	public void setQueryFields(Field[] queryFields) {
		this.queryFields = queryFields;
	}

	public Field[] getShowFields() {
		return this.showFields;
	}

	public void setShowFields(Field[] showFields) {
		this.showFields = showFields;
	}

	public Field[] getEditFields() {
		return this.editFields;
	}

	public void setEditFields(Field[] editFields) {
		this.editFields = editFields;
	}
}
