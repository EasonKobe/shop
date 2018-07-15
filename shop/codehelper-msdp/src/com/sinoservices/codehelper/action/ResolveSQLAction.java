package com.sinoservices.codehelper.action;

import com.alibaba.fastjson.JSONObject;
import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.util.DBUtils;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import java.io.IOException;
import java.util.List;

public class ResolveSQLAction extends HttpAction
{
  private Environment environment;

  public ResolveSQLAction(Environment environment)
  {
    this.environment = environment;
  }

  public JSONMessage handle(ActionContext context) throws IOException {
    JSONMessage jsonMessage = new JSONMessage();
    JSONObject jsonObject = context.getJSONObject();
    String sql = (String)jsonObject.get("sql");
    List columnList = DBUtils.getColumnsBySQL(this.environment.getConnection(), sql);
    jsonMessage.setData(columnList.toArray());
    return jsonMessage;
  }
}
