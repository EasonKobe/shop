<#escape x as x?html> 
<#assign form = page.formLayout>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/pui-tag" prefix="slt"%>

<div class="widget-box">
	<div class="widget-header">
		<span class="widget-title"><i class="icon-eidt"></i>编辑</span> 
		<span class="widget-toolbar"><a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a></span>
	</div>
	<div class="widget-body">
		<div class="widget-form">
			<div class="form-toolbar">
				<a class="btn btn-info" href="javascript:${tableName}Edit.save();">
					<i class="icon-search"></i>保存
				</a> 
				<a class="btn btn-danger" href="javascript:${tableName}Edit.reset();">
					<i class="icon-repeat"></i>重置
				</a>
			</div>
	 		<form class="form-horizontal" id="${tableName}EditForm">
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
<script src="${moduleName}/js/${tableName?uncap_first}Edit.js" charset="utf-8"></script>		
</#escape> 	
