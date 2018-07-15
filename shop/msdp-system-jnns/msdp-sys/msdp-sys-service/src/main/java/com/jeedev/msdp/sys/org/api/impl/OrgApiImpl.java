package com.jeedev.msdp.sys.org.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.org.api.OrgApi;
import com.jeedev.msdp.sys.org.api.OrgApiConstants;
import com.jeedev.msdp.sys.org.service.ISysOrgDeptRelService;
import com.jeedev.msdp.sys.org.service.ISysOrgService;
import com.jeedev.msdp.sys.user.service.IUserOrgRelService;
import com.jeedev.msdp.trace.IProviderSign;

@Service("orgApi")
public class OrgApiImpl implements OrgApi,IProviderSign {
	/**
	 * 用户与机构关系服务
	 */
	@Autowired
	private IUserOrgRelService userOrgRelService;
	/**
	 * 机构与部门管理服务
	 */
	@Autowired
	private ISysOrgDeptRelService sysOrgDeptRelService;
	/**
	 * 机构服务类
	 */
	@Autowired
	private ISysOrgService sysOrgService;

	@Override
	public PageInfo<Map<String, Object>> findOrgPage(Map<String, Object> params,PageParam pageParam) {
		return sysOrgService.findOrgPage(params, pageParam);
	}
	
	@Override
	public PageInfo<Map<String, Object>> findCoreOrgPage(Map<String, Object> params,PageParam pageParam) {
		return sysOrgService.findCoreOrgPage(params, pageParam);
	}

	@Override
	public Map<String, Object> getOrgMap(Map<String, Object> params) {
		return sysOrgService.getOrgMap(params);
	}

	@Override
	public Map<String, Object> saveOrgTrans(Map<String, Object> params) { 
		//有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, OrgApiConstants.Id);
		if (id == null) {
			return sysOrgService.saveOrg(params);
		}
		sysOrgService.updateOrg(params);
		return params;
	}

	@Override
	public void deleteOrgTrans(Map<String, Object> params) {
		 if(checkOrgRel(params)){
			 //有关联数据，无法删除
			 throw ExceptionFactory.getBizException("sys-org-00001");
		 }
		 sysOrgService.deleteOrg(params);
	}
	
	/**
	 * 
	 * @方法名称 checkOrgRel
	 * @功能描述 <pre>判断是否有关联存在</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月22日 下午3:02:45
	 * @param orgCode
	 * @return true 有 false 没有
	 */
	public boolean checkOrgRel(Map<String, Object> params){
		 //如果所属机构有部门则不让删除数据
		String orgCode=null;
		boolean result=false;
		if(params!=null){
			 if(params.containsKey(OrgApiConstants.Id)){
				 //获取机构编码
				 String id=MapUtils.getString(params, OrgApiConstants.Id);
				 Map<String,Object> queryMap=new HashMap<>();
				 queryMap.put(OrgApiConstants.Id, id);
				 Map<String,Object> org=sysOrgService.getOrgMap(queryMap);
				 if(org!=null)
					 orgCode=MapUtils.getString(org, OrgApiConstants.ORG_CODE);
			 }
			 if(params.containsKey(OrgApiConstants.ORG_CODE)){
				 orgCode=MapUtils.getString(params, OrgApiConstants.ORG_CODE);
			 }
			Map<String,Object> parmMap=new HashMap<>();
			//是否有关联机构
			parmMap.put(OrgApiConstants.ORG_CODE, orgCode);
			PageInfo<Map<String, Object>> pl =sysOrgDeptRelService.findOrgDeptRelPage(parmMap,null);
			if(pl.getTotal()>0){
				return true;
			}
			//是否有关联用户 
			PageInfo<Map<String, Object>> plu =userOrgRelService.findUserOrgRelPage(parmMap,null);
			if(plu.getTotal()>0){
				return true;
			}
		}
		return result;
	}

	@Override
	public PageInfo<Map<String, Object>> findOrgDeptRelPage(
			Map<String, Object> params, PageParam pageParam) {
		return sysOrgDeptRelService.findOrgDeptRelPage(params, pageParam);
	}

	@Override
	public Map<String, Object> getOrgDeptRelMap(Map<String, Object> params) {
		return sysOrgDeptRelService.getOrgDeptRelMap(params);
	}

	@Override
	public Map<String, Object> saveOrgDeptRelTrans(Map<String, Object> params) {
		return sysOrgDeptRelService.saveOrgDeptRel(params);
	}

	@Override
	public void updateOrgDeptRelTrans(Map<String, Object> params) {
		sysOrgDeptRelService.updateOrgDeptRel(params);
	}

	@Override
	public void deleteOrgDeptRelTrans(Map<String, Object> params) {
		sysOrgDeptRelService.deleteOrgDeptRel(params);
	}
	@Override
	public Map<String, Object> getOrgNameByOrgCode(Map<String, Object> params) {
		Map<String,Object> orgMap = sysOrgService.getOrgMap(params);
		Map<String,Object> orgNameMap = new HashMap<String,Object>();
		orgNameMap.put(OrgApiConstants.ORG_NAME, orgMap.get(OrgApiConstants.ORG_NAME));
		return orgNameMap;
	}


	@Override
	public List<Map<String, Object>> findParentOrgs(Map<String, Object> params) {
		return sysOrgService.findParentOrgs(params);
	}

	@Override
	public List<Map<String, Object>> findChildrenOrgs(Map<String, Object> params) {
		return sysOrgService.findChildrenOrgs(params);
	}


}
