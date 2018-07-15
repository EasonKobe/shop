package com.jeedev.msdp.index.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.client.ClientConfig;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.trace.constants.LoginUserConstants;
import com.jeedev.msdp.utlis.StringUtil;


/**
 * 
 * @类名称 IndexAction.java
 * @类描述 <pre></pre>
 * @作者   weiqinshu@tansun.com.cn
 * @创建时间 2016年12月19日 下午1:56:18
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	weiqinshu 	2016年12月19日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "")
public class IndexAction extends BaseAppAction{
		static String[] levelmenucss = {"iconmenu","iconlistmenu"};
		@Autowired
		private MenuApi menuApi;
		@Autowired
		private ClientConfig clientConfig;
	   /**
	    * 
	    * @方法名称 index
	    * @功能描述 <pre>跳转到主页面</pre>
	    * @作者    weiqinshu
	    * @创建时间 2016年12月19日 下午1:56:59
	    * @return
	    */
	    @RequestMapping(value={"/","/index"},method=RequestMethod.GET)
		public ModelAndView index(){
			return initPublicView();
		}
	    
	    /**
	     * @方法名称 menu
	     * @功能描述 <pre>根据用户信息通过数据库获取菜单信息</pre>
	     * @作者    yuyq
	     * @创建时间 2017年9月17日 下午9:33:26
	     * @param request 请求参数
	     * @param curUserMap 用户信息
	     * @return 返回菜单对象
	     */
	    @RequestMapping(value="/menu",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO menu(HttpServletRequest request,@CurrentUser Map<String,Object> curUserMap){
	    	List<MenuObj> menuList = new ArrayList<MenuObj>();
	    	// 图片， 130px 宽度计算
	    	Integer minWidth = 130;
	    	Map<String,Object> params = new HashMap<String, Object>();
	    	params.put("clntendId", clientConfig.getClientId());
	    	params.put("roleCode", curUserMap.get(LoginUserConstants.LOGIN_USER_ROLEINF_RLID));
	    	//没有分配角色的情况下,无法查看菜单 Colin.DZX 170921
	        if(!curUserMap.containsKey(LoginUserConstants.LOGIN_USER_ROLEINF_RLID)){
	        	 return successResponse(null);
	        }
	    	PageInfo<Map<String, Object>> usermenus =menuApi.findUserMenuPage(params, null);
	    	//List<MenuObj> menuList = new ArrayList<MenuObj>();
	    	Map<String,List<Map<String, Object>>> menuMidMap = new HashMap<String,List<Map<String, Object>>>();
	    	if(usermenus==null||usermenus.getSize()==0){
	    		usermenus = new PageInfo<Map<String, Object>>(new ArrayList<Map<String, Object>>());
	    	}
	    	for(Map<String, Object> tmp:usermenus.getList()){
	    		//跳过状态失效的数据 Colin.DZx 170921
	    		if(tmp.containsKey(MenuConstants.STATUS_CD)){
	    			String statusCd = (String) tmp.get(MenuConstants.STATUS_CD);
	    			String menuTypeCd = (String) tmp.get(MenuConstants.MENU_TYPE_CD);
	    			if("0".equals(statusCd)){
	    				continue;
	    			}
	    			//不是目录和页面的不组成页面菜单
	    			if(!"view".equals(menuTypeCd)&&!"no-source".equals(menuTypeCd)){
	    				continue;
	    			}
	    		}
	    		String parentMenuCode = (String) tmp.get(MenuConstants.PARENT_MENU_CODE);
	    		List<Map<String, Object>> subMenus= menuMidMap.get(parentMenuCode);
	    		if(subMenus==null){
	    			subMenus = new ArrayList<Map<String, Object>>();
	    			menuMidMap.put(parentMenuCode,subMenus);
	    		}
	    		subMenus.add(tmp);
	    	}
//	    	Map<String,MenuObj> menusmap = new HashMap<String,MenuObj >();
	    	String parentMenuCode = clientConfig.getClientId();
	    	MenuObj rootMenuObj = convertMenu(parentMenuCode, "根",minWidth, menuMidMap,1);
//	    	params.put("parentMenuCode", "res0500");
	    	menuList.add(rootMenuObj);
	    	return successResponse(rootMenuObj.menu);
		}
	    
	    /**
	     * 
	     * @方法名称 menu2
	     * @功能描述 <pre>根据用户信息通过方法设置获取菜单信息</pre>
	     * @作者    yuyq
	     * @创建时间 2017年9月17日 下午9:34:38
	 	 * @param request 请求参数
	     * @param curUserMap 用户信息
	     * @return 返回菜单对象
	     */
	    @RequestMapping(value="/menu2",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO menu2(HttpServletRequest request,@CurrentUser Map<String,Object> curUserMap){
	    	List<MenuObj> menuList = new ArrayList<MenuObj>();
	    	// 图片， 130px 宽度计算
	    	Integer minWidth = 130;
	    	String[] menuArr = new String[]{"我的客户", "我的业务", "我的工具", "我的设置"};
	    	
	    	String[] custArr = new String[]{"客户管理", "授信额度管理", "交易风险额度管理", "产品管理"};
	    	List<MenuObj> cstList = new ArrayList<MenuObj>();
	    	cstList.add(convertMenu(custArr[0], 
	    			new String[]{"客户信息管理Demo", "客户信息管理",  "黑名单管理", "产业链网管理", "受托支付名单管理"},
	    			new String[]{"/user/cst/index","/user/customerall/index","/user/checkBlack/checkList","/user/idychn/cstIdyChnList","/user/entrustedPy/index"}));
	    	cstList.add(convertMenu(custArr[1], 
	    			new String[]{"授信额度管理", "额度冻结", "额度解冻", "额度终止", "额度变更"},
	    			new String[]{"/user/limitlist/index","/user/limitfreezelist/index","/user/limitthawlist/index","/user/limitendlist/index","/user/limitadjustlist/index"}));
	    	cstList.add(convertMenu(custArr[2], new String[]{"交易风险额度管理", "额度冻结", "额度解冻", "额度终止", "额度变更"},
	    			new String[]{"#","#","#","#","#"}));
	    	cstList.add(convertMenu(custArr[3], new String[]{"产品管理", "产品变更", "产品查询","圈链维护"},new String[]{"/user/profactorylist/index", "/user/profactorychange/index", "#","/user/network/index"}));
	    	MenuObj menuObj1 = new MenuObj("1", menuArr[0]);
	    	menuObj1.setImg(levelmenucss[0]+"01");
	    	menuObj1.setMenu(cstList);
	    	menuObj1.setWidth(minWidth*custArr.length);
	    	menuList.add(menuObj1);
	    	
	    	String[] businessArr = new String[]{"工作台",  "应收类", "池管理", "争议管理", "费用管理"};
	    	List<MenuObj> businessList = new ArrayList<MenuObj>();
	    	businessList.add(convertMenu(businessArr[0], 
	    			new String[]{"我的任务", "风险提示", "风险预警"},
	    			new String[]{"/user/pending/list","/user/prmpt/index","/user/warn/index"}));
	    	businessList.add(convertMenu(businessArr[1], 
	    			new String[]{"应收账款录入", "应收账款转让(质押)", "融资申请", "应收账款调整", "应收账款催收", "应收账款销账(质押解除)", "应收账款台账查询", "买方付款", "卖方还款"},
	    			new String[]{"/user/invoice/index","/user/rcvbtrsfer/transferList","/user/disbursement/index","/user/rcvbadjust/fctrnRcvAdjList","/user/collection/collectionList","/user/rcvbwriteoff/writeoffList","/user/cprsvenqr/cprsvRcvbList","/user/buyrpym/buyrPymList","/user/rcvbsellrrepy/sellrRepyList"}));
//	    	businessList.add(convertMenu(businessArr[2],
//	    			new String[]{"出质管理", "库存查询", "融资申请", "提/赎货管理", "质物更新", "质物价值维护"},
//	    			new String[]{"#","#","/user/disbursement/index","#","#","#"}));
	    	businessList.add(convertMenu(businessArr[2],
	    			new String[]{"入池", "出池", "池查询","尾款转出"},
	    			new String[]{"/user/pools/poolInList","/user/pools/poolOutList","/user/pools/poolSearchList","/user/rsrvGldList/rsrvgldTfroutList"}));
//	    	businessList.add(convertMenu(businessArr[4],
//	    			new String[]{"订单管理", "融资申请", "收款确认", "发货登记", "质物入库", "库存查询", "提/赎货管理", "差额退款"},
//	    			new String[]{"/user/order/orderList","/user/disbursement/index","/user/confirmPayment/receiptsConfirmList","/user/dlvrgs/dlvRgsList","#","#","#","/user/difamt/rnfdList"}));
	    	businessList.add(convertMenu(businessArr[3],
	    			new String[]{"争议设定", "争议解除"},
	    			new String[]{"/user/rcvbdsptset/dsptsetList","/user/rcvbdsptrlv/dsptrlvList"}));
	    	businessList.add(convertMenu(businessArr[4],
                new String[]{"收费管理"},
                new String[]{"/user/expense/expenseList"}));
	    	MenuObj menuObj2 = new MenuObj("2", menuArr[1]);
	    	menuObj2.setImg(levelmenucss[0]+"02");
	    	menuObj2.setMenu(businessList);
	    	menuObj2.setWidth(minWidth*businessArr.length);
	    	menuList.add(menuObj2);
	    	
	    	String[] toolArr = new String[]{"综合查询", "应收类查询"};
	    	List<MenuObj> toolList = new ArrayList<MenuObj>();
	    	toolList.add(convertMenu(toolArr[0], 
	    			new String[]{"公共信息查询", "应收类查询"},
	    			new String[]{"#","#"}));
	    	toolList.add(convertMenu(toolArr[1], 
                new String[]{"回款查询", "应收账款台账查询", "应收账款对账查询", "池查询"},
                new String[]{"/user/cprsvenqr/repay","/user/cprsvenqr/rcvbinf", "/user/cprsvenqr/rcvbrcnc", "/user/cprsvenqr/pool"}));
	    	MenuObj menuObj3 = new MenuObj("3", menuArr[2]);
	    	menuObj3.setImg(levelmenucss[0]+"03");
	    	menuObj3.setMenu(toolList);
	    	menuObj3.setWidth(minWidth*toolArr.length);
	    	menuList.add(menuObj3);
	    	
	    	String[] setArr = new String[]{"网络管理", "合同管理", "系统管理"};
	    	List<MenuObj> setList = new ArrayList<MenuObj>();
	    	setList.add(convertMenu(setArr[0], 
	    			new String[]{"网络维护", "网络变更", "网络查询"},
	    			new String[]{"#","#","#"}));
	    	setList.add(convertMenu(setArr[1], 
	    			new String[]{"合同维护", "合同变更", "合同查询"},
	    			new String[]{"/user/prectr/index","/user/prectrmdf/index","/user/prectr/query"}));
	    	Map<String,Object> params = new HashMap<String, Object>();
	    	params.put("clntendId", clientConfig.getClientId());
	    	params.put("roleCode", curUserMap.get(LoginUserConstants.LOGIN_USER_ROLEINF_RLID));
	    	params.put("parentMenuCode", "res0500");
//	    	new String[]{"用户管理", "角色管理", "部门管理", "机构管理", "菜单管理"},
//			new String[]{"/user/usermg/personMgList","/role/tree","/department/tree","/org/tree", "/menu/tree"}));
	    	PageInfo<Map<String, Object>> usermenus =menuApi.findUserMenuPage(params, null);
	    	setList.add(convertMenu(setArr[2], 
	    			usermenus.getList()));
	    	MenuObj menuObj4 = new MenuObj("4", menuArr[3]);
	    	menuObj4.setImg(levelmenucss[0]+"04");
	    	menuObj4.setMenu(setList);
	    	menuObj4.setWidth(minWidth*setArr.length);
	    	menuList.add(menuObj4);
	    	
	    	return successResponse(menuList);
		}
	    
	    /**
	     * @方法名称 convertMenu
	     * @功能描述 <pre>将菜单信息转化为上下级结构</pre>
	     * @作者    yuyq
	     * @创建时间 2017年9月17日 下午9:36:03
	     * @param parentMenuCode 父级菜单编码
	     * @param parentText 父级菜单标识
	     * @param minWidth 最小宽度
	     * @param menuMidMap 子菜单集合
	     * @param level  菜单等级
	     * @return 菜单对象
	     */
	    public MenuObj convertMenu(String parentMenuCode,String parentText,int minWidth, Map<String,List<Map<String, Object>>> menuMidMap,int level) {
	    	// 图片， 130px 宽度计算
	    	MenuObj parent = new MenuObj(parentMenuCode, parentText);
	    	List<Map<String, Object>> menus = menuMidMap.get(parentMenuCode);
	    	if(menus==null) menus =new ArrayList<Map<String, Object>>();
	    	List<MenuObj> menuList = new ArrayList<>();
	    	boolean isLastSecond = false;
	    	for (int i = 0; i < menus.size(); i++) {
	    		MenuObj sub = null;
	    		if(menuMidMap.containsKey(menus.get(i).get("menuCode"))){
	    			sub = convertMenu(menus.get(i).get("menuCode").toString(), menus.get(i).get("menuName").toString(),minWidth, menuMidMap,level+1);
	    		}else{
	    			isLastSecond =true;
	    			String url=null;
					if(menus.get(i).get("url")!=null){
						url=menus.get(i).get("url").toString();
					}
	    			sub= new MenuObj((String)menus.get(i).get("menuCode"), menus.get(i).get("menuName").toString() ,url);
	    		}
	    		if(StringUtils.isNotBlank((String)menus.get(i).get("icon"))){
	    			sub.setImg(menus.get(i).get("icon").toString());
	    		}else{
	    			sub.setImg("");
	    		}
	    		
	    		menuList.add(sub);
			}
	    	if(!isLastSecond&&menuList.size()>0){
	    		parent.setWidth(minWidth*menuList.size());
	    	}
	    	
	    	parent.setMenu(menuList);
	    	return parent;
	    }
	    
	    /**
	     * @方法名称 convertMenu
	     * @功能描述 <pre>将菜单信息转化为上下级结构</pre>
	     * @作者    yuyq
	     * @创建时间 2017年9月17日 下午9:36:03
	     * @param parentText 父级菜单标识
	     * @param childText 子菜单标识数组
	     * @param urls  菜单url数组
	     * @return 菜单对象
	     */
	    public MenuObj convertMenu(String parentText, String[] childText,String[] urls) {
	    	MenuObj parent = new MenuObj("01", parentText);
	    	parent.setImg("iconlistmenu01");
	    	
	    	List<MenuObj> menuList = new ArrayList<>();
	    	for (int i = 0; i < childText.length; i++) {
	    		menuList.add(new MenuObj("00" + i+1, childText[i] ,urls[i]));
			}
	    	parent.setMenu(menuList);
	    	return parent;
	    }
	    
	    /**
	     * @方法名称 convertMenu
	     * @功能描述 <pre>将菜单信息转化为上下级结构</pre>
	     * @作者    yuyq
	     * @创建时间 2017年9月17日 下午9:36:03
	     * @param parentText 父级菜单标识
	     * @param menus 菜单集合
	     * @return 菜单对象
	     */
	    public MenuObj convertMenu(String parentText, List<Map<String,Object>> menus) {
	    	MenuObj parent = new MenuObj("01", parentText);
	    	parent.setImg("iconlistmenu01");
	    	List<MenuObj> menuList = new ArrayList<>();
	    	for (int i = 0; i < menus.size(); i++) {
	    		menuList.add(new MenuObj("00" + i+1, menus.get(i).get("menuName").toString() ,menus.get(i).get("url").toString()));
			}
	    	parent.setMenu(menuList);
	    	return parent;
	    }
	    
	    /**
	     * @类名称 MenuObj
	     * @类描述 <pre>菜单对象</pre>
	     * @作者 yuyq yuyq@tansun.com.cn
	     * @创建时间 2017年9月17日 下午9:39:32
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
	   class MenuObj{
		   private String id;
		   private String text;
		   private String url;
		   private String img = "/static/images/blue/icon_thirdmenu.png";
		   private Integer width;
		   private List<MenuObj> menu;
		   
		   public MenuObj(){
		   }
		   
		   public MenuObj(String id, String text){
			   this.id = id;
			   this.text = text;
		   }
		   
		   public MenuObj(String id, String text,String url){
			   this.id = id;
			   this.text = text;
			   this.url = url;
		   }
	
			public List<MenuObj> getMenu() {
				return menu;
			}
		
			public void setMenu(List<MenuObj> menu) {
				this.menu = menu;
			}

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getText() {
				return text;
			}

			public void setText(String text) {
				this.text = text;
			}

			public String getUrl() {
				return url;
			}

			public void setUrl(String url) {
				this.url = url;
			}

			public String getImg() {
				return img;
			}

			public void setImg(String img) {
				this.img = img;
			}

			public Integer getWidth() {
				return width;
			}

			public void setWidth(Integer width) {
				this.width = width;
			}
			   
	   }
}
