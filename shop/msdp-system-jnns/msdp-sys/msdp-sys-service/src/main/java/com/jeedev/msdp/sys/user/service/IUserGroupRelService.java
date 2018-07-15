package com.jeedev.msdp.sys.user.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

/**
 * 
 * @类名称 IUserGroupRelService.java
 * @类描述 <pre>用户与分组关系基础服务接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午2:09:10
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
public interface IUserGroupRelService {
	/**
	 * 
	 * @方法名称 findUserGroupRelPage
	 * @功能描述 <pre>分页查询用户与分组信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserGroupRelPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 
	 * @方法名称 getUserGroupRelMap
	 * @功能描述 <pre>查询选中用户与分组</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserGroupRelMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveUserGroupRel
	 * @功能描述 <pre>保存用户与分组信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUserGroupRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateUserGroupRel
	 * @功能描述 <pre>更新用户与分组</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params
	 * @return
	 */
	public void updateUserGroupRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteUserGroupRel
	 * @功能描述 <pre>删除用户与分组信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 */
	public void deleteUserGroupRel(Map<String, Object> params);
}
