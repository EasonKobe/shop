package ${packageName}.services.impl;

import com.sinoservices.xframework.core.support.dao.BaseMapper;
import java.util.List;

import ${packageName}.model.${tableName}Model;
import ${packageName}.mapper.${tableName}Mapper;
import ${packageName}.services.${tableName}Manager;
import com.sinoservices.xframework.core.support.Pagination;
import com.sinoservices.xframework.core.support.RowBounds;
import com.sinoservices.xframework.core.support.services.impl.ManangerSupport;
import org.springframework.beans.factory.annotation.Autowired;


public class ${tableName}ManagerImpl extends ManangerSupport implements ${tableName}Manager{

	@Autowired
	private ${tableName}Mapper ${tableName?uncap_first}Mapper;
	
	@Override
	public Pagination getPageByCondition(${tableName}Model condition, RowBounds bounds) {
		Integer count=${tableName?uncap_first}Mapper.getCountByCondition(condition);
		Pagination page=new Pagination(bounds, count);
		List list=${tableName?uncap_first}Mapper.findByCondition(condition, bounds);
		page.setData(list);
		return page;
	}
	
	/**
	 * 新增方法
	 * @param model
	 * @return
	 */
	public void insert(${tableName}Model model){
		${tableName?uncap_first}Mapper.insert(model);
	};
	
	/**
	 * 修改方法
	 * @param model
	 * @return
	 */
	public void update(${tableName}Model model){
		${tableName?uncap_first}Mapper.update(model);
	};
	
	/**
	 * 删除方法
	 * @param id
	 * @return
	 */
	public void delete(Long id){
		${tableName?uncap_first}Mapper.delete(id);
	};
}

