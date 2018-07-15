package com.jeedev.msdp.sys.org.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 ISysOrgService.java
 * @类描述 <pre>机构管理</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月18日 上午10:00:16
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月18日             
 *     ----------------------------------------------
 * </pre>
 */
public interface ISysOrgService {
	/**
	 * 
	 * @方法名称 findOrgPage
	 * @功能描述 <pre>分页查询机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params 查询条件
	 * @param  pageParam 页数
	 * @return 分页信息
	 */
	public PageInfo<Map<String, Object>> findOrgPage(Map<String, Object> params, PageParam pageParam);
	/**
	 * 
	 * @方法名称 findOrg
	 * @功能描述 <pre>根据条件查询机构信息如果id不为空，则根据id查询忽略其他条件</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params 查询参数
	 * id 机构id
	 * orgCode 机构编码
	 * @return
	 */
	public Map<String, Object> getOrgMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveOrg
	 * @功能描述 <pre>保存机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params 保存参数
	 * @return 机构信息map
	 */
	public Map<String, Object> saveOrg(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateOrg
	 * @功能描述 <pre>更新机构</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params 修改参数
	 */
	public void updateOrg(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteOrg
	 * @功能描述 <pre>删除机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params 删除条件参数
	 */
	public void deleteOrg(Map<String, Object> params);
	
	/**
	 * @方法名称 findChildrenOrgs
	 * @功能描述 <pre>查找子机构列表</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月18日 上午10:00:29
	 * @param params 参数
	 * @return 机构列表
	 */
	public List<Map<String, Object>> findChildrenOrgs(Map<String, Object> params);
	
	/**
	 * @方法名称 findParentOrgs
	 * @功能描述 <pre>查找父机构列表</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月18日 上午10:00:49
	 * @param params 参数
	 * @return 机构集合
	 */
	public List<Map<String, Object>> findParentOrgs(Map<String, Object> params);
	
	/**
	 * 
	 * @方法名称 findCoreOrgPage
	 * @功能描述 <pre>分页查询机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params 查询条件
	 * @param  pageParam 页数
	 * @return 分页信息
	 */
	public PageInfo<Map<String, Object>> findCoreOrgPage(Map<String, Object> params, PageParam pageParam);
}