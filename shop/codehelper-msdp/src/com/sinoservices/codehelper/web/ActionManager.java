package com.sinoservices.codehelper.web;

import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.action.ColumnQueryAction;
import com.sinoservices.codehelper.action.ConfigQueryAction;
import com.sinoservices.codehelper.action.ConfigSaveAction;
import com.sinoservices.codehelper.action.GenerateFileAction;
import com.sinoservices.codehelper.action.ModeQueryAction;
import com.sinoservices.codehelper.action.ResolveSQLAction;
import com.sinoservices.codehelper.action.TableQueryAction;
import com.sinoservices.codehelper.action.TemplateQueryAction;
import com.sinoservices.codehelper.template.TemplateFactory;
import java.util.HashMap;
import java.util.Map;

public class ActionManager {
	private Map<String, HttpAction> actionMap = new HashMap();

	private Environment environment = null;
	private TemplateFactory templateFactory;

	public ActionManager() {
		init();
		regisiter();
	}

	private void init() {
		this.environment = new Environment();
		this.templateFactory = new TemplateFactory();
	}

	private void regisiter() {
		this.actionMap.put("modeQueryAction", new ModeQueryAction(
				this.templateFactory));

		this.actionMap.put("tableQueryAction", new TableQueryAction(
				this.environment));

		this.actionMap.put("columnQueryAction", new ColumnQueryAction(
				this.environment));

		this.actionMap.put("templateQueryAction", new TemplateQueryAction(
				this.templateFactory, this.environment));

		this.actionMap.put("generateFileAction", new GenerateFileAction(
				this.environment));

		this.actionMap.put("resolveSQLAction", new ResolveSQLAction(
				this.environment));

		this.actionMap.put("configQueryAction", new ConfigQueryAction(
				this.environment));

		this.actionMap.put("configSaveAction", new ConfigSaveAction(
				this.environment));
	}

	public HttpAction getAction(String action) {
		return (HttpAction) this.actionMap.get(action);
	}
}
