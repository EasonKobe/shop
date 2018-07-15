package com.jeedev.msdp.sys.perm.api;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 PermApi.java
 * @类描述 <pre>权限对外接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 上午10:36:02
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
public interface PermApi {
	/**
	 * @方法名称 findPermPage
	 * @功能描述 <pre>分页查询权限信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:37:20
	 * @param params
	 * id：主键编号</br>
	 * permissionCode:权限编号</br>
	 * permissionCodes:权限编号集合 </br>
	 * permissionTypeCd：权限类型 -菜单0页面元素1文件2</br>
	 * resCode：资源编号</br>
	 * userNum：用户编号</br>
	 * roleCode：角色编号</br>
	 * @param pageNum
	 * @param pageSize
	 * @return
	 *  * id：主键编号</br>
	 * permissionCode:权限编号</br>
	 * permissionTypeCd：权限类型 -菜单0页面元素1文件2</br>
	 * resCode：资源编号</br>
	 * permissionName：权限名称</br>
	 * isBuiltIn： 是否系统内置权限(否0是1)</br>
	 * statusCd：状态：无效0有效1</br>
	 * remark：备注</br>
	 */
	public PageInfo<Map<String, Object>> findPermPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * @方法名称 getPermMap
	 * @功能描述 <pre>获取权限详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:37:45
	 * @param params
	 * id：主键编号</br>
	 * permissionCode:权限编号</br>
	 * permissionTypeCd：权限类型 -菜单0页面元素1文件2</br>
	 * resCode：资源编号</br>
	 * permissionName：权限名称</br>
	 * @return
	 * id：主键编号</br>
	 * permissionCode:权限编号</br>
	 * permissionTypeCd：权限类型 -菜单0页面元素1文件2</br>
	 * resCode：资源编号</br>
	 * permissionName：权限名称</br>
	 * isBuiltIn： 是否系统内置权限(否0是1)</br>
	 * statusCd：状态：无效0有效1</br>
	 * remark：备注</br>
	 */
	public Map<String, Object> getPermMap(Map<String, Object> params);
	/**
	 * @方法名称 savePermTrans
	 * @功能描述 <pre>新增&保存权限信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:38:17
	 * @param params
	 * permissionCode:权限编号</br>
	 * permissionTypeCd：权限类型 -菜单0页面元素1文件2</br>
	 * resCode：资源编号</br>
	 * permissionName：权限名称</br>
	 * isBuiltIn： 是否系统内置权限(否0是1)</br>
	 * statusCd：状态：无效0有效1</br>
	 * remark：备注</br>
	 * @return
	 * id：主键编号</br>
	 */
	public Map<String, Object> savePermTrans(Map<String, Object> params);
	/**
	 * @方法名称 deletePermTrans
	 * @功能描述 <pre>删除权限信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:38:43
	 * @param params
	 * id：主键编号</br>
	 * @return
	 */
	public void deletePermTrans(Map<String, Object> params);
	
}
