package com.jeedev.msdp.sys.user.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

/**
 * 
 * @类名称 IUserService.java
 * @类描述 <pre>用户基数接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午2:03:53
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
public interface IUserService {
	/**
	 * 
	 * @方法名称 findUserPage
	 * @功能描述 <pre>分页查询用户信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserPage(Map<String, Object> params);
	/**
	 * @方法名称 countSysUserByLoginName
	 * @功能描述 <pre>根据登录名统计用户数量，作唯一性判断使用</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月21日 下午8:37:39
	 * @param loginName
	 * @return
	 */
	public Integer countSysUserByLoginName(String loginName); 
	/**
	 * 
	 * @方法名称 findUser
	 * @功能描述 <pre>查询选中用户</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveUser
	 * @功能描述 <pre>保存用户信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUser(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateUser
	 * @功能描述 <pre>更新用户</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params
	 * @return
	 */
	public void updateUser(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteUser
	 * @功能描述 <pre>删除用户信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 */
	public void deleteUser(Map<String, Object> params);
}
