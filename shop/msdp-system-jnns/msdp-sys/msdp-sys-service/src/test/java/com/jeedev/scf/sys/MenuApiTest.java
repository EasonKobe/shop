package com.jeedev.scf.sys;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.sys.menu.api.MenuApi;

public class MenuApiTest extends BaseTest {
	@Autowired
	private MenuApi menuApi;

	
	public void findMenuPage() throws Exception {
		Map<String, Object> params = new HashMap<>();
		// params.put("roleCode","RL0001");
		PageInfo<Map<String, Object>> page = menuApi.findMenuPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println("menu:"+o);
		}

	}
	
	public void getMenuMap() throws Exception {
		Map<String, Object> params = new HashMap<>();
	    params.put("menuCode","res0001"); 
//	    params.put("parentMenuCode","res0001"); 
		Map<String, Object> page = menuApi.getMenuMap(params);
		System.out.println(page);

	}
	
	
	public void saveMenuTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
//		params.put("id","230");// 修改必传
	    params.put("menuCode","10003330");
	    params.put("menuName","test");
	    params.put("menuTypeCd","view");
	    params.put("parentMenuCode","11");//菜单编码
	    params.put("url","1");
	    params.put("sort","0");
	    params.put("leafFlagCd","0");
	    params.put("statusCd","1");
	    params.put("remark","ssssss");
	    params.put("delInd","0");

		Map<String, Object> page = menuApi.saveMenuTrans(params);
		System.out.println(page.get("id"));

	}
	
	@Test
	public void deletePermTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id","230");// 
		menuApi.deleteMenuTrans(params);
	}
	
	
}
