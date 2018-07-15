<#assign showFields = page.resource.showFields>
<#assign selectedTable = page.resource.selectedTable>

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
	<#list showFields as field>
		<@compress single_line=true> public <@getType vType = "${field.dataType}"/> ${field.fieldName};</@compress>
	</#list>
</#macro>
<#--生成SetFunction-->
<#macro buildSetFunction>
<#list showFields as field>
	/**
	 * Set ${field.comment}
	 */
	<@compress single_line=true>private void set${field.fieldName?cap_first}(<@getType vType = "${field.dataType}"/> ${field.fieldName}){</@compress>
		this.${field.fieldName} = ${field.fieldName};
		addValidField("${field.fieldName}");
	}
</#list>
</#macro>

<#--生成GetFunction-->
<#macro buildGetFunction>
<#list showFields as field>
	/**
	 * Get ${field.comment}
 	 */
 	@Column(name = "${field.colName}")
<#if field.isPk == "true">
	@GenericGenerator(name = "CommonGenerator", strategy = "com.sinoservices.framework.core.support.id.CommonIdGenerator")
	@Id 
	@GeneratedValue(generator="CommonGenerator")
</#if>
	<@compress single_line=true>public <@getType vType = "${field.dataType}"/> get${field.fieldName?cap_first}(){</@compress>
		return ${field.fieldName};
	}
</#list>
</#macro>
package ${packageName}.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import com.sinoservices.framework.core.model.BaseModel;
import com.sinoservices.framework.core.model.OperationLog;
import java.util.Date;

/**
 */
@Entity
@Table(name = "${selectedTable}")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ${tableName}Model extends BaseModel implements OperationLog {

<#--生成Field-->
<@buildField/>

<#--生成SetFunction-->
<@buildSetFunction/>

<#--生成GetFunction-->
<@buildGetFunction/>


}
