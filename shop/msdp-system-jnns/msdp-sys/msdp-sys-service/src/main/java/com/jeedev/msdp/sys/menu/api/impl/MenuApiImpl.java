package com.jeedev.msdp.sys.menu.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.sys.menu.service.ISysResMenuEventRelService;
import com.jeedev.msdp.sys.menu.service.ISysResMenuService;
import com.jeedev.msdp.sys.perm.api.PermConstants;
import com.jeedev.msdp.sys.perm.service.IPermService;
import com.jeedev.msdp.sys.role.service.ISysRoleRelPermService;
import com.jeedev.msdp.trace.IProviderSign;
import com.jeedev.msdp.trace.utils.PageUtil;
import com.jeedev.msdp.utlis.CollectionUtil;
/**
 * 
 * @类名称 MenuApiImpl.java
 * @类描述 <pre>菜单api实现类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午8:25:56
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
@Service("menuApi")
public class MenuApiImpl implements MenuApi,IProviderSign {
	/**
	 * 菜单信息服务类
	 */
	@Autowired
	private ISysResMenuService sysResMenuService;
	/**
	 * 角色权限服务类
	 */
	@Autowired
	private ISysRoleRelPermService sysRoleRelPermService;
	/**
	 * 权限信息服务类
	 */
	@Autowired
	private IPermService permService;
	/**
	 * 菜单事件服务
	 */
	@Autowired
	private ISysResMenuEventRelService sysResMenuEventRelService;
	
	/**
	 * 编码生成服务
	 */
	@Autowired
	private IEncodeService encodeService;
	
	@Override
	public PageInfo<Map<String, Object>> findMenuPage(Map<String, Object> params, PageParam pageParam) {
		String purpose = (String) params.get("purpose");
		if(purpose!=null) {
			if(purpose.equals("Assign")) {
				//用途是分配，先查询资源（按租户ID进行查询 ） ，再根据资源查询菜单
				Map<String, Object> conds = new HashMap<String,Object>();
				conds.put("tenantId", params.get("tenantId"));
				PageInfo<Map<String, Object>> permInfos = permService.findPermPage(conds, null);
				List<String> resMenuCodes = new ArrayList<String>();
				if(permInfos!=null&&permInfos.getList()!=null&&permInfos.getList().size()>0){
					for(Map<String, Object> rel:permInfos.getList()){
						resMenuCodes.add((String) rel.get(MenuConstants.RES_CODE));
					}
					params.put(MenuConstants.MENU_CODES, resMenuCodes);
					return sysResMenuService.findMenuPage(params,  pageParam);
				}else{
					return new PageInfo<>();
				}
			}
		}
		
		
		return sysResMenuService.findMenuPage(params,  pageParam);
	}

	@Override
	public Map<String, Object> getMenuMap(Map<String, Object> params) {
		return sysResMenuService.getMenuMap(params);
	}

	@Override
	public Map<String, Object> saveMenuTrans(Map<String, Object> params) {
		//有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, MenuConstants.ID);
		String menuName = MapUtils.getString(params, MenuConstants.MENU_NAME);
		String statusCd = MapUtils.getString(params, MenuConstants.STATUS_CD);
		if (menuName == null || menuName.isEmpty()) 
		   throw ExceptionFactory.getBizException("sys-00005", MenuConstants.MENU_NAME);
		if (statusCd == null || statusCd.isEmpty()) 
			   throw ExceptionFactory.getBizException("sys-00005", MenuConstants.STATUS_CD);
		if (id == null) {
			String menuCode="";
			Map<String, Object> menu=null;
			try {
				menuCode = encodeService.buildEncode("10001", "0000");
				
				//TODO　逻辑上弱校验重复性，最好数据库层做强校验
				Map<String,Object> queryMenuCond = new HashMap<String,Object>();
				queryMenuCond.put(MenuConstants.MENU_CODE, menuCode);
				
				Map<String, Object> existMenuCode = sysResMenuService.getMenuMap(queryMenuCond);
				if(existMenuCode!=null && !existMenuCode.isEmpty()) {
					 throw ExceptionFactory.getBizException("sys-menu-00001", menuCode);
				}
				
				params.put(MenuConstants.MENU_CODE, menuCode);
				menu=sysResMenuService.saveMenu(params);
				//对权限表也进行数据添加
				if(menu!=null){
					Map<String, Object> permMap=new HashMap<>();
					permMap.put(PermConstants.PERMISSION_NAME, menuName);
					permMap.put(PermConstants.PERMISSION_TYPE_CD,MapUtils.getString(params, MenuConstants.MENU_TYPE_CD));//菜单类型
					permMap.put(PermConstants.RES_CODE, menuCode);
					permMap.put(PermConstants.STATUS_CD, DictUtil.INDICATOR_NO());
					permMap.put(PermConstants.DEL_IND, DictUtil.INDICATOR_NO());
					permService.savePerm(permMap);
				}
			} catch (Exception e) {
				throw ExceptionFactory.getBizException("sys-00006");
			}
			return menu;
		}
		sysResMenuService.updateMenu(params);
		return null;
	}

	@Override
	public void deleteMenuTrans(Map<String, Object> params) {
		 Map<String,Object> queryMenuParams = new HashMap<String,Object>();
		 queryMenuParams.putAll(params);
		 //0.查询带删除的菜单
		 Map<String, Object> menu = sysResMenuService.getMenuMap(queryMenuParams);
		 
		 //1.删除菜单
		 sysResMenuService.deleteMenu(menu);
		 
		 //2.删除菜单权限
		 Map<String,Object> queryPermParams = PageUtil.createNoAutoPageMap();
		 queryPermParams.put("resCode", menu.get("menuCode"));
		 PageInfo<Map<String, Object>> permPage = permService.findPermPage(queryPermParams, null);
		 if(permPage==null || CollectionUtil.isEmpty(permPage.getList())) {
			 return ;
		 }
		 for(Map<String, Object> perm:permPage.getList()) {
			 permService.deletePerm(perm);
		 }
		 
		 //3.删除菜单下的事件
		 Map<String,Object> queryMenuEventRelParams = PageUtil.createNoAutoPageMap();
		 queryMenuEventRelParams.put("menuCode",menu.get("menuCode"));
		 PageInfo<Map<String, Object>> menuEventRelPage = sysResMenuEventRelService.findSysResMenuEventRelPage(queryMenuEventRelParams, null);
		 if(menuEventRelPage==null || CollectionUtil.isEmpty(menuEventRelPage.getList())) {
			 return ;
		 }
		 for(Map<String, Object> menuEventRel:menuEventRelPage.getList()) {
			 sysResMenuEventRelService.deleteSysResMenuEventRel(menuEventRel);
		 }
		
	}
	
	@Override
	public PageInfo<Map<String, Object>> findUserMenuPage(Map<String, Object> params, PageParam pageParam) {
		//1。如果包含角色编号，则，把角色关联的菜单code查询出来放在menuCodes
		if(params.containsKey(MenuConstants.ROLE_CODE)){
			//1.1 获取关联权限
			Map<String,Object> conds = new HashMap<String,Object>();
			conds.put(MenuConstants.ROLE_CODE, params.get(MenuConstants.ROLE_CODE));
			conds.put("tenantId", params.get("tenantId"));
			PageInfo<Map<String, Object>> rolePermissionRels = sysRoleRelPermService.findRolePermissionRelPage(conds, null);
			List<String> permcodes = new ArrayList<String>();
			//1.2 获取关联资源code
			if(rolePermissionRels!=null&&rolePermissionRels.getList()!=null&&rolePermissionRels.getList().size()>0){
				for(Map<String, Object> rel:rolePermissionRels.getList()){
					permcodes.add((String) rel.get(MenuConstants.PERMISSION_CODE));
				}
				conds = new HashMap<String,Object>();
				conds.put(MenuConstants.PERMISSION_CODES, permcodes);
				conds.put("tenantId", params.get("tenantId"));
				PageInfo<Map<String, Object>> permInfos = permService.findPermPage(conds, null);
				
				List<String> resMenuCodes = new ArrayList<String>();
				if(permInfos!=null&&permInfos.getList()!=null&&permInfos.getList().size()>0){
					for(Map<String, Object> rel:permInfos.getList()){
						resMenuCodes.add((String) rel.get(MenuConstants.RES_CODE));
					}
					params.put(MenuConstants.MENU_CODES, resMenuCodes);
					return sysResMenuService.findMenuPage(params, pageParam);
				}else{
					return new PageInfo<>(new ArrayList<Map<String, Object>>());
				}
				
			}else{
				return new PageInfo<>(new ArrayList<Map<String, Object>>());
			}
		}
		//2。查询
		return sysResMenuService.findMenuPage(params, pageParam);
	}

	@Override
	public PageInfo<Map<String, Object>> findMenuEventRelPage(Map<String, Object> params, PageParam pageParam) {
		return sysResMenuEventRelService.findSysResMenuEventRelPage(params, pageParam);
	}

	@Override
	public Map<String, Object> saveMenuEventRelTrans(Map<String, Object> params) {
		return sysResMenuEventRelService.saveSysResMenuEventRel(params);
	}

	@Override
	public void deleteMenuEventRelTrans(Map<String, Object> params) {
		sysResMenuEventRelService.deleteSysResMenuEventRel(params);
		
	}

}
