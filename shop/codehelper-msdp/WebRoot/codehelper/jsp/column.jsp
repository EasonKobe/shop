<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>选择列--CodeHelper</title>
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
    
    <a id="prev" href="codehelper/jsp/table.jsp" class="navigation-arrow">
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
          <h3>选择列</h3>
        </div>
        <div class="panel-body">
          <div id="mmGridContainer" class="">
            <div class="list-wrap">
              <div class="list-header">编辑列</div>
              <table id="firstGrid">
                
              </table>
            </div>
            <div class="list-wrap">
              <div class="list-header">显示列</div>
              <table id="secondGrid">
                
              </table>
            </div>
          </div>
          
        </div>
      </div>
    </div> <!-- /container -->
    
    <a id="next"  class="navigation-arrow">
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
    
    <script src="codehelper/js/column.js"></script>
  </body>
</html>
