package com.sinoservices.codehelper.template;

import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.util.TemplateUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public final class TemplateFactory {
	private Document document;
	private List<Mode> modes = null;
	private Map<String, List<Group>> groupMap;

	public TemplateFactory() {
		load();
	}

	private void load() {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("templates.xml");
		SAXReader saxReader = new SAXReader();
		try {
			this.document = saxReader.read(in);
			this.modes = new ArrayList<Mode>();
			this.groupMap = new HashMap<String, List<Group>>();
			TemplateUtils.parseTemplate(this.document, this.modes,
					this.groupMap);
		} catch (DocumentException e) {
			e.printStackTrace(System.err);
		}
	}

	public List<Mode> getModeList() {
		return this.modes;
	}

	public List<Group> compileGroup(String modeId, Map<String, String> params,
			Environment environment) {
		List<Group> groups = (List<Group>) this.groupMap.get(modeId);

		List<Group> targetGroups = new ArrayList<Group>();
		for (Group group : groups) {
			Group targetGroup = new Group();
			targetGroup.setName(group.getName());
			List<Template> templates = group.getTemplateList();
			List<Template> targetTemplates = new ArrayList<Template>();
			for (Template template : templates) {
				Template target = template.cloneTemplate();
				target.setTarget(TemplateUtils.compile(target.getTarget(),
						params, environment));
				targetTemplates.add(target);
			}
			targetGroup.setTemplateList(targetTemplates);
			targetGroups.add(targetGroup);
		}
		return targetGroups;
	}
}
