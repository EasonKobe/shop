package com.jeedev.msdp.sysmanage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.dataauth.api.DataAuthApi;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.sys.role.api.RoleConstants;
import com.jeedev.msdp.trace.constants.LoginUserConstants;
import com.jeedev.msdp.utlis.StringUtil;

/**
 * @类名称 RoleAction.java
 * @类描述 <pre>权限管理action</pre>
 * @作者 chenjiancong chenjiancong@tansun.com.cn
 * @创建时间 2017年9月1日 上午8:44:36
 * @版本 1.00
 * @修改记录
 *       <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjiancong 	2017年9月1日             
 *     ----------------------------------------------
 *       </pre>
 */
@Controller
@RequestMapping("/role")
public class RoleAction extends BaseAppAction {

	@Autowired
	private RoleApi roleApi;
	@Autowired
	private MenuApi menuApi;

	
	/**
	 * 角色id-树显示
	 */
	public final String ID="id";
	/**
	 * 角色名称-树显示
	 */
	public final String NAME="name";
	/**
	 * 父角色id-树显示
	 */
	public final String PID="pId";
	/**
	 * 父角色名称-树显示
	 */
	public final String PARENT_ROLE_NAME="parentRoleName";
	/**
	 * 是否展开
	 */
	public final String OPEN="open";
	/**
	 * 失败
	 */
	public final String CHECKED="checked";
	
