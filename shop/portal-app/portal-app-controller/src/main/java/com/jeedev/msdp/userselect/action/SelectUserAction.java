package com.jeedev.msdp.userselect.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.userselect.api.ISelectUserApi;
import com.jeedev.msdp.utlis.StringUtil;
/**
 * 
 * @类名称 SelectUserAction.java
 * @类描述 <pre>选人Action层</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年9月18日 下午3:54:13
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年9月18日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "/selectUser")
public class SelectUserAction extends BaseAppAction{
	
	@Autowired
	private ISelectUserApi selectUserApi;
	
	/**
	 * @方法名称 getCandidateUser
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午3:54:36
	 * @param instCode 编码
	 * @param deptCode 部门编码
	 * @param roleIds 角色编码IDs
	 * @param model 类型
	 * @return
	 */
	@RequestMapping("/getCandidateUser")
	@ResponseBody
	public ResponseVO getCandidateUser(String instCode,String deptCode,
			 String roleIds, String model) {
		if (StringUtil.isEmpty(instCode)
				&& StringUtil.isEmpty(deptCode)) {
			throw ExceptionFactory.getBizException("BSUR001");
		}
		if (null == roleIds || "".equals(roleIds)) {
			throw ExceptionFactory.getBizException("BSUR004");
		}
		
		if (null == model || "".equals(model)) {
			throw ExceptionFactory.getBizException("BSUR003");
		}
		Map<String,Object> param = new HashMap<String,Object>();
		if (null != instCode && !"".equals(instCode)) {
			param.put("instCode", instCode);
		}
		if (null !=  deptCode && !"".equals(deptCode)) {
			param.put("deptCode", deptCode);
		}
		
		param.put("roleIds", roleIds);
		param.put("model", model);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("grid", selectUserApi.getCandidateUser(param));
		
		return successResponse(result);
	}
	
	
	/**
	 * @方法名称 getInstDeptTree
	 * @功能描述 <pre>获取部门树</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午3:59:15
	 * @param instCode
	 * @param deptCode
	 * @param roleIds
	 * @param model
	 * @return
	 */
	@RequestMapping("/getInstDeptTree")
	@ResponseBody
	public ResponseVO getInstDeptTree(String instCode,String deptCode,
			 String roleIds, String model) {
		
		if ((null == instCode || "".equals(instCode))
				&& (null == deptCode || "".equals(deptCode))) {
			throw ExceptionFactory.getBizException("BSUR001");
		}
		if (null == roleIds || "".equals(roleIds)) {
			throw ExceptionFactory.getBizException("BSUR004");
		}
		
		if (null == model || "".equals(model)) {
			throw ExceptionFactory.getBizException("BSUR003");
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		if (null != instCode && !"".equals(instCode)) {
			param.put("instCode", instCode);
		}
		if (null !=  deptCode && !"".equals(deptCode)) {
			param.put("deptCode", deptCode);
		}
		param.put("roleIds", roleIds);
		param.put("model", model);
		
		List<Map<String,Object>> list = selectUserApi.getInstDeptTree(param);
		List<Map<String,Object>> rt = new ArrayList<>();
		if (list != null && list.size() > 0) {
			
			for (Map<String, Object> map : list) {
				Map<String,Object> rtunit = new HashMap<>();
				if (param.containsKey("instCode")) {
					rtunit.put("id", map.get("instCode"));
					rtunit.put("pid", map.get("instParentCode"));
					rtunit.put("name", map.get("instName"));
				}else {
					rtunit.put("id", map.get("deptCode"));
					rtunit.put("pid", map.get("deptParentCode"));
					rtunit.put("name", map.get("deptName"));
				}
				rt.add(rtunit);
			}
		}
		
		
		return successResponse(rt);
	}
	/**
	 * 
	 * @方法名称 createUser
	 * @功能描述 <pre>创建用户实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午3:58:42
	 * @param userId
	 * @param userName
	 * @param instCode
	 * @param deptCode
	 * @return
	 */
	public Map<String,Object> createUser(String userId,String userName,String instCode,String deptCode){
		Map<String,Object> user = new HashMap<String, Object>();
		user.put("userId", userId);
		user.put("userName", userName);
		user.put("instCode", instCode);
		user.put("deptCode", deptCode);
		return user;
		
	}
	
}
