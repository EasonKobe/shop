package com.jeedev.msdp.sys.event.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.event.entity.SysResEventEntity;
/**
 * 事件资源 DAO
 * @类名称 ISysResEventDao.java
 * @类描述 <pre></pre>
 * @作者 chenjc chenjc@tansun.com.cn
 * @创建时间 2017年11月7日 下午4:06:58
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjc 	2017年11月7日             
 *     ----------------------------------------------
 * </pre>
 */
@DataAuthClassIgnore
public interface ISysResEventDao extends BaseDao<SysResEventEntity, Integer> {

	List<Map<String,Object>> findEventList(Map<String, Object> params);
}
