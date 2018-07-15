package com.sinoservices.codehelper.model.layout;

public class PageLayout {
	private FormLayout formLayout;
	private GridLayout gridLayout;
	private Resource resource;
	private TemplateLayout[] templateLayoutArray;

	public FormLayout getFormLayout() {
		return this.formLayout;
	}

	public void setFormLayout(FormLayout formLayout) {
		this.formLayout = formLayout;
	}

	public GridLayout getGridLayout() {
		return this.gridLayout;
	}

	public void setGridLayout(GridLayout gridLayout) {
		this.gridLayout = gridLayout;
	}

	public TemplateLayout[] getTemplateLayoutArray() {
		return this.templateLayoutArray;
	}

	public void setTemplateLayoutArray(TemplateLayout[] templateLayoutArray) {
		this.templateLayoutArray = templateLayoutArray;
	}

	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
