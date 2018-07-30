<#--设置变量-->
<#assign queryFields = page.resource.queryFields>
<#assign showFields = page.resource.showFields>
<#--设置AS类型-->
<#assign bool = "Boolean">
<#assign all = "Object">
<#assign int = "int">
<#assign lng = "Long">
<#assign time = "Date">
<#assign d = "Number">
<#assign str = "String">
<#assign gBoolean = true>
<#assign gIgnoreCol = ["CREATE_USER","LAST_UPDATE_USER","LAST_UPDATE_TIME","RECORD_VERSION","CREATE_USER_NAME","LAST_UPDATE_USER_NAME","SYSTEM_STATUS"]>
<#--根据数据库类型返回AS类型-->
<#macro getType vType> 
 <#if vType == "BOOLEAN"> 
${all}
 <#elseif vType == "INTEGER">
${all}
 <#elseif vType == "LONG">
${lng}
 <#elseif vType == "DATE">
${time}
 <#elseif vType == "FLOAT">
${d}
<#elseif vType == "DOUBLE">
${all}
 <#else> 
${str}
 </#if>	
</#macro>
<#--属性显示的类型-->
<#macro getDisType vType> 
 <#if vType == "BOOLEAN"> 
${bool}
 <#elseif vType == "INTEGER">
${int}
 <#elseif vType == "LONG">
${lng}
 <#elseif vType == "DATE">
${time}
 <#elseif vType == "FLOAT">
${d}
<#elseif vType == "DOUBLE">
${d}
 <#else> 
${str}
 </#if>	
</#macro> 
<#--设置dataType的值-->
<#macro getDataType vType> 
 <#if vType == "BOOLEAN"> 
${boolean}
 <#elseif vType == "INTEGER">
${int}
<#elseif vType == "DOUBLE">
${double}
 <#elseif vType == "DATE">
${date}
<#elseif vType == "VARCHAR2">
${string}
 <#else> 
${string}
 </#if>	
</#macro> 

<#--生成Field-->
<#macro buildField> 
<#assign X = 12>
<#assign Y = 3>
<#assign labelW = 100>
<#assign fieldW = 125>
<#assign H = 20>
<#assign index = 0>

<#list queryFields as field>
<#if index%4 == 0 && index != 0>
	<#assign Y = Y + H + 3>
	<#assign X = 12>
</#if>
		<f:Label id="lbl_${field.fieldName}" width="100" x="${X}" y="${Y}" text="${field.comment}"/>
<#assign X= X + 3 + labelW>
<#assign type = field.dataType>

<#assign type><@getType vType="${field.dataType}" /></#assign>

	<#if type == "DATE">
		<f:DateField id="${field.fieldName}_query" x="${X}"  y="${Y}"  width="125" operator="=" dataType="<@getType vType = "${field.dataType}"/>" fieldName="${field.fieldName}"/>
	<#elseif type == "DOUBLE">
		<f:NumberField id="${field.fieldName}_query"  x="${X}"   y="${Y}" width="125" operator="=" dataType="<@getType vType = "${field.dataType}"/>" fieldName="${field.fieldName}"/>
	<#elseif type == "INTEGER">	
		<f:TextInput id="${field.fieldName}_query"   x="${X}"  y="${Y}"  width="125" operator="=" dataType="<@getType vType = "${field.dataType}"/>" fieldName="${field.fieldName}"/>
	<#else>	
		<f:TextInput id="${field.fieldName}_query"   x="${X}"  y="${Y}"  width="125" operator="=" dataType="<@getType vType = "${field.dataType}"/>" fieldName="${field.fieldName}"/>
	</#if>	
<#assign X= X + fieldW + 15>
<#assign index= index + 1>	
</#list>
</#macro> 

