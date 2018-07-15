package com.jeedev.msdp.slectuser.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.standard.log.CommonLoggerFactory;
import com.jeedev.msdp.standard.log.Logger;
import com.jeedev.msdp.sys.dept.api.DeptApi;
import com.jeedev.msdp.sys.org.api.OrgApi;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.userselect.spi.impl.DirectUserAccessSpiAdapter;

@Component("userAccessSpi")
public class UserAccessSpiImpl extends  DirectUserAccessSpiAdapter{
	
	Logger logger = CommonLoggerFactory.getLogger(UserAccessSpiImpl.class);
	
	@Autowired
	private UserApi userApi;
	@Autowired
	private OrgApi orgApi;
	@Autowired
	private DeptApi depApi;

	@Override
	public List<Map<String, Object>> getUserByCurrentInst(Map<String, Object> args) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgCodes", args.get("instCodes"));
		params.put("roleCodes", args.get("roleIds"));
		PageInfo<Map<String, Object>> result = userApi.findUserByOrgRoleRel(params);
		if (result == null || result.getList() == null) {
			return new ArrayList<Map<String, Object>>();
		}
		//设置部门信息
		List<Map<String,Object>> userCurrentInfoList = result.getList();
		if (userCurrentInfoList != null) {
			for (Map<String, Object> userCurrentInfo : userCurrentInfoList) {
				Map<String,Object> paramNest = new HashMap<>();
				paramNest.put("userNum", userCurrentInfo.get("userNum"));
				PageInfo<Map<String, Object>> userInfoIncludeDept = userApi.findUserDeptRelPage(paramNest,null);
				if (userInfoIncludeDept.getTotal() > 0) {
					userCurrentInfo.put("deptCode", userInfoIncludeDept.getList().get(0).get("deptCode"));
					userCurrentInfo.put("deptName", userInfoIncludeDept.getList().get(0).get("deptName"));
				}
			}
		}
		
