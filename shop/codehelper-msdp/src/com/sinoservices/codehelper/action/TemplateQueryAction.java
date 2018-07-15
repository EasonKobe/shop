package com.sinoservices.codehelper.action;

import com.alibaba.fastjson.JSONObject;
import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.template.TemplateFactory;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateQueryAction extends HttpAction
{
  private TemplateFactory templateFactory = null;

  private Environment environment = null;

  public TemplateQueryAction(TemplateFactory templateFactory, Environment environment) {
    this.templateFactory = templateFactory;
    this.environment = environment;
  }

  public JSONMessage handle(ActionContext context) throws IOException {
    JSONMessage jsonMessage = new JSONMessage();
    JSONObject object = context.getJSONObject();
    String modeId = object.getString("mode");
    String tableName = object.getString("selectedTable");
    Map params = new HashMap();
    params.put("tableName", tableName);
    List groupList = this.templateFactory.compileGroup(modeId, params, this.environment);
    jsonMessage.setData(groupList.toArray());
    return jsonMessage;
  }
}