<#--生成grid-->
<#macro buildGrid> 
	<g:PagingGrid  width="100%"  id="pagingGrid" itemDoubleClick="action.pagingGrid_dblClickItemHandler()" >
				<g:store>
					<d:Store queryType="${tableName}Model" recordClass="${packageName}.model.${tableName}Model" saveService="${tableName}Manager.saveAll"/>
				</g:store>
				<g:editorWindow>
					<view:GridEditorWindow dataCollector="{editorDataCollector}" renderTo="{childrenPanel}" />
				</g:editorWindow>
				<g:columns>
					<#list showFields as field>
						<g:DataGridColumn id="dataGridColumn_${field.fieldName}" headerText="${field.comment}" dataField="${field.fieldName}" width="100"/>
					</#list>			
				</g:columns>
	</g:PagingGrid>
</#macro> 

<#--生成弹出信息-->
<#macro buildPopInfo>
	<#assign X=12>
	<#assign Y=26>
	<#assign labelW=100>
	<#assign fieldW=125>
	<#assign H=20>
	<#assign index=0>
	<#list showFields as field>
		<#if index%3 == 0 && index != 0>
			<#assign Y = Y + H + 3>
			<#assign X = 12>
		</#if>	
		<f:Label id="lbl_editor_${field.fieldName}" width="100" x="${X}" y="${Y}" text="${field.comment}"/>
		<#assign X= X + 3 + labelW>
			<#if field.dataType == "DATE">
				<f:DateField id="${field.fieldName}_editor" x="${X}"  y="${Y}"  width="125" />
			<#elseif field.dataType == "DOUBLE">
				<f:NumberField id="${field.fieldName}_editor"  x="${X}"   y="${Y}" width="125"/>
			<#elseif field.dataType == "INTEGER">	
				<f:TextInput id="${field.fieldName}_editor"   x="${X}"  y="${Y}"  width="125"/>
			<#else>	
				<f:TextInput id="${field.fieldName}_editor"   x="${X}"  y="${Y}"  width="125"/>
			</#if>	
		<#assign X= X + fieldW + 15>
		<#assign index= index + 1>	
	</#list>
</#macro> 
<?xml version="1.0" encoding="utf-8"?>
<view:TabModule xmlns:mx="http://www.adobe.com/2006/mxml" layout="absolute" width="100%" height="100%"
 xmlns:view="com.sinoservices.core.view.*" 
 xmlns:f="com.sinoservices.core.controls.form.*" 
 xmlns:g="com.sinoservices.core.controls.grid.*" 
 xmlns:d="com.sinoservices.core.data.*" 
 xmlns:action="${packageName}.action.*"
 xmlns:tb="com.sinoservices.core.controls.toolbar.*"
 >
<f:DataCollector id="queryDataCollector" container="{this}" suffix="_query" modelClass="${packageName}.model.${tableName}Model" />
<f:DataCollector id="editorDataCollector" container="{this}" suffix="_editor" modelClass="${packageName}.model.${tableName}Model" />
<action:${tableName}ListAction id="action" view="{this}" queryDataCollector="{queryDataCollector}" pagingGrid="{this.pagingGrid}" />
	<mx:ViewStack  id="${tableName}Info" width="100%" height="100%" creationPolicy="all">

		<mx:VBox width="100%" height="100%">
		<tb:ToolBar id="toolbar" x="0" y="0" width="100%" height="25">
			<tb:CommonButton id="btn_query" label="查询" menuItemClick="action.queryHandler();" authority=""/>
			<tb:CommonButton id="btn_reset" label="重置" menuItemClick="action.resetHandler();" authority=""/>
			<tb:CommonButton id="btn_add" label="新增" menuItemClick="action.addHandler();" authority=""/>
			<tb:CommonButton id="btn_copy" label="复制" menuItemClick="action.duplicateHandler();" authority=""/>
			<tb:CommonButton id="btn_edit" label="编辑" menuItemClick="action.editHandler();" authority=""/>
			<tb:CommonButton id="btn_remove" label="删除" menuItemClick="action.removeHandler();" authority=""/>
		</tb:ToolBar>
		<mx:Canvas>
			<#--生成查询表单内容-->
			<@buildField/>
			
		</mx:Canvas>
			<#--生成grid-->
			<@buildGrid/>
		</mx:VBox>
	
	
	<mx:Canvas  id="childrenPanel">
		<#--生成弹出窗内容-->
		<@buildPopInfo/>
		
	</mx:Canvas>
	</mx:ViewStack>
	
</view:TabModule>