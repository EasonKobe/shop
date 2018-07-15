package com.jeedev.msdp.sys.org.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.org.entity.SysOrgDeptRelEntity;
@DataAuthClassIgnore
public interface ISysOrgDeptRelDao extends BaseDao<SysOrgDeptRelEntity, Integer>{

	List<Map<String,Object>> findOrgDeptRelList(Map<String, Object> params);

	
}