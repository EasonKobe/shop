<#assign editFields = page.resource.editFields>
<#macro  buildField>
	<#list editFields as field>
    *         ${field.fieldName}：${field.comment}<br>
	</#list>
</#macro>
package ${packageName}.dao;

import java.util.List;
import java.util.Map;

import ${packageName}.entity.${tableName};
import com.jeedev.jfast.core.dao.BaseDao;
import org.apache.ibatis.annotations.Param;

/**
 * 【实例】Dao类
 *
 * @类名称 ${tableName}Dao
 * @类描述 <pre> 【实例】Dao类</pre>
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
public interface ${tableName}Dao extends BaseDao<${tableName}Entity, Integer> {

	List<Map<String, Object>> findDemoList(Map<String, Object> params);

}

