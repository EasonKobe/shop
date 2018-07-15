package com.jeedev.msdp.sys.user.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.user.entity.SysUserEntity;
/**
 * 
 * @类名称 SysUserDao.java
 * @类描述 <pre></pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午2:26:49
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月17日             
 *     ----------------------------------------------
 * </pre>
 */
@DataAuthClassIgnore
public interface ISysUserDao extends BaseDao<SysUserEntity, Integer> {
	/**
	 * 
	 * @方法名称 findUserList
	 * @功能描述<pre>获取用户列表</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2017年8月16日 下午5:12:00
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findUserList(Map<String, Object> params);
	/**
	 * @方法名称 countSysUserByLoginName
	 * @功能描述 <pre>根据登录名统计用户数量，作唯一性判断使用</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月21日 下午8:37:39
	 * @param loginName
	 * @return
	 */
	public Integer countSysUserByLoginName(String loginName); 
	/**
	 * @方法名称 findUniqueByProperty
	 * @功能描述 <pre>根据实体名称和字段名称和字段值获取唯一记录</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月19日 上午10:18:08
	 * @param propertyName
	 * @param value
	 * @return
	 */
//	Map<String, Object> findUniqueByProperty(Map<String, Object> params);
}