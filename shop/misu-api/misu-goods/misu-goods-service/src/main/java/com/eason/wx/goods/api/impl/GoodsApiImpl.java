package com.eason.wx.goods.api.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eason.wx.goods.api.IGoodsApi;
import com.eason.wx.goods.service.IWxGoodsService;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.BizException;

@Service("goodsApi")
public class GoodsApiImpl implements IGoodsApi {
	
	@Autowired
	private IWxGoodsService wxGoodsService;

	@Override
	public List<Map<String, Object>> findGoodsList(Map<String, Object> params, PageParam pageParam) throws BizException {
		
		return this.wxGoodsService.findGoodsList(params);
	}

	@Override
	public Map<String, Object> getGoods(Map<String, Object> params) throws BizException {
		return this.wxGoodsService.getGoods(params);
	}

	@Override
	public Map<String, Object> saveOrUpdateGoods(Map<String, Object> params) throws BizException {
		return this.wxGoodsService.saveOrUpdateGoods(params);
	}

	@Override
	public void deleteGoods(Map<String, Object> params) throws BizException {
		this.wxGoodsService.deleteGoods(params);
	}

}
