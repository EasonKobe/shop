package com.eason.wx.goods.dao;

import java.util.List;
import java.util.Map;

import com.eason.wx.goods.entity.WxGoodsEntity;
import com.jeedev.msdp.core.dao.BaseDao;

/**
 * 【实例】Dao类
 *
 * @类名称 WxGoodsDao
 * @类描述 <pre> 【实例】Dao类</pre>
 * @作者 chenyuxin
 * @创建时间 2018年07月26日
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	chenyuxin     	2018年07月26日 	新建
 * ----------------------------------------------
 * </pre>
 */
public interface WxGoodsDao extends BaseDao<WxGoodsEntity, Integer> {

	List<Map<String, Object>> findGoodsList(Map<String, Object> params);

}

