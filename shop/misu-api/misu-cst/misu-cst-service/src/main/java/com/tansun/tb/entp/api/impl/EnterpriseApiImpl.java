package com.tansun.tb.entp.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.captcha.AuthUtils;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.tenant.api.TenantApi;
import com.jeedev.msdp.sys.tenant.api.TenantConstants;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseApi;
import com.tansun.tb.entp.api.EnterpriseApiConstants;
import com.tansun.tb.entp.api.EnterpriseRoleApi;
import com.tansun.tb.entp.api.EnterpriseRoleApiConstants;
import com.tansun.tb.entp.service.IEnterpriseService;

@Service("enterpriseApi")
public class EnterpriseApiImpl implements EnterpriseApi {

	@Autowired
	private IEnterpriseService enterpriseService;

	@Autowired
	private TenantApi tenantApi;
	/**
	 * 序号生成器服务
	 */
	@Autowired
	private IEncodeService encodeService;

	@Autowired
	private UserApi userApi;

	@Autowired
	private EnterpriseRoleApi enterpriseRoleApi;
	
	
	
	@Override
	public PageInfo<Map<String, Object>> findEnterprisePage(Map<String, Object> params, PageParam pageParam) {
		PageInfo<Map<String, Object>> page = enterpriseService.findEnterprisePage(params);
		return page;
	}

	/**
	 * @param params
	 * @return
	 * @方法名称 findParentEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询企业和上级企业列表
	 *       </pre>
	 * 
	 * @作者 赵健 zhaojianxm@tansun.com.cn
	 * @创建时间 2018年01月29日 AM 09:59
	 */
	@Override
	public List<Map<String, Object>> findParentEnterpriseList(Map<String, Object> params) {
		Map<String, Object> enterpriseMap = getEnterpriseMap(params);
		String tenantId = MapUtil.getString(enterpriseMap, EnterpriseApiConstants.TENANT_ID);

		Map<String, Object> queryParams = new HashMap<>();
		PageInfo<Map<String, Object>> page;

		// 查本级和上级
		String[] split = tenantId.split("_");
		StringBuilder sb = new StringBuilder();
		List<String> tenantIds = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			if (i > 0) {
				sb.append("_");
			}
			sb.append(split[i]);
			tenantIds.add(sb.toString());
		}
		queryParams.put("tenantIds", tenantIds);
		page = enterpriseService.findEnterprisePage(queryParams);

