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
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.dept.api.DeptApi;
import com.jeedev.msdp.sys.dept.api.DeptApiConstants;
import com.jeedev.msdp.sys.org.api.OrgApi;
import com.jeedev.msdp.sys.org.api.OrgApiConstants;

/**
 * 部门管理action
 * 
 * @类名称 DepartmentAction.java
 * @类描述<pre>部门管理action</pre>
 * @作者 chenjiancong chenjiancong@tansun.com.cn
 * @创建时间 2017年9月1日 上午8:43:16
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjiancong 	2017年9月1日             
 *     ----------------------------------------------
 *       </pre>
 */
@Controller
@RequestMapping("/department")
public class DepartmentAction extends BaseAppAction {
	private static String DEFAULTIND_NO = "0";
	@Autowired
	private DeptApi deptApi;
	@Autowired
	private OrgApi orgApi;
	
	public String ID="id";//部门id
	public String DELIND="delInd";//删除标志
	public String NAME="name";//部门名称
	public String ORGNAME="orgName";//部门名称
	public String OPEN="open";//是否展开子节点
	public String CHECKED="checked";//是否选中
	public String ORGCODE="orgCode";//部门编码
	public String PID="pId";//父级id
	public String PARENTDEPTNAME="parentDeptName";//父级部门名称
	public String ORGDEPTRELID="orgDeptRelId";//部门机构id

	
	/**
	 * @方法名称 tree
	 * @功能描述 <pre>获取部门树</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午9:50:09
	 * @param reqMap 请求参数
	 *         deptName 部门名称
	 *         deptCode 部门代码
	 *         deptLevelCd部门级别
	 *         parentDeptCode 上级部门代码
	 *         remark 备注
	 * @return 部门信息集合
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/departmentTree", method = RequestMethod.POST)
	public ResponseVO tree(@RequestParam Map<String, Object> reqMap) throws Exception {
		String openNodeId = (String) reqMap.get("openNodeId");
		reqMap.put("DELIND", DEFAULTIND_NO);
		PageInfo<Map<String, Object>> pageInfo = deptApi.findDeptPage(reqMap, null);
		List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();

		for (Map<String, Object> deptMap : pageInfo.getList()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(ID, deptMap.get(DeptApiConstants.FindDeptPageReqKey.deptCode.getKey()));
			map.put(NAME, deptMap.get(DeptApiConstants.FindDeptPageReqKey.deptName.getKey()));

			String pId = MapUtils.getString(deptMap, DeptApiConstants.FindDeptPageReqKey.parentDeptCode.getKey());
			if (StringUtils.isBlank(pId))
				pId = "0";
			map.put(PID, pId);
			map.put(OPEN, openNodeId != null && openNodeId.equals(map.get("id")));
			map.put(CHECKED, true);
			ahrList.add(map);
		}
		return this.successResponse(ahrList);
	}

	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据部门编码获取部门信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午9:51:52
	 * @param RequstObjectMap  deptCode 部门编码
	 * @return 部门信息map
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResponseVO get(@RequestParam Map<String, Object> RequstObjectMap) throws Exception {
		Map<String, Object> departmentMap = new HashMap<String, Object>();
		if (RequstObjectMap.containsKey("deptCode")) {
			departmentMap = deptApi.getDeptMap(RequstObjectMap);
			String parentDeptCode_key = DeptApiConstants.GetDeptMapResKey.parentDeptCode.getKey();
			if (departmentMap != null && departmentMap.containsKey(parentDeptCode_key)
					&& StringUtils.isNotBlank((String) departmentMap.get(parentDeptCode_key))) {
				Map<String, Object> parentDepartmentQueryCondMap = new HashMap<String, Object>();
				parentDepartmentQueryCondMap.put(DeptApiConstants.GetDeptMapReqKey.deptCode.getKey(),
						departmentMap.get(parentDeptCode_key));
				Map<String, Object> parentDepartmentMap = deptApi.getDeptMap(parentDepartmentQueryCondMap);
				if (parentDepartmentMap != null) {
					departmentMap.put(PARENTDEPTNAME,
							parentDepartmentMap.get(DeptApiConstants.GetDeptMapResKey.deptName.getKey()));
				} else {
					departmentMap.put(PARENTDEPTNAME, "");
				}
			}
			/**
			 * 当部门与机构1:1或者1:0 关系时 需要设置以下信息
			 */
			Map<String, Object> cond = new HashMap<String, Object>();
			cond.put(OrgApiConstants.FindOrgDeptRelPageReqKey.deptCode.getKey(), RequstObjectMap.get("deptCode"));
			PageInfo<Map<String, Object>> orgs = orgApi.findOrgDeptRelPage(cond, null);
			if (orgs != null && orgs.getSize() > 0) {
				Map<String, Object> org = orgs.getList().get(0);
				departmentMap.put(ORGCODE, org.get(OrgApiConstants.FindOrgDeptRelPageResKey.orgCode.getKey()));
				Map<String, Object> tmp = new HashMap<String, Object>();
				tmp.put(ORGCODE, org.get(OrgApiConstants.FindOrgDeptRelPageResKey.orgCode.getKey()));
				departmentMap.put(ORGNAME, org.get(OrgApiConstants.FindOrgDeptRelPageResKey.orgName.getKey()));
				departmentMap.put(ORGDEPTRELID, org.get(OrgApiConstants.FindOrgDeptRelPageResKey.id.getKey()));
			}
		}
		return this.successResponse(departmentMap);
	}

	/**
	 * @方法名称 delete
	 * @功能描述 <pre>根据部门id删除部门信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午9:52:41
	 * @param reqMap orgDeptRelId 部门编id
	 * @return 成功或失败
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestParam Map<String, Object> reqMap) throws Exception {
		try{
			deptApi.deleteDeptTrans(reqMap);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 save
	 * @功能描述 <pre>新增或或修改部门信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午9:53:55
	 * @param RequstObjectMap 请求参数
	 *         deptName 部门名称
	 *         deptLevelCd部门级别
	 *         parentDeptCode 上级部门代码
	 *         remark 备注
	 * @return 成功或失败
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String, Object> RequstObjectMap,@CurrentUser Map<String,Object> curUserMap) throws Exception {
		try{
			deptApi.saveDeptTrans(RequstObjectMap);
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 selector
	 * @功能描述 <pre>部门分页信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午9:57:51
	 * @param reqMap 请求参数
	 *         deptName 部门名称
	 *         deptCode 部门代码
	 *         deptLevelCd部门级别
	 *         parentDeptCode 上级部门代码
	 *         remark 备注
	 * @param page 分页参数
	 * @return 分页信息
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/selector")
	public ResponseVO selector(@RequestParam Map<String, Object> reqMap, PageParam page) throws Exception { 
		PageInfo<Map<String, Object>> depts = deptApi.findDeptPage(reqMap, page); 
		Map<String, Object> result = new HashMap<String, Object>();
	    result.put("grid", depts);
	    return this.successResponse(result);  
	}
}
