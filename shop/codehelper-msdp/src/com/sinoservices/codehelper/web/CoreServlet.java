package com.sinoservices.codehelper.web;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CoreServlet extends HttpServlet {
	private static final long serialVersionUID = -3911657886999105450L;
	private static final String PARAM_TYPE = "type";
	private static final String PARAM_JSON = "json";
	private ActionManager actionManager = new ActionManager();

	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action = getAction(req);
		String json = req.getParameter("json");
		String type = req.getParameter("type");
		ActionContext context = new ActionContext(req, resp, action, json);
		HttpAction httpAction = this.actionManager.getAction(action);
		if ("json".equals(type)) {
			handleJSON(resp, context, httpAction);
		} else {
			if ("redirect".equals(type)) {
				String url = httpAction.redirect(context);
				resp.sendRedirect(url);
				return;
			}
			String url = httpAction.forward(context);
			req.getRequestDispatcher(url).forward(req, resp);
		}
	}

	private String getAction(HttpServletRequest req) {
		String path = req.getRequestURI();
		return path.substring(path.lastIndexOf("/") + 1);
	}

	private void handleJSON(HttpServletResponse resp, ActionContext context,
			HttpAction httpAction) throws IOException {
		JSONMessage msg = null;

		msg = httpAction.handle(context);
		if (msg == null) {
			msg = new JSONMessage();
			msg.setResult(Boolean.valueOf(false));
			msg.setMsg("return message not found.");
		}

		String returnMsg = JSON.toJSONString(msg);
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		resp.getWriter().write(returnMsg);
		resp.flushBuffer();
	}
}
