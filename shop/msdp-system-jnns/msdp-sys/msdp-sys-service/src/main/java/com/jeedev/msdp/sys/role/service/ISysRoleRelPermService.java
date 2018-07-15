package com.jeedev.msdp.sys.role.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

/**
 * 
 * @类名称 ISysRoleRelPermService.java
 * @类描述 <pre></pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月16日 下午9:01:17
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月16日             
 *     ----------------------------------------------
 * </pre>
 */
public interface ISysRoleRelPermService {
	/**
	 * 
	 * @方法名称 findRolePermissionRel
	 * @功能描述 <pre>获取角色所拥有的权限列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:56:19
	 * @param params
	 * @param  pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findRolePermissionRelPage(Map<String, Object> params, PageParam pageParam) ;
	/**
	 * 
	 * @方法名称 deleteRolePermissionRelTrans
	 * @功能描述 <pre>删除角色和权限的关联关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:33:53
	 * @param params
	 */
	public void deleteRolePermissionRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 saveRolePermissionRelTrans
	 * @功能描述 <pre>保存角色和权限的关联关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:34:07
	 * @param params
	 */
	public Map<String, Object> saveRolePermissionRel(Map<String, Object> params);

}
