package com.jeedev.msdp.sys.dataauth.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.dataauth.entity.SysDataAuthEntity;
/**
 * @类名称 ISysDataAuthDao.java
 * @类描述 <pre>数据权限授权dao</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月25日 下午5:22:21
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
public interface ISysDataAuthDao extends BaseDao<SysDataAuthEntity, Integer> {

	List<Map<String,Object>> findDataAuthList(Map<String, Object> params);
	List<Map<String,Object>> findDataAuthListByExact(Map<String, Object> params);
}