		Map<String,String> mapper = new HashMap<>();
		mapper.put("deptCode", "deptCode");
		mapper.put("deptName", "deptName");
		mapper.put("orgCode", "instCode");
		mapper.put("orgName", "instName");
		mapper.put("roleCode", "roleId");
		mapper.put("userNum", "userId");
		mapper.put("realName", "userName");
		return this.mapperKeyName(result.getList(),mapper);
	}

	@Override
	public List<Map<String, Object>> getUserByCurrentDept(Map<String, Object> args) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deptCodes", args.get("deptCodes"));
		params.put("roleCodes", args.get("roleIds"));
		PageInfo<Map<String, Object>> result = userApi.findUserByDeptRoleRel(params);
		if (result == null || result.getList() == null) {
			return new ArrayList<Map<String, Object>>();
		}
		//设置机构信息
				List<Map<String,Object>> userCurrentInfoList = result.getList();
				if (userCurrentInfoList != null) {
					for (Map<String, Object> userCurrentInfo : userCurrentInfoList) {
						Map<String,Object> paramNest = new HashMap<>();
						paramNest.put("userNum", userCurrentInfo.get("userNum"));
						PageInfo<Map<String, Object>> userInfoIncludeDept = userApi.findUserDeptRelPage(paramNest,null);
						if (userInfoIncludeDept.getTotal() > 0) {
							userCurrentInfo.put("orgCode", userInfoIncludeDept.getList().get(0).get("orgCode"));
							userCurrentInfo.put("orgName", userInfoIncludeDept.getList().get(0).get("orgName"));
						}
					}
				}
		
		Map<String,String> mapper = new HashMap<>();
		mapper.put("deptCode", "deptCode");
		mapper.put("deptName", "deptName");
		mapper.put("orgCode", "instCode");
		mapper.put("orgName", "instName");
		mapper.put("roleCode", "roleId");
		mapper.put("userNum", "userId");
		mapper.put("realName", "userName");
		return this.mapperKeyName(result.getList(),mapper);
	}
	
	@Override
	public Map<String, Object> getCurrentInst(Map<String, Object> args) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgCode", args.get("instCode"));
		params.put("orgLevelCd", args.get("orgLevelCd"));
		Map<String, Object> instInfo = orgApi.getOrgMap(params);
		if (instInfo == null )
			return null;
		Map<String,Object> rt = new HashMap<>(); 
		rt.put("instCode", instInfo.get("orgCode"));
		rt.put("instName", instInfo.get("orgName"));
		rt.put("instParentCode", instInfo.get("parentOrgCode"));
		return rt;
	}

 	@Override
	public List<Map<String, Object>> getSuppertInst(Map<String, Object> args) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgCode", args.get("instCode"));
		params.put("orgLevelCd", args.get("orgLevelCd"));
		List<Map<String, Object>> listMap = orgApi.findParentOrgs(params);
		Map<String,String> mapper = new HashMap<>();
		mapper.put("orgCode", "instCode");
		mapper.put("orgName", "instName");
		mapper.put("parentOrgCode", "instParentCode");
		return this.mapperKeyName(listMap, mapper);
	}

	@Override
	public List<Map<String, Object>> getChildInst(Map<String, Object> args) {
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("orgCode", args.get("instCode"));
		params.put("orgLevelCd", args.get("orgLevelCd"));
		List<Map<String, Object>> listMap = orgApi.findChildrenOrgs(params);
		Map<String,String> mapper = new HashMap<>();
		mapper.put("orgCode", "instCode");
		mapper.put("orgName", "instName");
		mapper.put("parentOrgCode", "instParentCode");
		return this.mapperKeyName(listMap, mapper);
	}

	@Override
	public Map<String, Object> getCurrentDept(Map<String, Object> args) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deptCode", args.get("deptCode"));
		params.put("deptLevelCd", args.get("deptLevelCd"));
		
		Map<String, Object> deptInfo = depApi.getDeptMap(params);
		if (deptInfo == null )
			return null;
		Map<String,Object> rt = new HashMap<>(); 
		rt.put("deptCode", deptInfo.get("deptCode"));
		rt.put("deptName", deptInfo.get("deptName"));
		rt.put("deptParentCode", deptInfo.get("parentDeptCode"));
		return rt;
	}

	@Override
	public List<Map<String, Object>> getSuppertDept(Map<String, Object> args) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deptCode", args.get("deptCode"));
		params.put("deptLevelCd", args.get("deptLevelCd"));
		List<Map<String, Object>> depts = depApi.findParentDepts(params);
		Map<String,String> mapper = new HashMap<>();
		mapper.put("deptCode", "deptCode");
		mapper.put("deptName", "deptName");
		mapper.put("parentDeptCode", "deptParentCode");
		
		return mapperKeyName(depts,mapper);
	}

	
	
	@Override
	public List<Map<String, Object>> getChildDept(Map<String, Object> args) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("deptCode", args.get("deptCode"));
		params.put("deptLevelCd", args.get("deptLevelCd"));
		List<Map<String, Object>> depts = depApi.findChildrenDepts(params);
		Map<String,String> mapper = new HashMap<>();
		mapper.put("deptCode", "deptCode");
		mapper.put("deptName", "deptName");
		mapper.put("parentDeptCode", "deptParentCode");
		
		return mapperKeyName(depts,mapper);
	}

	private List<Map<String,Object>>  mapperKeyName(List<Map<String,Object>> source,Map<String,String> mapperInfo) {
		
		if (source == null) {
			return null;
		}
		List<Map<String,Object>> result = new ArrayList<>();
		for (Map<String, Object> map : source) {
			Map<String,Object> resultUnit = new HashMap<>();
			for (Map.Entry<String, String>  mapper: mapperInfo.entrySet()) {
				resultUnit.put(mapper.getValue(), map.get(mapper.getKey()));
			}
			result.add(resultUnit);
		}
		return result;
	}
	

}
