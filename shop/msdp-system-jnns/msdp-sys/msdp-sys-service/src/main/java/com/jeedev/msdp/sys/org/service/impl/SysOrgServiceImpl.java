package com.jeedev.msdp.sys.org.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.org.api.OrgApiConstants;
import com.jeedev.msdp.sys.org.dao.ISysOrganizationDao;
import com.jeedev.msdp.sys.org.entity.SysOrganizationEntity;
import com.jeedev.msdp.sys.org.service.ISysOrgService;
import com.jeedev.msdp.sys.utils.RecursionHelper;
/**
 * @类名称 SysOrgServiceImpl.java
 * @类描述 <pre>机构服务实现类</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月18日 上午9:55:40
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
@Service("sysOrgService")
public class SysOrgServiceImpl  implements ISysOrgService
{
	/**
	 * 机构dao
	 */
	@Autowired
	private ISysOrganizationDao sysOrganizationDao;
	/**
	 *
	 * @方法名称 findOrgPage
	 * @功能描述 <pre>分页获取机构与部门关系的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findOrgPage(Map<String, Object> params,PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(sysOrganizationDao.findOrgList(params));
	}
	/**
	 *
	 * @方法名称 getOrgMap
	 * @功能描述 <pre>根据条件获取单个机构与部门关系数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getOrgMap(Map<String, Object> params) {
		String OrgCode = MapUtils.getString(params, OrgApiConstants.ORG_CODE);
		String id = MapUtils.getString(params, OrgApiConstants.Id);
		if (!StringUtil.isEmpty(id)) {
			params.remove(OrgApiConstants.ORG_CODE);
		}
		if (!StringUtil.isEmpty(OrgCode)) {
			params.put(OrgApiConstants.DEL_IND, "0");
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> OrgPage = this.findOrgPage(params, null);
		//判断是否存在数据
		long total = OrgPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List<Map<String, Object>> OrgList = OrgPage.getList();
			if(null!= OrgList && OrgList.size()>0 ){
				return (Map<String, Object>) OrgList.get(0);
			}
		}
		return null;
	}
	/**
	 *
	 * @方法名称 saveOrg
	 * @功能描述 <pre>保存机构与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveOrg(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		//状态为空默认设置为生效
		if(params.get("statusCd")==null){
			params.put("statusCd", "1");
		}

		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put(OrgApiConstants.DEL_IND,"0");
		queryParams.put(OrgApiConstants.ORG_CODE,params.get(OrgApiConstants.ORG_CODE));
        List<Map<String, Object>> orgList = sysOrganizationDao.findOrgList(queryParams);
        if (CollectionUtils.isNotEmpty(orgList)){
			throw ExceptionFactory.getBizException("sys-org-00003");
		}

        SysOrganizationEntity OrgEntity = sysOrganizationDao.save(new SysOrganizationEntity().coverToEntity(params, null));
		params.put(OrgApiConstants.Id, OrgEntity.getId());
		return params;
	}
	/**
	 *
	 * @方法名称 updateOrg
	 * @功能描述 <pre>更新机构与部门关系数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateOrg(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值
		Integer id = MapUtils.getInteger(params, OrgApiConstants.Id);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysOrganizationEntity findOne = sysOrganizationDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysOrganizationEntity entity = findOne.coverToEntity(params, findOne);
		sysOrganizationDao.update(entity);
	}
	/**
	 *
	 * @方法名称 deleteOrg
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:07:35
	 * @param params
	 */
	@Override
	public void deleteOrg(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, OrgApiConstants.Id);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysOrganizationEntity entity = sysOrganizationDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //TODO 逻辑删除标识
		sysOrganizationDao.update(entity);

	}
	@Override
	public List<Map<String, Object>> findChildrenOrgs(Map<String, Object> params) {
		List<Map<String, Object>> orgs = sysOrganizationDao.findOrgList(params);
		if (orgs == null || orgs.size() != 1 ) {
			throw ExceptionFactory.getBizException();

		}

		Map<String,Object> reqParam = new HashMap<>();
		reqParam.put(OrgApiConstants.DEL_IND, "0");
		List<Map<String,Object>>  list = sysOrganizationDao.findOrgList(reqParam);
		List<Map<String, Object>> childrenOrgs = new ArrayList<>();
		Map<String,List<Map<String,Object>>>  deptsMap = RecursionHelper.createMapByListForCatalog(list, OrgApiConstants.PARENT_ORG_CODE);
		RecursionHelper.findAllChildren(deptsMap, orgs.get(0), OrgApiConstants.ORG_CODE,childrenOrgs);
		return childrenOrgs;
	}


	@Override
	public List<Map<String, Object>> findParentOrgs(Map<String, Object> params) {
		List<Map<String, Object>> orgs = sysOrganizationDao.findOrgList(params);
		if (orgs == null || orgs.size() != 1 ) {
			throw ExceptionFactory.getBizException();

		}
		Map<String,Object> reqParam = new HashMap<>();
		reqParam.put(OrgApiConstants.DEL_IND, "0");
		List<Map<String,Object>>  list = sysOrganizationDao.findOrgList(reqParam);
		List<Map<String, Object>> parentsOrgs = new ArrayList<>();

		Map<String,Map<String,Object>>  orgsMap = RecursionHelper.createMapByListForIndex(list, OrgApiConstants.ORG_CODE);
		RecursionHelper.findAllParent(orgsMap, orgs.get(0), OrgApiConstants.PARENT_ORG_CODE,parentsOrgs);
		return parentsOrgs;
	}


	/**
	 *
	 * @方法名称 findCoreOrgPage
	 * @功能描述 <pre>分页获取机构与部门关系的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findCoreOrgPage(Map<String, Object> params,PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		return new PageInfo<>(sysOrganizationDao.findCoreOrgList(params));
	}



}