	/**
	 * @方法名称 tree
	 * @功能描述 <pre>根据条件分页返回角色树</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:21:52
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/roleTree", method = RequestMethod.POST)
	public ResponseVO tree(@RequestObjectParam Map<String, Object> RequstObjectMap) throws Exception {
		PageInfo<Map<String, Object>> pageInfo = roleApi.findRolePage(RequstObjectMap,null);
		List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> deptMap : pageInfo.getList()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ID, deptMap.get(RoleConstants.ROLE_CODE));
			map.put(NAME, deptMap.get(RoleConstants.ROLE_NAME));
			String pId = MapUtils.getString(deptMap, RoleConstants.PARENT_ROLE_CODE);
			if (pId == null)
				map.put(PID, "0");
			else
				map.put(PID, pId);
			map.put(OPEN, false);
			map.put(CHECKED, true);
			ahrList.add(map);
		}
		return this.successResponse(ahrList);
	}

	/**
	 * 
	 * @方法名称 roleMenuPerm
	 * @功能描述 <pre>根据角色获取可以分配的、已分配的权限菜单树</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月9日 下午8:07:09
	 * @param requstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/roleMenuAssigned", method = RequestMethod.POST)
	public ResponseVO roleMenuAssigned(@CurrentUser Map<String,Object> curUserMap,@RequestObjectParam Map<String, Object> requstObjectMap, PageParam page) throws Exception {
		requstObjectMap.put("clntendId", curUserMap.get(LoginUserConstants.LOGIN_USER_CLNTEND_ID));
    	PageInfo<Map<String, Object>> pageInfo =menuApi.findUserMenuPage(requstObjectMap, null);
    	List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> deptMap : pageInfo.getList()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ID, deptMap.get(MenuConstants.MENU_CODE));
			map.put(NAME, deptMap.get(MenuConstants.MENU_NAME));
			String pId = MapUtils.getString(deptMap, MenuConstants.PARENT_MENU_CODE);
			if (pId == "10001" && pId == "10002")
				map.put("pId", "0");
			else
				map.put(PID, pId);
			map.put(OPEN, false);
			map.put(CHECKED, true);
			map.put("type", deptMap.get(MenuConstants.MENU_TYPE_CD));
			ahrList.add(map);
		}
		return this.successResponse(ahrList);
	}
	/**
	 * @方法名称 roleMenu
	 * @功能描述 <pre>根据角色获取可以分配的、已分配的权限菜单树</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:22:46
	 * @param requstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/roleMenu", method = RequestMethod.POST)
	public ResponseVO roleMenu(@RequestObjectParam Map<String, Object> requstObjectMap, PageParam page) throws Exception {
		// 校验入参塞入reqMap；
		// 获取所有菜单数据
		requstObjectMap.put("purpose", "Assign");//用途是分配菜单 ，只拿出有资源信息的
		PageInfo<Map<String, Object>> pageInfo = menuApi.findMenuPage(requstObjectMap, null);
		// 获取角色所有的全部权限
		Map<String, Object> params = new HashMap<>();
		params.put(RoleConstants.ROLE_CODE, MapUtils.getString(requstObjectMap, RoleConstants.ROLE_CODE));// 获取选择到的roleCode
		List<Map<String, Object>> roleMenuList = null;
		try {
			roleMenuList = roleApi.findRoleMenuList(params);
		} catch (Exception e) {
			return this.errorResponse(e.getMessage());
		}
		List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> menuMap : pageInfo.getList()) {
			//跳过状态失效的数据 Colin.DZx 170921
    		if(menuMap.containsKey(MenuConstants.STATUS_CD)){
    			String statusCd = (String) menuMap.get(MenuConstants.STATUS_CD);
    			if("0".equals(statusCd)){
    				continue;
    			}
    		}
			Map<String, Object> map = new HashMap<String, Object>();
			String pId = MapUtils.getString(menuMap, MenuConstants.PARENT_MENU_CODE);
			String id = MapUtils.getString(menuMap, MenuConstants.MENU_CODE);
			map.put(ID, id);
			map.put(NAME, menuMap.get(MenuConstants.MENU_NAME));
			if (pId == "10001" && pId == "10002")
				map.put(PID, "0");
			else
				map.put(PID, pId);
			map.put(OPEN, false);
			boolean checked = false;
			if (roleMenuList != null) {
				for (Map<String, Object> check_menu : roleMenuList) {
					String menuCode = MapUtils.getString(check_menu, MenuConstants.MENU_CODE);
					if (id.equals(menuCode)) {
						checked = true;// 是否已经选择
						continue;
					}
				}
			}
			map.put(CHECKED, checked);
			ahrList.add(map);
		}
		return this.successResponse(ahrList);
	}

	
	
	/**
	 * @方法名称 roleUser
	 * @功能描述 <pre>根据角色返回对应的用户列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:23:34
	 * @param RequstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/roleUser", method = RequestMethod.POST)
	public ResponseVO roleUser(@RequestObjectParam Map<String, Object> RequstObjectMap, PageParam page) throws Exception {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			//无过滤条件默认显示为空
			if(RequstObjectMap.get("roleCode")==null){
				result.put("grid",new PageInfo<>());
				return this.successResponse(result);
			}
			result.put("grid", roleApi.findRoleUserRelPage(RequstObjectMap, page));
			return this.successResponse(result);
		} catch (Exception e) {
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据编号ID获取角色的详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:24:09
	 * @param requstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResponseVO get(@RequestObjectParam Map<String, Object> requstObjectMap) throws Exception { 
		Map<String, Object> roleMap = roleApi.getRoleMap(requstObjectMap);
		if(roleMap!=null) {
			String parentId = MapUtils.getString(roleMap, RoleConstants.PARENT_ROLE_CODE);
			
			if (StringUtil.isNotEmpty(parentId)) {
				// 获取父节点信息
				Map<String, Object> parentMap = new HashMap<>();
				parentMap.put(RoleConstants.ROLE_CODE, parentId);// 父节点
				Map<String, Object> resultParent = roleApi.getRoleMap(parentMap);
				roleMap.put(PARENT_ROLE_NAME, MapUtils.getString(resultParent, RoleConstants.ROLE_NAME));
			} else {
				roleMap.put(PARENT_ROLE_NAME, "");
			}
		}else {
			roleMap = new HashMap<String,Object>();
		}
		

		return this.successResponse(roleMap);
	}

	/**
	 * @方法名称 delete
	 * @功能描述 <pre>根据角色编号ID删除对应实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:24:29
	 * @param requstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestObjectParam Map<String, Object> requstObjectMap) throws Exception {
		try {
			roleApi.deleteRoleTrans(requstObjectMap);
			return this.successResponse("删除成功");
		} catch (Exception e) {
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 save
	 * @功能描述 <pre>新增或者保存角色实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:24:53
	 * @param requstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String, Object> requstObjectMap) throws Exception {
		try{
			roleApi.saveRoleTrans(requstObjectMap);
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 saveRoleMuen
	 * @功能描述 <pre>保存或修改角色拥有的权限菜单</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:25:19
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRoleMuen")
	public ResponseVO saveRoleMuen(@RequestObjectParam Map<String, Object> RequstObjectMap) throws Exception {
		try{
			roleApi.saveRolePermissionRelTrans(RequstObjectMap);
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 selector
	 * @功能描述 <pre>提供给页面展示的下拉角色数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:25:55
	 * @param RequstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selector")
	public ResponseVO selector(@RequestObjectParam Map<String, Object> RequstObjectMap, PageParam page) throws Exception {
		PageInfo<Map<String, Object>> roles = roleApi.findRolePage(RequstObjectMap, page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("grid", roles);
		return this.successResponse(result);
	}
}
