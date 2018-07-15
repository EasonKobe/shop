package com.jeedev.msdp.sys.user.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.user.entity.SysUserDeptRelEntity;
@DataAuthClassIgnore
public interface ISysUserDeptRelDao extends BaseDao<SysUserDeptRelEntity, Integer> {

	public List<Map<String, Object>> findUserDeptRelList(Map<String, Object> params);
	/**
	 * @方法名称 countSysUserByLoginName
	 * @功能描述 <pre>唯一性判断使用</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月21日 下午8:37:39
	 * @param loginName
	 * @return
	 */
	public Integer countNum(Map<String, Object> params); 
	
	
	public List<Map<String, Object>> findUserByDeptRoleRel(Map<String, Object> params) ;

}