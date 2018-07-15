package com.jeedev.msdp.sys.tenant.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthMethod;
import com.jeedev.msdp.sys.tenant.entity.SysTenantEntity;

@DataAuthClassIgnore
public interface ISysTenantDao extends BaseDao<SysTenantEntity, Integer>{
	@DataAuthMethod
	List<Map<String, Object>> findTenantList(Map<String, Object> params);

	Map<String, Object> getTenantMap(Map<String, Object> params);
}
