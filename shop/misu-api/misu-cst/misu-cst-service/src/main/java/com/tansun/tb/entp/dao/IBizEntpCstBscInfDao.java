/**
 * 包名称 com.tansun.tb.cst.dao
 * 类名称 IBizCstBscInfDao.java
 * 创建时间 2017年8月10日下午2:26:43
 * Copyright (c) 2017, www.tansun.com.cn All Rights Reserved.
 *
 */
package com.tansun.tb.entp.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.tansun.tb.entp.entity.EnterpriseEntity;

/**
 * @类名称 IBizCstBscInfDao.java
 * @类描述 <pre>客户基础信息表查询用Dao</pre>
 * @作者 chenqun chenqun@tansun.com.cn
 * @创建时间 2017年8月10日 下午2:26:43
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenqun 	2017年8月10日             
 *     ----------------------------------------------
 * </pre>
 */
//忽略数据权限过滤
@DataAuthClassIgnore
public interface IBizEntpCstBscInfDao extends BaseDao<EnterpriseEntity, Integer>{
	
	/**
	 * @方法名称 findBizCstBscInfPage
	 * @功能描述 <pre>查询客户基本信息列表</pre>
	 * @作者    chenqun
	 * @创建时间 2017年8月10日 下午3:09:25
	 * @param 查询客户基本信息列表
	 * @return
	 */
	
	public PageInfo<Map<String,Object>> findBizCstBscInfPage(Map<String, Object> params);
	
	/**
	 * @方法名称 findBizCstMgtTeamListByParams
	 * @功能描述 <pre>查询客户管理团队信息</pre>
	 * @作者    chenqun
	 * @创建时间 2017年8月10日 下午3:47:02
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findBizCstMgtTeamPage(Map<String, Object> params);
	
}
