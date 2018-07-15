package com.jeedev.scf.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;

public class UserApiTest extends BaseTest {
	@Autowired
	private UserApi userApi;
	
	
	
	public void changePassword() throws Exception {
		Map<String, Object> params = new HashMap<>();
		 params.put(UserConstants.LOGIN_NAME,"cg01");
		 params.put("password","aa");
		 params.put("newPswd","bb"); 
//		Integer result= userApi.changePassword(params);
//		System.out.println("结果："+result);
	} 
	@Test
	public void findUserPage() throws Exception {
		Map<String, Object> params = new HashMap<>();
		// params.put("roleCode","RL0001");
		PageInfo<Map<String, Object>> page = userApi.findUserPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println(o);
		}
	}
	public void getUserMap() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userNum", "USR170207000093");
		// params.put("password","f22deb6929f74d44e86ff47cfdd77b96");

		// Map<String, Object> page = userApi.getUserMap(params);
		// System.out.println(page);

	}

	public void saveUser() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", "134");// 修改必传
		params.put("userNum", "USR170207000093");
		params.put("password", "f22deb6929f74d44e86ff47cfdd77b96");
		params.put("realname", "dzx_test");
		params.put("loginName", "xx1xx2");
		params.put("password", "test");
		params.put("salt", "salt1");
		params.put("statusCd", "1");
		params.put("sexCd", "1");
		params.put("certificateTypeCd", "0");
		params.put("certificateNum", "11");
		params.put("qq", "11");
		params.put("wechat", "wechat1");
		params.put("email", "email11");
		params.put("telephone", "33");
		params.put("mobile", "3");
		params.put("province", "2");
		params.put("city", "2");
		params.put("district", "1");
		params.put("remark", "1xxxxxxxx");

		// Map<String, Object> page = userApi.saveUserTrans(params);
		// System.out.println(page.get("id"));

	}

	public void delUser() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", "134");//
		// userApi.deleteUserTrans(params);
		// System.out.println(page.get("id"));
	}

	public void findUserRolePage() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userNum", "USR170120000087");
		List<Map<String, Object>> mapList = userApi.findUserRoleRelList(params);
		for (Map<String, Object> o : mapList) {
			System.out.println(o);
		}
	}
	
	public void saveUserRoleRelTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userNum", "USR170120000087");
		params.put("roleCode", "USR170120000087");
		params.put("statusCd", "1"); 
		userApi.saveUserRoleRelTrans(params);
	}

	public void findUserDeptRelPage() throws Exception {

		Map<String, Object> params = new HashMap<>();
		// params.put("userNum","USR170120000087");
		// params.put("deptCode","100050");
		params.put("orgCode", "ICBC");
		PageInfo<Map<String, Object>> page = userApi.findUserDeptRelPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println(o);
		}
	}
	
	public void saveUserDeptRelTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("userNum", "deng-test");
		params.put("deptCode", "10004112"); 
		params.put("statusCd", "1"); 
		userApi.saveUserDeptRelTrans(params);
	}
	
	public void findUserOrgPage() throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("userNum", "userVo223456");
		// params.put("orgCode","ICBC");
		PageInfo<Map<String, Object>> page = userApi.findUserOrgRelPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println(o);
		}
	}
	
	public void saveUserOrgRelTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id", "162");
		params.put("userNum", "deng-tes1t");
		params.put("orgCode", "xmhz"); 
		params.put("statusCd", "1"); 
		userApi.saveUserOrgRelTrans(params);
	}
	
	
	public void findUserGroupPage() throws Exception {

		Map<String, Object> params = new HashMap<>();
		params.put("userNum", "ceshi12");
		// params.put("orgCode","ICBC");
		PageInfo<Map<String, Object>> page = userApi.findUserGroupRelPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println(o);
		}
	}
	
	public void saveUserGroupRelTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
//		params.put("id", "162");
		params.put("userNum", "deng-test");
		params.put("groupCode", "1011"); 
		params.put("statusCd", "1"); 
		userApi.saveUserGroupRelTrans(params);
	}

}
