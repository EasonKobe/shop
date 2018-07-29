
package com.eason.wx.goods.entity;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jeedev.msdp.core.data.BaseEntity;
import com.jeedev.msdp.utlis.MapUtil;

/**
 * 【实例】实体类
 *
 * @类名称 WxGoods
 * @类描述 <pre> 【实例】实体类</pre>
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
@Entity
@Table(name = "WX_GOODS")
public class WxGoodsEntity extends BaseEntity<Integer>{

	private static final long serialVersionUID = 1L;

 	/**  
 	 *商品编号 
 	 */
	@Column(name = "GOODS_ID")
	private String goodsId;

 	/**  
 	 *目录编号 
 	 */
	@Column(name = "CATALOG_ID")
	private String catalogId;

 	/**  
 	 *商品名称 
 	 */
	@Column(name = "GOODS_NAME")
	private String goodsName;

 	/**  
 	 *商品价格 
 	 */
	@Column(name = "GOODS_PRICE")
	private BigDecimal goodsPrice;

 	/**  
 	 *商品折扣 
 	 */
	@Column(name = "GOODS_DISCOUNT")
	private BigDecimal goodsDiscount;

 	/**  
 	 *商品销量 
 	 */
	@Column(name = "GOODS_SALE")
	private Integer goodsSale;

 	/**  
 	 *商品库存 
 	 */
	@Column(name = "GOODS_NUM")
	private Integer goodsNum;

 	/**  
 	 *商品标题 
 	 */
	@Column(name = "GOODS_TITLE")
	private String goodsTitle;

 	/**  
 	 *商品描述 
 	 */
	@Column(name = "DESCRIPTION")
	private String description;

 	/**  
 	 *运费标识 
 	 */
	@Column(name = "FREE_IND")
	private BigDecimal freeInd;

 	/**  
 	 *热销标识 
 	 */
	@Column(name = "HOT_IND")
	private String hotInd;

 	/**  
 	 *商品品牌 
 	 */
	@Column(name = "GOODS_BRANDS")
	private String goodsBrands;

 	/**  
 	 *新品标识 
 	 */
	@Column(name = "NEW_IND")
	private String newInd;

 	/**  
 	 *优惠信息 
 	 */
	@Column(name = "BENEFIT")
	private String benefit;

 	/**  
 	 *上架标识 
 	 */
	@Column(name = "STATUS")
	private String status;

 	/**  
 	 *商品可得积分 
 	 */
	@Column(name = "GOODS_SCORE")
	private Integer goodsScore;

 	/**  
 	 *备份字段1 
 	 */
	@Column(name = "REMARK_1")
	private String remark1;

 	/**  
 	 *备份字段2 
 	 */
	@Column(name = "REMARK_2")
	private String remark2;

 	/**  
 	 *备份字段3 
 	 */
	@Column(name = "REMARK_3")
	private String remark3;



	public String getGoodsId(){
		return goodsId;
	}

	public void setGoodsId(String goodsId){
		this.goodsId = goodsId;
	}

	public String getCatalogId(){
		return catalogId;
	}

	public void setCatalogId(String catalogId){
		this.catalogId = catalogId;
	}

	public String getGoodsName(){
		return goodsName;
	}

	public void setGoodsName(String goodsName){
		this.goodsName = goodsName;
	}

	public BigDecimal getGoodsPrice(){
		return goodsPrice;
	}

	public void setGoodsPrice(BigDecimal goodsPrice){
		this.goodsPrice = goodsPrice;
	}

	public BigDecimal getGoodsDiscount(){
		return goodsDiscount;
	}

	public void setGoodsDiscount(BigDecimal goodsDiscount){
		this.goodsDiscount = goodsDiscount;
	}

	public Integer getGoodsSale(){
		return goodsSale;
	}

	public void setGoodsSale(Integer goodsSale){
		this.goodsSale = goodsSale;
	}

	public Integer getGoodsNum(){
		return goodsNum;
	}

	public void setGoodsNum(Integer goodsNum){
		this.goodsNum = goodsNum;
	}

	public String getGoodsTitle(){
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle){
		this.goodsTitle = goodsTitle;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getFreeInd(){
		return freeInd;
	}

	public void setFreeInd(BigDecimal freeInd){
		this.freeInd = freeInd;
	}

	public String getHotInd(){
		return hotInd;
	}

	public void setHotInd(String hotInd){
		this.hotInd = hotInd;
	}

	public String getGoodsBrands(){
		return goodsBrands;
	}

	public void setGoodsBrands(String goodsBrands){
		this.goodsBrands = goodsBrands;
	}

	public String getNewInd(){
		return newInd;
	}

	public void setNewInd(String newInd){
		this.newInd = newInd;
	}

	public String getBenefit(){
		return benefit;
	}

	public void setBenefit(String benefit){
		this.benefit = benefit;
	}

	public String getStatus(){
		return status;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public Integer getGoodsScore(){
		return goodsScore;
	}

	public void setGoodsScore(Integer goodsScore){
		this.goodsScore = goodsScore;
	}

	public String getRemark1(){
		return remark1;
	}

	public void setRemark1(String remark1){
		this.remark1 = remark1;
	}

	public String getRemark2(){
		return remark2;
	}

	public void setRemark2(String remark2){
		this.remark2 = remark2;
	}

	public String getRemark3(){
		return remark3;
	}

	public void setRemark3(String remark3){
		this.remark3 = remark3;
	}

	public WxGoodsEntity coverToEntity(Map<String,Object> params) {
		if(params.containsKey("goodsId")){
			this.goodsId = MapUtil.getString (params,"goodsId");
		}
		if(params.containsKey("catalogId")){
			this.catalogId = MapUtil.getString (params,"catalogId");
		}
		if(params.containsKey("goodsName")){
			this.goodsName = MapUtil.getString (params,"goodsName");
		}
		if(params.containsKey("goodsPrice")){
			this.goodsPrice = MapUtil.getBigDecimal (params,"goodsPrice");
		}
		if(params.containsKey("goodsDiscount")){
			this.goodsDiscount = MapUtil.getBigDecimal (params,"goodsDiscount");
		}
		if(params.containsKey("goodsSale")){
			this.goodsSale = MapUtil.getInteger (params,"goodsSale");
		}
		if(params.containsKey("goodsNum")){
			this.goodsNum = MapUtil.getInteger (params,"goodsNum");
		}
		if(params.containsKey("goodsTitle")){
			this.goodsTitle = MapUtil.getString (params,"goodsTitle");
		}
		if(params.containsKey("description")){
			this.description = MapUtil.getString (params,"description");
		}
		if(params.containsKey("freeInd")){
			this.freeInd = MapUtil.getBigDecimal (params,"freeInd");
		}
		if(params.containsKey("hotInd")){
			this.hotInd = MapUtil.getString (params,"hotInd");
		}
		if(params.containsKey("goodsBrands")){
			this.goodsBrands = MapUtil.getString (params,"goodsBrands");
		}
		if(params.containsKey("newInd")){
			this.newInd = MapUtil.getString (params,"newInd");
		}
		if(params.containsKey("benefit")){
			this.benefit = MapUtil.getString (params,"benefit");
		}
		if(params.containsKey("status")){
			this.status = MapUtil.getString (params,"status");
		}
		if(params.containsKey("goodsScore")){
			this.goodsScore = MapUtil.getInteger (params,"goodsScore");
		}
		if(params.containsKey("remark1")){
			this.remark1 = MapUtil.getString (params,"remark1");
		}
		if(params.containsKey("remark2")){
			this.remark2 = MapUtil.getString (params,"remark2");
		}
		if(params.containsKey("remark3")){
			this.remark3 = MapUtil.getString (params,"remark3");
		}
		if(params.containsKey("id")){
			this.id = MapUtil.getInteger(params, "id");
		}
		if(params.containsKey("delInd")){
			this.delInd = MapUtil.getString(params, "delInd");
		}
		return this;
	}
	
	
	
}
