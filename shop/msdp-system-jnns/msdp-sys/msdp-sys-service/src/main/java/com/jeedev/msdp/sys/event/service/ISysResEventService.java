package com.jeedev.msdp.sys.event.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 事件资源服务类
 * @类名称 ISysResEventService.java
 * @类描述 <pre></pre>
 * @作者 chenjc chenjc@tansun.com.cn
 * @创建时间 2017年11月7日 下午4:07:47
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjc 	2017年11月7日             
 *     ----------------------------------------------
 * </pre>
 */
public interface ISysResEventService {
	/**
	 * 分页查询事件列表
	 * @方法名称 findEventPage
	 * @功能描述 <pre>分页查询事件列表</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:08:05
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findEventPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 分页查询事件列表
	 * @方法名称 findEventPage
	 * @功能描述 <pre>分页查询事件列表</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:08:05
	 * @param params
	 *  * menuCode 菜单编号
	 * {list} menuCodes 菜单编号集合
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findEventAndDataauthPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 
	 * @方法名称 getEventMap
	 * @功能描述 <pre>根据事件过滤条件查询事件资源，如存在id则只用id进行查询</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:08:45
	 * @param params
	 * @return
	 */
	public Map<String, Object> getEventMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveEvent
	 * @功能描述 <pre>保存事件资源信息</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:09:31
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveEvent(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateEvent
	 * @功能描述 <pre>修改事件资源信息</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:09:48
	 * @param params
	 */
	public void updateEvent(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteEvent
	 * @功能描述 <pre>删除事件资源信息</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:10:01
	 * @param params
	 */
	public void deleteEvent(Map<String, Object> params);
	
}
