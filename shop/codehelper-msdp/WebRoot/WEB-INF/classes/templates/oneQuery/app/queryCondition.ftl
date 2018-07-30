<#assign queryFields = page.resource.queryFields>
<#--设置Java类型,定义变量-->
<#assign lng = "Long">
<#assign time = "Date">
<#assign d = "Double">
<#assign str = "String">
<#assign gBoolean = true>
<#assign gIgnoreCol = []>
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
	<#list queryFields as field>
		<@compress single_line=true> private <@getType vType = "${field.dataType}"/> ${field.fieldName};</@compress>
	</#list>
</#macro>

<#--生成SetFunction-->
<#macro buildSetFunction>
<#list queryFields as field>
	<@compress single_line=true>public void set${field.fieldName?cap_first}(<@getType vType = "${field.dataType}"/> ${field.fieldName}){</@compress>
		this.${field.fieldName} = ${field.fieldName};
	}
</#list>
</#macro>

<#--生成GetFunction-->
<#macro buildGetFunction>
<#list queryFields as field>
	<@compress single_line=true>public <@getType vType = "${field.dataType}"/> get${field.fieldName?cap_first}(){</@compress>
		return ${field.fieldName};
	}
</#list>
</#macro>
package ${packageName}.query;

import java.util.Date;
import com.sinoservices.framework.core.query.BaseQueryCondition;

public class ${tableName}QueryCondition extends BaseQueryCondition {

	<#--生成Field-->
	<@buildField/>

	<#--生成SetFunction-->
	<@buildSetFunction/>

	<#--生成GetFunction-->
	<@buildGetFunction/>
}
