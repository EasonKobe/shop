package ${packageName}.services;

import com.sinoservices.xframework.core.support.services.BaseMananger;
import java.util.List;

import ${packageName}.view.${tableName}QueryConditionView;
import ${packageName}.view.${tableName}QueryItemView;
import com.sinoservices.xframework.core.support.RowBounds;
import com.sinoservices.xframework.core.support.dao.BaseMapper;
import com.sinoservices.xframework.core.support.Pagination;



public interface ${tableName}Manager extends BaseMananger {

	/**
	 * 分页查询方法
	 * @param example
	 * @param bounds
	 * @return
	 */
	public Pagination getPageByCondition(${tableName}QueryConditionView condition,RowBounds bounds);
}
