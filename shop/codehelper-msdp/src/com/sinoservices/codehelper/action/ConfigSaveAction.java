package com.sinoservices.codehelper.action;

import com.alibaba.fastjson.JSONObject;
import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import java.io.IOException;

public class ConfigSaveAction extends HttpAction
{
  private Environment environment;

  public ConfigSaveAction(Environment environment)
  {
    this.environment = environment;
  }
  public JSONMessage handle(ActionContext context) throws IOException {
    JSONMessage jsonMessage = new JSONMessage();

    JSONObject jsonObject = context.getJSONObject();
    this.environment.store(jsonObject);
    jsonMessage.setResult(Boolean.valueOf(true));
    return jsonMessage;
  }
}
