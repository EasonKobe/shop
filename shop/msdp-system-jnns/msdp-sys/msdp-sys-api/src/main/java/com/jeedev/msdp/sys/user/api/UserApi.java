package com.jeedev.msdp.sys.user.api;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 UserApi.java
 * @类描述 <pre>用户模块对外接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 上午10:40:40
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
public interface UserApi {	
	/**
	 * @方法名称 resetPassword
	 * @功能描述
	 *  <pre>重置密码</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午2:33:31
	 * @param paramMap
	 *            参数Map</br>
	 * 			loginName：用户名</br>
	 * 			newPswd：新密码</br>
	 * 			salt：盐
	 * @return 1成功，-1失败
	 */
	public Integer resetPasswordTrans(Map<String, Object> paramMap);
	/**
	 * @方法名称 updatePassword
	 * @功能描述
	 *       <pre>修改密码</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午2:06:46
	 * @param paramMap
	 *            参数Map</br>
	 * 			loginName：用户名</br>
	 * 			password：原密码</br>
	 * 			newPswd：新密码</br>
	 * @return 1成功，-1失败，-2原密码错误
	 */
	public Integer changePasswordTrans(Map<String, Object> paramMap);
	/**
	 * @方法名称 findUserPage
	 * @功能描述 <pre>查询用户分页</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2016年11月22日 下午3:03:31
	 * @param paramMap 查询参数Map</br>
	 * loginName：用户名</br>
	 * realname：真实用户名（模糊）</br>
	 * userNum：用户编号</br>
	 * mobile：手机号码</br>
	 * email：邮箱</br>
	 * statusCd：用户状态。1正常，2锁定 
	 * @param pageSize 分页条数
	 * @param pageNum 分页页数 
	 * @return 用户信息</br>
	 * id:主键编号</br>
	 * userNum：用户编号</br>
	 * loginName：用户名</br>
	 * realname：真实用户名</br>
	 * mobile：手机号</br>
	 * email：邮箱</br>
	 * statusCd：用户状态。1正常，2锁定
	 */
	public PageInfo<Map<String,Object>> findUserPage(Map<String,Object> paramMap,PageParam pageParam) ;
	/**
	 * @方法名称 getUserMap
	 * @功能描述 <pre>获取用户详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月19日 下午3:32:37
	 * @param params 查询参数Map<br>
	 * id：用户主键编号<br>
	 * loginName：用户名<br>
	 * userNum：用户编号<br>
	 * realname：真实用户名（模糊）</br>
	 * mobile：手机号码<br>
	 * email：邮箱<br>
	 * statusCd：用户状态。1正常，2锁定
	 * @return 用户信息<br>
	 * id：用户主键编号<br>
	 * userNum：用户编号<br>
	 * loginName：用户名<br>
	 * realname：真实用户名</br>
	 * password：密码</br>
	 * salt：盐</br>
	 * city：城市</br>
	 * sexCd：性别</br>
	 * mobile：手机号<br>
	 * email：邮箱<br>
	 * statusCd：用户状态。1正常，2锁定
	 */
	public Map<String, Object> getUserMap(Map<String,Object> params);
	/**
	 * @方法名称 saveUserTrans
	 * @功能描述 <pre>新增或修改用户信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月19日 下午3:32:43
	 * @param params
	 * id：用户主键编号<br>
	 * userNum：用户编号<br>
	 * loginName：用户名<br>
	 * realname：真实用户名</br>
	 * password：密码</br>
	 * salt：盐</br>
	 * city：城市</br>
	 * sexCd：性别</br>
	 * mobile：手机号<br>
	 * email：邮箱<br>
	 * statusCd：用户状态。1正常，2锁定
	 * @return
	 * userNum：用户编号
	 */
	public Map<String, Object> saveUserTrans(Map<String,Object> params);
	/**
	 * @方法名称 updateUserTrans
	 * @功能描述 <pre>修改用户信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月13日 上午9:06:44
	 * @param params
	 * id：用户主键编号<br>
	 * userNum：用户编号<br>
	 * loginName：用户名<br>
	 * realname：真实用户名</br>
	 * password：密码</br>
	 * salt：盐</br>
	 * city：城市</br>
	 * sexCd：性别</br>
	 * mobile：手机号<br>
	 * email：邮箱<br>
	 * statusCd：用户状态。1正常，2锁定
	 * @return
	 */
	public void updateUserTrans(Map<String,Object> params);
	/**
	 * @方法名称 deleteUserTrans
	 * @功能描述 <pre>删除用户实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月19日 下午3:32:47
	 * @param params
	 * @return
	 */
	public void deleteUserTrans(Map<String,Object> params);
	// 用户与部门
	/**
	 * @方法名称 findUserDeptRelPage
	 * @功能描述 <pre>分页获取用户和部门关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:43:20
	 * @param params 参数
	 * userNum 用户编码
	 * deptCode 部门编码
	 * orgCode 机构编码
	 * @param pageNum 页数
	 * @param pageSize 条数
	 * @return 分页数据
	 */
	public PageInfo<Map<String, Object>> findUserDeptRelPage(Map<String, Object> params, PageParam pageParam);
	
