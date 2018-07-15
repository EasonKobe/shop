package com.jeedev.msdp.sys.user.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.user.entity.SysUserGroupRelEntity;
@DataAuthClassIgnore
public interface ISysUserGroupRelDao extends BaseDao<SysUserGroupRelEntity, Integer> {

	List<Map<String,Object>> findUserGroupRelList(Map<String, Object> params);
}