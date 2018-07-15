package com.jeedev.msdp.userselect.action;
/*package com.jeedev.msdp.userselect.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.data.ResponseVO;

@Controller
@RequestMapping(value = "/selectUser/")
public class SelectUserAction extends BaseAppAction{
	
	@Autowired
	private ISelectUserApi selectUserApi;
	
	@RequestMapping("getCandidateUser")
	@ResponseBody
	public ResponseVO getCandidateUser(String instCode,String deptCode,
			 String roleIds, String model) {
		
		if (null == instCode || "".equals(instCode)) {
			throw ExceptionFactory.getBizException("BSUR001");
		}
		if (null == roleIds || "".equals(roleIds)) {
			throw ExceptionFactory.getBizException("BSUR004");
		}
		
		if (null == model || "".equals(model)) {
			throw ExceptionFactory.getBizException("BSUR003");
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("instCode", instCode);
		param.put("roleIds", roleIds);
		param.put("model", model);
		
		
		/*return successResponse(selectUserApi.getCandidateUser(param));
		*//**[
		{ 
			userId: 'USR170120000085',
			userName: 'cg01',
			instCode: 'inst1000',
			deptCode: 'dept1000',
			
		},
		{ 
			userId: 'USR170120000086',
			userName: 'gy01',
			instCode: 'inst1100',
			deptCode: 'dept1100',
			
		},
		{ 
			userId: 'USR170120000087',
			userName: 'superadmin',
			instCode: 'inst1200',
			deptCode: 'dept1200',
			
		}
		]
		**//*
		Map<String,Object> returnMap = new HashMap<String,Object>();
		List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
		if ("0".equals(model)) {//返回当前部门或机构的用户
			rtList.add(this.createUser("USR170120000085", "cg01", instCode, deptCode));
			returnMap.put("grid", rtList);
			return successResponse(returnMap);
		}
		if ("1".equals(model)) {//返回上一级机构或部门的用户、
			rtList.add(this.createUser("USR170120000085", "cg01", "inst1000", "dept1000"));
			rtList.add(this.createUser("USR170120000086", "gy01", "inst1100", "dept1100"));
			returnMap.put("grid", rtList);
			return successResponse(returnMap);
		}
		if ("2".equals(model)) {//返回下一级机构或部门的用户
			rtList.add(this.createUser("USR170120000085", "cg01", instCode, deptCode));
			rtList.add(this.createUser("USR170120000086", "gy01", deptCode, deptCode));
			rtList.add(this.createUser("USR170120000087", "gy02", deptCode, deptCode));
			rtList.add(this.createUser("USR170120000088", "inst1200", deptCode,deptCode));
			returnMap.put("grid", rtList);
			return successResponse(returnMap);
		}

		return super.errorResponse("无法获取树");
		
	}
	
	
	@RequestMapping("getInstDeptTree")
	@ResponseBody
	public ResponseVO getInstDeptTree(String instCode,String deptCode,
			 String roleIds, String model) {
		
		if (null == instCode || "".equals(instCode)) {
			throw ExceptionFactory.getBizException("BSUR001");
		}
		if (null == roleIds || "".equals(roleIds)) {
			throw ExceptionFactory.getBizException("BSUR004");
		}
		
		if (null == model || "".equals(model)) {
			throw ExceptionFactory.getBizException("BSUR003");
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("instCode", instCode);
		param.put("roleIds", roleIds);
		param.put("model", model);
		
		
		/*return successResponse(selectUserApi.getCandidateUser(param));
		if (instCode != null && !"".equals(instCode)) {
			if ("0".equals(model)) {//只返回 当前机构
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
				Map<String,Object> root = new HashMap<String, Object>();
				
				root.put("name", "一级行");
				root.put("id", "inst1000");
				root.put("pid", "0");
				
				rtList.add(root);
				return successResponse(rtList);
			}
			if ("1".equals(model)) {//返回上一级机构
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
				

				Map<String,Object> node1 = new HashMap<String, Object>();
				node1.put("name", "一级行");
				node1.put("id", "inst1000");
				node1.put("pid", "0");
				
				rtList.add(node1);
				
				Map<String,Object> node2 = new HashMap<String, Object>();
				
				node2.put("name", "二级级行A");
				node2.put("id", "inst1100");
				node2.put("pid", "inst1000");
				
				rtList.add(node2);
				
				
				return successResponse(rtList);
			}
			
			if ("2".equals(model)) {//返回下一级机构
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
				Map<String,Object> root = new HashMap<String, Object>();
				

				Map<String,Object> node1 = new HashMap<String, Object>();
				node1.put("name", "一级行");
				node1.put("id", "inst1000");
				node1.put("pid", "0");
				
				rtList.add(node1);
				
				Map<String,Object> node2 = new HashMap<String, Object>();
				
				node2.put("name", "二级级行A");
				node2.put("id", "inst1100");
				node2.put("pid", "inst1000");
				
				rtList.add(node2);
				
				Map<String,Object> node3 = new HashMap<String, Object>();
				
				node3.put("name", "二级级行B");
				node3.put("id", "inst1200");
				node3.put("pid", "1000");
				
				rtList.add(node3);
				return successResponse(rtList);
				
			}
		}else {
			
			if ("0".equals(model)) {//只返回 当前部门
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
				Map<String,Object> root = new HashMap<String, Object>();
				
				root.put("name", "一级行部门");
				root.put("id", "dept1000");
				root.put("pid", "0");
				
				rtList.add(root);
				return successResponse(rtList);
			}
			if ("1".equals(model)) {//返回上一级机构
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
				

				Map<String,Object> node1 = new HashMap<String, Object>();
				node1.put("name", "一级行部门");
				node1.put("id", "dept1000");
				node1.put("pid", "0");
				
				rtList.add(node1);
				
				Map<String,Object> node2 = new HashMap<String, Object>();
				
				node2.put("name", "二级行部门A");
				node2.put("id", "dept1100");
				node2.put("pid", "dept1000");
				
				rtList.add(node2);
				
				
				return successResponse(rtList);
			}
			
			if ("3".equals(model)) {//返回下一级
				List<Map<String, Object>> rtList = new ArrayList<Map<String, Object>>();
				

				Map<String,Object> node1 = new HashMap<String, Object>();
				node1.put("name", "一级行");
				node1.put("id", "dept1000");
				node1.put("pid", "0");
				
				rtList.add(node1);
				
				Map<String,Object> node2 = new HashMap<String, Object>();
				
				node2.put("name", "二级行部门A");
				node2.put("id", "dept1100");
				node2.put("pid", "dept1000");
				
				rtList.add(node2);
				
				Map<String,Object> node3 = new HashMap<String, Object>();
				
				node3.put("name", "二级行部门B");
				node3.put("id", "dept1200");
				node3.put("pid", "dept1000");
				
				rtList.add(node3);
				return successResponse(rtList);
				
			}
			
			
			
		}
		
		
		return super.errorResponse("无法创建树");
	}
	
	
	
	
	
	public Map<String,Object> createUser(String userId,String userName,String instCode,String deptCode){
		Map<String,Object> user = new HashMap<String, Object>();
		user.put("userId", userId);
		user.put("userName", userName);
		user.put("instCode", instCode);
		user.put("deptCode", deptCode);
		return user;
		
	}
	
}
*/