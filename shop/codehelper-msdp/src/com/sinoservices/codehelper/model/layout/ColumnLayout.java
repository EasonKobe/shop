package com.sinoservices.codehelper.model.layout;

public class ColumnLayout
{
  private String order;
  private String title;
  private String column;
  private String dataType;

  public String getOrder()
  {
    return this.order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getColumn() {
    return this.column;
  }

  public void setColumn(String column) {
    this.column = column;
  }

  public String getDataType() {
    return this.dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }
}
