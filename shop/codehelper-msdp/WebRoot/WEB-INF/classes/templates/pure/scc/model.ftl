<#ftl strip_text = false>
<#--设置Java类型,定义变量-->
<#assign lng = "Long">
<#assign time = "Date">
<#assign d = "Double">
<#assign str = "String">
<#assign gBoolean = true>
<#assign gIgnoreCol = []>
<#assign editFields = page.resource.editFields>
<#--根据数据库类型返回java类型-->
<#macro getType vType> 
 <#if vType == "INTEGER"> 
${lng}
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
<#--生成Field-->
<#macro  buildField>
	<#list editFields as field>
		<@compress single_line=true> private <@getType vType = "${field.dataType}"/> ${field.fieldName};</@compress>
	</#list>
</#macro>

<#--生成SetFunction-->
<#macro buildSetFunction>
<#list editFields as field>
	<@compress single_line=true>public void set${field.fieldName?cap_first}(<@getType vType = "${field.dataType}"/> ${field.fieldName}){</@compress>
		this.${field.fieldName} = ${field.fieldName};
	}
</#list>
</#macro>

<#--生成GetFunction-->
<#macro buildGetFunction>
<#list editFields as field>
	<@compress single_line=true>public <@getType vType = "${field.dataType}"/> get${field.fieldName?cap_first}(){</@compress>
		return ${field.fieldName};
	}
</#list>
</#macro>
package ${packageName}.model;

import java.util.Date;
import com.sinoservices.pureui.view.PagingCondition;

public class ${tableName}Model extends PagingCondition{

	private static final long serialVersionUID = 1L;

	<#--生成Field-->
	<@buildField/>

	<#--生成SetFunction-->
	<@buildSetFunction/>

	<#--生成GetFunction-->
	<@buildGetFunction/>
}
