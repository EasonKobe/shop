package com.jeedev.msdp.index.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.client.ClientConfig;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.trace.constants.LoginUserConstants;
import com.jeedev.msdp.utlis.StringUtil;

/**
 * 
 * @类名称 IndexAction.java
 * @类描述 <pre></pre>
 * @作者   yuyq@tansun.com.cn
 * @创建时间 2017年11月16日 下午1:56:18
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	2017年11月16日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "/sys/indexMenu")
public class SystemIndexMenuAction extends BaseAppAction{
	   
	@Autowired
	private ClientConfig clientConfig;
	@Autowired
	private MenuApi menuApi;
	   @RequestMapping(value="/permission",method=RequestMethod.POST)
	   @ResponseBody
	   public ResponseVO permission(@CurrentUser Map<String,Object> curUserMap){
		   //0.初始化返回的map
		   Map<String,Object> map = new HashMap<>() ;
		   Set<String> permission = new HashSet<>() ;
//		   permission.add("fncAply.add") ;
//		   permission.add("fncAply.edit") ; 
//		   permission.add("fncAply.entrstd.add") ;
		   map.put("permission", permission) ;
		   Set<String> permitted = new HashSet<>() ;
//		   permitted.add("fncAply.add") ;
//		   permitted.add("fncAply.edit") ;
//		   permitted.add("fncAply.entrstd.add") ;
		   map.put("permitted", permitted) ;
		   
		   //1.查询需要权限过滤的页面元素，按钮 
		   Map<String,Object> queryparams =new HashMap<String,Object>();
		   queryparams.put("menuTypeCd", "click");
		   PageInfo<Map<String, Object>> clickMenuList = menuApi.findMenuPage(queryparams, null);
		   
		   //1.1找不到数据直接返回
		   if(clickMenuList ==null || clickMenuList.getList()==null || clickMenuList.getList().size()==0) {
			   return successResponse(map) ;
		   }
		   //1.2组装权限
		   String urlTmp = "";
		   for(Map<String,Object> clickmenu:clickMenuList.getList()) {
			   urlTmp = (String) clickmenu.get("url");
			   if(StringUtil.isNotBlank(urlTmp)) {
				   permission.add(urlTmp) ;
			   }
			   
		   }

		   //2.查找用户当前角色已授权的页面元素权限
		   
		   //2.1页面未登录 直接返回 
		   if(!curUserMap.containsKey(LoginUserConstants.LOGIN_USER_ROLEINF_RLID)){
			   return successResponse(map) ;
	       }
		   
		   Map<String,Object> params = new HashMap<String, Object>();
	    	params.put("clntendId", clientConfig.getClientId());
	    	params.put("roleCode", curUserMap.get(LoginUserConstants.LOGIN_USER_ROLEINF_RLID));
	       
	       PageInfo<Map<String, Object>> usermenus =menuApi.findUserMenuPage(params, null);
	       //2.2未有权限信息,直接返回
	       if(usermenus ==null || usermenus.getList()==null || usermenus.getList().size()==0) {
			   return successResponse(map) ;
		   }
	       //2.3组装用户已授权列表 
		   for(Map<String,Object> clickmenu:usermenus.getList()) {
			   urlTmp = (String) clickmenu.get("url");
			   if(StringUtil.isNotBlank(urlTmp)) {
				   permitted.add(urlTmp) ;
			   }
			   
		   }
		   

		   //3返回 
		   return successResponse(map) ;
	   }
}
