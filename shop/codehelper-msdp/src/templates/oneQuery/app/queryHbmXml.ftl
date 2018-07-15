<#assign showFields = page.resource.showFields>
<#assign queryFields = page.resource.queryFields>
<#assign sql = page.resource.sql>
<#assign selectedTable = page.resource.selectedTable>
<?xml version="1.0"?>
<!DOCTYPE hibernate-mapping PUBLIC 
	"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
	"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping>
	<sql-query name="${tableName}Query">
		<![CDATA[
		<#if sql != ""> 
		  ${sql}
		<#else> 
		  SELECT 
			<#list showFields as field>
				${field.colName},
			</#list>
			
			FROM  ${selectedTable}
		</#if>  
		
		WHERE 1=1
		<#list queryFields as field>
			<<and ${field.colName}  like '%'||:${field.fieldName}||'%'>>
		</#list>
		]]>
	</sql-query>
</hibernate-mapping>


		
