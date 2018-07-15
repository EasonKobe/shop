package com.jeedev.msdp.sys.user.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * @类名称 IUserDeptRelService.java
 * @类描述 <pre>用户与部门关系基础服务接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午2:11:15
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
public interface IUserDeptRelService {
	/**
	 * @方法名称 findUserDeptRelPage
	 * @功能描述 <pre>分页查询用户与部门信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserDeptRelPage(Map<String, Object> params, PageParam pageParam);
	/**
	 * @方法名称 countNum
	 * @功能描述 <pre>唯一性判断使用</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月21日 下午8:37:39
	 * @param userNum,
	 * @param deptCode,
	 * @param orgCode,
	 * @return
	 */
	public Integer countNum(Map<String, Object> params); 
	/**
	 * @方法名称 getUserDeptRelMap
	 * @功能描述 <pre>查询选中用户与部门</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params
	 * @return
	 */
	public Map<String, Object> getUserDeptRelMap(Map<String, Object> params);
	/**
	 * @方法名称 saveUserDeptRel
	 * @功能描述 <pre>保存用户与部门信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveUserDeptRel(Map<String, Object> params) ;
	/**
	 * @方法名称 updateUserDeptRel
	 * @功能描述 <pre>更新用户与部门</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params
	 * @return
	 */
	public void updateUserDeptRel(Map<String, Object> params) ;
	/**
	 * @方法名称 deleteUserDeptRel
	 * @功能描述 <pre>删除用户与部门信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 */
	public void deleteUserDeptRel(Map<String, Object> params);
	
	/**
	 * 查找用户根据部门角色
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserByDeptRoleRel(Map<String, Object> params) ;

	
}
