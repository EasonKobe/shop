package com.jeedev.msdp.auth.client.spi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.client.SessionUtil;
import com.jeedev.msdp.auth.client.spi.AuthPermSpi;
import com.jeedev.msdp.standard.exception.BizException;
import com.jeedev.msdp.sys.event.api.EventApi;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.perm.api.PermApi;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.sys.role.api.RoleConstants;

@Service("client.authPerm")
public class AuthPermImpl implements AuthPermSpi {
	@Autowired
	private PermApi permApi;
	@Autowired
	private RoleApi roleApi;
	@Autowired
	private EventApi eventApi;
	
	@Autowired
	private MenuApi menuApi;
	
	@Autowired
	private SessionUtil sessionUtil;
	
	@Override
	public List findByUsrId(String usrId, String ahrTpCd) throws BizException {
//		return permApi.findByUsrId(usrId, ahrTpCd);
		return null;
	}

	@Override
	public List<String> findAhrIdByRscIds(List<String> rscIds)
			throws BizException {
//		return permApi.findAhrIdByRscIds(rscIds);
		return null;
	}

	private List findAllUrl() {
		Map<String,Object>  cond = new HashMap<String,Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		PageInfo<Map<String, Object>> pageInfo = eventApi.findEventPage(cond,null);
		if(pageInfo!=null && pageInfo.getList()!=null && pageInfo.getList().size()>0) {
			result = pageInfo.getList();
			for(Map<String, Object> tmp:pageInfo.getList()) {
				tmp.put("ahrId", (String) tmp.get("eventCode"));
			}
		}
		return result;
	}
	@Override
	public List findByRlId(String rlId, String s1) throws BizException {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(StringUtils.isBlank(rlId)&&"3".equals(s1)) {
			//加载所有 事件 URL
			return findAllUrl();
		}
		//1.根据角色查找已分配菜单权限
		Map<String,Object> cond = new HashMap<String,Object>();
		cond.put(RoleConstants.ROLE_CODE, rlId);
		PageInfo<Map<String, Object>> pageInfo = roleApi.findRolePermissionRelPage(cond, null);
		if(pageInfo==null || pageInfo.getList()==null || pageInfo.getList().size()==0) {
			return result;
		}
		result = pageInfo.getList();
		//权限集合 
		List<String> permissionCodes = new ArrayList<String>();
		for(Map<String, Object> tmp:result) {
			tmp.put("ahrId", (String) tmp.get("permissionCode"));
			permissionCodes.add((String) tmp.get("permissionCode"));
		}
		
		//2.按钮下的事件权限
		//2.1查出已授权菜单
		cond = new HashMap<String,Object>();
		cond.put("permissionCodes", permissionCodes);
		pageInfo = permApi.findPermPage(cond, null);
		if(pageInfo==null || pageInfo.getList()==null || pageInfo.getList().size()==0) {
			return result;
		}
		
		//2.2根据已授权菜单（含页面元素等）查出已授权事件   
		List<String> menuCodes = new ArrayList<String>();
		for(Map<String, Object> tmp:pageInfo.getList()) {
			menuCodes.add((String) tmp.get("resCode"));
		}
		cond = new HashMap<String,Object>();
		cond.put("menuCodes", menuCodes);
		//查找事件集合 
		pageInfo = menuApi.findMenuEventRelPage(cond,null);
		if("3".equals(s1)) {
			result = new ArrayList<Map<String, Object>>();
			if(pageInfo!=null && pageInfo.getList()!=null && pageInfo.getList().size()>0) {
				result = pageInfo.getList();
				for(Map<String, Object> tmp:pageInfo.getList()) {
					tmp.put("ahrId", (String) tmp.get("eventCode"));
				}
			}
		}else {
			if(pageInfo!=null && pageInfo.getList()!=null && pageInfo.getList().size()>0) {
				for(Map<String, Object> tmp:pageInfo.getList()) {
					tmp.put("ahrId", (String) tmp.get("eventCode"));
					result.add(tmp);
				}
			}
			
		}
		return result;
	}

}
