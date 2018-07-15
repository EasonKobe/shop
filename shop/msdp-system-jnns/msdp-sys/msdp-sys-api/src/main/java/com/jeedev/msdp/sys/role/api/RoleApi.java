package com.jeedev.msdp.sys.role.api;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
 
/**
 * @类名称 RoleApi.java
 * @类描述 <pre>角色对外接口</pre>
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
public interface RoleApi {
	/**
	 * @方法名称 findRolePage
	 * @功能描述 <pre>分页查询角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * roleCode:角色编号<br>
	 * roleName:角色名称<br>
	 * statusCd:状态(无效0/有效1)
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * id:角色主键<br>
	 * roleCode:角色编号<br>
	 * roleName:角色名称<br>
	 * statusCd:状态(无效0/有效1)<br>
	 * parentRoleCode:父角色编号<br>
	 * remark:备注<br>
	 */
	public PageInfo<Map<String, Object>> findRolePage(Map<String, Object> params,PageParam pageParam);
	/**
	 * @方法名称 getRoleMap
	 * @功能描述 <pre>获取角色详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:28:48
	 * @param params
	 * id:角色主键<br>
	 * roleCode:角色编号<br>
	 * roleName:角色名称<br>
	 * statusCd:状态(无效0/有效1)
	 * @return
	 * roleCode:角色编号<br>
	 * roleName:角色名称<br>
	 * statusCd:状态(无效0/有效1)<br>
	 * parentRoleCode:父角色编号<br>
	 * remark:备注<br>
	 */
	public Map<String, Object> getRoleMap(Map<String, Object> params);
	/**
	 * @方法名称 saveRole
	 * @功能描述 <pre>保存角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * roleCode:角色编号<br>
	 * roleName:角色名称<br>
	 * parentRoleCode:父角色编号<br>
	 * statusCd:状态(无效0/有效1)
	 * @return
	 */
	public Map<String, Object> saveRoleTrans(Map<String, Object> params) ;
	/**
	 * @方法名称 deleteRole
	 * @功能描述 <pre>删除角色信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 * roleCode:角色编号<br>
	 * id:角色主键<br>
	 */
	public void deleteRoleTrans(Map<String, Object> params);
	//角色与用户
	/**
	 * @方法名称 findRoleUserRelPage
	 * @功能描述 <pre>根据角色获取用户列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:10:27
	 * @param params
	 * roleCode:角色编号<br>
	 * userNum：用户编号<br>
	 * userRealname：用户名称<br>
	 * userLoginName：用户登录名<br>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * roleCode:角色编号<br>
	 * userNum：用户编号<br>
	 * userRealname：用户名称<br>
	 * userLoginName：用户登录名<br>
	 * userStatusCd：用户状态<br>
	 * userSexCd：用户性别<br>
	 * userCity：城市<br>
	 * userEmail:邮件<br>
	 * userMobile：手机<br>
	 * userTelephone：电话
	 */
	public PageInfo<Map<String, Object>> findRoleUserRelPage(Map<String, Object> params,  PageParam pageParam) ;
	/**
	 * @方法名称 findRolePermissionRel
	 * @功能描述 <pre>获取角色所拥有的权限列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:56:19
	 * @param params
	 * roleCode:角色编号
	 * permissionCode：权限编号
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * id:主键编号
	 * roleCode:角色编号
	 * permissionCode：权限编号
	 */
	public PageInfo<Map<String, Object>> findRolePermissionRelPage(Map<String, Object> params, PageParam pageParam) ;
	/**
	 * @方法名称 findRoleMenuList
	 * @功能描述 <pre>根据角色获取出菜单权限</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月25日 上午11:18:36
	 * @param params 
	 * roleCode：角色编号
	 * @return
	 * menuCode:菜单编号 <br>,
	 * menuName：菜单名称<br>,
	 * parentMenuCode:上级菜单编号<br>,
	 * statusCd：状态<br>,
	 * url：跳转地址<br>,
	 * leafFlagCd:是否分页<br>,
	 * icon ：菜单图标<br>,
	 * menuTypeCd:菜单类型<br>,
	 * remark：备注<br>
	 */
	public List<Map<String, Object>> findRoleMenuList(Map<String, Object> params);
	/** 
	 * @方法名称 deleteRolePermissionRelTrans
	 * @功能描述 <pre>删除角色和权限的关联关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:33:53
	 * @param params
	 * roleCode：角色编号
	 */
	public void deleteRolePermissionRelTrans(Map<String, Object> params) ;
	/** 
	 * @方法名称 saveRolePermissionRelTrans
	 * @功能描述 <pre>保存角色和权限的关联关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:34:07
	 * @param params
	 * roleCode：角色编号
	 * menuCodes：菜单编号-数据以,号隔开
	 */
	public Map<String, Object> saveRolePermissionRelTrans(Map<String, Object> params) ;
}
