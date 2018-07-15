package com.jeedev.msdp.auth.client.spi.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeedev.msdp.auth.client.spi.AuthClientSpi;
import com.jeedev.msdp.sys.client.api.IClientApi;

@Service("client.authClient")
public class AuthClientImpl implements AuthClientSpi {
	@Autowired
	private IClientApi clientApi;
	@Override
	public Map<String, Object> findByClntendId(String clntendId) {
		Map<String,Object> cond = new HashMap<String,Object>();
		cond.put("clntendId", clntendId);
		return clientApi.findByClntendId(cond);
//		return null;
	}

	@Override
	public List<Map<String, Object>> findEnabledList() {
		return clientApi.findEnabledList();
//		return null;
	}

}
