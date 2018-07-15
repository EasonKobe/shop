package com.jeedev.msdp.sys.quickmenu.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.sys.quickmenu.entity.SysQuickMenuEntity;
/**
 * 
 * @类名称 IBizMsgAppDao.java
 * @类描述 <pre></pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年9月23日 下午4:06:31
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年9月23日             
 *     ----------------------------------------------
 * </pre>
 */
public interface ISysQuickMenuDao extends BaseDao<SysQuickMenuEntity, Integer> {

	/**
	 * 
	 * @方法名称 findQuickMenuList
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月12日 下午4:04:52
	 * @param params
	 * @return
	 */
    List<Map<String, Object>> findQuickMenuList(Map<String, Object> params);

}
