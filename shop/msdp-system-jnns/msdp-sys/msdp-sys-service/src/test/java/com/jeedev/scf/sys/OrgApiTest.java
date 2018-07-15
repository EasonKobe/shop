package com.jeedev.scf.sys;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.sys.org.api.OrgApi;

public class OrgApiTest extends BaseTest {
	@Autowired
	private OrgApi orgApi;

	
	public void findOrgPage() throws Exception {
		Map<String, Object> params = new HashMap<>();
		// params.put("orgCode","xmhz");
		PageInfo<Map<String, Object>> page = orgApi.findOrgPage(params, null);
		for (Map<String, Object> o : page.getList()) {
			System.out.println("org:"+o);
		}

	}
	
	public void getOrgMap() throws Exception {
		Map<String, Object> params = new HashMap<>();
	    params.put("orgCode","xmhz"); 
		Map<String, Object> page = orgApi.getOrgMap(params);
		System.out.println(page);

	}
	
	public void savePermTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
//		params.put("id","282");// 修改必传
	    params.put("orgCode","100020");
	    params.put("orgName","test");
	    params.put("parentOrgCode","10000");//菜单编码
	    params.put("orgLevelCd","1");
	    params.put("sort","0");
	    params.put("statusCd","1");
	    params.put("remark","ssssss");
	    params.put("delInd","0");

		Map<String, Object> page = orgApi.saveOrgTrans(params);
		System.out.println(page.get("id"));

	}
	
	@Test
	public void deletePermTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("id","282");// 
		orgApi.deleteOrgTrans(params);
	}
	
	
}
