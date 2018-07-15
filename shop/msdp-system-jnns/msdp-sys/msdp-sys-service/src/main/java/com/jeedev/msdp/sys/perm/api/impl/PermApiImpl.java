package com.jeedev.msdp.sys.perm.api.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.sys.perm.api.PermApi;
import com.jeedev.msdp.sys.perm.service.IPermService;
import com.jeedev.msdp.trace.IProviderSign;


@Service("permApi")
public class PermApiImpl implements PermApi,IProviderSign {

	/**
	 * 权限服务
	 */
	@Autowired
	private IPermService permService;


	@Override
	public PageInfo<Map<String, Object>> findPermPage(Map<String, Object> params, PageParam pageParam) {
		// TODO Auto-generated method stub
		return permService.findPermPage(params,pageParam);
	}

	@Override
	public Map<String, Object> getPermMap(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return permService.getPermMap(params);
	}

	@Override
	public Map<String, Object> savePermTrans(Map<String, Object> params) { 
		//有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
		
			return permService.savePerm(params);
		}
		permService.updatePerm(params);
		return null;
	}

	@Override
	public void deletePermTrans(Map<String, Object> params) {
		// TODO Auto-generated method stub
		 permService.deletePerm(params);
	}

}
