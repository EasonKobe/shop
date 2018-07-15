package com.jeedev.msdp.sys.user.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 IUserOrgRelService.java
 * @类描述 <pre>用户与机构关系基础接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午2:07:31
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
public interface IUserOrgRelService {
	/**
	 * 
	 * @方法名称 findUserOrgRelPage
	 * @功能描述 <pre>分页查询用户与机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserOrgRelPage(Map<String, Object> params,PageParam  pageParam);
	/**
	 * 
	 * @方法名称 getUserOrgRelMap
	 * @功能描述 <pre>查询选中用户与机构</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserOrgRelMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveUserOrgRel
	 * @功能描述 <pre>保存用户与机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUserOrgRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateUserOrgRel
	 * @功能描述 <pre>更新用户与机构</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params
	 * @return
	 */
	public void updateUserOrgRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteUserOrgRel
	 * @功能描述 <pre>删除用户与机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 */
	public void deleteUserOrgRel(Map<String, Object> params);
	
	/**
	 * 
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserByOrgRoleRel(Map<String, Object> params,PageParam pageParam);
	
	/**
	 * 
	 * @方法名称 findUserOrgDeptRelList
	 * @功能描述 <pre>分页获取用户与机构和部门的数据集合</pre>
	 * @作者    yuyq
	 * @创建时间 2017年11月18日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<Map<String, Object>> findUserOrgDeptRelPage(Map<String, Object> params, PageParam pageParam);
	
	
	
}
