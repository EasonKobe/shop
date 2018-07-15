package com.jeedev.msdp.sys.client.api.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.client.api.IClientApi;
import com.jeedev.msdp.sys.client.service.IClientService;
import com.jeedev.msdp.trace.IProviderSign;

@Service("clientApi")
public class ClientApiImpl implements IClientApi,IProviderSign{
	
	@Autowired
	private IClientService clientService;
	/**
	 * @方法名称 findByClntendId
	 * @功能描述 <pre>通过客户端编号获取客户端信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 下午12:24:15
	 * @param clntendId 客户端编号
	 * @return 客户端信息
     * id 表主键
	 * clntendId 客户端编号
	 * clntendNm 客户端名称
	 * clntendSecret 客户端安全码
	 * clntendDomain 客户端域名
	 * clntendSt 客户端状态(1:启用，2:禁用)
	 */
	@Override
	public Map<String, Object> findByClntendId(Map<String, Object> params) {
		String clntendId=MapUtils.getString(params, "clntendId");
		if (clntendId == null) 
		throw ExceptionFactory.getBizException("查询客户端编号为空");
		
		return clientService.findByClntendId(clntendId);
	}
	/**
	 * @方法名称 findEnabledList
	 * @功能描述 <pre>获取启用的客户端列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 下午12:24:15
	 * @return 客户端信息</br>clntendId：客户端编号</br>clntendNm：客户端名称
	 */
	@Override
	public List<Map<String, Object>> findEnabledList() {
		return clientService.findEnabledList();
	}
	@Override
	public PageInfo<Map<String, Object>> findClientPage(Map<String, Object> params, PageParam pageParam) {
		return clientService.findClientPage(params, pageParam);
	}

}
