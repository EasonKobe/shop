package com.sinoservices.codehelper.action;

import com.alibaba.fastjson.JSONObject;
import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.util.DBUtils;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ColumnQueryAction extends HttpAction
{
  private Environment environment;

  public ColumnQueryAction(Environment environment)
  {
    this.environment = environment;
  }

  public JSONMessage handle(ActionContext context) throws IOException {
    JSONMessage jsonMessage = new JSONMessage();
    try {
      JSONObject jsonObject = context.getJSONObject();
      String tableName = (String)jsonObject.get("tableName");
      List columnList = DBUtils.getColumnsByTable(this.environment.getConnection(), tableName);
      jsonMessage.setData(columnList.toArray());
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    return jsonMessage;
  }
}
