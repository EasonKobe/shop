package com.jeedev.msdp.sys.area.api.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeedev.msdp.base.area.service.IBaseNationAreaService;
import com.jeedev.msdp.sys.area.api.BaseNationAreaApi;
import com.jeedev.msdp.trace.IProviderSign;

@Service("baseNationAreaApi")
public class BaseNationAreaApiImpl implements BaseNationAreaApi,IProviderSign{

	@Autowired
	private IBaseNationAreaService baseNationAreaService;
	
	
	@Override
	public List<Map<String, Object>> findByParentAreaCode(Map<String, String> params) {
		return baseNationAreaService.findByParentAreaCode(params);
	}

	@Override
	public Map<String, Object> getAreaMap(Map<String, String> params) {
		return baseNationAreaService.findOneArea(params);
	}

	@Override
	public int saveAreaTrans(Map<String, String> params) throws Exception {
		return baseNationAreaService.saveOneArea(params);
	}

	@Override
	public int updateAreaTrans(Map<String, String> params) throws Exception {
		return baseNationAreaService.updateOneArea(params);
	}

	@Override
	public int deleteAreaTransById(Map<String, String> params) throws Exception {
		return baseNationAreaService.deleteOneAreaById(params);
	}

	@Override
	public Integer countNotDistinct(String areaCode) {
		return baseNationAreaService.countNotDistinct(areaCode);
	}

	@Override
	public List<Map<String, Object>> findAddresList(Map<String, String> params) {
		return baseNationAreaService.findAddresList(params);
	}

}
