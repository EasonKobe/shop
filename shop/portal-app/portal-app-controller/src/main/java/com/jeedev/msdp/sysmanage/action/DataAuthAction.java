package com.jeedev.msdp.sysmanage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.HeadInit;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
/**
 * 
 */
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.dataauth.api.DataAuthApi;
import com.jeedev.msdp.sys.dataauth.api.DataAuthConstants;
import com.jeedev.msdp.trace.constants.HeadConstants;
import com.jeedev.msdp.translate.annotation.Translater;
import com.jeedev.msdp.translate.annotation.Translaters;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
/**
 * 
 * @类名称 DataAuthAction.java
 * @类描述 <pre>数据权限Action层</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月17日 下午11:06:24
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	2017年9月17日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/dataauth")
@HeadInit(name=HeadConstants.HEAD_SCENE,value="DataauthManage")
public class DataAuthAction extends BaseAppAction {
	
	
	
	/**
	 * 数据权限api
	 */
	@Autowired
	private DataAuthApi dataAuthApi;
	
	/**
	 * @方法名称 tree
	 * @功能描述 <pre>根据条件请求获取数据权限分页信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:06:09
	 * @Dataauth RequstObjectMap 请求参数
	 * @Dataauth page 分页参数
	 * @return 分页列表
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/query")
	@Translaters(value = { 
			@Translater(name="user",fromKeys= {"userNum"},toKeys= {"loginName"}) ,
			@Translater(name="org",fromKeys= {"orgCode"},toKeys= {"orgName"}) ,
			@Translater(name="dept",fromKeys= {"deptCode"},toKeys= {"deptName"}) ,
			@Translater(name="role",fromKeys= {"roleCode"},toKeys= {"roleName"}) ,
			@Translater(name="client",fromKeys= {"clntendId"},toKeys= {"clntendNm"}) ,
			@Translater(name="event",fromKeys= {"eventCode"},toKeys= {"eventName"}) 
			})
	public ResponseVO query(@RequestObjectParam Map<String, Object> requstObjectMap,PageParam page) throws Exception {
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			PageInfo<Map<String, Object>> pageInfo = dataAuthApi.findDataAuthPage(requstObjectMap,page);
			result.put("grid", pageInfo);
			return this.successResponse(result);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据编号ID获取参数详细信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:07:37
	 * @Dataauth RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	@Translaters(value = { 
			@Translater(name="user",fromKeys= {"userNum"},toKeys= {"loginName"}) ,
			@Translater(name="org",fromKeys= {"orgCode"},toKeys= {"orgName"}) ,
			@Translater(name="dept",fromKeys= {"deptCode"},toKeys= {"deptName"}) ,
			@Translater(name="role",fromKeys= {"roleCode"},toKeys= {"roleName"}) 
			})
	public ResponseVO get(@RequestObjectParam Map<String, Object> requstObjectMap) throws Exception {  
		try{
			Map<String, Object> dataauthMap = new HashMap<String,Object>();
			//1.查询有查询方式时用列表查询，
			if(requstObjectMap.containsKey(DataAuthConstants.QUERY_TYPE)) {
				PageInfo<Map<String, Object>> list =  dataAuthApi.findDataAuthPage(requstObjectMap,null);
				if(list!= null && list.getSize()>0) {
					dataauthMap = list.getList().get(0);
				}
			}else {
				//无查询方式调用查单个的接口
				dataauthMap = dataAuthApi.getDataAuthMap(requstObjectMap); 
			}
			
			return this.successResponse(dataauthMap);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	
	
	
	/**
	 * @方法名称 delete
	 * @功能描述 <pre>根据编号删除对象的数据</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:08:00
	 * @Dataauth RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestObjectParam Map<String, Object> dataauthMap) throws Exception { 
		try{
			dataAuthApi.deleteDataAuthTrans(dataauthMap);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 save
	 * @功能描述 <pre>保存或修改数据权限实例</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:08:24
	 * @Dataauth RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam  Map<String, Object> dataauthMap) throws Exception { 
		try{
			Map<String, Object> result = dataAuthApi.saveDataAuthTrans(dataauthMap);
			return this.successResponse(result,"保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 save
	 * @功能描述 <pre>保存或修改数据权限实例</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:08:24
	 * @Dataauth RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/batchSave")
	public ResponseVO batchSave(@RequestObjectParam  Map<String, Object> dataauthMap) throws Exception { 
		try{
			String eventCodesStr = MapUtil.getString(dataauthMap, "eventCodes");
			String dataauthIdsStr = MapUtil.getString(dataauthMap, "dataauthIds");
			
			String[] eventCodes = eventCodesStr.split(",");
			String[] dataauthIds = dataauthIdsStr.split(",");
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=0;i<eventCodes.length;i++) {
				if(StringUtil.isBlank(eventCodes[i])) {
					continue;
				}
				Map<String,Object> tmp = new HashMap<String,Object>();
				tmp.put("eventCode", eventCodes[i]);
				if(dataauthIds.length>i&&StringUtil.isNotBlank(dataauthIds[i])) {
					tmp.put("id", Integer.parseInt(dataauthIds[i]));
				}
				tmp.put("roleCode", MapUtil.getString(dataauthMap, "roleCode"));
				tmp.put("modelCode", MapUtil.getString(dataauthMap, "modelCode"));
				list.add(tmp);
			}
			Map<String,Object> batchParams = new HashMap<String,Object>();
			batchParams.put("list", list);
			dataAuthApi.saveBatchDataAuthTrans(batchParams);
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 selector
	 * @功能描述 <pre>分页获取数据权限类型</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月26日 下午10:38:08
	 * @param reqMap 请求参数
	 * @param page 分页参数
	 * @return 分页列表
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/modelSelector")
	public ResponseVO selector(@RequestParam Map<String,Object> reqMap,PageParam page) throws Exception{
		PageInfo<Map<String, Object>> orgs = dataAuthApi.findDataAuthModelPage(reqMap,page);
		Map<String, Object> result = new HashMap<String, Object>();
	    result.put("grid", orgs);
	    return this.successResponse(result); 
	}
}
