<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>选择模式--CodeHelper</title>
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

    <div class="container-fluid">
      <div class="panel">
        <div class="panel-header">
          <h3>选择模式</h3>
        </div>
        <div class="panel-body">
        </div>
        <div class="panel-footer hide">
          <a class="btn btn-warning">上一步</a>
          <a class="btn btn-primary">下一步</a>
        </div>
      </div>
    </div> <!-- /container -->
    <script src="codehelper/js/begin.js"></script>
  </body>
</html>
