<#assign editFields = page.resource.editFields>
<#assign selectedTable = page.resource.selectedTable>
<#assign jinghao = '#'>
<#--设置Java类型,定义变量-->
<#assign lng = "\"java.lang.Long\"">
<#assign time = "\"java.util.Date\"">
<#assign d = "\"java.util.Double\"">
<#assign str = "\"java.lang.String\"">
<#assign i = "\"java.lang.Integer\"">
<#assign dec = "\"java.math.BigDecimal\"">
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
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${packageName}.dao.${tableName}Dao">
	<resultMap type="java.util.Map" id="${tableName?uncap_first}Map">
	<#list editFields as field>
		<result column="${field.colName?upper_case}" property="${field.fieldName}"/>
	</#list>
        <result column="ID" property="id" />
        <result column="CREATE_TIME" property="createTime" />
        <result column="UPDATE_TIME" property="updateTime" />
        <result column="CREATE_USER" property="createUser" />
        <result column="UPDATE_USER" property="updateUser" />
        <result column="EXPD_ID" property="expdId" />
        <result column="DEL_IND" property="delInd" />
        <result column="VERSION" property="version" />
	</resultMap>

    <sql id="column_list">
        ID,  <#list editFields as field>${field.colName?upper_case}, </#list>TENANT_ID,CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER, EXPD_ID, DEL_IND, VERSION
	</sql>
	
    <sql id="where_filters">
        <where>
        	<if test="1==1">
        		AND DEL_IND = '0'
			</if>
        	<if test="id!=null and id!=''">
				AND ID = ${jinghao}{id}
			</if>
		<#list editFields as field>
			<if test="${field.fieldName}!=null and ${field.fieldName}!=''">
				AND ${field.colName?upper_case} = ${jinghao}{${field.fieldName}}
			</if>
		</#list>
        </where>
    </sql>

    <!--根据条件查询列表-->
    <select id="findDemoList" parameterType="map" resultMap="${tableName?uncap_first}Map">
        SELECT <include refid="column_list"/> FROM ${selectedTable} <include refid="where_filters"/>
    </select>

</mapper>
