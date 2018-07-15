package com.jeedev.msdp.sys.menu.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.menu.entity.SysResMenuEntity;
/**
 * @类名称 ISysResMenuDao.java
 * @类描述 <pre>菜单dao</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月20日 下午5:39:40
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	yuyq            
 *     ----------------------------------------------
 * </pre>
 */
@DataAuthClassIgnore
public interface ISysResMenuDao extends BaseDao<SysResMenuEntity, Integer> {

	List<Map<String,Object>> findMenuList(Map<String, Object> params);

}
