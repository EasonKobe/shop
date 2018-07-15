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
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;

/**
 * 菜单管理action
 * @类名称 MenuAction.java
 * @类描述 <pre></pre>
 * @作者 chenjiancong chenjiancong@tansun.com.cn
 * @创建时间 2017年9月1日 上午8:44:01
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjiancong 	2017年9月1日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/menu")
public class MenuAction extends BaseAppAction {
	@Autowired
	private MenuApi menuApi;
	
	/**
	 * 数据权限api
	 */
	@Autowired
	private DataAuthApi dataAuthApi;
	
	public String ID="id";//菜单id
	public String DELIND="delInd";//删除标志
	public String NAME="name";//菜单名称
	public String OPEN="open";//是否展开子节点
	public String CHECKED="checked";//是否选中
	public String PID="pId";//父级id
	public String PARENTMENUNAME="parentMenuName";//父级菜单名称

	/**
	 * @方法名称 tree
	 * @功能描述 <pre>获取菜单树</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:25:31
	 * @param reqMap 请求参数
	 * @return 菜单集合
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/menuTree", method = RequestMethod.POST)
	public ResponseVO tree(@RequestObjectParam Map<String, Object> reqMap) throws Exception {

		// 校验入参塞入reqMap； 
		PageInfo<Map<String, Object>> pageInfo = menuApi.findMenuPage(reqMap, null);
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
			map.put(CHECKED, false);
			ahrList.add(map);
		}
		return this.successResponse(ahrList);
	}

	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据菜单编码获取菜单信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:26:15
	 * @param reqMap menuCode 菜单编码
	 * @return 菜单对象map
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResponseVO get(@RequestObjectParam Map<String, Object> reqMap) throws Exception { 
//		reqMap.put("menuCode", MapUtils.getString(params, "menuCode"));
		Map<String, Object> orgMap = menuApi.getMenuMap(reqMap);
		String parentId = MapUtils.getString(orgMap, MenuConstants.PARENT_MENU_CODE);
		if (StringUtil.isNotEmpty(parentId)) {
			// 获取父节点信息
			Map<String, Object> parentMap = new HashMap<>();
			parentMap.put(MenuConstants.MENU_CODE, parentId);// 父节点
			Map<String, Object> resultParent = menuApi.getMenuMap(parentMap);
			orgMap.put(PARENTMENUNAME, MapUtils.getString(resultParent, MenuConstants.MENU_NAME));
		} else {
			orgMap.put(PARENTMENUNAME, "");
		}
		return this.successResponse(orgMap);
	}
	
	/**
	 * 
	 * @方法名称 delete
	 * @功能描述 <pre>根据id删除菜单信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:28:26
	 * @param params id 菜单id
	 * @return 成功或失败
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestObjectParam Map<String, Object> params) throws Exception { 
		try{
			menuApi.deleteMenuTrans(params);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 save
	 * @功能描述 <pre>新增或修改菜单信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:29:30
	 * @param requstObjectMap 请求参数
	 *         menuName 菜单名称
	 *         menuTypeCd 菜单类型
	 *         parentMenuCode 父级菜单编号
	 *         url 菜单url
	 *         sort 排序
	 *         icon 菜单图标
	 *         id 菜单id
	 *         remark 菜单备注
	 *         statusCd 状态
	 * @return 成功或失败
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String, Object> requstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception { 
		try{
			String clntendId=(String)requstObjectMap.get(UserConstants.CLNTEND_ID);
			if(requstObjectMap.containsKey(MenuConstants.LEAF_FLAG_CD)){
				String  root=(String)requstObjectMap.get(MenuConstants.LEAF_FLAG_CD);
				//根节点
				if(root.equals("0")){
					//分前后端菜单
					requstObjectMap.put(MenuConstants.PARENT_MENU_CODE,clntendId);
				}
			}
			if(StringUtil.isBlank(clntendId)){
				clntendId=MapUtils.getString(curUserMap, UserConstants.CLNTEND_ID);
			}
			requstObjectMap.put(UserConstants.TENANT_ID, MapUtils.getString(curUserMap, UserConstants.TENANT_ID));
			requstObjectMap.put(UserConstants.CLNTEND_ID,clntendId);
			menuApi.saveMenuTrans(requstObjectMap );
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * 
	 * @方法名称 selector
	 * @功能描述 <pre>菜单下拉列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月21日 下午12:37:06
	 * @param reqMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/selector")
	public ResponseVO selector(@RequestObjectParam Map<String,Object> reqMap,PageParam page) throws Exception{
		PageInfo<Map<String, Object>> pageInfo = menuApi.findMenuPage(reqMap, page); 
		Map<String, Object> result = new HashMap<String, Object>();
	    result.put("grid", pageInfo);
	    return this.successResponse(result); 
	}
	/**
	 * 查询菜单下的事件关联关系
	 * @方法名称 queryEvent
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午3:23:28
	 * @param reqMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/eventRel/query")
	public ResponseVO queryEventRel(@RequestObjectParam Map<String,Object> reqMap,PageParam page) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		
		String menuCode = MapUtil.getString(reqMap, "menuCode");
		String eventName = MapUtil.getString(reqMap, "eventName");
		String url = MapUtil.getString(reqMap, "url");
		
		//无过滤条件默认显示为空
		if(StringUtil.isBlank(menuCode)){
			result.put("grid",new PageInfo<>());
			return this.successResponse(result);
		}
		Map<String,Object> condMap =new HashMap<String,Object>();
		condMap.put("menuCode", menuCode);
		condMap.put("eventName", eventName);
		condMap.put("url", url);
		PageInfo<Map<String, Object>> pageInfo = menuApi.findMenuEventRelPage(condMap, page); 
		
	    result.put("grid", pageInfo);
	    return this.successResponse(result); 
	}
	
	/**
	 * 删除菜单下的事件关联关系
	 * @方法名称 deleteEvent
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午3:22:55
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/eventRel/delete")
	public ResponseVO deleteEventRel(@RequestObjectParam Map<String, Object> params) throws Exception { 
		try{
			menuApi.deleteMenuEventRelTrans(params);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * 新增菜单下的事件关联关系
	 * @方法名称 saveEvent
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午3:23:09
	 * @param requstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/eventRel/save")
	public ResponseVO saveEventRel(@RequestObjectParam Map<String, Object> requstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception { 
		try{
			menuApi.saveMenuEventRelTrans(requstObjectMap );
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	
	
	/**
	 * 查询菜单下的事件信息及数据权限
	 * @方法名称 queryEvent
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午3:23:28
	 * @param reqMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/eventDataauth/query")
	public ResponseVO queryEvent(@RequestObjectParam Map<String,Object> reqMap,PageParam page) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		
		String menuCodesStr= MapUtil.getString(reqMap, "menuCodes");
		String roleCode = MapUtil.getString(reqMap, "roleCode");
		
		//1。查询菜单下的事件 
		//1.1获得菜单列表
		//无过滤条件默认显示为空
		if(StringUtil.isBlank(menuCodesStr)){
			result.put("grid",new PageInfo<>());
			return this.successResponse(result);
		}
		
		List<Object> menuCodes = new ArrayList<Object>();
		for(String menuCode:menuCodesStr.split(",")) {
			if(StringUtil.isNotBlank(menuCode))
				menuCodes.add(menuCode);
		}
		
		//无过滤条件默认显示为空
		if(CollectionUtil.isEmpty(menuCodes)){
			result.put("grid",new PageInfo<>());
			return this.successResponse(result);
		}
		//1.2查询事件
		Map<String,Object> condMap =new HashMap<String,Object>();
		condMap.put("menuCodes", menuCodes);
		PageInfo<Map<String, Object>> pageInfo = menuApi.findMenuEventRelPage(condMap, page); 
		//获得已设置的数据权限 
		if(pageInfo==null) {
			 result.put("grid", new PageInfo<>());
			  return this.successResponse(result); 
		}
		result.put("grid", pageInfo);		
		//2.根据已查询的事件和角色查询已设置的数据权限
		//是否返回数据权限信息
		Boolean returnDataAuth = MapUtil.getBoolean(reqMap, "returnDataAuth");
		if(!returnDataAuth) {
			  return this.successResponse(result); 
		}
		
		//查询参数
		Map<String,Object> dataauthCond = new HashMap<String,Object>();
		//2.1查询条件：场景集合
		List<Object> eventCodes = new ArrayList<Object>();
		List<Map<String, Object>> list = pageInfo.getList();
		for(Map<String, Object> event:list) {
			eventCodes.add(event.get("eventCode"));
		}
		
		
		dataauthCond.put("eventCodes", eventCodes);
		//2.2查询条件：角色
		dataauthCond.put("roleCode", roleCode);
		dataauthCond.put("queryType", "0");//精确查询
		//2.3查询数据权限		
		PageInfo<Map<String, Object>> dataauthList = dataAuthApi.findDataAuthPage(dataauthCond,null);
		//3.把数据权限追加待回返的列表中
		if(dataauthList!=null) {
			for(Map<String, Object> datauth:dataauthList.getList() ) {
				String eventCode = MapUtil.getString(datauth, "eventCode");
				for(Map<String, Object> eventMap:list) {
					if(eventCode.equals(eventMap.get("eventCode"))){
						eventMap.put("modelCode", datauth.get("modelCode"));
						eventMap.put("modelName", datauth.get("modelName"));
						eventMap.put("dataauthId", datauth.get("id"));
					}
				}
			}
		}
		 
		return this.successResponse(result); 
		
		
	}
}
