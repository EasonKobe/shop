<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>生成结果--CodeHelper</title>
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
    
    <a id="prev"  href="codehelper/jsp/template.jsp" class="navigation-arrow">
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
                    <h3>生成结果</h3>
                </div>
                <!-- <div class="header-query">
                    <a id="generate" href="javascript:void(0);" class="btn btn-primary pull-right">生成</a>
                </div> -->
                
            </div>
            <div class="panel-body">
				<!--<form id="template-group" class="form-horizontal">
					 <div class="control-group">
						<label class="control-label" for="temptlate-group-slt">选择模板组</label>
						<div class="controls">
							<select id="temptlate-group-slt">
								<option>SCC</option>
								<option>APP</option>
							</select>
						</div>
					</div> 
				</form>-->
				<div id="grid">
          
                </div>
            </div>
        </div>
    </div>
    
    <script src="codehelper/js/result.js"></script>
  </body>
</html>
