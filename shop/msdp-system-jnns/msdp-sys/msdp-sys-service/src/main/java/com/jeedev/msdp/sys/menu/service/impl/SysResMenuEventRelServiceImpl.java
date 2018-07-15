package com.jeedev.msdp.sys.menu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.menu.dao.ISysResMenuEventRelDao;
import com.jeedev.msdp.sys.menu.entity.SysResMenuEventRelEntity;
import com.jeedev.msdp.sys.menu.service.ISysResMenuEventRelService;
import com.jeedev.msdp.sys.user.api.UserConstants;

@Service("sysResMenuEventRelService")
public class SysResMenuEventRelServiceImpl implements ISysResMenuEventRelService {
	@Autowired
	private ISysResMenuEventRelDao sysResMenuEventRelDao;

	@Override
	public PageInfo<Map<String, Object>> findSysResMenuEventRelPage(Map<String, Object> params, PageParam pageParam) {
		// 判断当前参数params是否为空，则为默认查询
		if (null == params) {
			params = new HashMap<String, Object>();
		}
		params.put("delInd", "0");
		
		List<Map<String,Object>> list = sysResMenuEventRelDao.findMenuEventRelList(params);
		
		return new PageInfo<>(list);
	}

	@Override
	public Map<String, Object> getSysResMenuEventRelMap(Map<String, Object> params) {
		String id = MapUtils.getString(params, UserConstants.ID);
		if (StringUtil.isEmpty(id)) {
			throw ExceptionFactory.getBizException("sys-usr-00008");
		}
		params.put("delInd", "0");
		// 默认调用分页查询方法。
		PageInfo<Map<String, Object>> UserPage = this.findSysResMenuEventRelPage(params, null);
		// 判断是否存在数据
		long total = UserPage.getTotal();
		if (0 < total) {
			// 获取查询结果列表
			@SuppressWarnings("rawtypes")
			List UserList = UserPage.getList();
			if (null != UserList && UserList.size() > 0) {
				return (Map<String, Object>) UserList.get(0);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> saveSysResMenuEventRel(Map<String, Object> params) {
		// 数据校验
		PageInfo<Map<String, Object>> existMenuEvents = findSysResMenuEventRelPage(params, null);
		if (existMenuEvents.getSize() > 0) {
			throw ExceptionFactory.getBizException("sys-menu-00002");
		}
		if (!params.containsKey("statusCd")) {
			params.put("statusCd", "1");
		}
		SysResMenuEventRelEntity entity = sysResMenuEventRelDao
				.save(new SysResMenuEventRelEntity().coverToEntity(params, null));
		params.put("id", entity.getId());
		return params;
	}

	@Override
	public void updateSysResMenuEventRel(Map<String, Object> params) {
		// update要先根据ID获取BO对象，然后在拷贝map里面的值
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResMenuEventRelEntity findOne = sysResMenuEventRelDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		// 数据校验
		SysResMenuEventRelEntity entity = findOne.coverToEntity(params, findOne);
		sysResMenuEventRelDao.update(entity);

	}

	@Override
	public void deleteSysResMenuEventRel(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResMenuEventRelEntity entity = sysResMenuEventRelDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); // TODO 逻辑删除标识
		sysResMenuEventRelDao.update(entity);

	}


}
