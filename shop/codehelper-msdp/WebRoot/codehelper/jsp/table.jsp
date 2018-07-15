<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>选择表--CodeHelper</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<%@ include file="/codehelper/common_codehelper.jsp" %>
    <!-- Le styles -->
    <style>
      .container-fluid{
      	margin-top:60px;
      }
    </style>
  </head>

  <body>
    <%@ include file="/codehelper/top.jsp" %>
    
    <a id="prev" href="codehelper/jsp/origin.jsp" class="navigation-arrow">
        <!--[if !IE]>-->
          <img src="codehelper/images/arrow.svg" alt="" width="28" height="42"> 
        <!--<![endif]-->
        <!--[if lt IE 9]>
          <object src="codehelper/images/arrow-left.svg" classid="image/svg+xml"
                  width="28" height="42" id="leftSVGObject"> 
          </object>
        <![endif]-->
        <!--[if gte IE 9]>
          <object data="codehelper/images/arrow-left.svg" type="image/svg+xml"
                  width="28" height="42" id="leftSVGObject">
          </object>
        <![endif]-->
    </a>
    
    <div class="container-fluid">
        <div class="panel">
            <div class="panel-header">
                <div class="header-title">
                    <h3>选择表</h3>
                </div>
                <div class="header-query">
                    <input placeholder="搜索..." class="search-field" id='keyWord'>
                </div>
            </div>
            <div class="panel-body">
                <table id="grid">
                    
                </table>
            </div>
        </div>
    </div>
    
    <a id="next" href="codehelper/jsp/column.jsp" class="navigation-arrow">
        <!--[if !IE]>-->
          <img src="codehelper/images/arrow.svg" alt="" width="28" height="42"> 
        <!--<![endif]-->
        <!--[if lt IE 9]>
          <object src="codehelper/images/arrow-right.svg" classid="image/svg+xml"
                  width="28" height="42" id="rightSVGObject"> 
          </object>
        <![endif]-->
        <!--[if gte IE 9]>
          <object data="codehelper/images/arrow-right.svg" type="image/svg+xml"
                  width="28" height="42" id="rightSVGObject">
          </object>
        <![endif]-->
    </a>
    
    <script src="codehelper/js/table.js"></script>
  </body>
</html>
