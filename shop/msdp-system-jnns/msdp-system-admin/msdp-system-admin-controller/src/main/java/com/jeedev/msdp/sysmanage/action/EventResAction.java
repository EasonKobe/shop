package com.jeedev.msdp.sysmanage.action;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.event.api.EventApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;

/**
 * 菜单管理action
 * @类名称 MenuAction.java
 * @类描述 <pre></pre>
 * @作者 chenjiancong chenjiancong@tansun.com.cn
 * @创建时间 2017年9月1日 上午8:44:01
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjiancong 	2017年9月1日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/eventRes")
public class EventResAction extends BaseAppAction {
	@Autowired
	private EventApi eventApi;

	

	/**
	 * 获取事件详情
	 * @方法名称 get
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午5:31:19
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResponseVO get(@RequestObjectParam Map<String, Object> reqMap) throws Exception { 
		Map<String, Object> orgMap = eventApi.getEventMap(reqMap);
		return this.successResponse(orgMap);
	}
	
	/**
	 * 删除事件
	 * @方法名称 delete
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午5:31:08
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestObjectParam Map<String, Object> params) throws Exception { 
		try{
			eventApi.deleteEventTrans(params);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * 保存事件
	 * @方法名称 save
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午5:30:56
	 * @param requstObjectMap
	 * @param curUserMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String, Object> requstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception { 
		try{
			Map<String,Object> result = eventApi.saveEventTrans(requstObjectMap );
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	

	
	/**
	 * 查询事件
	 * @方法名称 queryEvent
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月6日 下午4:52:13
	 * @param reqMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/query")
	public ResponseVO queryEvent(@RequestObjectParam Map<String,Object> reqMap,PageParam page) throws Exception{
		Map<String, Object> result = new HashMap<String, Object>();
		String eventName = MapUtil.getString(reqMap, "eventName");
		String url = MapUtil.getString(reqMap, "url");
		Map<String,Object> condMap =new HashMap<String,Object>();
		condMap.put("eventName", eventName);
		condMap.put("url", url);
		PageInfo<Map<String, Object>> pageInfo = eventApi.findEventPage(condMap, page); 
		
	    result.put("grid", pageInfo);
	    return this.successResponse(result); 
	}
}
