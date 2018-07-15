package com.test;

import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import java.io.IOException;

public class DemoAction extends HttpAction {
	public String forward(ActionContext context) throws IOException {
		DemoObject obj = (DemoObject) context.parseJSON(DemoObject.class);
		return "/index.jsp";
	}
}
