package com.jeedev.msdp.sys.org.api;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 OrgApi.java
 * @类描述 <pre>机构对外接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 上午10:30:54
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
public interface OrgApi {
	/**
	 * @方法名称 getOrgNameByOrgCode
	 * @功能描述 <pre>获取机构名</pre>
	 * @作者    chenjiancong
	 * @创建时间 2017年8月28日 下午3:44:26
	 * @param params orgCode:客户编号
	 * @return orgName:客户名称
	 */
	public Map<String, Object> getOrgNameByOrgCode(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 findOrgPage
	 * @功能描述 <pre>分页查询机构列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:31:14
	 * @param params  查询参数
	 * @param  pageParam 分页信息
	 * @return 分页信息
	 */
	public PageInfo<Map<String, Object>> findOrgPage(Map<String, Object> params,PageParam pageParam);
	
	/**
	 * 
	 * @方法名称 findCoreOrgPage
	 * @功能描述 <pre>分页查询机构列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:31:14
	 * @param params  查询参数
	 * @param  pageParam 分页信息
	 * @return 分页信息
	 */
	public PageInfo<Map<String, Object>> findCoreOrgPage(Map<String, Object> params,PageParam pageParam);

	/**
	 * 
	 * @方法名称 getOrgMap
	 * @功能描述 <pre>获取机构详情信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:31:32
	 * @param params 查询参数
	 * @return 机构信息map
	 */
	public Map<String, Object> getOrgMap(Map<String, Object> params);

	/**
	 * 
	 * @方法名称 saveOrgTrans
	 * @功能描述 <pre>新增&变更机构详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:32:05
	 * @param params 保存参数
	 * @return 机构信息map
	 */
	public Map<String, Object> saveOrgTrans(Map<String, Object> params);

	/**
	 * 
	 * @方法名称 deleteOrgTrans
	 * @功能描述 <pre>删除机构详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:32:29
	 * @param params 删除参数
	 */
	public void deleteOrgTrans(Map<String, Object> params);
	
	
	/**
	 * 
	 * @方法名称 findOrgDeptRelPage
	 * @功能描述 <pre>分页查询机构与部门关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params 查询参数
	 * @param pageParam 分页信息
	 * @return 机构与部门分页信息
	 */
	public PageInfo<Map<String, Object>> findOrgDeptRelPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 
	 * @方法名称 findOrgDeptRel
	 * @功能描述 <pre>查询选中机构与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params 查询阐述
	 * @return 机构与部门信息map
	 */
	public Map<String, Object> getOrgDeptRelMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveOrgDeptRelTrans
	 * @功能描述 <pre>保存机构与部门关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params 保存参数
	 * @return 机构与部门信息map
	 */
	public Map<String, Object> saveOrgDeptRelTrans(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateOrgDeptRelTrans
	 * @功能描述 <pre>更新机构与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params 更新参数
	 */
	public void updateOrgDeptRelTrans(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteOrgDeptRel
	 * @功能描述 <pre>删除机构与部门关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params 删除参数
	 */
	public void deleteOrgDeptRelTrans(Map<String, Object> params);
	
	
	/**
	 * @方法名称 findParentOrgs
	 * @功能描述 <pre>根据条件查询父级机构信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月18日 上午9:25:01
	 * @param params 查询参数
	 * @return 机构集合
	 */
	public List<Map<String,Object>> findParentOrgs(Map<String, Object> params);
	
	/**
	 * @方法名称 findChildrenOrgs
	 * @功能描述 <pre>根据条件查询子集机构信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月18日 上午9:25:25
	 * @param params 查询参数
	 * @return 机构集合
	 */
	public List<Map<String,Object>>  findChildrenOrgs(Map<String, Object> params);
}
