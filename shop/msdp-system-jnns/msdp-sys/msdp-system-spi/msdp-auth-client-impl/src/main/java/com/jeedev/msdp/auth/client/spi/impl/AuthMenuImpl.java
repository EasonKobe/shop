package com.jeedev.msdp.auth.client.spi.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.client.spi.AuthMenuSpi;
import com.jeedev.msdp.standard.exception.BizException;
import com.jeedev.msdp.sys.menu.api.MenuApi;

@Service("client.authMenu")
public class AuthMenuImpl implements AuthMenuSpi {
	@Autowired
	private MenuApi menuApi;
	@Override
	public List<Map<String, Object>> findMenus(Map<String, Object> params)
			throws BizException {
		PageInfo<Map<String, Object>> menusInf = menuApi.findMenuPage(params, null);
		if(menusInf.getSize()>0){
			return menusInf.getList();
		}
		return null;
//		return menuApi.findMenus(params);
	}

}
