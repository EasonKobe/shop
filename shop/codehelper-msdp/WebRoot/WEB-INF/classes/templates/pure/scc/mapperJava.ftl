package ${packageName}.mapper;

import com.sinoservices.xframework.core.support.dao.BaseMapper;
import java.util.List;

import ${packageName}.model.${tableName}Model;
import com.sinoservices.xframework.core.support.RowBounds;

public interface ${tableName}Mapper extends BaseMapper {

	/**
	 * 分页查询方法
	 * @param example
	 * @param bounds
	 * @return
	 */
	public List<${tableName}Model> findByCondition(${tableName}Model condition,RowBounds bounds);

	/**
	 * 按条件统计方法
	 * @param example
	 * @param bounds
	 * @return
	 */
	public Integer getCountByCondition(${tableName}Model condition);
	
		/**
	 * 新增方法
	 * @param model
	 * @return
	 */
	public void insert(${tableName}Model model);
	
	/**
	 * 修改方法
	 * @param model
	 * @return
	 */
	public void update(${tableName}Model model);
	
	/**
	 * 删除方法
	 * @param id
	 * @return
	 */
	public void delete(Long id);
}

