package com.sinoservices.codehelper.action;

import com.sinoservices.codehelper.Environment;
import com.sinoservices.codehelper.util.DBUtils;
import com.sinoservices.codehelper.web.ActionContext;
import com.sinoservices.codehelper.web.HttpAction;
import com.sinoservices.codehelper.web.JSONMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TableQueryAction extends HttpAction
{
  private Environment environment;

  public TableQueryAction(Environment environment)
  {
    this.environment = environment;
  }

  public JSONMessage handle(ActionContext context) throws IOException {
    JSONMessage jsonMessage = new JSONMessage();
    try {
      List tableList = DBUtils.getTablesByConn(this.environment.getConnection());
      jsonMessage.setData(tableList.toArray());
    } catch (SQLException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    return jsonMessage;
  }
}
