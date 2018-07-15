package com.jeedev.msdp.sys.event.api;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 EventApi.java
 * @类描述 <pre>事件资源对外接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 上午10:19:18
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月17日             
 *     ----------------------------------------------
 * </pre>
 */
public interface EventApi {
	/**
	 * @方法名称 findEventPage
	 * @功能描述 <pre>分页查询事件资源数据</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月15日 下午6:45:52
	 * @param params
	 * eventCode 事件编码
	 * url 事件URL
	 * eventName 事件名称
	 * statusCd 状态：无效0有效1
	 * clntendId 客户端编号
	 * @param pageParam
	 * @return
	 * id 物理主键ID
	 * eventCode 事件编码
	 * url 事件URL
	 * eventName 事件名称
	 * statusCd 状态：无效0有效1
	 * clntendId 客户端编号
	 */
	public PageInfo<Map<String, Object>> findEventPage(Map<String, Object> params, PageParam pageParam);
	/**
	 * @方法名称 getEventMap
	 * @功能描述 <pre>获取事件资源详情</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月15日 下午6:45:59
	 * @param params
	 * id 物理主键ID
	 * eventCode 事件编码
	 * @return
	 * id 物理主键ID
	 * eventCode 事件编码
	 * url 事件URL
	 * eventName 事件名称
	 * statusCd 状态：无效0有效1
	 * clntendId 客户端编号
	 */
	public Map<String, Object> getEventMap(Map<String, Object> params);
	/**
	 * @方法名称 saveEventTrans
	 * @功能描述 <pre>新增&变更事件资源信息</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月15日 下午6:46:18
	 * @param params
	 * id 物理主键ID
	 * eventCode 事件编码
	 * url 事件URL
	 * eventName 事件名称
	 * statusCd 状态：无效0有效1
	 * clntendId 客户端编号
	 * @return
	 * id 物理主键ID
	 * eventCode 事件编码
	 * url 事件URL
	 * eventName 事件名称
	 * statusCd 状态：无效0有效1
	 * clntendId 客户端编号
	 */
	public Map<String, Object> saveEventTrans(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 deleteEventTrans
	 * @功能描述 <pre>删除事件资源信息</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月15日 下午6:46:28
	 * @param params
	 * id 物理主键ID
	 */
	public void deleteEventTrans(Map<String, Object> params);
}
