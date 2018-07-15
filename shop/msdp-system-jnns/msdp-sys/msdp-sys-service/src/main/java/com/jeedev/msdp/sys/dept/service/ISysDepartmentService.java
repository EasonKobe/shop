package com.jeedev.msdp.sys.dept.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 SysDepartmentService.java
 * @类描述 <pre>部门管理服务类 </pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 下午1:57:43
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月15日             
 *     ----------------------------------------------
 * </pre>
 */
public interface ISysDepartmentService
{
	/**
	 * @方法名称 findSysDepartmentPage
	 * @功能描述 <pre> 查询所有部门按代码升序排序</pre>
	 * @作者 ZengWenFeng
	 * @创建时间 2015年11月12日 下午4:55:27
	 * @return 部门集合
	 */
	public PageInfo<Map<String, Object>> findSysDepartmentPage(Map<String, Object> params, PageParam pageParam) ; 

	/**
	 * @方法名称 getSysDepartmentMap
	 * @功能描述 <pre> 根据ID查询部门 </pre>
	 * @作者 ZengWenFeng
	 * @创建时间 2015年11月12日 下午4:55:27
	 * @param id
	 * @return 部门
	 */
	public Map<String,Object> getSysDepartmentMap(Map<String, Object> params);

	/**
	 * @方法名称 saveSysDepartment
	 * @功能描述 <pre> 保存 </pre>
	 * @作者 ZengWenFeng
	 * @创建时间 2015年11月12日 下午4:55:27
	 * @param vo
	 * @return 部门
	 */
	public Map<String, Object> saveSysDepartment(Map<String, Object> params); 

	/**
	 * @方法名称 updateSysDepartment
	 * @功能描述 <pre> 修改 </pre>
	 * @作者 ZengWenFeng
	 * @创建时间 2015年11月12日 下午4:55:27
	 * @param bo
	 * @return 部门
	 */
	public void updateSysDepartment(Map<String, Object> params);

	/**
	 * @方法名称 deleteSysDepartment
	 * @功能描述 <pre> 删除 </pre>
	 * @作者 ZengWenFeng
	 * @创建时间 2015年11月12日 下午4:55:27
	 * @param id
	 * @return 部门
	 */
	public void deleteSysDepartment(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 findParentDepts
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:51:13
	 * @param params
	 * @return
	 */
    public List<Map<String,Object>> findParentDepts(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 findChildrenDepts
	 * @功能描述 <pre>根据部门编号获取部门列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:51:17
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> findChildrenDepts(Map<String, Object> params);
    
}
