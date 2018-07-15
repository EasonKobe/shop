package com.jeedev.msdp.sys.user.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.user.entity.SysUserRoleRelEntity;
@DataAuthClassIgnore
public interface ISysUserPermissionRelDao extends BaseDao<SysUserRoleRelEntity, Integer> {

	List<Map<String, Object>> findUserPermRelList(Map<String, Object> params);
}