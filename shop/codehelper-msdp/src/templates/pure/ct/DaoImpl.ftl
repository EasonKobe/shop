package ${packageName}.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import ${packageName}.dao.I${tableName}Dao;
import ${packageName}.pojo.${tableName};
import com.cloudtop.core.dao.BaseDaoImpl;

@Repository
public class ${tableName}DaoImpl extends BaseDaoImpl<${tableName}, Integer> implements I${tableName}Dao{

	/**
	 * 方法说明：根据条件查询
	 * @param model
	 * @return
	 */
	public List<${tableName}> findByCondition(${tableName} model) throws Exception{
		return getSqlSession().selectList(${tableName}.DOMAIN+"findByCondition",model);
	}
	/**
	 * 方法说明：新增方法
	 * @param model
	 */
	public void insert(${tableName} model) throws Exception{
		getSqlSession().insert(${tableName}.DOMAIN+"insert",model);
	}
	
	/**
	 * 方法说明：修改方法
	 * @param model
	 */
	public void update(${tableName} model) throws Exception{
		getSqlSession().update(${tableName}.DOMAIN+"update",model);
	}
	
	/**
	 * 方法说明：删除方法
	 * @param id
	 */
	public void delete(int id) throws Exception{
		getSqlSession().delete(${tableName}.DOMAIN+"delete",id);
	}
}

