package com.jeedev.msdp.sys.org.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.org.entity.SysOrganizationEntity;
@DataAuthClassIgnore
public interface ISysOrganizationDao extends BaseDao<SysOrganizationEntity, Integer>{

	public List<Map<String,Object>> findOrgList(Map<String, Object> params);
	
	
	public List<Map<String,Object>> findParentOrgs(Map<String, Object> params);
	
	
	public List<Map<String,Object>> findChildrenOrgs(Map<String, Object> params);
	
	public List<Map<String,Object>> findCoreOrgList(Map<String, Object> params);

}