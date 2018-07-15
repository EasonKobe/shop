package com.jeedev.msdp.index.action;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.Constants;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.dict.api.DictApi;


/**
 * @类名称 CommonAction.java
 * @类描述 <pre></pre>
 * @作者 yangkunwei yangkunwei@tansun.com.cn
 * @创建时间 2017年7月4日 下午6:24:01
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yangkunwei 	2017年7月4日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "")
public class CommonAction extends BaseAppAction{
	@Autowired
	private DictApi dictApi;
	
	    @RequestMapping(value="/doc/query",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO menu(HttpServletRequest request){
	    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("docId", "WDBH001");
	    	map.put("docNm", "身份证图片");
	    	map.put("docTp", ".jpg");
	    	map.put("docDt", new Date());
	    	map.put("createUser", "admin");
	    	list.add(map);
	    	PageInfo<Map<String, Object>> page = new PageInfo<Map<String, Object>>(list);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("grid", page);
			return successResponse(result);
		}
	    
	    @RequestMapping(value="/doc/delete",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO delete(HttpServletRequest request){
			return successResponse("删除成功");
		}
	    
	    @RequestMapping(value="/doc/get",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO get(HttpServletRequest request){
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("path", "/upload/scf-pc/doc/superadmin/2653df5b51a169c33dbd98c5a15d1785.docx");
			return successResponse(map);
		}
	    
	    @RequestMapping(value="/doc/save",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO save(HttpServletRequest request){
			return successResponse("保存成功");
		}
	    
	    @RequestMapping(value="/user/queryUser",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO queryUser(HttpServletRequest request){
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("usrNm", "superadmin");
			return successResponse(map);
		}
	    
	    @SuppressWarnings("unchecked")
		@RequestMapping(value="/getSession",method=RequestMethod.POST)
	    @ResponseBody
		public ResponseVO getSession(HttpServletRequest request){
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	if(request.getAttribute(Constants.CURRENT_USER)!=null){
	    		map.putAll((Map<? extends String, ? extends Object>) request.getAttribute(Constants.CURRENT_USER));
	    	}
			return successResponse(map);
		}
	    @ResponseBody
		@RequestMapping("/dict")
		public ResponseVO dict(@RequestParam Map<String, String> reqMap, @CurrentUser Map<String, Object> curUserMap) {
			PageInfo<Map<String, String>> pageList = dictApi.findDictPage(reqMap, null);
			Map<String, List<Map<String, String>>> dicts = new HashMap<>();
			List<Map<String, String>> dictList = pageList.getList();
			if (dictList != null && !dictList.isEmpty()) {
				List<String> keyList = new ArrayList<>();
				for (Map<String, String> map : dictList) {
					keyList.add(map.get("dctTpCd"));
				}
				//key去重
				keyList = new ArrayList<String>(new HashSet<String>(keyList)); 
				for (String key : keyList) {
					List<Map<String, String>> list = new ArrayList<>();
					for (Map<String, String> map : dictList) {
						if (key.equals(map.get("dctTpCd"))) {
							Map<String, String> item = new HashMap<>();
							item.put("group", map.get("dctGrp"));
							item.put("text", map.get("dctValNm"));
							item.put("type", map.get("dctKey"));
							item.put("value", map.get("dctVal"));
							list.add(item);
						}
					}
					dicts.put(key, list);
				}
	
			}
			return successResponse(dicts);
		}
}
