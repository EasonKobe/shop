package com.jeedev.msdp.sysmanage.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jeedev.msdp.utlis.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.captcha.AuthUtils;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.HeadInit;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.sys.role.api.RoleConstants;
import com.jeedev.msdp.sys.tenant.api.TenantApi;
import com.jeedev.msdp.sys.tenant.api.TenantConstants;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.trace.constants.HeadConstants;
import com.jeedev.msdp.utlis.ExcelReadUtil;
import com.jeedev.msdp.utlis.StringUtil;

/**
 * 
 * @类名称 TenantAction
 * @类描述 <pre>租户管理</pre>
 * @作者 linyixing
 * @创建时间 2017年10月02日 上午10:05:08
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	lisongtao 	2017年8月22日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "/tenant")
public class TenantAction extends BaseAppAction {
	
	@Autowired
	private TenantApi tenantApi;
	
	@Autowired
	private MenuApi menuApi;
	
	@Autowired
	private RoleApi roleApi;
	
	
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
	 * @方法名称 query
	 * @功能描述 <pre>根据条件分页获取租户列表</pre>
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	@HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
	public ResponseVO query(@RequestObjectParam Map<String,Object> RequstObjectMap ,@CurrentUser Map<String,Object> curUserMap,PageParam page) throws Exception {	
		try{
			Map<String, Object> result = new HashMap<String, Object>();
		    result.put("grid", tenantApi.findTenantPage(RequstObjectMap, page));
		    return this.successResponse(result);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据租户编号ID获取租户详情</pre>
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get")
    @ResponseBody
    @HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
    public ResponseVO get(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception { 
    	String tenantNum = MapUtils.getString(RequstObjectMap, TenantConstants.TENANT_ID);
    	String id = MapUtils.getString(RequstObjectMap, TenantConstants.ID);
    	if (!StringUtil.isEmpty(tenantNum) || !StringUtil.isEmpty(id)) {
    		try{
	    		//查询租户信息
	    		Map<String, Object> tenantMap = tenantApi.getTenantMap(RequstObjectMap);
	    		return successResponse(tenantMap);
	    	}catch(Exception e){
				return this.errorResponse(e.getMessage());
			}
		}else{
			return successResponse("查询条件不存在");
		}
    }
	/**
	 * @方法名称 save
	 * @功能描述 <pre>新增或修改租户的详细信息</pre>
	 * @param requstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
 	@RequestMapping(value = "/save")
    @ResponseBody
    @HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
    public ResponseVO save(@RequestObjectParam Map<String,Object> requstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
		if (!StringUtil.isEmpty(MapUtils.getString(requstObjectMap, TenantConstants.USER_NUM)) || !StringUtil.isEmpty(MapUtils.getString(requstObjectMap, TenantConstants.ID))) {//修改
			String password = (String) requstObjectMap.get(TenantConstants.PASSWORD);
			String oldpassword = (String) requstObjectMap.get(TenantConstants.OLDPASSWORD);
			if (StringUtil.isNotBlank(password) && !password.equals(oldpassword)) {//证明修改了密码
				String salt = (String) requstObjectMap.get(TenantConstants.SALT);
				String passWord = AuthUtils.encryptPassword((String) requstObjectMap.get(TenantConstants.LOGIN_NAME), password, salt);
				requstObjectMap.put(TenantConstants.PASSWORD, passWord);
			}
			Map<String, Object> tenantMap = tenantApi.saveTenantMap(requstObjectMap);
			return successResponse(tenantMap, "修改成功");
		} else {//保存

			String salt = AuthUtils.getSalt();
			String passWord = AuthUtils.encryptPassword((String) requstObjectMap.get(TenantConstants.LOGIN_NAME), (String) requstObjectMap.get(TenantConstants.PASSWORD), salt);
			requstObjectMap.put(TenantConstants.STATUS_CD, "0");//默认租户为未生效装填
			requstObjectMap.put(TenantConstants.PASSWORD, passWord);
			requstObjectMap.put(TenantConstants.SALT, salt);
			requstObjectMap.put(UserConstants.TENANT_ID, MapUtils.getString(curUserMap, UserConstants.TENANT_ID));
			requstObjectMap.put(UserConstants.CLNTEND_ID, MapUtils.getString(curUserMap, UserConstants.CLNTEND_ID));
			Map<String, Object> tenantMap = null;
			try {
				tenantMap = tenantApi.saveTenantMap(requstObjectMap);
			} catch (Exception e) {
				return this.errorResponse(e.getMessage());
			}
			return successResponse(tenantMap, "保存成功");
		}
    }
 	
 	@RequestMapping(value = "/roleMenu/save")
    @ResponseBody
    @HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
 	 public ResponseVO saveRoleMenu(@RequestObjectParam Map<String,Object> requstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
 		tenantApi.saveTenantRole(requstObjectMap);
 		return successResponse("保存成功");
     }
	/**
	 * @方法名称 delete
	 * @功能描述 <pre>根据租户编号ID删除租户实例</pre>
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	 @HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
	public ResponseVO delete(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception {
    	String id = MapUtils.getString(RequstObjectMap, TenantConstants.ID);
    	String statusCd = MapUtils.getString(RequstObjectMap, TenantConstants.STATUS_CD);

    	if (StringUtil.isEmpty(id)) {
			return this.errorResponse("租户id不能为空");
		}
    	if (statusCd.equals("1")) {
			return this.errorResponse("生效后不可删除！");
		}
    	tenantApi.deleteTenant(RequstObjectMap);
    	//调用删除方法
		return this.successResponse("删除成功");
	}
	
	/**
	 * @方法名称 effect
	 * @功能描述 <pre>使租户生效，使租户对应的租户能登录</pre>
	 * @param requstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
 	@RequestMapping(value = "/effect")
    @ResponseBody
    @HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
    public ResponseVO effect(@RequestObjectParam Map<String,Object> requstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
		if (!StringUtil.isEmpty(MapUtils.getString(requstObjectMap, TenantConstants.USER_NUM)) || !StringUtil.isEmpty(MapUtils.getString(requstObjectMap, TenantConstants.ID))) {//修改
			requstObjectMap.put(TenantConstants.STATUS_CD, "1");//默认租户为生效装填
			String password = (String) requstObjectMap.get(TenantConstants.PASSWORD);
			String oldpassword = (String) requstObjectMap.get(TenantConstants.OLDPASSWORD);
			if (StringUtil.isNotBlank(password) && !password.equals(oldpassword)) {//证明修改了密码
				String salt = (String) requstObjectMap.get(TenantConstants.SALT);
				String passWord = AuthUtils.encryptPassword((String) requstObjectMap.get(TenantConstants.LOGIN_NAME), password, salt);
				requstObjectMap.put(TenantConstants.PASSWORD, passWord);
			}
			Map<String, Object> tenantMap = tenantApi.saveTenantMap(requstObjectMap);
			return successResponse(tenantMap, "生效成功");
		} else {//保存

			String salt = AuthUtils.getSalt();
			String passWord = AuthUtils.encryptPassword((String) requstObjectMap.get(TenantConstants.LOGIN_NAME), (String) requstObjectMap.get(TenantConstants.PASSWORD), salt);
			requstObjectMap.put(TenantConstants.PASSWORD, passWord);
			requstObjectMap.put(TenantConstants.SALT, salt);
			requstObjectMap.put(TenantConstants.STATUS_CD, "1");//默认租户为生效装填
			requstObjectMap.put(UserConstants.TENANT_ID, MapUtils.getString(curUserMap, UserConstants.TENANT_ID));
			requstObjectMap.put(UserConstants.CLNTEND_ID, MapUtils.getString(curUserMap, UserConstants.CLNTEND_ID));
			Map<String, Object> tenantMap = null;
			try {
				tenantMap = tenantApi.saveTenantMap(requstObjectMap);
			} catch (Exception e) {
				return this.errorResponse(e.getMessage());
			}
			return successResponse(tenantMap, "生效成功");
		}
	}
	@RequestMapping(value = "/invalid")
	@ResponseBody
	@HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
	public ResponseVO invalid(@RequestObjectParam Map<String, Object> requstObjectMap, @CurrentUser Map<String, Object> curUserMap) {
		String id = MapUtils.getString(requstObjectMap, TenantConstants.ID);

		if (StringUtil.isEmpty(id)) {
			return this.errorResponse("租户id不能为空");
		}

		Map<String, Object> params = new HashMap<>();
		params.put(TenantConstants.ID, id);
		Map<String, Object> tenantMap = tenantApi.getTenantMap(params);

		if (MapUtils.isEmpty(tenantMap)) {
			return this.errorResponse("找不到租户！");
		}
		tenantMap.put(TenantConstants.STATUS_CD, "2");
		tenantApi.saveTenantMap(tenantMap);
		//调用删除方法
		return this.successResponse("设置成功!");
	}
 	
 	
 	
 	/**
	 * @方法名称 roleMenu
	 * @功能描述 <pre>根据角色获取对应的权限菜单树</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:22:46
	 * @param requstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/roleMenu/menuTree", method = RequestMethod.POST)
	@HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
	public ResponseVO roleMenu(@RequestObjectParam Map<String, Object> requstObjectMap, PageParam page) throws Exception {
		// 校验入参塞入reqMap；
		// 获取所有菜单数据
		PageInfo<Map<String, Object>> pageInfo = menuApi.findMenuPage(requstObjectMap, null);
		// 获取角色所有的全部权限
		Map<String, Object> params = new HashMap<>();
		params.put(RoleConstants.ROLE_CODE, MapUtils.getString(requstObjectMap, RoleConstants.ROLE_CODE));// 获取选择到的roleCode
		List<Map<String, Object>> roleMenuList = null;
		try {
			params.put("tenantId", requstObjectMap.get("tenantId"));
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
	 * @方法名称 save
	 * @功能描述 <pre>设置租户失效</pre>
	 * @param requstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
 	@RequestMapping(value = "/updateInvalid")
    @ResponseBody
    @HeadInit(name=HeadConstants.HEAD_SCENE,value="tenantManage")
    public ResponseVO updateInvalid(@RequestObjectParam Map<String,Object> requstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
        Map<String, Object> tenantMap = tenantApi.saveTenantMap(requstObjectMap);
		return successResponse(tenantMap, "设置成功");
    }
 	
 	/**
	 * @方法名称 importTenant
	 * @功能描述 <pre>保理商信息导入</pre>
	 * @作者    yuyq
	 * @创建时间 2017年10月30日 下午2:14:00
	 * @param request
	 * @return
 	 * @throws IOException 
	 */
	@RequestMapping(value="/importTenant")
	@ResponseBody
	public ResponseVO importTenant(HttpServletRequest request) throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("importFile");
        if (multipartFile == null) {
            return this.errorResponse("请先上传文件");
        }
        CommonsMultipartFile cf= (CommonsMultipartFile)multipartFile; 
        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
        File file = fi.getStoreLocation();
        @SuppressWarnings("rawtypes")
		List<List> list= ExcelReadUtil.readExcel(file);
		return this.successResponse("导入成功");
	}
 	
}
