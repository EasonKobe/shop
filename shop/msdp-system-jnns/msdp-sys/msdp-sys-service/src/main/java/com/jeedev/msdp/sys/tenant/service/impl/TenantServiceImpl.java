package com.jeedev.msdp.sys.tenant.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.tenant.api.TenantConstants;
import com.jeedev.msdp.sys.tenant.dao.ISysTenantDao;
import com.jeedev.msdp.sys.tenant.entity.SysTenantEntity;
import com.jeedev.msdp.sys.tenant.service.ITenantService;
import com.jeedev.msdp.sys.user.api.UserConstants;

@Service("tenantService")
public class TenantServiceImpl implements ITenantService {
	@Autowired
	private ISysTenantDao sysTenantDao;
	
	@Override
	public PageInfo<Map<String, Object>> findTenantPage(Map<String, Object> params) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put(UserConstants.DEL_IND, "0");
		return new PageInfo<>(sysTenantDao.findTenantList(params));
	}
	
	
	@Override
	public Map<String, Object> getTenantMap(Map<String, Object> params) {
		return sysTenantDao.getTenantMap(params);
	}
	
	
	@Override
	public Map<String, Object> saveTenant(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}

		SysTenantEntity entity=new SysTenantEntity().coverToEntity(params, null);
		SysTenantEntity tenantEntity = sysTenantDao.save(entity);
		params.put("id", tenantEntity.getId());
		return params;
	}


	@Override
	public void updateTenant(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, TenantConstants.ID); 
		if (!params.containsKey(TenantConstants.ID)) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysTenantEntity findEntity = sysTenantDao.findOne(id);
		if (findEntity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		findEntity.coverToEntity(params, findEntity); 
		sysTenantDao.update(findEntity);
		
	}


	@Override
	public void deleteTenant(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysTenantEntity entity = sysTenantDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //TODO 逻辑删除标识
		sysTenantDao.update(entity);
		
	}
}
