package com.sinoservices.codehelper.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sinoservices.codehelper.model.layout.ColumnLayout;
import com.sinoservices.codehelper.model.layout.Field;
import com.sinoservices.codehelper.model.layout.FormLayout;
import com.sinoservices.codehelper.model.layout.GridLayout;
import com.sinoservices.codehelper.model.layout.InputLayout;
import com.sinoservices.codehelper.model.layout.Layout;
import com.sinoservices.codehelper.model.layout.PageLayout;
import com.sinoservices.codehelper.model.layout.Resource;
import com.sinoservices.codehelper.model.layout.TemplateLayout;

public class ObjectConversionUtils {
	public static PageLayout jsonToPage(JSONObject object, int colSize) {
		PageLayout pageLayout = new PageLayout();
		for (Iterator localIterator = ((JSONArray) object.get("wrap"))
				.iterator(); localIterator.hasNext();) {
			Object subObject = localIterator.next();
			Layout layout = resolveInfo((JSONObject) subObject, colSize);
			if ((layout instanceof FormLayout))
				pageLayout.setFormLayout((FormLayout) layout);
			else if ((layout instanceof GridLayout)) {
				pageLayout.setGridLayout((GridLayout) layout);
			}
		}
		JSONObject resourceObject = (JSONObject) object.get("resource");
		JSONArray templateArray = (JSONArray) object.get("templateList");
		pageLayout.setResource(getResource(resourceObject));
		pageLayout.setTemplateLayoutArray(getTemplateLayoutArray(templateArray));
		return pageLayout;
	}

	public static Layout resolveInfo(JSONObject wrapObject, int colSize) {
		String type = wrapObject.get("type").toString();
		if ("FORM".equals(type)) {
			FormLayout formLayout = new FormLayout();
			for (Iterator localIterator1 = ((JSONArray) wrapObject.get("wrap"))
					.iterator(); localIterator1.hasNext();) {
				Object subObject = localIterator1.next();
				Layout subLayout = resolveInfo((JSONObject) subObject, colSize);
				if ((subLayout instanceof InputLayout)) {
					formLayout.addInputLayout((InputLayout) subLayout, colSize);
				}
			}
			return formLayout;
		}
		if ("GRID".equals(type)) {
			GridLayout gridLayout = new GridLayout();
			JSONObject infoObject = (JSONObject) wrapObject.get("info");
			Object columnList = new ArrayList();
			for (Iterator localIterator2 = ((JSONArray) infoObject
					.get("bindData")).iterator(); localIterator2.hasNext();) {
				Object bindDataObject = localIterator2.next();
				ColumnLayout columnLayout = new ColumnLayout();
				columnLayout.setColumn(((JSONObject) bindDataObject).get(
						"column").toString());
				columnLayout.setOrder(((JSONObject) bindDataObject)
						.get("order").toString());
				columnLayout.setTitle(((JSONObject) bindDataObject)
						.get("title").toString());
				columnLayout.setDataType(((JSONObject) bindDataObject).get(
						"dataType").toString());
				((List) columnList).add(columnLayout);
			}
			gridLayout.setColumnList((List) columnList);
			return gridLayout;
		}
		if (InputLayout.TYPE.contains(type)) {
			JSONObject infoObject = (JSONObject) wrapObject.get("info");
			InputLayout inputLayout = new InputLayout();
			inputLayout.setType(type);
			inputLayout.setId(infoObject.get("id").toString());
			inputLayout.setName(infoObject.get("name").toString());
			inputLayout.setLabel(infoObject.get("label").toString());
			inputLayout.setBindCol(infoObject.get("bindCol").toString());
			inputLayout.setColIndex(infoObject.get("colIndex").toString());
			inputLayout.setRowIndex(infoObject.get("rowIndex").toString());
			inputLayout.setColspan(infoObject.get("colspan").toString());
			inputLayout.setDataType(infoObject.get("dataType").toString());
			return inputLayout;
		}
		return (Layout) null;
	}

