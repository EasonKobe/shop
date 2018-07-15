package com.sinoservices.codehelper.model.layout;

import java.util.ArrayList;
import java.util.List;

public class FormLayout extends Layout {
	private int rowSize;
	private int colSize;
	private List<List<InputLayout>> rowList = new ArrayList();

	public FormLayout() {
	}

	public void addInputLayout(InputLayout inputModel, int colSize) {
		if (this.rowList.isEmpty()) {
			List inputList = new ArrayList();
			this.rowList.add(inputList);
			inputList.add(inputModel);
			return;
		}
		List inputList = (List) this.rowList.get(this.rowList.size() - 1);
		if (((List) this.rowList.get(this.rowList.size() - 1)).size() % colSize == 0) {
			inputList = new ArrayList();
			this.rowList.add(inputList);
		}
		inputList.add(inputModel);
	}

	public FormLayout(int colSize) {
		this.colSize = colSize;
	}

	public List<List<InputLayout>> getRowList() {
		return this.rowList;
	}

	public void setRowList(List<List<InputLayout>> rowList) {
		this.rowList = rowList;
	}

	public int getRowSize() {
		return this.rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getColSize() {
		return this.colSize;
	}

	public void setColSize(int colSize) {
		this.colSize = colSize;
	}
}
