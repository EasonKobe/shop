package com.eason.wx.goods.dao;

import java.util.List;
import java.util.Map;

import com.eason.wx.goods.entity.LjContactUsInfoEntity;
import com.jeedev.msdp.core.dao.BaseDao;

/**
 * 联系我们信息表Dao类
 *
 * @类名称 LjContactUsInfoDao
 * @类描述 <pre> 联系我们信息表Dao类</pre>
 * @作者 chenyuxin
 * @创建时间 2018年03月14日
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	chenyuxin     	2018年03月14日 	新建
 * ----------------------------------------------
 * </pre>
 */
public interface ILjContactUsInfoDao extends BaseDao<LjContactUsInfoEntity, Integer> {

	List<Map<String, Object>> findLjContactUsInfoList(Map<String, Object> params);

}

