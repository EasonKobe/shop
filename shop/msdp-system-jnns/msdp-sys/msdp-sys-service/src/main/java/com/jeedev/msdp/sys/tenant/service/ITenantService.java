package com.jeedev.msdp.sys.tenant.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;

public interface ITenantService {

	PageInfo<Map<String, Object>> findTenantPage(Map<String, Object> params);

	Map<String, Object> getTenantMap(Map<String, Object> params);

	Map<String, Object> saveTenant(Map<String, Object> params);

	void updateTenant(Map<String, Object> params);

	void deleteTenant(Map<String, Object> params);

}