	public static Resource getResource(JSONObject resourceObject) {
		Resource resource = new Resource();
		JSONArray queryCols = (JSONArray) resourceObject.get("queryCols");
		JSONArray showCols = (JSONArray) resourceObject.get("showCols");
		JSONArray editCols = (JSONArray) resourceObject.get("editCols");
		List queryFieldList = new ArrayList();
		for (Iterator localIterator = queryCols.iterator(); localIterator
				.hasNext();) {
			Object queryCol = localIterator.next();
			Field field = new Field();
			field.setColName(((JSONObject) queryCol).get("colName").toString());
			field.setComment(((JSONObject) queryCol).get("comment").toString());
			field.setDataType(((JSONObject) queryCol).get("dataType")
					.toString());
			field.setIsPk(((JSONObject) queryCol).get("isPk").toString());
			field.setFieldName(CharacterUtils
					.replaceUnderlineAndfirstToLower(((JSONObject) queryCol)
							.get("colName").toString()));
			queryFieldList.add(field);
		}

		List showFieldList = new ArrayList();
		for (Iterator localIterator = showCols.iterator(); localIterator.hasNext();) {
			Object showCol = localIterator.next();
			Field field = new Field();
			field.setColName(((JSONObject) showCol).get("colName").toString());
			field.setComment(((JSONObject) showCol).get("comment").toString());
			field.setDataType(((JSONObject) showCol).get("dataType").toString());
			field.setIsPk(((JSONObject) showCol).get("isPk").toString());
			field.setFieldName(CharacterUtils
					.replaceUnderlineAndfirstToLower(((JSONObject) showCol)
							.get("colName").toString()));
			showFieldList.add(field);
		}

		Object editFieldList = new ArrayList();
		for (Iterator localIterator = editCols.iterator(); localIterator.hasNext();) {
			Object editCol = localIterator.next();
			Field field = new Field();
			field.setColName(((JSONObject) editCol).get("colName").toString());
			field.setComment(((JSONObject) editCol).get("comment").toString());
			field.setDataType(((JSONObject) editCol).get("dataType").toString());
			field.setIsPk(((JSONObject) editCol).get("isPk").toString());
			field.setFieldName(CharacterUtils
					.replaceUnderlineAndfirstToLower(((JSONObject) editCol)
							.get("colName").toString()));
			((List) editFieldList).add(field);
		}
		resource.setMode(resourceObject.getString("mode"));
		resource.setOrigin(resourceObject.getString("origin"));
		resource.setSql(resourceObject.getString("sql"));
		resource.setSelectedTable(resourceObject.getString("selectedTable"));

		resource.setQueryFields((Field[]) queryFieldList
				.toArray(new Field[queryFieldList.size()]));
		resource.setShowFields((Field[]) showFieldList
				.toArray(new Field[showFieldList.size()]));
		resource.setEditFields((Field[]) ((List) editFieldList)
				.toArray(new Field[((List) editFieldList).size()]));
		return (Resource) resource;
	}

	public static TemplateLayout[] getTemplateLayoutArray(
			JSONArray templateArray) {
		TemplateLayout[] templateLayoutArray = null;
		List templateLayoutList = new ArrayList();
		for (Iterator localIterator = templateArray.iterator(); localIterator
				.hasNext();) {
			Object template = localIterator.next();
			TemplateLayout templateLayout = new TemplateLayout();
			templateLayout.setDescript(((JSONObject) template).get("desc")
					.toString());
			templateLayout.setName(((JSONObject) template).get("name")
					.toString());
			templateLayout.setSaveDir(((JSONObject) template).get("target")
					.toString());
			templateLayout.setTemplateDir(((JSONObject) template).get("path")
					.toString());

			templateLayoutList.add(templateLayout);
		}
		templateLayoutArray = (TemplateLayout[]) templateLayoutList
				.toArray(new TemplateLayout[templateLayoutList.size()]);
		return templateLayoutArray;
	}
}
