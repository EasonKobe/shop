package ${packageName}.services.impl;

import com.sinoservices.xframework.core.support.dao.BaseMapper;
import java.util.List;

import ${packageName}.view.${tableName}QueryConditionView;
import ${packageName}.view.${tableName}QueryItemView;
import ${packageName}.mapper.${tableName}Mapper;
import ${packageName}.services.${tableName}Manager;
import com.sinoservices.xframework.core.support.Pagination;
import com.sinoservices.xframework.core.support.RowBounds;
import com.sinoservices.xframework.core.support.services.impl.ManangerSupport;
import org.springframework.beans.factory.annotation.Autowired;


public class ${tableName}ManagerImpl extends ManangerSupport implements ${tableName}Manager{

	@Autowired
	private ${tableName}Mapper ${tableName}Mapper;
	
	@Override
	public Pagination getPageByCondition(${tableName}QueryConditionView condition, RowBounds bounds) {
		Integer count=${tableName}Mapper.getCountByCondition(condition);
		Pagination page=new Pagination(bounds, count);
		List list=${tableName}Mapper.findByCondition(condition, bounds);
		page.setData(list);
		return page;
	}
}

