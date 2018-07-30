package ${packageName}.service.impl;

import java.util.List;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jeedev.msdp.standard.exception.BizException;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageName}.dao.${tableName}Dao;
import ${packageName}.entity.${tableName};
import ${packageName}.service.I${tableName}Service;
import org.springframework.util.StringUtils;

/**
 * 【实例】实现类
 *
 * @类名称 ${tableName}ServiceImpl
 * @类描述 <pre> 【实例】实现类 </pre>
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
@Service("${tableName?uncap_first}Service")
public class ${tableName}ServiceImpl implements I${tableName}Service{

	@Autowired
	private ${tableName}Dao ${tableName?uncap_first}Dao;

	@Override
	public List<Map<String, Object>> findDemoList(Map<String, Object> params) throws BizException {
		return ${tableName?uncap_first}Dao.findDemoList(params);
	}

	@Override
	public Map<String, Object> getDemo(Map<String, Object> params) throws BizException {
		List<Map<String, Object>> list = findDemoList(params);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Map<String, Object> saveOrUpdateDemo(Map<String, Object> params) throws BizException {
		Integer id = MapUtils.getInteger(params, "id");
		if(id == null){
			${tableName}Entity ${tableName?uncap_first} = ${tableName?uncap_first}Dao.save(new ${tableName}Entity().coverToEntity(params));
			params.put("id", ${tableName?uncap_first}.getId());
		}else{
			${tableName}Entity findOne = ${tableName?uncap_first}Dao.findOne(id);
			${tableName?uncap_first}Dao.update(findOne.coverToEntity(params));
			params = findOne.coverToMap();
		}
		return params;
	}

	@Override
	public void deleteDemo(Map<String,Object> params) throws BizException {
		Integer id = MapUtils.getInteger(params, "id");
		${tableName}Entity findOne = ${tableName?uncap_first}Dao.findOne(id);
		findOne.setDelInd("1");
		${tableName?uncap_first}Dao.update(findOne);
	}
}

