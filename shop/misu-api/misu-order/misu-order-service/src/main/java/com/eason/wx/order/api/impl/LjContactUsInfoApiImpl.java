package com.eason.wx.order.api.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eason.wx.order.api.ILjContactUsInfoApi;
import com.eason.wx.order.service.ILjContactUsInfoService;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.BizException;

@Service("ljContactUsInfoApi")
public class LjContactUsInfoApiImpl implements ILjContactUsInfoApi {

	@Autowired
	private ILjContactUsInfoService ljContactUsInfoService;
	
	@Override
	public List<Map<String, Object>> findLjContactUsInfoList(
			Map<String, Object> params, PageParam pageParam) throws BizException {
		return this.ljContactUsInfoService.findLjContactUsInfoList(params);
	}

	@Override
	public Map<String, Object> getLjContactUsInfo(Map<String, Object> params)
			throws BizException {
		return this.ljContactUsInfoService.getLjContactUsInfo(params);
	}

	@Override
	public Map<String, Object> saveOrUpdateLjContactUsInfoTrans(
			Map<String, Object> params) throws BizException {
		return this.ljContactUsInfoService.saveOrUpdateLjContactUsInfo(params);
	}

	@Override
	public void deleteLjContactUsInfoTrans(Map<String, Object> params)
			throws BizException {
		this.ljContactUsInfoService.deleteLjContactUsInfo(params);
	}

}
