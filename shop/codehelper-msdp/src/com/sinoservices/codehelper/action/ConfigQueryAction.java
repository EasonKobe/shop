package com.sinoservices.codehelper.action;

import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import java.io.IOException;

public class ConfigQueryAction extends HttpAction
{
  private Environment environment;

  public ConfigQueryAction(Environment environment)
  {
    this.environment = environment;
  }
  public JSONMessage handle(ActionContext context) throws IOException {
    JSONMessage jsonMessage = new JSONMessage();
    jsonMessage.setData(this.environment.getProperties());
    return jsonMessage;
  }
}