		return page.getList();
	}
	
	/**
	 * @param params
	 * @return
	 * @方法名称 findOnlyParentEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询上级企业列表
	 *       </pre>
	 * 
	 * @创建时间 2018年01月29日 AM 09:59
	 */
	@Override
	public List<Map<String, Object>> findOnlyParentEnterpriseList(Map<String, Object> params) {
		Map<String, Object> enterpriseMap = getEnterpriseMap(params);
		String tenantId = MapUtil.getString(enterpriseMap, EnterpriseApiConstants.TENANT_ID);

		Map<String, Object> queryParams = new HashMap<>();
		PageInfo<Map<String, Object>> page;

		// 查本级和上级
		String[] split = tenantId.split("_");
		StringBuilder sb = new StringBuilder();
		List<String> tenantIds = new ArrayList<>();
		for (int i = 0; i < split.length; i++) {
			if (i > 0) {
				sb.append("_");
			}
			sb.append(split[i]);
			tenantIds.add(sb.toString());
		}
		tenantIds.remove(tenantId);//删除自己
		queryParams.put("tenantIds", tenantIds);
		page = enterpriseService.findEnterprisePage(queryParams);

		return page.getList();
	}

	/**
	 * @param params
	 * @return
	 * @方法名称 findChildEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询企业和下级企业列表
	 *       </pre>
	 * 
	 * @作者 赵健 zhaojianxm@tansun.com.cn
	 * @创建时间 2018年01月29日 AM 09:59
	 */
	@Override
	public List<Map<String, Object>> findChildEnterpriseList(Map<String, Object> params) {
		Map<String, Object> enterpriseMap = getEnterpriseMap(params);
		String tenantId = MapUtil.getString(enterpriseMap, EnterpriseApiConstants.TENANT_ID);

		Map<String, Object> queryParams = new HashMap<>();
		PageInfo<Map<String, Object>> page;

		// 查本级和下级
		queryParams.put("childTenantId", tenantId);
//		String entpCode = MapUtil.getString(params, "entpCode");
//		if (null != entpCode && !entpCode.equals("null")) {//传入机构号
//			queryParams.put("entpCode", entpCode);
//		}
		page = enterpriseService.findEnterprisePage(queryParams);

		return page.getList();
	}
	
	/**
	 * @param params
	 * @return
	 * @方法名称 findOnlyChildEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询下级企业列表
	 *       </pre>
	 * 
	 * @创建时间 2018年01月29日 AM 09:59
	 */
	@Override
	public List<Map<String, Object>> findOnlyChildEnterpriseList(Map<String, Object> params) {
		Map<String, Object> enterpriseMap = getEnterpriseMap(params);
		String tenantId = MapUtil.getString(enterpriseMap, EnterpriseApiConstants.TENANT_ID);

		Map<String, Object> queryParams = new HashMap<>();
		PageInfo<Map<String, Object>> page;

		// 只查下级
		queryParams.put("childTenantId", tenantId);
//		queryParams.put("onlyChild", "1");//过滤自己
		page = enterpriseService.findEnterprisePage(queryParams);
		page.getList().remove(enterpriseMap);
		return page.getList();
	}

	@Override
	public Map<String, Object> getEnterpriseMap(Map<String, Object> params) {
		Map<String, Object> enterpriseMap = enterpriseService.getEnterpriseMap(params);

		return enterpriseMap;
	}

	@Override
	public Map<String, Object> saveEnterpriseTrans(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, EnterpriseApiConstants.ID);

		String entpName = MapUtils.getString(params, EnterpriseApiConstants.ENTP_NM);
		String parentEntpCode = MapUtils.getString(params, EnterpriseApiConstants.PARENT_ENTP_CODE);

		String wthrCptplHostEntp = MapUtils.getString(params, EnterpriseApiConstants.WTHR_CPTPL_HOST_ENTP);
		String cptplModel = MapUtils.getString(params, EnterpriseApiConstants.CPTPL_MODEL);

		// 如果是资金池主办单位，必须指定资金池模式
		if (StringUtil.equals(wthrCptplHostEntp, "1") && StringUtil.isBlank(cptplModel)) {
			throw ExceptionFactory.getBizException("entp-mg-00005", "资金池模式");
		}

		// 新增企业
		if (null == id) {
			if (StringUtil.isBlank(entpName)) {
				throw ExceptionFactory.getBizException("entp-mg-00005", "企业名称");
			}
			
			String entpCode = MapUtil.getString(params, "entpCode");
			if(StringUtil.isBlank(entpCode)){
				// 自动生成村镇编号和机构级别
				try {
					entpCode = encodeService.buildEncode("10200", null);
				} catch (Exception e) {
					throw ExceptionFactory.getBizException("entp-mg-00006");
				}

				if (StringUtil.isBlank(entpCode)) {
					throw ExceptionFactory.getBizException("entp-mg-00006");
				}
			}

			// params.put(EnterpriseApiConstants.ENTP_ORG_CODE, entpCode);
			params.put(EnterpriseApiConstants.ENTP_CODE, entpCode);

			int lvl = 1;

			Map<String, Object> parentEnterpriseMap = null;

			if (StringUtil.isNotBlank(parentEntpCode)) {
				Map<String, Object> map = new HashMap<>();
				map.put(EnterpriseApiConstants.ID, parentEntpCode);

				parentEnterpriseMap = getEnterpriseMap(map);
				Integer level = MapUtil.getInteger(parentEnterpriseMap, EnterpriseApiConstants.ENTP_ORG_LVL_CD);
				if (level != null) {
					lvl = level + 1;
				}
			}

			if (lvl > 5) {
				throw ExceptionFactory.getBizException("entp-mg-00007");
			}

			params.put(EnterpriseApiConstants.ENTP_ORG_LVL_CD, lvl);

			// 1级机构需要租户ID，其他使用上级的租户ID+自己的编号
			if (lvl == 1) {
				String salt = AuthUtils.getSalt();
				String passWord = AuthUtils.encryptPassword(entpCode, "111111", salt);

				// 调用保存租户的api
				Map<String, Object> tentantMap = new HashMap<>();
				tentantMap.put("tenantName", entpName);
				tentantMap.put("managerName", entpCode);
				tentantMap.put("loginName", entpName);
				tentantMap.put("telephone", MapUtil.getString(params, EnterpriseApiConstants.ENTP_CTCPSN_TEL));
				tentantMap.put("password", passWord);
				tentantMap.put("salt", salt);
				tentantMap.put("clntendId", MapUtil.getString(params, "clntendId"));
				tentantMap.put("withoutUser", true);

				Map<String, Object> result = tenantApi.saveTenantMap(tentantMap);
				String tenantId = MapUtil.getString(result, TenantConstants.TENANT_ID);

				// 保存租户ID（企业关联租户）
				params.put(EnterpriseApiConstants.TENANT_ID, tenantId);
			} else {
				String tenantId = MapUtil.getString(parentEnterpriseMap, EnterpriseApiConstants.TENANT_ID);
				params.put(EnterpriseApiConstants.TENANT_ID, tenantId + "_" + entpCode);
			}

			return enterpriseService.saveEnterprise(params);
		} else {
			// 修改企业
			// params.remove(EnterpriseApiConstants.ENTP_ORG_CODE);
			params.remove(EnterpriseApiConstants.ENTP_CODE);
			params.remove(EnterpriseApiConstants.ENTP_ORG_LVL_CD);
			params.remove(EnterpriseApiConstants.TENANT_ID);
			enterpriseService.updateEnterprise(params);
		}
		return null;
	}

	@Override
	public void deleteEnterpriseTrans(Map<String, Object> params) {
		Map<String, Object> enterpriseMap = getEnterpriseMap(params);
		List<Map<String, Object>> childEnterpriseList = findChildEnterpriseList(params);

		// 存在子机构无法删除
		if (CollectionUtil.isNotEmpty(childEnterpriseList) && childEnterpriseList.size() > 1) {
			throw ExceptionFactory.getBizException("entp-mg-00010");
		}

		// 存在用户无法删除
		Map<String, Object> userParams = new HashMap<>();
		userParams.put(UserConstants.TENANT_ID, enterpriseMap.get(UserConstants.TENANT_ID));
		PageInfo<Map<String, Object>> userPage = userApi.findUserPage(userParams, null);
		if (userPage.getList().size() > 0) {
			throw ExceptionFactory.getBizException("entp-mg-00011");
		}

		// 删除角色关联
		String entpCode = MapUtil.getString(enterpriseMap, EnterpriseRoleApiConstants.ENTP_CODE);
		if (StringUtil.isNotBlank(entpCode)) {
			userParams.clear();
			userParams.put(EnterpriseRoleApiConstants.ENTP_CODE, entpCode);
			PageInfo<Map<String, Object>> enterpriseRolePage = enterpriseRoleApi.findEnterpriseRolePage(userParams,
					null);
			for (Map<String, Object> map : enterpriseRolePage.getList()) {
				enterpriseRoleApi.deleteEnterpriseRoleTrans(map);
			}
		}

		// 删除租户
		userParams.clear();
		userParams.put(TenantConstants.TENANT_ID, enterpriseMap.get(EnterpriseApiConstants.TENANT_ID));
		tenantApi.deleteTenant(userParams);

		enterpriseService.deleteEnterprise(enterpriseMap);

	}

	@Override
	public Map<String, Object> findEntpCodeByLoginName(Map<String, Object> params) {
		return this.enterpriseService.findEntpCodeByLoginName(params);
	}

	/**
	 * @方法名称 findHostEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       根据客户编号或租户id查询主办机构
	 *       </pre>
	 * 
	 * @作者 linjunfa
	 * @创建时间 2018 4月4日 下午6:58:04
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> findHostEnterpriseList(Map<String, Object> params) {
		List<Map<String, Object>> parentList = this.findParentEnterpriseList(params);
		Map<String, Object> hostEntpMap = new HashMap<String, Object>();
		if (parentList != null && parentList.size() > 0) {
			for (Map<String, Object> entpMap : parentList) {
				if (MapUtil.getInteger(entpMap, "wthrCptplHostEntp") != null
						&& MapUtil.getInteger(entpMap, "wthrCptplHostEntp") == 1) {// 是主办单位
					hostEntpMap = entpMap;
					break;
				}
			}
		}
		return hostEntpMap;
	}

	/**
	 * @方法名称 findHostEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       根据客户编号或租户id集团客户列表
	 *       </pre>
	 * 
	 * @作者 linjunfa
	 * @创建时间 2018 4月4日 下午6:58:04
	 * @param params
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findGroupEnterpriseList(Map<String, Object> entpCode, Map<String, Object> params,
			PageParam pageParam) {
		Map<String, Object> hostEntpMap = this.findHostEnterpriseList(entpCode);
		// 主办单位的租户id作为查询条件，like查询所有相关的客户信息
		params.put("childTenantId", hostEntpMap.get("tenantId"));
		PageInfo<Map<String, Object>> page = enterpriseService.findEnterprisePage(params);
		return page.getList();
	}


}
