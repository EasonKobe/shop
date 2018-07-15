package com.jeedev.msdp.sys.user.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.user.entity.SysUserOrgRelEntity;
@DataAuthClassIgnore
public interface ISysUserOrgRelDao extends BaseDao<SysUserOrgRelEntity, Integer> {
	/**
	 * @方法名称 findUserOrgRelList
	 * @功能描述 <pre>根据条件查询用户机构</pre>
	 * @作者    yuyq
	 * @创建时间 2017年11月18日 下午4:40:14
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> findUserOrgRelList(Map<String, Object> params);
	
	/**
	 * @方法名称 findUserOrgDeptRelList
	 * @功能描述 <pre>根据条件查询用户机构部门</pre>
	 * @作者    yuyq
	 * @创建时间 2017年11月18日 下午4:40:39
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> findUserOrgDeptRelList(Map<String, Object> params);
	
	public List<Map<String, Object>> findUserByOrgRoleRel(Map<String, Object> params);

}