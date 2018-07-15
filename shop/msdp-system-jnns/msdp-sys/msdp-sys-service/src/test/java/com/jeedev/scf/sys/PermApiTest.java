package com.jeedev.scf.sys;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.sys.perm.api.PermApi;

public class PermApiTest extends BaseTest {
	@Autowired
	private PermApi permApi;

	
	public void findPermPage() throws Exception {
		Map<String, Object> params = new HashMap<>();
		// params.put("permissionCode","AHR0031");
		PageInfo<Map<String, Object>> page = permApi.findPermPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println("perm:"+o);
		}

	}
	
	public void getUserMap() throws Exception {
		Map<String, Object> params = new HashMap<>();
	    params.put("permissionCode","AHR0031"); 
		Map<String, Object> page = permApi.getPermMap(params);
		System.out.println(page);

	}
	
	public void savePermTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id","125");// 修改必传
	    params.put("permissionCode","000012");
	    params.put("permissionTypeCd","0");
	    params.put("resCode","res0053");//菜单编码
	    params.put("permissionName","xx1xx2");
	    params.put("isBuiltIn","0");
	    params.put("statusCd","1");
	    params.put("remark","ssssss");
	    params.put("delInd","0");

		Map<String, Object> page = permApi.savePermTrans(params);
//		System.out.println(page.get("id"));

	}
	
	@Test
	public void deletePermTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id","125");// 
		permApi.deletePermTrans(params); 
	}
	
	
}
