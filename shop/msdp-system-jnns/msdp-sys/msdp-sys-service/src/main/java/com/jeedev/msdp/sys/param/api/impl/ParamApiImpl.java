package com.jeedev.msdp.sys.param.api.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.param.api.ParamConstants;
import com.jeedev.msdp.sys.param.api.SysParamApi;
import com.jeedev.msdp.sys.param.service.ISysParamService;
import com.jeedev.msdp.trace.IProviderSign;

@Service("sysParamApi")
public class ParamApiImpl implements SysParamApi,IProviderSign{

	@Autowired
	private  ISysParamService permService;
	
	@Override
	public PageInfo<Map<String, String>> findParamPage(Map<String, String> params, PageParam pageParam) { 
		return permService.findParamPage(params,  pageParam);
	}
	
	@Override
	public void saveParamTrans(Map<String, String> params) throws Exception {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		String id= MapUtils.getString(params, ParamConstants.ID);
		if(id==null){
			permService.saveParam(params);//新增
			return;
		}
		permService.updateParam(params);
	}
	
	@Override
	public void deleteParamTrans(Map<String, String> params) throws Exception {
		permService.deleteParam(params);
	}

	@Override
	public void updateParamTrans(Map<String, String> params) throws Exception {
		permService.updateParam(params);
	}
	@Override
	public Map<String, String> getParamMap(Map<String, String> params) {
		 return permService.getParamMap(params);
	}
}
