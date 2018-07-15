package com.jeedev.msdp.sys.event.api.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.sys.event.api.EventApi;
import com.jeedev.msdp.sys.event.service.ISysResEventService;
@Service("eventApi")
public class EventApiImpl implements EventApi {
	@Autowired
	private ISysResEventService sysResEventService;
	@Override
	public PageInfo<Map<String, Object>> findEventPage(Map<String, Object> params, PageParam pageParam) {
		return sysResEventService.findEventPage(params, pageParam);
	}

	@Override
	public Map<String, Object> getEventMap(Map<String, Object> params) {
		return sysResEventService.getEventMap(params);
	}

	@Override
	public Map<String, Object> saveEventTrans(Map<String, Object> params) {
		return sysResEventService.saveEvent(params);
	}

	@Override
	public void deleteEventTrans(Map<String, Object> params) {
		sysResEventService.deleteEvent(params);
	}

}
