package com.sinoservices.codehelper.model;

public class ResourceModel {
	private String mode;
	private String origin;
	private String sql;
	private String selectedTable;
	private ColsModel[] queryCols;
	private ColsModel[] showCols;
	private ColsModel[] editCols;

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

	public ColsModel[] getQueryCols() {
		return this.queryCols;
	}

	public void setQueryCols(ColsModel[] queryCols) {
		this.queryCols = queryCols;
	}

	public ColsModel[] getShowCols() {
		return this.showCols;
	}

	public void setShowCols(ColsModel[] showCols) {
		this.showCols = showCols;
	}

	public ColsModel[] getEditCols() {
		return this.editCols;
	}

	public void setEditCols(ColsModel[] editCols) {
		this.editCols = editCols;
	}
}
