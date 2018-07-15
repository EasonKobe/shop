package com.jeedev.msdp.sysmanage.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.org.api.OrgApi;
import com.jeedev.msdp.sys.org.api.OrgApiConstants;

/**
 * @类名称 OrgAction
 * @类描述
 * 
 *      <pre></pre>
 * 
 * @作者 zouyaobin@tansun.com.cn
 * @创建时间 2017年7月5日
 * @版本 v1.00
 * @修改记录
 * 
 *       <pre>
 *  版本        修改人      修改时间          修改内容描述
 * ----------------------------------------------
 * 1.00    zouyaobin@tansun.com.cn  2017年7月5日	 新建
 * ----------------------------------------------
 *       </pre>
 */
@Controller
@RequestMapping("/org")
public class OrgAction extends BaseAppAction {
	private static String DEFAULTIND_NO="0";
	@Autowired
	private OrgApi orgApi; 
	
	public String ID="id";//机构id
	public String DELIND="delInd";//删除标志
	public String NAME="name";//机构名称
	public String OPEN="open";//是否展开子节点
	public String CHECKED="checked";//是否选中
	public String ORGCODE="orgCode";//机构编码
	public String PID="pId";//父级id
	public String PARENTORGNAME="parentOrgName";//父级机构名称
	
	/**
	 * @方法名称 tree
	 * @功能描述 <pre>获取机构树</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:35:33
	 * @param reqMap其你去参数
	 * @return 机构集合
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/orgTree", method = RequestMethod.POST)
	public ResponseVO tree(@RequestParam Map<String,Object> reqMap) throws Exception {
		String openNodeId=(String) reqMap.get("openNodeId");
		reqMap.put(DELIND, DEFAULTIND_NO);
		PageInfo<Map<String, Object>> pageInfo=orgApi.findOrgPage(reqMap,null);
		List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();
		
		for(Map<String, Object> orgMap:pageInfo.getList()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ID, orgMap.get(OrgApiConstants.FindOrgPageResKey.orgCode.getKey()));
			map.put(NAME, orgMap.get(OrgApiConstants.FindOrgPageResKey.orgName.getKey()));
			
			String pId=MapUtils.getString(orgMap, OrgApiConstants.FindOrgPageResKey.parentOrgCode.getKey());
			if(StringUtils.isBlank(pId)) pId="0";
			map.put(PID,pId);
			map.put(OPEN, openNodeId!=null&&openNodeId.equals(map.get("id")));
			map.put(CHECKED,true);
			ahrList.add(map);
		}
		return this.successResponse(ahrList);
	}

	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据机构编码吗获取机构信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:39:00
	 * @param reqMap 请求参数
	 * 		  orgCode 机构编码
	 * @return 机构信息map
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResponseVO get(@RequestParam Map<String,Object> reqMap) throws Exception { 
		Map<String, Object> orgMap = new HashMap<String, Object>();
		if(reqMap.containsKey(ORGCODE)){
			orgMap = orgApi.getOrgMap(reqMap);
			String parentOrgCodekey =OrgApiConstants.GetOrgMapResKey.parentOrgCode.getKey();
			if(orgMap!=null&&orgMap.containsKey(parentOrgCodekey)
					&&StringUtils.isNotBlank((String)orgMap.get(parentOrgCodekey))){
				Map<String,Object> parentOrgQueryCondMap = new HashMap<String,Object>();
				parentOrgQueryCondMap.put(OrgApiConstants.GetOrgMapReqKey.orgCode.getKey(), orgMap.get(parentOrgCodekey));
				Map<String,Object> parentOrgMap = orgApi.getOrgMap(parentOrgQueryCondMap);
				if(parentOrgMap!=null)
					orgMap.put(PARENTORGNAME, parentOrgMap.get(OrgApiConstants.GetOrgMapResKey.orgName.getKey()));
			}
			
		}
		return this.successResponse(orgMap);
	}
	
	/**
	 * @方法名称 delete
	 * @功能描述 <pre>根据机构id删除机构信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:38:57
	 * @param reqMap 请求参数 
	 *         id 机构id
	 * @return 成功或失败
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestParam Map<String,Object> reqMap) throws Exception {
		try{
			orgApi.deleteOrgTrans(reqMap);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	
	/**
	 * @方法名称 save
	 * @功能描述 <pre>新增或修改机构信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:38:53
	 * @param RequstObjectMap 请求参数
	 * @return 成功或失败
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String,Object> RequstObjectMap) throws Exception { 
		try{
			orgApi.saveOrgTrans(RequstObjectMap);
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	
	/**
	 * @方法名称 selector
	 * @功能描述 <pre>分页获取机构列表</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午10:38:08
	 * @param reqMap 请求参数
	 * @param page 分页参数
	 * @return 分页列表
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/selector")
	public ResponseVO selector(@RequestParam Map<String,Object> reqMap,PageParam page) throws Exception{
		PageInfo<Map<String, Object>> orgs = orgApi.findOrgPage(reqMap, page);
		Map<String, Object> result = new HashMap<String, Object>();
	    result.put("grid", orgs);
	    return this.successResponse(result); 
	}
}
