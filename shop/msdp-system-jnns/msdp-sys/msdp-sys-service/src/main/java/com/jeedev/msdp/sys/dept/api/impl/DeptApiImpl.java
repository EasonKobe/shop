package com.jeedev.msdp.sys.dept.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeedev.msdp.utlis.MapUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.dept.api.DeptApi;
import com.jeedev.msdp.sys.dept.api.DeptApiConstants;
import com.jeedev.msdp.sys.dept.service.ISysDepartmentService;
import com.jeedev.msdp.sys.org.api.OrgApiConstants;
import com.jeedev.msdp.sys.org.service.ISysOrgDeptRelService;
import com.jeedev.msdp.sys.user.service.IUserDeptRelService;
import com.jeedev.msdp.trace.IProviderSign;

/**
 * 
 * @类名称 DeptApiImpl.java
 * @类描述 <pre></pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年9月22日 下午3:17:28
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年9月22日             
 *     ----------------------------------------------
 * </pre>
 */
@Service("deptApi")
public class DeptApiImpl implements DeptApi,IProviderSign{
	
	@Autowired
	ISysDepartmentService sysDepartmentService;
	@Autowired
	ISysOrgDeptRelService sysOrgDeptRelService;
	@Autowired
	IUserDeptRelService userDeptRelService;
	@Override
	public PageInfo<Map<String, Object>> findDeptPage(Map<String, Object> params,PageParam pageParam) {
		return sysDepartmentService.findSysDepartmentPage(params, pageParam);
	}

	@Override
	public Map<String, Object> getDeptMap(Map<String, Object> params) {
		return sysDepartmentService.getSysDepartmentMap(params);
	}

	@Override
	public Map<String, Object> saveDeptTrans(Map<String, Object> params) { 
		//有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, "id");
		String deptCode = MapUtil.getString(params, "deptCode");

		//判断编号重复
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("deptCode", deptCode);
		PageInfo<Map<String, Object>> page = sysDepartmentService.findSysDepartmentPage(queryParams, null);
		List<Map<String, Object>> list = page.getList();
		Integer duplicateId = null;
		if (CollectionUtils.isNotEmpty(list)) {
			duplicateId = MapUtil.getInteger(list.get(0), "id");
		}
		if ((id == null && CollectionUtils.isNotEmpty(list))
				|| (id != null && !id.equals(duplicateId))) {
			throw ExceptionFactory.getBizException("sys-dept-00002");
		}


		//保存部门与机构关系
		Map<String,Object> orgDeptRel = new HashMap<String,Object>();
		if(params.containsKey("orgDeptRelId")){
			orgDeptRel.put("id", params.get("orgDeptRelId"));
			orgDeptRel.put("orgCode", params.get("orgCode"));
			orgDeptRel.put("deptCode", params.get("deptCode"));
			sysOrgDeptRelService.updateOrgDeptRel(orgDeptRel);
		}else if(params.containsKey("orgCode")){
			orgDeptRel.put("orgCode", params.get("orgCode"));
			orgDeptRel.put("deptCode", params.get("deptCode"));
			orgDeptRel.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
			sysOrgDeptRelService.saveOrgDeptRel(orgDeptRel);
		}
		if(!params.containsKey("deptLevelCd")){
			params.put("deptLevelCd", 1);
		}
		if(!params.containsKey("statusCd")){
			params.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
		}
		if(!params.containsKey("sort")){
			params.put("sort", 1);
		}
		if (id == null) {
			if(params.containsKey(DeptApiConstants.PARENT_DEPT_CODE)){
				//子部门添加父级机构
				Map<String,Object> query=new HashMap<>();
				query.put(DeptApiConstants.DEPT_CODE, MapUtils.getString(params, DeptApiConstants.PARENT_DEPT_CODE));
				Map<String,Object> orgDept=sysOrgDeptRelService.getOrgDeptRelMap(query);
				if(orgDept!=null){
					orgDeptRel.clear();
					orgDeptRel.put("orgCode", orgDept.get("orgCode"));
					orgDeptRel.put("deptCode", params.get("deptCode"));
					orgDeptRel.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
					sysOrgDeptRelService.saveOrgDeptRel(orgDeptRel);
				}
			}
			params.put("delInd", "0");
			return sysDepartmentService.saveSysDepartment(params);
		}
		sysDepartmentService.updateSysDepartment(params);
		return null;
	}

	@Override
	public void deleteDeptTrans(Map<String, Object> params) {
		if(params.containsKey("orgDeptRelId")){
			Map<String,Object> orgDeptRel = new HashMap<String,Object>();
			orgDeptRel.put("id", params.get("orgDeptRelId"));
			sysOrgDeptRelService.deleteOrgDeptRel(orgDeptRel);
		}
		 if(checkDeptRel(params)){
			 //有关联数据，无法删除
			 throw ExceptionFactory.getBizException("sys-dept-00001");
		 }
		 sysDepartmentService.deleteSysDepartment(params);
	}
	/**
	 * 
	 * @方法名称 checkDeptRel
	 * @功能描述 <pre>判断是否有关联存在</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月22日 下午3:02:45
	 * @param deptCode
	 * @return true 有 false 没有
	 */
	public boolean checkDeptRel(Map<String, Object> params){
		 //如果有关联部门数据，则不让删除数据
		String deptCode=null;
		boolean result=false;
		if(params!=null){
			 if(params.containsKey(DeptApiConstants.ID)){
				 //获取部门编码
				 String id=MapUtils.getString(params, DeptApiConstants.ID);
				 Map<String,Object> queryMap=new HashMap<>();
				 queryMap.put(OrgApiConstants.Id, id);
				 Map<String,Object> dept=sysDepartmentService.getSysDepartmentMap(queryMap);
				 if(dept!=null)
					 deptCode=MapUtils.getString(dept, DeptApiConstants.DEPT_CODE);
			 }
			 if(params.containsKey(DeptApiConstants.DEPT_CODE)){
				 deptCode=MapUtils.getString(params, DeptApiConstants.DEPT_CODE);
			 }
			Map<String,Object> parmMap=new HashMap<>();
			//是否有关联机构
			parmMap.put(DeptApiConstants.DEPT_CODE, deptCode);
//			PageInfo<Map<String, Object>> pl =sysOrgDeptRelService.findOrgDeptRelPage(parmMap, 0, 9999);
//			if(pl.getTotal()>0){
//				return true;
//			}
			//是否有关联用户 
			PageInfo<Map<String, Object>> plu =userDeptRelService.findUserDeptRelPage(parmMap,null);
			if(plu.getTotal()>0){
				return true;
			}
		}
		return result;
	}
	@Override
	public List<Map<String, Object>> findDeptTreeList(Map<String, Object> params) {
		//树形转换  
		PageInfo<Map<String, Object>> pageInfo=sysDepartmentService.findSysDepartmentPage(params,  null);
		List<Map<String, Object>> treeList=pageInfo.getList();
		return treeList;
	}

	@Override
	public List<Map<String, Object>> findParentDepts(Map<String, Object> params) {
		return sysDepartmentService.findParentDepts(params);
	}

	@Override
	public List<Map<String, Object>> findChildrenDepts(Map<String, Object> params) {
		return sysDepartmentService.findChildrenDepts(params);
	}
	
	
	
}
