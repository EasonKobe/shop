<#assign editFields = page.resource.editFields>
<#assign selectedTable = page.resource.selectedTable>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="${packageName}.mapper.${tableName}Mapper">
	<resultMap type="${packageName}.model.${tableName}Model" id="${tableName?uncap_first}ModelResultMap">
	<#list editFields as field>
		<result property="${field.fieldName}" column="${field.colName}"/>
	</#list>
	</resultMap>
	<sql id="select">
		<![CDATA[
			SELECT 
			<#list editFields as field>
				${field.colName},
			</#list>
		]]>
	</sql>
	
	<sql id="count">
		<![CDATA[
	    	SELECT COUNT(1) 
	  	]]>
	</sql>
	
	<sql id="findByExample">
		WHERE 1=1
		<#list editFields as field>
			<if test="${field.fieldName}!=null and ${field.fieldName}!=''"> AND ${field.colName} LIKE ('%'||'${field.fieldName}'||'%' )</if>
		</#list>
	</sql>
	
	<select id="countByCondtion" resultType="int" parameterType="${packageName}.model.${tableName}Model">
		<include refid="count"/>
		FROM  ${selectedTable}
		<include refid="findByExample"/>
	</select>
	
	<select id="getPage" resultMap="${tableName?uncap_first}ModelMap" parameterType="${packageName}.model.${tableName}Model">
		<include refid="select"/>
		FROM  ${selectedTable}
		<include refid="findByExample"/>
	</select>
	
	<insert id="insert">
		INSERT INTO ${selectedTable} (
			<#list editFields as field>
				${field.colName},
			</#list>
		) VALUES (
			<#list editFields as field>
			   ${'#'}{${field.fieldName}},
			</#list>  
		);
	</insert>
	
	<update id="update">
		UPDATE ${selectedTable} SET
		<#list editFields as field>
				${field.colName} = ${'#'}{${field.fieldName}},
			</#list>
	</update>
</mapper>
