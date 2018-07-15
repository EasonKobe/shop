package com.jeedev.msdp.sys.user.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 IUserRoleRelService.java
 * @类描述 <pre>用户与角色与角色关系基础接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午2:04:58
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
public interface IUserRoleRelService {
	/**
	 * 
	 * @方法名称 findUserRoleRelPage
	 * @功能描述 <pre>分页查询用户与角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserRoleRelPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 
	 * @方法名称 getUserRoleRelMap
	 * @功能描述 <pre根据用户角色id或用户编码，角色编码组合 获取用户与角色关系信息详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserRoleRelMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveUserRoleRel
	 * @功能描述 <pre>保存用户与角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUserRoleRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateUserRoleRel
	 * @功能描述 <pre>更新用户与角色</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params
	 * @return
	 */
	public void updateUserRoleRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteUserRoleRel
	 * @功能描述 <pre>删除用户与角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 */
	public void deleteUserRoleRel(Map<String, Object> params);
}
