package com.jeedev.msdp.sysmanage.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.Constants;
import com.jeedev.msdp.auth.client.SessionUtil;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.trace.constants.LoginUserConstants;


@Controller
@RequestMapping(value = "/admin")
public class AdminAction extends BaseAppAction {
	
	@Autowired
	private UserApi userApi;
	
	/**
	 * @方法名称 roleAlter
	 * @功能描述 <pre>切换角色、机构、部门</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月9日 下午5:22:45
	 * @param RequstObjectMap
	 * @return
	 */
	@RequestMapping(value = "/changer", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVO changer(@RequestObjectParam Map<String, Object> RequstObjectMap) {
		try{
		// 更新用户和角色关系默认值
//		userApi.saveUserRoleRelTrans(RequstObjectMap);
		// 获取Session并变更Session中角色中对象
		Session session = SessionUtil.getSession(); 
		@SuppressWarnings("unchecked")
		Map<String,Object> user = (Map<String, Object>) session.getAttribute(Constants.CURRENT_USER); 
		// 前台js重定向跳转登陆后界面
		String type=MapUtils.getString(RequstObjectMap,"type");
		session.removeAttribute(Constants.CURRENT_USER);//清空
		if("2".equals(type)){
			//角色
			String roleCode=MapUtils.getString(RequstObjectMap,"roleCode");
			String roleName=MapUtils.getString(RequstObjectMap,"roleName");
			user.remove(LoginUserConstants.LOGIN_USER_ROLEINF_RLID);
			user.remove(LoginUserConstants.LOGIN_USER_ROLEINF_RLNM); 
			user.put(LoginUserConstants.LOGIN_USER_ROLEINF_RLID, roleCode);
			user.put(LoginUserConstants.LOGIN_USER_ROLEINF_RLNM, roleName);
		}
		if("0".equals(type)){
			//机构
			String orgCode=MapUtils.getString(RequstObjectMap,"orgCode");
			String orgName=MapUtils.getString(RequstObjectMap,"orgName");
			String deptCode=MapUtils.getString(RequstObjectMap,"deptCode");
			String deptName=MapUtils.getString(RequstObjectMap,"deptName");
			user.remove(LoginUserConstants.LOGIN_USER_ORGINF_INSTCD);
			user.remove(LoginUserConstants.LOGIN_USER_ORGINF_INSTNM); 
			user.put(LoginUserConstants.LOGIN_USER_ORGINF_INSTCD, orgCode);
			user.put(LoginUserConstants.LOGIN_USER_ORGINF_INSTNM, orgName);
			
			user.remove(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTCD);
			user.remove(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTNM); 
			user.put(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTCD, deptCode);
			user.put(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTNM, deptName);
			Map<String, Object> queryCond=new HashMap<>();
			queryCond.put("userNum", MapUtils.getString(user,LoginUserConstants.LOGIN_USER_USRID));
			queryCond.put("orgCode", orgCode);
			PageInfo usrDepts = this.userApi.findUserDeptRelPage(queryCond, null);
			user.remove(LoginUserConstants.LOGIN_USER_DEPTINF_LIST); 
			user.put(LoginUserConstants.LOGIN_USER_DEPTINF_LIST, usrDepts.getList());  
		}
		if("1".equals(type)){
			//部门
			String deptCode=MapUtils.getString(RequstObjectMap,"deptCode");
			String deptName=MapUtils.getString(RequstObjectMap,"deptName");
			user.remove(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTCD);
			user.remove(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTNM); 
			user.put(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTCD, deptCode);
			user.put(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTNM, deptName);
		}
		session.setAttribute(Constants.CURRENT_USER, user);//重新添加

		}catch(Exception e){
			return errorResponse(e.getMessage());
		}
		return successResponse("ok");
	}
	
	/**
	 * 
	 * @方法名称 userOrgDept
	 * @功能描述 <pre>获取用户机构部门列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月11日 下午1:58:56
	 * @param RequstObjectMap
	 * @return
	 */
	@RequestMapping(value = "/userOrgDept", method = RequestMethod.POST)
	@ResponseBody
	public ResponseVO userOrgDept(@RequestObjectParam Map<String, Object> RequstObjectMap) {
		PageInfo<Map<String, Object>> usrDepts=null;
		try{
		Session session = SessionUtil.getSession(); 
		@SuppressWarnings("unchecked")
		Map<String,Object> user = (Map<String, Object>) session.getAttribute(Constants.CURRENT_USER); 
			Map<String, Object> queryCond=new HashMap<>();
			queryCond.put("userNum", MapUtils.getString(user,LoginUserConstants.LOGIN_USER_USRID));
			 usrDepts = userApi.findUserOrgDeptRelPage(queryCond,null);
		}catch(Exception e){
			return errorResponse(e.getMessage());
		}
		return successResponse(usrDepts.getList());
	}
	
}