	/**
	 * @方法名称 getUserDeptRelMap
	 * @功能描述 <pre>获取用户与部门信息详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:44:29
	 * @param params 参数
	 * userNum 用户编码
	 * id ID
	 * @return  用户与部门信息详情
	 */
	public Map<String, Object> getUserDeptRelMap(Map<String, Object> params);
	/**
	 * @方法名称 saveUserDeptRelTrans
	 * @功能描述 <pre>新增或更新用户与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:45:32
	 * @param params 参数
	 * userNum 用户编码
	 * deptCode 部门编码
	 * orgCode 机构编码
	 * @return 用户与部门信息详情
	 */
	public Map<String, Object> saveUserDeptRelTrans(Map<String, Object> params);
	/**
	 * @方法名称 deleteUserDeptRelTrans
	 * @功能描述 <pre>删除用户与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:46:05
	 * @param params
	 * @return
	 */
	public void deleteUserDeptRelTrans(Map<String, Object> params);
	// 用户与角色
	/**
	 * @方法名称 findUserRoleRelPage
	 * @功能描述 <pre>分页获取用户与角色关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:46:53
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserRoleRelPage(Map<String, Object> params, PageParam pageParam);
	/**
	 * @方法名称 findUserRoleRelList
	 * @功能描述 <pre>获取用户角色关系集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月19日 下午2:11:46
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findUserRoleRelList(Map<String, Object> params);
	/**
	 * @方法名称 getUserRoleRelMap
	 * @功能描述 <pre>根据用户角色id或用户编码，角色编码组合 获取用户与角色关系信息详情</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月17日 上午10:47:30
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserRoleRelMap(Map<String, Object> params);
	/**
	 * @方法名称 saveUserRoleRelTrans
	 * @功能描述 <pre>新增&更新用户与角色关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:48:11
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUserRoleRelTrans(Map<String, Object> params);
	/** 
	 * @方法名称 deleteUserRoleRelTrans
	 * @功能描述 <pre>删除用户与角色关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:48:56
	 * @param params
	 * @return
	 */
	public void deleteUserRoleRelTrans(Map<String, Object> params);
	// 用户与机构
	/**
	 * @方法名称 findUserOrgRelPage
	 * @功能描述 <pre>分页获取用户与机构关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:49:14
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserOrgRelPage(Map<String, Object> params,  PageParam pageParam);
	/**
	 * @方法名称 getUserOrgRelMap
	 * @功能描述 <pre>获取用户与机构关系信息详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:49:35
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserOrgRelMap(Map<String, Object> params);
	/**
	 * @方法名称 saveUserOrgRelTrans
	 * @功能描述 <pre>新增或更新用户与机构关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:50:01
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUserOrgRelTrans(Map<String, Object> params);
	/**
	 * @方法名称 deleteUserOrgRelTrans
	 * @功能描述 <pre>删除用户与机构关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:50:30
	 * @param params
	 * @return
	 */
	public void deleteUserOrgRelTrans(Map<String, Object> params);
	// 用户与分组
	/**
	 * @方法名称 findUserGroupRelPage
	 * @功能描述 <pre>分页查询用户与分组关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:50:47
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserGroupRelPage(Map<String, Object> params, PageParam pageParam);
	/**
	 * @方法名称 getUserGroupRelMap
	 * @功能描述 <pre>获取用户与分组关系信息详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:52:13
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserGroupRelMap(Map<String, Object> params);
	/**
	 * @方法名称 saveUserGroupRelTrans
	 * @功能描述 <pre>新增或更新用户与分组关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:52:18
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUserGroupRelTrans(Map<String, Object> params);
	/**
	 * @方法名称 deleteUserGroupRelTrans
	 * @功能描述 <pre>删除用户与分组关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:52:22
	 * @param params
	 * @return
	 */
	public void deleteUserGroupRelTrans(Map<String, Object> params);
	/**
	 * @方法名称 findUserByOrgRoleRel
	 * @功能描述 <pre>根据角色机构查找用户</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:26:06
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String, Object>>  findUserByOrgRoleRel(Map<String, Object> params);
	/**
	 * @方法名称 findUserByDeptRoleRel
	 * @功能描述 <pre>根据角色部门查找用户</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:26:22
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String, Object>>  findUserByDeptRoleRel(Map<String, Object> params);
	/**
	 * @方法名称 findUserOrgDeptRelPage
	 * @功能描述 <pre>根据条件查询用户机构与部门的信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年11月18日 下午4:50:08
	 * @param params
	 * @param pageParam
	 * @return
	 */
	PageInfo<Map<String, Object>> findUserOrgDeptRelPage(Map<String, Object> params, PageParam pageParam);
	
}
