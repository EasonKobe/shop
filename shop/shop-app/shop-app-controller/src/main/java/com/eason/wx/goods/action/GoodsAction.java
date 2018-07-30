package com.eason.wx.goods.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eason.wx.goods.api.IGoodsApi;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.web.action.BaseAppAction;

/**
 * @类名称 GoodsAction.java
 * @类描述 <pre></pre>
 * @作者 chenyuxin 136267719@qq.com
 * @创建时间 2018年7月27日 下午10:23:43
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenyuxin 	2018年7月27日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/public/goods")
public class GoodsAction extends BaseAppAction{
	
	@Autowired
	private IGoodsApi goodsApi; 
	
	@ResponseBody
	@RequestMapping("/findGodosList")
	public ResponseVO findGoodsList(@RequestParam Map<String, Object> params, PageParam pageParam) {
		List<Map<String, Object>> goodsList = this.goodsApi.findGoodsList(params, pageParam);
		return this.successResponse(new PageInfo<>(goodsList));
	}
	
	
	@ResponseBody
	@RequestMapping("/getGoods")
	public ResponseVO getGoods(@RequestParam Map<String, Object> params) {
		Map<String, Object> goodsMap = this.goodsApi.getGoods(params);
		return this.successResponse(goodsMap);
	}
	
	@ResponseBody
	@RequestMapping("/deleteGoods")
	public ResponseVO deleteGoods(@RequestParam Map<String, Object> params) {
		this.goodsApi.deleteGoods(params);
		return this.successResponse("删除成功");
	}
	
	@ResponseBody
	@RequestMapping("/saveOrUpdateGoods")
	public ResponseVO saveOrUpdateGoods(@RequestParam Map<String, Object> params) {
		Map<String, Object> goodsMap = this.goodsApi.saveOrUpdateGoods(params);
		return this.successResponse(goodsMap);
	}
	
}
