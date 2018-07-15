package com.jeedev.msdp.sys.param.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.base.param.service.IParamService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.param.api.ParamConstants;
import com.jeedev.msdp.sys.param.service.ISysParamService;

@Service("sysParamService")
public class ParamServiceImpl implements ISysParamService {
	@Autowired
	private IParamService paramService;
	

	@Override
	public PageInfo<Map<String, String>> findParamPage(Map<String, String> params, PageParam pageParam) {
		return new PageInfo<>(paramService.findParams(params));
	}

	@Override
	public void saveParam(Map<String, String> params) throws Exception {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		String id = MapUtils.getString(params, ParamConstants.ID);
		if (id == null) {
			// 新增
			paramService.saveParam(params);
			return;
		}
		paramService.updateParam(params);
	}

	@Override
	public void deleteParam(Map<String, String> params) throws Exception {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		String id = MapUtils.getString(params, ParamConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		paramService.deleteParam(params);
	}

	@Override
	public void updateParam(Map<String, String> params) throws Exception {
		paramService.updateParam(params);
	}

	@Override
	public Map<String, String> getParamMap(Map<String, String> params) {
		String parmId = MapUtils.getString(params, ParamConstants.PARM_ID);
		String id = MapUtils.getString(params, ParamConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(ParamConstants.PARM_ID);
		}
		if (!StringUtil.isEmpty(parmId)) {
			params.remove(ParamConstants.ID);
		}
		params.put("delInd", "0");
		// 默认调用查询方法。
		List<Map<String, String>> parmList = paramService.findParams(params);
		// 判断是否存在数据
		long total = parmList.size();
		if (0 < total) {
			// 获取查询结果列表
			if (null != parmList && parmList.size() > 0) {
				return (Map<String, String>) parmList.get(0);
			}
		}
		return null;
	}
}
