package com.eason.wx.goods.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eason.wx.goods.dao.WxGoodsDao;
import com.eason.wx.goods.entity.WxGoodsEntity;
import com.eason.wx.goods.service.IWxGoodsService;
import com.jeedev.msdp.standard.exception.BizException;

/**
 * 【实例】实现类
 *
 * @类名称 WxGoodsServiceImpl
 * @类描述 <pre> 【实例】实现类 </pre>
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
@Service("wxGoodsService")
public class WxGoodsServiceImpl implements IWxGoodsService{

	@Autowired
	private WxGoodsDao wxGoodsDao;

	@Override
	public List<Map<String, Object>> findGoodsList(Map<String, Object> params) throws BizException {
		return wxGoodsDao.findGoodsList(params);
	}

	@Override
	public Map<String, Object> getGoods(Map<String, Object> params) throws BizException {
		List<Map<String, Object>> list = findGoodsList(params);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public Map<String, Object> saveOrUpdateGoods(Map<String, Object> params) throws BizException {
		Integer id = MapUtils.getInteger(params, "id");
		if(id == null){
			WxGoodsEntity wxGoods = wxGoodsDao.save(new WxGoodsEntity().coverToEntity(params));
			params.put("id", wxGoods.getId());
		}else{
			WxGoodsEntity findOne = wxGoodsDao.findOne(id);
			wxGoodsDao.update(findOne.coverToEntity(params));
//			params = findOne.coverToMap();
		}
		return params;
	}

	@Override
	public void deleteGoods(Map<String,Object> params) throws BizException {
		Integer id = MapUtils.getInteger(params, "id");
		WxGoodsEntity findOne = wxGoodsDao.findOne(id);
		findOne.setDelInd("1");
		wxGoodsDao.update(findOne);
	}
}

