package com.jeedev.scf.sys;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.sys.role.api.RoleApi;

public class RoleApiTest extends BaseTest{
	
	@Autowired
	private RoleApi roleApi;

	
    public void findRolePage() throws Exception {
        Map<String, Object> params = new HashMap<>();
//        params.put("roleCode","RL0001");
        PageInfo<Map<String, Object>> page=roleApi.findRolePage(params, null);
        for(Map<String,Object> o:page.getList()){
        	 System.out.println(o.toString());
        }
       
    }
    
    public void saveRole() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("roleCode","test");
        params.put("roleName","demo112121");
        params.put("statusCd","1");
        params.put("remark","RL0001"); 
        params.put("delInd","0"); 
//        params.put("id","60"); //新增OR修改
        Map<String, Object> r= roleApi.saveRoleTrans(params);
        System.out.println(r.get("id"));
    }
    
    public void getRoleMap() throws Exception {
		Map<String, Object> params = new HashMap<>();
	    params.put("roleCode","RL0102");
	    params.put("id","54");
		Map<String, Object> map = roleApi.getRoleMap(params);
		System.out.println(map);
	}
    @Test
    public void deleteRoleTrans() throws Exception {
		Map<String, Object> params = new HashMap<>();
//	    params.put("roleCode","RL0102");
	    params.put("id","61");
		 roleApi.deleteRoleTrans(params);
//		System.out.println(map);
	}
    
    public void findRolePermissionRelPage() throws Exception {
        Map<String, Object> params = new HashMap<>();
//        params.put("roleCode","AHR0002");
        params.put("permissionCode","rl0009");
        PageInfo<Map<String, Object>> page=roleApi.findRolePermissionRelPage(params, null);
        for(Map<String,Object> o:page.getList()){
        	 System.out.println(o.toString());
        }
    }
}
