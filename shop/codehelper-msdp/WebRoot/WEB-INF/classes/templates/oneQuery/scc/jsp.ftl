<#escape x as x?html> 
<#assign form = page.formLayout>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/pui-tag" prefix="slt"%>

<div class="widget-box">
	<div class="widget-header">
		<span class="widget-title"><i class="icon-search"></i>查询条件</span> 
		<span class="widget-toolbar"><a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a></span>
	</div>
	<div class="widget-body">
		<div class="widget-form">
			<div class="form-toolbar">
				<a class="btn btn-info" href="javascript:${tableName}Query.query();">
					<i class="icon-search"></i>查询
				</a> 
				<a class="btn btn-danger" href="javascript:${tableName}Query.reset();">
					<i class="icon-repeat"></i>重置
				</a>
			</div>
	 		<form class="form-horizontal" id="${tableName}QueryForm">
			<#list form.rowList as inputList>
	 			<div class="row-fluid">
	 				<#list inputList as input>
	 				 	<div class="span3 control-group full">
							<label class="control-label" for="">${input.label}</label>
							<div class="controls txt">
								<input type="text" name="${input.name}" >
							</div>
						</div>
	 				</#list>
	 			</div>
	 		</#list>
	 		</form>
	 	</div>
	 </div>	
</div>
<div class="widget-box">
	<div class="widget-header">
		<span class="widget-title"><i class="icon-list"></i>查询结果列表</span> <span class="widget-toolbar"><a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a></span>
	</div>
	<div class="widget-body">
		<div class="widget-grid">
			<div class="grid-toolbar"></div>
			<table class="mmg">
				<tr>
					<th rowspan="" colspan=""></th>
				</tr>
			</table>
			<div id="pg" style="text-align: right;"></div>
		</div>
	</div>
</div>
</#escape> 	
<script src="${moduleName}/js/${tableName?uncap_first}Query.js" charset="utf-8"></script>		
 	



