package com.sinoservices.codehelper.util;

import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.template.Group;
import com.sinoservices.codehelper.template.Mode;
import com.sinoservices.codehelper.template.Template;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.Element;

public class TemplateUtils {
	public static void parseTemplate(Document document, List<Mode> modeList,
			Map<String, List<Group>> groupMap) {
		Element rootEl = document.getRootElement();
		Iterator modes = rootEl.elementIterator("mode");
		while (modes.hasNext()) {
			Mode mode = new Mode();

			Element modeEl = (Element) modes.next();
			mode.setId(modeEl.attributeValue("id"));
			mode.setIsLayout(modeEl.attributeValue("isLayout"));
			mode.setName(modeEl.attributeValue("name"));
			modeList.add(mode);

			Iterator groups = modeEl.elementIterator("group");
			List groupList = new ArrayList();
			while (groups.hasNext()) {
				Element groupEl = (Element) groups.next();
				Group group = new Group();
				group.setName(groupEl.attributeValue("name"));
				Iterator templates = groupEl.elementIterator("template");
				List templateList = new ArrayList();
				while (templates.hasNext()) {
					Element templateEl = (Element) templates.next();
					Template template = new Template();
					template.setDesc(templateEl.elementText("desc"));
					template.setName(templateEl.elementText("name"));
					template.setPath(templateEl.elementText("path"));
					template.setTarget(templateEl.elementText("target"));
					templateList.add(template);
				}
				group.setTemplateList(templateList);
				groupList.add(group);
			}
			groupMap.put(mode.getId(), groupList);
		}
	}

	public static String compile(String str, Map<String, String> params,
			Environment environment) {
		str = str.replace("${project.path}", environment.getProjectPath());
		str = str.replace("${package.name}", environment.getPackageName());
		str = str.replace("${module.name}", environment.getMoudleName());
		if ((params.get("tableName") == null)
				|| ("".equals(params.get("tableName"))))
			str = str.replace("${table.name}", "");
		else {
			str = str.replace("${table.name}", CharacterUtils
					.replaceUnderlineAndfirstToUpper((String) params
							.get("tableName")));
		}
		str = str.replace("${table.name?uncap_first}", CharacterUtils
				.replaceUnderlineAndfirstToLower((String) params
						.get("tableName")));
		return str;
	}
}
