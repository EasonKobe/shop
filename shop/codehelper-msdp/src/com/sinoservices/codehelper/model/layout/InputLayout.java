package com.sinoservices.codehelper.model.layout;

import java.util.Arrays;
import java.util.List;

public class InputLayout extends Layout {
	public static final List<String> TYPE = Arrays.asList(new String[] {
			"INPUT", "SELECT" });
	private String id;
	private String name;
	private String type;
	private String label;
	private String bindCol;
	private String dataType;
	private String colspan;
	private String rowIndex;
	private String colIndex;
	private InputExpandLayout expandModel;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InputExpandLayout getExpandModel() {
		return this.expandModel;
	}

	public void setExpandModel(InputExpandLayout expandModel) {
		this.expandModel = expandModel;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBindCol() {
		return this.bindCol;
	}

	public void setBindCol(String bindCol) {
		this.bindCol = bindCol;
	}

	public String getDataType() {
		return this.dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColspan() {
		return this.colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

	public String getRowIndex() {
		return this.rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getColIndex() {
		return this.colIndex;
	}

	public void setColIndex(String colIndex) {
		this.colIndex = colIndex;
	}
}
