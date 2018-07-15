<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/codehelper/common_head.jsp" %>
<%
	response.setHeader("Pragma","No-cache");
	response.setHeader("Cache-Control","no-cache");
	response.setDateHeader("Expires", 0);
%>
<!-- 商标 -->
<link rel="shortcut icon" href="codehelper/images/layout.png" />
<!-- 主要样式 -->
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/libs/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/libs/bootstrap/css/bootstrap-responsive.css" />
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/bootstrap/js/bootstrap.js"></script>
<!-- ie fall back -->
<!--[if (gte IE 5.5)&(lte IE 8)]>
	<script src="<%=basePath %>codehelper/libs/selectivizr/selectivizr.js"></script>
<![endif]-->
<!--[if lt IE 9]>
	<script src="<%=basePath %>codehelper/libs/html5shiv/dist/html5shiv.js"></script>
<![endif]-->
<!-- 引用jqueryUI库 -->
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/libs/jqueryUI/css/jquery-ui-1.10.4.css" />
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jqueryUI/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jqueryUI/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jqueryUI/js/jquery.ui.mouse.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jqueryUI/js/jquery.ui.draggable.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jqueryUI/js/jquery.ui.droppable.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jqueryUI/js/jquery.ui.sortable.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/jqueryUI/js/jquery.ui.resizable.js"></script>
<!-- 页面样式 -->
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/style/styles.css" />
<!-- HTML5检测 -->
<script type="text/javascript" src="<%=basePath %>codehelper/libs/modernizr/modernizr.js"></script>
<!-- mmGrid -->
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/libs/mmGrid/mmGrid.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/libs/mmGrid/mmPaginator.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/libs/mmGrid/theme/bootstrap/mmGrid-bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>codehelper/libs/mmGrid/theme/bootstrap/mmPaginator-bootstrap.css" />
<script type="text/javascript" src="<%=basePath %>codehelper/libs/mmGrid/mmGrid.js"></script>
<script type="text/javascript" src="<%=basePath %>codehelper/libs/mmGrid/mmPaginator.js"></script>
<!-- 自定义 帮助 js -->
<script type="text/javascript" src="<%=basePath %>codehelper/js/util.js"></script>
<style>
	.navbar-fixed-bottom{
		z-index:999;
	}
</style>
<script src="<%=basePath %>codehelper/libs/svgweb/src/svg.js" data-path="<%=basePath %>codehelper/libs/svgweb/src/"></script>
<!-- 校验js -->
<script type="text/javascript" src="<%=basePath %>codehelper/libs/valid/js/jquery.validate.js"></script>
