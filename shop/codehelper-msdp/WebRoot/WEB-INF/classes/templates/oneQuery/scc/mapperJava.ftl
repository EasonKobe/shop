package ${packageName}.mapper;

import com.sinoservices.xframework.core.support.dao.BaseMapper;
import java.util.List;

import ${packageName}.view.${tableName}QueryConditionView;
import ${packageName}.view.${tableName}QueryItemView;
import com.sinoservices.xframework.core.support.RowBounds;

public interface ${tableName}Mapper extends BaseMapper {

	/**
	 * 分页查询方法
	 * @param example
	 * @param bounds
	 * @return
	 */
	public List<${tableName}QueryItemView> findByCondition(${tableName}QueryConditionView condition,RowBounds bounds);

	/**
	 * 按条件统计方法
	 * @param example
	 * @param bounds
	 * @return
	 */
	public Integer getCountByCondition(${tableName}QueryConditionView condition);
	
}

