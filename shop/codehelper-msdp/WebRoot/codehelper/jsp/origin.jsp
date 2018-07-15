<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>选择生成方式--CodeHelper</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
	<%@ include file="/codehelper/common_codehelper.jsp" %>
    <!-- Le styles -->
    <style>
      .container-fluid {
        margin-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
  </head>

  <body>
    <%@ include file="/codehelper/top.jsp" %>
    
    <a id="prev" href="codehelper/jsp/begin.jsp" class="navigation-arrow">
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
                <h3>选择生成方式</h3>
            </div>
            <div class="panel-body">
                <div class="row-fluid">
                    <a href='codehelper/jsp/table.jsp?origin=DB' class="box shadow lazy">
                     	 从数据库生成
                    </a>
                    <a id="sqlOrigin" href='codehelper/jsp/sql.jsp?origin=SQL' class="box shadow lazy">
                     	 从SQL语句生成
                    </a>
                </div>
            </div>
        </div>

    </div><!-- /container -->
    
    <!-- Modal -->
    <div id="sqlEditModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="myModalLabel">输入SQL语句</h3>
        </div>
        <div class="modal-body">
            <form>
              <textarea id="sqlEditor" rows="10" class="input-block-level"></textarea>
            </form>
        </div>
        <div class="modal-footer">
            <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            <button id="sqlOK" class="btn btn-primary">确定</button>
        </div>
    </div>
    <script src="codehelper/js/origin.js"></script>
  </body>
</html>
