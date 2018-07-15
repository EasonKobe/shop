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
          <h3>填写配置</h3>
           <div class="header-query">
           		<a id="save" class="btn btn-primary pull-right">保存</a>
           </div>
        </div>
        <div class="panel-body">
        	<!-- 作者信息 -->
			<div class="accordion-group">
              <div class="accordion-heading">
                  User Setting
              </div>
              <div id="collapseOne" class="accordion-body in collapse" style="height: auto;">
	              <form class="form-horizontal" id="userForm">
					  <div class="control-group">
					    <label class="control-label" for="inputEmail"><font color="red">*</font>Author</label>
					    <div class="controls">
					      <input type="text" name="author" data-required="不能为空">
					    </div>
					  </div>
					  <div class="control-group">
					    <label class="control-label" for="inputPassword"><font color="red">*</font>Email</label>
					    <div class="controls">
					      <input type="text" id="inputPassword"  name="email" data-required="不能为空">
					    </div>
					  </div>
				  </form>
              </div>
            </div>
            
            <!-- 数据库信息 -->
            <div class="accordion-group">
              <div class="accordion-heading">
                  DB Setting
              </div>
              <div id="collapseOne" class="accordion-body in collapse" style="height: auto;">
	              <form class="form-horizontal" id="DBForm">
					  <div class="control-group">
					    <label class="control-label" for="inputEmail"><font color="red">*</font>DriverClass</label>
					    <div class="controls">
					      <input type="text" id="inputEmail" name="jdbc.driverClassName" data-required="不能为空">
					    </div>
					  </div>
					  <div class="control-group">
					    <label class="control-label" for="inputPassword"><font color="red">*</font>URL</label>
					    <div class="controls">
					      <input type="text" id="inputPassword"  name="jdbc.url" data-required="不能为空">
					    </div>
					  </div>
					   <div class="control-group">
					    <label class="control-label" for="inputPassword"><font color="red">*</font>User</label>
					    <div class="controls">
					      <input type="text" id="inputPassword"  name="jdbc.username" data-required="不能为空">
					    </div>
					  </div>
					   <div class="control-group">
					    <label class="control-label" for="inputPassword"><font color="red">*</font>Password</label>
					    <div class="controls">
					      <input type="text" id="inputPassword"  name="jdbc.password" data-required="不能为空">
					    </div>
					  </div>
				  </form>
              </div>
            </div>
            
            <!-- 输出信息 -->
            <div class="accordion-group">
              <div class="accordion-heading">
                  Out Setting
              </div>
              <div id="collapseOne" class="accordion-body in collapse" style="height: auto;">
	              <form class="form-horizontal" id="outForm">
					  <div class="control-group">
					    <label class="control-label" for="inputEmail"><font color="red">*</font>Project Path</label>
					    <div class="controls">
					      <input type="text" id="inputEmail"  name="project.path" data-required="不能为空">
					    </div>
					  </div>
					  <div class="control-group">
					    <label class="control-label" for="inputPassword"><font color="red">*</font>Package Name</label>
					    <div class="controls">
					      <input type="text" id="inputPassword"  name="package.name" data-required="不能为空">
					    </div>
					  </div>
					  <div class="control-group">
					    <label class="control-label" for="inputPassword"><font color="red">*</font>Module Name</label>
					    <div class="controls">
					      <input type="text" id="inputPassword"  name="module.name" data-required="不能为空">
					    </div>
					  </div>
					  <div class="control-group">
					    <label class="control-label" for="inputPassword"><font color="red">*</font>colSize</label>
					    <div class="controls">
					      <input type="text" id="inputPassword"  name="colSize" data-required="不能为空">
					    </div>
					  </div>
				  </form>
              </div>
            </div>
        </div>
      </div>
    </div> <!-- /container -->
    <script src="codehelper/js/config.js"></script>
  </body>
</html>
