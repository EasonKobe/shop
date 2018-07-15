package com.sinoservices.codehelper.action;

import com.sinoservices.codehelper.template.TemplateFactory;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import java.io.IOException;
import java.util.List;

public class ModeQueryAction extends HttpAction
{
  private TemplateFactory templateFactory = null;

  public ModeQueryAction(TemplateFactory templateFactory) {
    this.templateFactory = templateFactory;
  }

  public JSONMessage handle(ActionContext context) throws IOException {
    JSONMessage jsonMessage = new JSONMessage();
    List modeList = this.templateFactory.getModeList();
    jsonMessage.setData(modeList.toArray());
    return jsonMessage;
  }
}
