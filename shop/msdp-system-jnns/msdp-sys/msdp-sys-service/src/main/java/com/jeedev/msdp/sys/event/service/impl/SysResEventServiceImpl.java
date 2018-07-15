package com.jeedev.msdp.sys.event.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.event.api.EventConstants;
import com.jeedev.msdp.sys.event.dao.ISysResEventDao;
import com.jeedev.msdp.sys.event.entity.SysResEventEntity;
import com.jeedev.msdp.sys.event.service.ISysResEventService;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.trace.constants.LoginUserConstants;
import com.jeedev.msdp.trace.utils.HeadUtil;
import com.jeedev.msdp.utlis.StringUtil;

@Service("sysResEventService")
public class SysResEventServiceImpl implements ISysResEventService {
	@Autowired
	private ISysResEventDao sysResEventDao;
	/**
	 * 编码生成服务
	 */
	@Autowired
	private IEncodeService encodeService;
	
	@Override
	public PageInfo<Map<String, Object>> findEventPage(Map<String, Object> params, PageParam pageParam) {
		// 判断当前参数params是否为空，则为默认查询
		if (null == params) {
			params = new HashMap<String, Object>();
		}
		params.put(EventConstants.DEL_IND, "0");
		return new PageInfo<>(sysResEventDao.findEventList(params));
	}

	@Override
	public Map<String, Object> getEventMap(Map<String, Object> params) {
		String eventCode = MapUtils.getString(params, EventConstants.EVENT_CODE);
		String id = MapUtils.getString(params, EventConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(EventConstants.EVENT_CODE);
		}
		if (!StringUtil.isEmpty(eventCode)) {
			params.put(EventConstants.DEL_IND, "0");
		}
		// 默认调用分页查询方法。
		PageInfo<Map<String, Object>> eventPage = this.findEventPage(params, null);
		// 判断是否存在数据
		long total = eventPage.getTotal();
		if (0 < total) {
			// 获取查询结果列表

			List eventList = eventPage.getList();
			if (null != eventList && eventList.size() > 0) {
				return (Map<String, Object>) eventList.get(0);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> saveEvent(Map<String, Object> params) {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		
		if(params.containsKey("id")) {
			updateEvent(params);
			return params;
		}
		
		// 默认调用分页查询方法。
		Map<String,Object> queryEvent = new HashMap<String,Object>();
		queryEvent.put("url", params.get("url"));
		PageInfo<Map<String, Object>> existEventPage = this.findEventPage(queryEvent, null);
		if(existEventPage.getSize()>0) {
			throw ExceptionFactory.getBizException("sys-event-00001");
		}
		
		if (!params.containsKey("statusCd")) {
			params.put("statusCd", "1");
		}
		if (!params.containsKey("clntendId")) {
			params.put("clntendId", HeadUtil.getCurUser().get(LoginUserConstants.LOGIN_USER_CLNTEND_ID));
		}
		String eventCode = MapUtils.getString(params, "eventCode");
		if(com.jeedev.msdp.utlis.StringUtil.isBlank(eventCode)) {
			try {
				eventCode = encodeService.buildEncode("10094", "systemAuto");
			} catch (Exception e) {
				throw ExceptionFactory.getBizException("sys-00006");
			}
			params.put("eventCode", eventCode);
		}
		SysResEventEntity entity = sysResEventDao.save(new SysResEventEntity().coverToEntity(params, null));

		Map<String, Object> result = new HashMap<>();
		result.put(MenuConstants.ID, entity.getId());
		return result;
	}

	@Override
	public void updateEvent(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, MenuConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResEventEntity findEntity = sysResEventDao.findOne(id);
		if (findEntity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysResEventEntity entity = findEntity.coverToEntity(params, findEntity);
		sysResEventDao.update(entity);

	}

	@Override
	public void deleteEvent(Map<String, Object> params) {
		// 删除和update一样，更新的是delInd
		Integer id = MapUtils.getInteger(params, MenuConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResEventEntity entity = sysResEventDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); // 是否删除
		sysResEventDao.update(entity);

	}

	@Override
	public PageInfo<Map<String, Object>> findEventAndDataauthPage(Map<String, Object> params,
			PageParam pageParam) {
		// TODO Auto-generated method stub
		return null;
	}

}
