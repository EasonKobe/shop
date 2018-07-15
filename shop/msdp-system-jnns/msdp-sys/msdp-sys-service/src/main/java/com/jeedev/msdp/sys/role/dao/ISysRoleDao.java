package com.jeedev.msdp.sys.role.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.role.entity.SysRoleEntity;
/**
 * 
 * @类名称 SysRoleDao.java
 * @类描述 <pre>角色管理</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 下午7:53:49
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月15日             
 *     ----------------------------------------------
 * </pre>
 */
@DataAuthClassIgnore
public interface ISysRoleDao extends BaseDao<SysRoleEntity, Integer>{

	/**
	 * 
	 * @方法名称 findRoleList
	 * @功能描述 <pre>获取角色列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午5:12:00
	 * @param params
	 * @return
	 */
	List<Map<String,Object>> findRoleList(Map<String, Object> params);
	
}
