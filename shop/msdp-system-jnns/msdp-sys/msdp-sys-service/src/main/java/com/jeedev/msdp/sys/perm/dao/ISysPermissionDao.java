package com.jeedev.msdp.sys.perm.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.perm.entity.SysPermissionEntity;
@DataAuthClassIgnore
public interface ISysPermissionDao extends BaseDao<SysPermissionEntity, Integer> {

	List<Map<String,Object>> findPermList(Map<String, Object> params);
	
}