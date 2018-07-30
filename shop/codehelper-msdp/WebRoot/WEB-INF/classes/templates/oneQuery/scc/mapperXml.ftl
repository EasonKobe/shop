<#assign showFields = page.resource.showFields>
<#assign queryFields = page.resource.showFields>
<#assign selectedTable = page.resource.selectedTable>
<#assign sql = page.resource.sql>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageName}.mapper.${tableName}Mapper">
	<resultMap type="${packageName}.view.${tableName}QueryItemView" id="${tableName?uncap_first}ItemViewResultMap">
	<#list showFields as field>
		<result property="${field.fieldName}" column="${field.colName}"/>
	</#list>
	</resultMap>
	<sql id="select">
		<![CDATA[
		
		<#if sql != ""> 
		  ${sql}
		<#else> 
		  SELECT 
			<#list showFields as field>
				${field.colName},
			</#list>
		</#if> 
		]]>
	</sql>
	
	<sql id="count">
		<![CDATA[
	    	SELECT COUNT(1) 
	  	]]>
	</sql>
	
	<sql id="findByExample">
		WHERE 1=1
		<#list showFields as field>
			<if test="${field.fieldName}!=null and ${field.fieldName}!=''"> AND ${field.colName} LIKE ('%'||'${field.fieldName}'||'%' )</if>
		</#list>
	</sql>
	
	<select id="countByCondtion" resultType="int" parameterType="${packageName}.view.${tableName}QueryConditionView">
		<include refid="count"/>
		FROM  ${selectedTable}
		<include refid="findByExample"/>
	</select>
	
	<select id="getPage" resultMap="${tableName?uncap_first}ItemViewResultMap" parameterType="${packageName}.view.${tableName}QueryConditionView">
		<include refid="select"/>
		FROM  ${selectedTable}
		<include refid="findByExample"/>
	</select>
</mapper>
