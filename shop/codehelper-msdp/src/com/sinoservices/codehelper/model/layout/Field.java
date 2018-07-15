package com.sinoservices.codehelper.model.layout;

public class Field
{
  private String colName;
  private String comment;
  private String dataType;
  private String isPk;
  private String fieldName;

  public String getColName()
  {
    return this.colName;
  }

  public void setColName(String colName) {
    this.colName = colName;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public String getDataType() {
    return this.dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getFieldName() {
    return this.fieldName;
  }

  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  public String getIsPk() {
    return this.isPk;
  }

  public void setIsPk(String isPk) {
    this.isPk = isPk;
  }
}
