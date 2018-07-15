package com.jeedev.msdp.auth.client.spi.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.client.spi.AuthLoginSpi;
import com.jeedev.msdp.auth.client.token.LoginAuthToken;
import com.jeedev.msdp.sys.tenant.api.TenantApi;
import com.jeedev.msdp.sys.tenant.api.TenantConstants;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.utlis.StringUtil;

@Service("client.authLogin")
public class AuthLoginImpl implements AuthLoginSpi {
	@Autowired
	private UserApi loginApi;
	@Autowired
	private TenantApi tenantApi;
	
	@Override
	public Map<String, Object> findByLoginName(String loginName, String clientId) {
		//Colin.DZX 20170912
		Map<String,Object> params=new HashMap<>();
		params.put(UserConstants.MUL_LOGIN_NAME, loginName);
		params.put(UserConstants.CLNTEND_ID, clientId);
		return loginApi.getUserMap(params);
	}
	@Override
	public Map<String, Object> findByLoginAuthToken(LoginAuthToken token) {
		Map<String,Object> params=new HashMap<>();
		params.put(UserConstants.MUL_LOGIN_NAME, token.getPrincipal());
		params.put(UserConstants.CLNTEND_ID, token.getClientId());
		
		
		String tenantId = "";
		String tenantName = token.getRequest().getParameter("tenantName");
		if(StringUtils.isNotBlank(tenantName)) {
			Map<String,Object> queryTenant=new HashMap<>();
			queryTenant.put("tenantName", tenantName);
			PageInfo<Map<String,Object>> tenants = tenantApi.findTenantPage(queryTenant,null);
			if(tenants==null) {
				return null;
			}
			if(tenants.getSize()==0) {
				return null;
			}
			Map<String, Object> tenantInfo = tenants.getList().get(0);
			
			if(tenantInfo==null) return null;
			if(tenantInfo.get("statusCd")==null||
					!tenantInfo.get("statusCd").toString().equals("1")) {
				//租户状态未生效，不允许登陆
				return null;
			}
			tenantId = (String) tenantInfo.get(TenantConstants.TENANT_ID);
			params.put(UserConstants.TENANT_ID, tenantId);
			return loginApi.getUserMap(params);
		}else {
			//租户ID为空  ,租户ID不作为条件 
			PageInfo<Map<String,Object>> users = loginApi.findUserPage(params, null);
			if(users!=null && users.getList()!=null && users.getList().size()>0) {
				for(Map<String,Object> user:users.getList()) {
//					String thisTenantId = (String) user.get(UserConstants.TENANT_ID);
//					if(StringUtils.isNotBlank(thisTenantId)) {
//						continue;
//					}
					return user;
				}
			}
			
			//用户不存在或者存在多个用户,默认返回 第一个 
			return null;
		}
		
	}
	@Override
	public Map<String, Object> findLoginConfigBySubLogin(String clntendId,String loginUriSuffix) {
		Map<String, Object> config = null;
		if(StringUtil.isBlank(loginUriSuffix)) {
			return config;
		}
		
		Map<String,Object> queryTenant=new HashMap<>();
		queryTenant.put("loginUriSuffix", loginUriSuffix);
		Map<String, Object> tenantInfo = tenantApi.getTenantMap(queryTenant);
		if(tenantInfo==null) {
			return config;
		}
		return tenantInfo;
	}
	
}
