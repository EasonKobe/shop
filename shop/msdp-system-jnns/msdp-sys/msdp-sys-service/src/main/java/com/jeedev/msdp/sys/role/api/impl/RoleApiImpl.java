package com.jeedev.msdp.sys.role.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.perm.service.IPermService;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.sys.role.api.RoleConstants;
import com.jeedev.msdp.sys.role.service.ISysRoleRelPermService;
import com.jeedev.msdp.sys.role.service.ISysRoleService;
import com.jeedev.msdp.sys.user.service.IUserRoleRelService;
import com.jeedev.msdp.trace.IProviderSign;
 
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
@Service("roleApi")
public class RoleApiImpl  implements RoleApi,IProviderSign {
	/**
	 * 角色服务
	 */
	@Autowired
	private ISysRoleService sysRoleService;
	/**
	 * 角色与权限关系服务
	 */
	@Autowired
	private ISysRoleRelPermService sysRoleRelPermService;
	/**
	 * 用户与角色关系服务
	 */
	@Autowired
	private IUserRoleRelService userRoleRelService;
	/**
	 * 权限服务
	 */
	@Autowired
	private IPermService permService;
	/**
	 * 菜单服务
	 */
	@Autowired
	private MenuApi menuApi;
	/**
	 * 序号生成器编号
	 */
	@Autowired
	private IEncodeService encodeService;

	@Override
	public PageInfo<Map<String, Object>> findRolePage(Map<String, Object> params, PageParam pageParam) {
		return sysRoleService.findRolePage(params,pageParam);
	}

	@Override
	public Map<String, Object> getRoleMap(Map<String, Object> params) {
		return sysRoleService.getRoleMap(params);
	}

	@Override
	public Map<String, Object> saveRoleTrans(Map<String, Object> params) {
		// 有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, RoleConstants.Id);
		if (id == null) {
			
			String roleName = MapUtils.getString(params, RoleConstants.ROLE_NAME);
			if (roleName == null || roleName.isEmpty())
				throw ExceptionFactory.getBizException("sys-role-00001");
			try {
				String roleCode = encodeService.buildEncode("10002", "0000");
				params.put(RoleConstants.ROLE_CODE, roleCode);
				
			} catch (Exception e) { 
				throw ExceptionFactory.getBizException("sys-00006");
			}
			return sysRoleService.saveRole(params);
		}
		sysRoleService.updateRole(params);
		return null;
	}

	@Override
	public void deleteRoleTrans(Map<String, Object> params) {
		 if(checkRoleRel(params)){
			 //有关联数据，无法删除
			 throw ExceptionFactory.getBizException("sys-role-00003");
		 }
		sysRoleService.deleteRole(params);
	}
	
	
	/**
	 * 
	 * @方法名称 checkRoleRel
	 * @功能描述 <pre>判断是否有关联存在</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月22日 下午3:02:45
	 * @param orgCode
	 * @return true 有 false 没有
	 */
	public boolean checkRoleRel(Map<String, Object> params){
		 //如果有关联角色数据，则无法删除
		String roleCode=null;
		boolean result=false;
		if(params!=null){
			 if(params.containsKey(RoleConstants.Id)){
				 //获取角色编码
				 String id=MapUtils.getString(params, RoleConstants.Id);
				 Map<String,Object> queryMap=new HashMap<>();
				 queryMap.put(RoleConstants.Id, id);
				 Map<String,Object> role=sysRoleService.getRoleMap(queryMap);
				 if(role!=null)
					 roleCode=MapUtils.getString(role, RoleConstants.ROLE_CODE);
			 }
			 if(params.containsKey(RoleConstants.ROLE_CODE)){
				 roleCode=MapUtils.getString(params, RoleConstants.ROLE_CODE);
			 }
			Map<String,Object> parmMap=new HashMap<>();
			parmMap.put(RoleConstants.ROLE_CODE, roleCode);
			//是否有关联用户 
			PageInfo<Map<String, Object>> plu =userRoleRelService.findUserRoleRelPage(parmMap, null);
			if(plu.getTotal()>0){
				return true;
			}
		}
		return result;
	}

	@Override
	public PageInfo<Map<String, Object>> findRoleUserRelPage(Map<String, Object> params, PageParam pageParam) {
		return userRoleRelService.findUserRoleRelPage(params, pageParam);
	}

	@Override
	public PageInfo<Map<String, Object>> findRolePermissionRelPage(Map<String, Object> params, PageParam pageParam) {
		return sysRoleRelPermService.findRolePermissionRelPage(params, pageParam);
	}

	public List<Map<String, Object>> findRoleMenuList(Map<String, Object> params){
		//获取角色和权限关系表
		String roleCode=MapUtils.getString(params, RoleConstants.ROLE_CODE);
		if(roleCode!=null){
			PageInfo<Map<String, Object>> menuInfo =menuApi.findUserMenuPage(params,null);
			return menuInfo.getList();
		}
		return null;
	}

	@Override
	public void deleteRolePermissionRelTrans(Map<String, Object> params) {
		sysRoleRelPermService.deleteRolePermissionRel(params);
	}
	
	@Override
	public Map<String, Object> saveRolePermissionRelTrans(Map<String, Object> params) {
		//获取菜单列表
		String menuCodes=MapUtils.getString(params, "menuCodes");
		Map<String, Object> menuParams=new HashMap<>();
		String[] menus = menuCodes.split(","); 
		List<String> menusList=new ArrayList<String>();
		for(String str:menus){
			menusList.add(str);
		}
		menuParams.put("resCodes", menusList);//菜单编号
		menuParams.put("tenantId", MapUtils.getString(params, "tenantId"));//组户ID
		//根据菜单编号获取权限编号
		PageInfo<Map<String, Object>> permsInfo=permService.findPermPage(menuParams, null);
		//权限编号
		List<String> code_list=new ArrayList<String>();
		for(Map<String, Object> m:permsInfo.getList()){
			String permCode=MapUtils.getString(m, RoleConstants.PERMISSION_CODE);
			if(permCode!=null){
				code_list.add(permCode);
			}
		} 
		params.put("permissionCodes", code_list);
		return sysRoleRelPermService.saveRolePermissionRel(params);
	}
	
}
