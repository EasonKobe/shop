package com.jeedev.msdp.sys.menu.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.menu.entity.SysResMenuEventRelEntity;

/**
 * 
 * @类名称 SysRolePermissionRelDao.java
 * @类描述 <pre>角色关联用户管理</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 下午7:56:39
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
public interface ISysResMenuEventRelDao extends BaseDao<SysResMenuEventRelEntity, Integer> {

    /**
     * 
     * @方法名称 findRolePermList
     * @功能描述 <pre>获取菜单与 事件资源关系列表</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年8月16日 下午5:10:45
     * @param params
     * @return
     */
	List<Map<String,Object>> findMenuEventRelList(Map<String, Object> params);
	
}