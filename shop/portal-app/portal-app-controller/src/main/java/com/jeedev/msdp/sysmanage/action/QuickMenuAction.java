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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageParam;
import com.jeedev.msdp.auth.client.ClientConfig;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.sys.quickmenu.api.QuickMenuApi;
import com.jeedev.msdp.sys.quickmenu.api.QuickMenuConstants;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.trace.constants.LoginUserConstants;


@Controller
@RequestMapping("/quickMenu")
public class QuickMenuAction extends BaseAppAction {
	
	@Autowired
	private UserApi userApi;
	@Autowired
	private ClientConfig clientConfig;
	@Autowired
	private QuickMenuApi quickMenuApi;
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
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVO query(@RequestObjectParam Map<String, Object> RequstObjectMap,PageParam page) {try{
		Map<String, Object> result = new HashMap<String, Object>();
	    result.put("grid", quickMenuApi.findQuickMenuPage(RequstObjectMap,page).getList());
//	    result=quickMenuApi.findQuickMenuPage(RequstObjectMap,page);
	    return this.successResponse(result);
	}catch(Exception e){
		return this.errorResponse(e.getMessage());
	}}

	@RequestMapping(value = "/queryMenuTree", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVO queryMenuTree(@RequestObjectParam Map<String, Object> RequstObjectMap,
			@CurrentUser Map<String, Object> curUserMap) {
		// 获取角色所有的全部权限
		Map<String, Object> params = new HashMap<>();
		params.put("clntendId", clientConfig.getClientId());
		params.put("roleCode", curUserMap.get(LoginUserConstants.LOGIN_USER_ROLEINF_RLID));
		List<Map<String, Object>> roleMenuList = null;
		try {
			roleMenuList = roleApi.findRoleMenuList(params);
		} catch (Exception e) {
			return this.errorResponse(e.getMessage());
		}
		List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> menuMap : roleMenuList) {
			// 跳过状态失效的数据 Colin.DZx 170921
			if (menuMap.containsKey(MenuConstants.STATUS_CD)) {
				String statusCd = (String) menuMap.get(MenuConstants.STATUS_CD);
				if ("0".equals(statusCd)) {
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
//			if (roleMenuList != null) {
//				for (Map<String, Object> check_menu : roleMenuList) {
//					String menuCode = MapUtils.getString(check_menu, MenuConstants.MENU_CODE);
//					if (id.equals(menuCode)) {
//						checked = true;// 是否已经选择
//						continue;
//					}
//				}
//			}
			map.put("checkbox", true);
			ahrList.add(map);
		}
		return this.successResponse(ahrList);
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestObjectParam Map<String, Object> reqMap) throws Exception {
		try {
			quickMenuApi.deleteQuickMenuTrans(reqMap);
			return this.successResponse("删除成功");
		} catch (Exception e) {
			return this.errorResponse(e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String, Object> RequstObjectMap,@CurrentUser Map<String, Object> curUserMap) throws Exception {
		try {
			RequstObjectMap.put(QuickMenuConstants.USER_NUM, curUserMap.get(LoginUserConstants.LOGIN_USER_USRID));
			Map<String,Object> quickMenu=quickMenuApi.getQuickMenuMap(RequstObjectMap);
			if(quickMenu!=null){
				return this.errorResponse(quickMenu.get("menuName")+"已存在！");
			}
			Map<String,Object> menu=menuApi.getMenuMap(RequstObjectMap);
			menu.put(QuickMenuConstants.USER_NUM, curUserMap.get(LoginUserConstants.LOGIN_USER_USRID));
			quickMenuApi.saveQuickMenuTrans(menu);
			return this.successResponse("保存成功");
		} catch (Exception e) {
			return this.errorResponse(e.getMessage());
		}
	}
}
