package com.jeedev.msdp.sysmanage.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageParam;
import com.jeedev.msdp.captcha.AuthUtils;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.constants.TranslaterConstant;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.standard.exception.BizException;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.translate.annotation.Translater;
import com.jeedev.msdp.translate.annotation.Translaters;
import com.jeedev.msdp.utlis.StringUtil;
import com.jeedev.msdp.web.utils.DictWebUtil;

/**
 * 
 * @类名称 UserAction.java
 * @类描述 <pre>用户管理</pre>
 * @作者 lisongtao lisongtao@tansun.com.cn
 * @创建时间 2017年8月20日 上午10:05:08
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
@RequestMapping(value = "/usermg")
public class UserAction extends BaseAppAction {
	
	private static String DEFAULTIND_NO="0";
	@Autowired
	private UserApi userApi;
	
	
	/**
	 * @方法名称 query
	 * @功能描述 <pre>根据条件分页获取用户列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:02:53
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/query")
	@ResponseBody
	public ResponseVO query(@RequestObjectParam Map<String,Object> RequstObjectMap ,@CurrentUser Map<String,Object> curUserMap,PageParam page) throws Exception {	
		//请求参数不建议使用HttpServletRequest request
		try{
			String dictValue = DictWebUtil.getDictVule("BussModCd", "BussModCd_1", "");
			System.out.println(dictValue);
			String dictName = DictWebUtil.getDictName("BussModCd", "BussModCd_1", "");
			System.out.println(dictName);
			Map dictMap = DictWebUtil.getDictMap("BussModCd", "BussModCd_1", "");
			System.out.println(dictMap.toString());
					
			Map<String, Object> result = new HashMap<String, Object>();
			page.setOrderBy("userNum  asc");
		    result.put("grid", userApi.findUserPage(RequstObjectMap,page));
		    return this.successResponse(result);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据用户编号ID获取用户详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:03:52
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get")
    @ResponseBody
    public ResponseVO get(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception { 
    	String userNum = MapUtils.getString(RequstObjectMap, UserConstants.USER_NUM);
    	String id = MapUtils.getString(RequstObjectMap, UserConstants.ID);
    	if (!StringUtil.isEmpty(userNum) || !StringUtil.isEmpty(id)) {
    		try{
	    		//查询用户信息
	    		Map<String, Object> userMap = userApi.getUserMap(RequstObjectMap);
	    		return successResponse(userMap);
	    	}catch(Exception e){
				return this.errorResponse(e.getMessage());
			}
		}else{
			return successResponse("查询条件不存在");
		}
    }
	/**
	 * @方法名称 save
	 * @功能描述 <pre>新增或修改用户的详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:04:46
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
 	@RequestMapping(value = "/save")
    @ResponseBody
    public ResponseVO save(@RequestObjectParam Map<String,Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
    	if (!StringUtil.isEmpty(MapUtils.getString(RequstObjectMap, UserConstants.USER_NUM)) || !StringUtil.isEmpty(MapUtils.getString(RequstObjectMap, UserConstants.ID))) {//修改
		Map<String, Object> userMap = userApi.saveUserTrans(RequstObjectMap);
		return successResponse(userMap, "修改成功");
 	    } else {//保存
		
		String salt = AuthUtils.getSalt();
		String passWord = AuthUtils.encryptPassword( (String)RequstObjectMap.get(UserConstants.LOGIN_NAME), (String)RequstObjectMap.get(UserConstants.PASSWORD), salt);
		RequstObjectMap.put(UserConstants.STATUS_CD, "1");//默认用户为生效装填
		RequstObjectMap.put(UserConstants.PASSWORD, passWord);
		RequstObjectMap.put(UserConstants.SALT, salt);
		RequstObjectMap.put(UserConstants.TENANT_ID, MapUtils.getString(curUserMap, UserConstants.TENANT_ID));
		RequstObjectMap.put(UserConstants.CLNTEND_ID, MapUtils.getString(curUserMap, UserConstants.CLNTEND_ID));
		Map<String,Object> userMap =null;
		try{
		 userMap = userApi.saveUserTrans(RequstObjectMap);
 	    }catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
		return successResponse(userMap,"保存成功");
 	    }	
    }
	/**
	 * @方法名称 delete
	 * @功能描述 <pre>根据用户编号ID删除用户实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:05:19
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public ResponseVO delete(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception {
    	String id = MapUtils.getString(RequstObjectMap, UserConstants.ID);
    	if (StringUtil.isEmpty(id)) {
			return this.errorResponse("用户id不能为空");
		}
    	userApi.deleteUserTrans(RequstObjectMap);
    	//调用删除方法
		return this.successResponse("删除成功");
	}
	/**
	 * @方法名称 queryUserAndOrg
	 * @功能描述 <pre>获取当前用户与机构关系列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:05:40
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndOrgRel/query")
	@Translaters({
		@Translater(name=TranslaterConstant.org,fromKeys="orgCode",toKey="orgName2")
	})	
	@ResponseBody
	public ResponseVO queryUserAndOrg(@RequestObjectParam Map<String,Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap,PageParam page) throws Exception {	
		//请求参数不建议使用HttpServletRequest request
		//@RequestObjectParam RequstObjectMap 请求参数使用 
		//@CurrentUser  curUserMap 当前登录用户信息
		try{
			RequstObjectMap.put(UserConstants.DEL_IND, DEFAULTIND_NO);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("grid", userApi.findUserOrgRelPage(RequstObjectMap, page));
			return this.successResponse(result);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 deleteUserAndOrgRel
	 * @功能描述 <pre>根据用户编号删除所属机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:07:26
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndOrgRel/delete")
	@ResponseBody
	public ResponseVO deleteUserAndOrgRel(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception {
    	String id = MapUtils.getString(RequstObjectMap, UserConstants.ID);
    	if (StringUtil.isEmpty(id)) {
			return this.errorResponse("用户机构关联id不能为空");
		}
    	userApi.deleteUserOrgRelTrans(RequstObjectMap);
    	//调用删除方法
		return this.successResponse("删除成功");
	}
	/**
	 * @方法名称 saveUserAndOrgRel
	 * @功能描述 <pre>新增或修改用户的机构信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:08:10
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndOrgRel/save")
    @ResponseBody
    public ResponseVO saveUserAndOrgRel(@RequestObjectParam Map<String,Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
    	try{
    		if (!StringUtil.isEmpty(MapUtils.getString(RequstObjectMap, UserConstants.ID))) {//修改
        		userApi.saveUserOrgRelTrans(RequstObjectMap);
        		return successResponse("修改成功");
    		} else {//保存
    			userApi.saveUserOrgRelTrans(RequstObjectMap);
    			return successResponse("保存成功");
    		}
    	}catch(Exception  e){
    		return errorResponse(e.getMessage());
    	}
		
    }
	/**
	 * @方法名称 getUserAndOrgRel
	 * @功能描述 <pre>获取用户与机构关系列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:08:41
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndOrgRel/get")
    @ResponseBody
    public ResponseVO getUserAndOrgRel(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception { 
    	String id = MapUtils.getString(RequstObjectMap, UserConstants.ID);
    	if (!StringUtil.isEmpty(id)) {
    		//查询用户信息
    		Map<String, Object> userMap = userApi.getUserOrgRelMap(RequstObjectMap);
    		return successResponse(userMap);
		}else{
			return successResponse(null);
		}
    }
	/**
	 * @方法名称 selectorUserAndOrgRel
	 * @功能描述 <pre>提供用户与机构的下拉选择列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:09:55
	 * @param RequstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndOrgRel/selector")
    @ResponseBody
    public ResponseVO selectorUserAndOrgRel(@RequestObjectParam Map<String,Object> RequstObjectMap,PageParam page) throws Exception {
		RequstObjectMap.put(UserConstants.DEL_IND, DEFAULTIND_NO); 
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("grid", userApi.findUserOrgRelPage(RequstObjectMap, page));
		return this.successResponse(result);
    }
	/**
	 * @方法名称 queryUserAndDept
	 * @功能描述 <pre>获取用户与部门关系列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:10:48
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndDeptRel/query")
	@ResponseBody
	public ResponseVO queryUserAndDept(@RequestObjectParam Map<String,Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap,PageParam page) throws Exception {	
		//请求参数不建议使用HttpServletRequest request
		//@RequestObjectParam RequstObjectMap 请求参数使用 
		//@CurrentUser  curUserMap 当前登录用户信息
		try{
			RequstObjectMap.put(UserConstants.DEL_IND, DEFAULTIND_NO);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("grid", userApi.findUserDeptRelPage(RequstObjectMap, page));
			return this.successResponse(result);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 deleteUserAndDeptRel
	 * @功能描述 <pre>删除用户与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:11:28
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndDeptRel/delete")
	@ResponseBody
	public ResponseVO deleteUserAndDeptRel(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception {
    	String id = MapUtils.getString(RequstObjectMap, UserConstants.ID);
    	if (StringUtil.isEmpty(id)) {
			return this.errorResponse("用户部门关联id不能为空");
		}
    	userApi.deleteUserDeptRelTrans(RequstObjectMap);
    	//调用删除方法
		return this.successResponse("删除成功");
	}
	/**
	 * @方法名称 saveUserAndDeptRel
	 * @功能描述 <pre>新增或修改用户与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:12:02
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndDeptRel/save")
    @ResponseBody
    public ResponseVO saveUserAndDeptRel(@RequestObjectParam Map<String,Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
    	try{
    		if (!StringUtil.isEmpty(MapUtils.getString(RequstObjectMap, UserConstants.ID))) {//修改
        		userApi.saveUserDeptRelTrans(RequstObjectMap);
        		return successResponse("修改成功");
    		} else {//保存
    			userApi.saveUserDeptRelTrans(RequstObjectMap);
    			return successResponse("保存成功");
    		}
    	}catch(Exception  e){
    		return errorResponse(e.getMessage());
    	}
    }
	/**
	 * @方法名称 getUserAndDeptRel
	 * @功能描述 <pre>根据用户编号ID获取用户与部门详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:12:25
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndDeptRel/get")
    @ResponseBody
    public ResponseVO getUserAndDeptRel(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception { 
		try{
	    	String id = MapUtils.getString(RequstObjectMap, UserConstants.ID);
	    	if (!StringUtil.isEmpty(id)) {
	    		//查询用户信息
	    		Map<String, Object> userMap = userApi.getUserDeptRelMap(RequstObjectMap);
	    		return successResponse(userMap);
			}else{
				return successResponse(null);
			}
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
    }
	/**
	 * @方法名称 queryUserAndRole
	 * @功能描述 <pre>分页获取用户与角色关系列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:13:13
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndRoleRel/query")
	@ResponseBody
	public ResponseVO queryUserAndRole(@RequestObjectParam Map<String,Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap,PageParam page) throws Exception {	
		//请求参数不建议使用HttpServletRequest request
		//@RequestObjectParam RequstObjectMap 请求参数使用 
		//@CurrentUser  curUserMap 当前登录用户信息
		try{
			RequstObjectMap.put("delInd", DEFAULTIND_NO);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("grid", userApi.findUserRoleRelPage(RequstObjectMap, page));
		    return this.successResponse(result);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 deleteUserAndRoleRel
	 * @功能描述 <pre>删除用户与角色关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:13:46
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndRoleRel/delete")
	@ResponseBody
	public ResponseVO deleteUserAndRoleRel(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception {
		try{
	    	String id = MapUtils.getString(RequstObjectMap, UserConstants.ID);
	    	if (StringUtil.isEmpty(id)) {
				return this.errorResponse("用户角色关联id不能为空");
			}
	    	userApi.deleteUserRoleRelTrans(RequstObjectMap);
	    	//调用删除方法
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 saveUserAndRoleRel
	 * @功能描述 <pre>新增或修改用户与角色关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:14:07
	 * @param RequstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndRoleRel/save")
    @ResponseBody
    public ResponseVO saveUserAndRoleRel(@RequestObjectParam Map<String,Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
    	try{
    		if (!StringUtil.isEmpty(MapUtils.getString(RequstObjectMap, UserConstants.ID))) {//修改
        		userApi.saveUserRoleRelTrans(RequstObjectMap);
        		return successResponse("修改成功");
    		} else {//保存
    			userApi.saveUserRoleRelTrans(RequstObjectMap);
    			
    			return successResponse("保存成功");
    		}
    	}catch(Exception  e){
    		return errorResponse(e.getMessage());
    	}
		
    }
	/**
	 * @方法名称 getUserAndRoleRel
	 * @功能描述 <pre>获取用户与角色关系详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午9:14:28
	 * @param requstObjectMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userAndRoleRel/get")
    @ResponseBody
    public ResponseVO getUserAndRoleRel(@RequestObjectParam Map<String,Object> requstObjectMap) throws Exception { 
    	String id = MapUtils.getString(requstObjectMap, UserConstants.ID);
    	if (!StringUtil.isEmpty(id)) {
    		//查询用户信息
    		Map<String, Object> userMap = userApi.getUserRoleRelMap(requstObjectMap);
    		return successResponse(userMap);
		}else{
			return successResponse(null);
		}
    }
	/**
	 * @方法名称 selectorUser
	 * @功能描述 <pre>提供用户下拉选择列表</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月28日 上午9:09:55
	 * @param RequstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/selector")
    @ResponseBody
    public ResponseVO selectorUser(@RequestObjectParam Map<String,Object> RequstObjectMap,PageParam page) throws Exception {
		RequstObjectMap.put(UserConstants.DEL_IND, DEFAULTIND_NO); 
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("grid", userApi.findUserPage(RequstObjectMap,page));
		return this.successResponse(result);
    }
	
	/**
	 * @方法名称 changePassword
	 * @功能描述 <pre>修改用户密码</pre>
	 * @作者    yuyq
	 * @创建时间 2017年10月24日 下午3:45:47
	 * @param reqMap
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVO changePassword(@RequestParam Map<String,Object> reqMap)throws BizException{
		try{
			Map<String,Object> map=new HashMap<>();
			map.put(UserConstants.ID, reqMap.get(UserConstants.ID));
			Map<String,Object>  user=userApi.getUserMap(map);
			if(user==null){
				return errorResponse("用户不存在！");
			}
			String password = AuthUtils.encryptPassword( (String)user.get(UserConstants.LOGIN_NAME), (String)reqMap.get(UserConstants.PASSWORD), (String) user.get(UserConstants.SALT));
			String newPswd = AuthUtils.encryptPassword( (String)user.get(UserConstants.LOGIN_NAME), (String)reqMap.get(UserConstants.NEW_PSWD), (String) user.get(UserConstants.SALT));
			reqMap.put(UserConstants.LOGIN_NAME, user.get(UserConstants.LOGIN_NAME));
			reqMap.put(UserConstants.PASSWORD, password);
			reqMap.put(UserConstants.NEW_PSWD,newPswd);
			Integer result =userApi.changePasswordTrans(reqMap);
			if(result.equals(-2)){
				return errorResponse("原密码输入错误！");
			}
			return successResponse("修改成功");
 	    }catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	
	/**
	 * @方法名称 resetPassword
	 * @功能描述 <pre>重置用户密码</pre>
	 * @作者    yuyq
	 * @创建时间 2017年10月24日 下午3:45:47
	 * @param reqMap
	 * @return
	 * @throws BizException
	 */
	@RequestMapping(value="/resetPassword",method=RequestMethod.POST)
	@ResponseBody
	public ResponseVO resetPassword(@RequestParam Map<String,Object> reqMap)throws BizException{
		 try{
				Map<String,Object> map=new HashMap<>();
				map.put(UserConstants.ID, reqMap.get(UserConstants.ID));
				Map<String,Object>  user=userApi.getUserMap(map);
				if(user==null){
					return errorResponse("用户不存在！");
				}
				String newPswd = AuthUtils.encryptPassword( (String)user.get(UserConstants.LOGIN_NAME),UserConstants.DEFAULT_PSWD, (String) user.get(UserConstants.SALT));
				reqMap.put(UserConstants.LOGIN_NAME, user.get(UserConstants.LOGIN_NAME));
				reqMap.put(UserConstants.NEW_PSWD,newPswd);
				userApi.resetPasswordTrans(reqMap);
				return successResponse("重置成功");
	 	    }catch(Exception e){
				return this.errorResponse(e.getMessage());
			}
	}
}
