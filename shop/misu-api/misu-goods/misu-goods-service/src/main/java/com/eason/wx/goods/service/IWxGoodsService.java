package com.eason.wx.goods.service;

import com.jeedev.msdp.standard.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * 【实例】对内接口
 *
 * @类名称 WxGoodsService
 * @类描述 <pre> 【实例】对外接口</pre>
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
public interface IWxGoodsService {

	/**
	 * 查询【实例】列表
	 * @方法名称 findGoodsList
	 * @功能描述 <pre> 查询【实例】列表 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/07/26 23:04
	 * @param params 参数<br>
	 * @return
	 *         goodsId：商品编号<br>
	 *         catalogId：目录编号<br>
	 *         goodsName：商品名称<br>
	 *         goodsPrice：商品价格<br>
	 *         goodsDiscount：商品折扣<br>
	 *         goodsSale：商品销量<br>
	 *         goodsNum：商品库存<br>
	 *         goodsTitle：商品标题<br>
	 *         abstract：商品描述<br>
	 *         freeInd：运费标识<br>
	 *         hotInd：热销标识<br>
	 *         goodsBrands：商品品牌<br>
	 *         newInd：新品标识<br>
	 *         benefit：优惠信息<br>
	 *         status：上架标识<br>
	 *         goodsScore：商品可得积分<br>
	 *         remark1：备份字段1<br>
	 *         remark2：备份字段2<br>
	 *         remark3：备份字段3<br>
	 * @throws RpcException RpcException
	 */
	List<Map<String, Object>> findGoodsList(Map<String, Object> params) throws BizException;

	/**
	 * 查询【实例】
	 * @方法名称 findGoods
	 * @功能描述 <pre> 查询【实例】 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/07/26 23:04
	 * @param params 参数<br>
	 * @return
	 *         goodsId：商品编号<br>
	 *         catalogId：目录编号<br>
	 *         goodsName：商品名称<br>
	 *         goodsPrice：商品价格<br>
	 *         goodsDiscount：商品折扣<br>
	 *         goodsSale：商品销量<br>
	 *         goodsNum：商品库存<br>
	 *         goodsTitle：商品标题<br>
	 *         abstract：商品描述<br>
	 *         freeInd：运费标识<br>
	 *         hotInd：热销标识<br>
	 *         goodsBrands：商品品牌<br>
	 *         newInd：新品标识<br>
	 *         benefit：优惠信息<br>
	 *         status：上架标识<br>
	 *         goodsScore：商品可得积分<br>
	 *         remark1：备份字段1<br>
	 *         remark2：备份字段2<br>
	 *         remark3：备份字段3<br>
	 * @throws RpcException RpcException
	 */
	Map<String, Object> getGoods(Map<String, Object> params) throws BizException;

	/**
	 * 新增/更新【实例】
	 * @方法名称 saveOrUpdateGoods
	 * @功能描述 <pre> 新增【实例】 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/07/26 23:04
	 * @param params 参数<br>
	 *			operator：操作用户<br>
	 * @return
	 *         goodsId：商品编号<br>
	 *         catalogId：目录编号<br>
	 *         goodsName：商品名称<br>
	 *         goodsPrice：商品价格<br>
	 *         goodsDiscount：商品折扣<br>
	 *         goodsSale：商品销量<br>
	 *         goodsNum：商品库存<br>
	 *         goodsTitle：商品标题<br>
	 *         abstract：商品描述<br>
	 *         freeInd：运费标识<br>
	 *         hotInd：热销标识<br>
	 *         goodsBrands：商品品牌<br>
	 *         newInd：新品标识<br>
	 *         benefit：优惠信息<br>
	 *         status：上架标识<br>
	 *         goodsScore：商品可得积分<br>
	 *         remark1：备份字段1<br>
	 *         remark2：备份字段2<br>
	 *         remark3：备份字段3<br>
	 * @throws RpcException RpcException
	 */
	Map<String, Object> saveOrUpdateGoods(Map<String, Object> params) throws BizException;

	/**
	 * 删除【实例】
	 * @方法名称 deleteGoods
	 * @功能描述 <pre> 删除【实例】 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/07/26 23:04
	 * @param params 参数<br>
	 *          GoodsId：【实例】编号<br>
	 *          operator：操作用户
	 * @throws RpcException RpcException
	 */
	void deleteGoods(Map<String,Object> params) throws BizException;
}
