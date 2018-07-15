package com.jeedev.msdp.sys.dept.api;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 DeptApi.java
 * @类描述 <pre>部门对外接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 上午10:19:01
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月17日             
 *     ----------------------------------------------
 * </pre>
 */
public interface DeptApi {
	/**
	 * @方法名称 findDeptPage
	 * @功能描述 <pre>分页查询部门数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:15:43
	 * @param params
	 * deptCode 部门代码
	 * deptName 部门名称
	 * parentDeptCode 上级部门代码
	 * @param pageNum 页码
	 * @param pageSize 条数
	 * @return
	 * deptCode 部门代码
	 * deptName 部门名称
	 * parentDeptCode 上级部门代码
	 * deptLevelCd 部门级别(一级1二级2三级3四级4)
	 * sort 排序
	 * statusCd 状态：无效0有效1
	 */
	public PageInfo<Map<String, Object>> findDeptPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * @方法名称 getDeptMap
	 * @功能描述 <pre>获取部门详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:16:22
	 * @param params
	 * id 主键编码
	 * deptCode 部门代码
	 * parentDeptCode 上级部门代码
	 * @return
	 * deptCode 部门代码
	 * deptName 部门名称
	 * parentDeptCode 上级部门代码
	 * deptLevelCd 部门级别(一级1二级2三级3四级4)
	 * sort 排序
	 * statusCd 状态：无效0有效1
	 */
	public Map<String, Object> getDeptMap(Map<String, Object> params);
    /**
     * @方法名称 saveDeptTrans
     * @功能描述 <pre>新增&更新部门信息</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年8月17日 上午10:17:00
     * @param params
     * deptCode 部门代码
	 * deptName 部门名称
	 * parentDeptCode 上级部门代码
	 * deptLevelCd 部门级别(一级1二级2三级3四级4)
     * @return
     * id 主键编码
     */
	public Map<String, Object> saveDeptTrans(Map<String, Object> params);
	/**
	 * @方法名称 deleteDeptTrans
	 * @功能描述 <pre>删除部门信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:17:15
	 * @param params
	 * id 主键编码
	 * @return
	 */
	public void deleteDeptTrans(Map<String, Object> params);
    /** 
     * @方法名称 findDeptTreeList
     * @功能描述 <pre>获取部门数据树</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年8月17日 上午10:18:22
     * @param params
     * deptCode 部门代码
	 * deptName 部门名称
	 * parentDeptCode 上级部门代码
	 * deptLevelCd 部门级别(一级1二级2三级3四级4)
     * @return
     */
	public List<Map<String, Object>> findDeptTreeList(Map<String, Object> params) ;
	
	/**
	 * @方法名称 findParentDepts
	 * @功能描述 <pre>根据条件获取父部门</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:01:11
	 * @param params
	 * deptCode 部门代码
	 * deptName 部门名称
	 * parentDeptCode 上级部门代码
	 * deptLevelCd 部门级别(一级1二级2三级3四级4)
	 * @return
	 */
	public List<Map<String,Object>> findParentDepts(Map<String, Object> params);
	
	/**
	 * @方法名称 findChildrenDepts
	 * @功能描述 <pre>根据条件获取部门的所有子部门</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:01:22
	 * @param params
	 * deptCode 部门代码
	 * deptName 部门名称
	 * parentDeptCode 上级部门代码
	 * deptLevelCd 部门级别(一级1二级2三级3四级4)
	 * @return
	 */
	public List<Map<String,Object>> findChildrenDepts(Map<String, Object> params);
	    
}
