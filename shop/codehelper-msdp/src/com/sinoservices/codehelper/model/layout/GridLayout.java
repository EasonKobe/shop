package com.sinoservices.codehelper.model.layout;

import java.util.List;

public class GridLayout extends Layout {
	private List<ColumnLayout> columnList;

	public List<ColumnLayout> getColumnList() {
		return this.columnList;
	}

	public void setColumnList(List<ColumnLayout> columnList) {
		this.columnList = columnList;
	}
}
