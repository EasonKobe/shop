package ${packageName}.services;

import com.sinoservices.xframework.core.support.services.BaseMananger;
import java.util.List;

import ${packageName}.model.${tableName}Model;
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
	public Pagination getPageByCondition(${tableName}Model condition,RowBounds bounds);
	
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
