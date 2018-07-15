package com.jeedev.msdp.sys.role.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 RoleApi.java
 * @类描述 <pre>角色管理</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 下午4:31:08
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
public interface ISysRoleService {
	/**
	 * 
	 * @方法名称 findRolePage
	 * @功能描述 <pre>分页查询角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * @param  pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findRolePage(Map<String, Object> params, PageParam pageParam);
	/**
	 * 
	 * @方法名称 findRole
	 * @功能描述 <pre>查询选中角色</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params
	 * @return
	 */
	public Map<String, Object> getRoleMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveRole
	 * @功能描述 <pre>保存角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveRole(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateRole
	 * @功能描述 <pre>更新角色</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params
	 * @return
	 */
	public void updateRole(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteRole
	 * @功能描述 <pre>删除角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 */
	public void deleteRole(Map<String, Object> params);
}
