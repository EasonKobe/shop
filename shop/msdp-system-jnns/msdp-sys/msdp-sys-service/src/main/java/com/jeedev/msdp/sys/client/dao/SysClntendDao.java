package com.jeedev.msdp.sys.client.dao;
import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.client.entity.SysClntendEntity;
/**
 * @类名称 ClntendDao.java
 * @类描述 <pre>授权客户端信息</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月22日 下午12:23:07
 * @版本 1.00
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月22日             
 *     ----------------------------------------------
 * </pre>
 */
@DataAuthClassIgnore 
public interface SysClntendDao extends BaseDao<SysClntendEntity, Integer> {
	/**
	 * @方法名称 findByClntendId
	 * @功能描述 <pre>通过客户端编号获取客户端信息<</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 下午12:24:15
	 * @param clntendId 客户端编号
	 * @return 客户端信息
	 */
	public Map<String,Object> findByClntendId(String clntendId) ;
	/**
	 * @方法名称 findEnabledList
	 * @功能描述 <pre>获取启用的客户端信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 下午12:24:15
	 * @return 客户端信息
	 */
	public List<Map<String, Object>> findEnabledList();
	
	List<Map<String,Object>> queryList(Map<String, Object> params);
}
