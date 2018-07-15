package com.jeedev.msdp.sys.dataauth.api.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.sys.dataauth.api.DataAuthApi;
import com.jeedev.msdp.sys.dataauth.service.IDataAuthModelService;
import com.jeedev.msdp.sys.dataauth.service.IDataAuthService;
import com.jeedev.msdp.trace.IProviderSign;
import com.jeedev.msdp.utlis.MapUtil;

/**
 * @类名称 DataAuthApiImpl.java
 * @类描述 <pre>数据权限服务api</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月25日 下午5:26:32
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	yuyq            
 *     ----------------------------------------------
 * </pre>
 */
@Service("dataAuthApi")
public class DataAuthApiImpl implements DataAuthApi,IProviderSign {

	/**
	 * 数据权限服务
	 */
	@Autowired
	private IDataAuthService dataAuthService;
	/**
	 * 数据权限类型服务
	 */
	@Autowired
	private IDataAuthModelService dataAuthModelService;

	@Override
	public PageInfo<Map<String, Object>> findDataAuthPage(Map<String, Object> params,PageParam pageParam) {
		return dataAuthService.findDataAuthPage(params);
	}

	@Override
	public Map<String, Object> getDataAuthMap(Map<String, Object> params) {
		return dataAuthService.getDataAuthMap(params);
	}

	@Override
	public Map<String, Object> saveDataAuthTrans(Map<String, Object> params) { 
		//有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			return dataAuthService.saveDataAuth(params);
		}
		dataAuthService.updateDataAuth(params);
		return params;
	}

	@Override
	public void deleteDataAuthTrans(Map<String, Object> params) {
		 dataAuthService.deleteDataAuth(params);
	}

	@Override
	public PageInfo<Map<String, Object>> findDataAuthModelPage(Map<String, Object> params,PageParam pageParam) {
		return dataAuthModelService.findDataAuthModelPage(params);
	}

	@Override
	public Map<String, Object> saveBatchDataAuthTrans(Map<String, Object> params) {
		List<Map<String,Object>> list = MapUtil.getList(params, "list");
		for(Map<String,Object> map:list) {
			saveDataAuthTrans(map);
		}
		return params;
	}

}
