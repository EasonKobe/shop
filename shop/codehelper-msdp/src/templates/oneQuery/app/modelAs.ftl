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

<#--生成Field-->
<#macro  buildField>
	<#list showFields as field>
		<@compress single_line=true> public var ${field.fieldName} : <@getType vType = "${field.dataType}"/> ;</@compress>
	</#list>
</#macro>

package ${packageName}.model
{

import com.sinoservices.core.vo.Model;

	/**
	 * <p>Description: AS类说明 </p>
	 * @author 
	 * @version 1.00
	 * <pre>
	 * </pre>
	 */
[Bindable]
[RemoteClass(alias="${packageName}.model.${tableName}Model")]
public class ${tableName}Model extends Model 
{
	<#--生成Field-->
	<@buildField/>
}
}
