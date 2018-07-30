<#ftl strip_text = false>
<#--设置Java类型,定义变量-->
<#assign lng = "Long">
<#assign time = "Date">
<#assign d = "Double">
<#assign str = "String">
<#assign i = "Integer">
<#assign dec = "BigDecimal">
<#assign gBoolean = true>
<#assign gIgnoreCol = []>
<#assign editFields = page.resource.editFields>
<#assign selectedTable = page.resource.selectedTable>
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
 <#elseif vType == "INT">
${i}
 <#elseif vType == "DATETIME">
${time}
 <#elseif vType == "DECIMAL">
${dec}
 <#else> 
${str}
 </#if>	
</#macro> 
<#--生成Field-->
<#macro  buildField>
<#list editFields as field>
 	/**  
 	 *${field.comment} 
 	 */
	@Column(name = "${field.colName?upper_case}")
	<@compress single_line=true>private <@getType vType = "${field.dataType}"/> ${field.fieldName};</@compress>

</#list>
</#macro>

<#--生成GetFunction SetFunction-->
<#macro buildGetSetFunction>
<#list editFields as field>

	<@compress single_line=true>public <@getType vType = "${field.dataType}"/> get${field.fieldName?cap_first}(){</@compress>
		return ${field.fieldName};
	}

	<@compress single_line=true>public void set${field.fieldName?cap_first}(<@getType vType = "${field.dataType}"/> ${field.fieldName}){</@compress>
		this.${field.fieldName} = ${field.fieldName};
	}
</#list>
</#macro>

package ${packageName}.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.collections.MapUtils;

import com.jeedev.msdp.core.data.BaseEntity;
import com.jeedev.msdp.core.utils.StringUtil;
import com.jeedev.msdp.standard.exception.BizException;

/**
 * 【实例】实体类
 *
 * @类名称 ${tableName}
 * @类描述 <pre> 【实例】实体类</pre>
 * @作者 ${author}
 * @创建时间 ${nowDateZh}
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	${author}     	${nowDateZh} 	新建
 * ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "${selectedTable?upper_case}")
public class ${tableName}Entity extends BaseEntity<Integer>{

	private static final long serialVersionUID = 1L;

	<#--生成Field-->
	<@buildField/>

	<#--生成Get SetFunction-->
	<@buildGetSetFunction/>

	public ${tableName}Entity coverToEntity(Map<String,Object> params) {
		<#list editFields as field>
		if(params.containsKey("${field.fieldName}")){
			<@compress single_line=true>this.${field.fieldName} = MapUtil.get<@getType vType = "${field.dataType}"/>(params,"${field.fieldName}");</@compress>
		}
		</#list>
		if(params.containsKey("id")){
			this.id = MapUtil.getInteger(params, "id");
		}
		if(params.containsKey("delInd")){
			this.delInd = MapUtil.getString(params, "delInd");
		}
		return this;
	}
	
}
